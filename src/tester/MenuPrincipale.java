package tester;

import obj.*;
import utility.Materie;

import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * <h1>Registro Scolastico – Console Edition</h1>
 *
 * Classe <b>MenuPrincipale</b>: entry‑point dell'applicazione.  Fornisce un menù testuale che
 * orchestri <i>tutte</i> le classi fornite (Calendario, Classe_Scolastica, Studente, Presenza,
 * Voti, Nota, Compito, Materie) così da simulare un registro digitale minimale.
 * <p>
 * <b>Gestione bug Calendario</b>: il costruttore di {@code obj.Calendario} può lanciare
 * {@link DateTimeException} se la durata provoca l'esistenza di date inesistenti (p.es.
 * «SEPTEMBER 31»).  Per evitare crash, il calendario viene costruito in un ciclo che riduce la
 * durata finché la creazione va a buon fine.
 */
public class MenuPrincipale {

    /* ============================ CAMPI STATICI ============================ */
    private static final Scanner SC = new Scanner(System.in);

    private static Calendario calendario;
    private static Classe_Scolastica classe;

    /* ====================================================================== */
    public static void main(String[] args) {
        inizializzaRegistro();
        mainLoop();
        System.out.println("\nGrazie per aver utilizzato il registro. Arrivederci!");
    }

    /* ====================== INIZIALIZZAZIONE REGISTRO ====================== */
    private static void inizializzaRegistro() {
        System.out.println("==============================");
        System.out.println("   CONFIGURAZIONE REGISTRO   ");
        System.out.println("==============================\n");

        /* ---- calendario scolastico ---- */
        System.out.println(">>> CALENDARIO DELL'ANNO SCOLASTICO");
        int anno;
        while (true) {
            System.out.print("Anno di inizio (es. 2025): ");
            try {
                anno = Integer.parseInt(SC.nextLine());
                if (anno >= 1900) break;
            } catch (NumberFormatException ignore) {}
            System.out.println("Valore non valido. Riprova.");
        }

        int mese = 9; // settembre
        int giorno = 1;
        int durata = 300; // valore iniziale; verrà ridotto se necessario
        while (true) {
            try {
                calendario = new Calendario(anno, mese, giorno, durata);
                break;
            } catch (DateTimeException ex) {
                durata--; // correggi
                if (durata < 1) throw ex; // irrimediabile
            }
        }

        /* ---- dati classe ---- */
        System.out.println("\n>>> DATI DELLA CLASSE");
        System.out.print("Sezione (es. A): ");
        String sezione = SC.nextLine().trim().toUpperCase();

        int annoCorso;
        while (true) {
            System.out.print("Anno di corso (1‑5): ");
            try {
                annoCorso = Integer.parseInt(SC.nextLine());
                if (annoCorso >= 1 && annoCorso <= 5) break;
            } catch (NumberFormatException ignore) {}
            System.out.println("Valore non valido.");
        }

        System.out.print("Indirizzo (es. Informatica): ");
        String indirizzo = SC.nextLine();

        int maxStud;
        while (true) {
            System.out.print("Numero massimo studenti: ");
            try {
                maxStud = Integer.parseInt(SC.nextLine());
                if (maxStud > 0) break;
            } catch (NumberFormatException ignore) {}
            System.out.println("Valore non valido.");
        }

        classe = new Classe_Scolastica(sezione, annoCorso, indirizzo, maxStud, calendario);
        System.out.println("\nRegistro creato con calendario di " + durata + " giorni.\n");
    }

    /* ============================= MAIN LOOP ============================== */
    private static void mainLoop() {
        boolean attivo = true;
        while (attivo) {
            mostraMenu();
            System.out.print("Scelta: ");
            String sc = SC.nextLine();
            switch (sc) {
                case "1" -> inserisciStudente();
                case "2" -> registraPresenza();
                case "3" -> inserisciVoto();
                case "4" -> inserisciNota();
                case "5" -> inserisciCompito();
                case "6" -> stampaReportStudente();
                case "7" -> stampaReportClasse();
                case "8" -> calendario.stampaCalendario();
                case "9" -> attivo = false;
                default   -> System.out.println("Opzione non valida.");
            }
            System.out.println();
        }
    }

    private static void mostraMenu() {
        System.out.println("================ MENU ================");
        System.out.println("1. Inserisci nuovo studente");
        System.out.println("2. Registra presenza / assenza");
        System.out.println("3. Inserisci voto");
        System.out.println("4. Inserisci nota disciplinare / merito");
        System.out.println("5. Inserisci nuovo compito");
        System.out.println("6. Stampa report singolo studente");
        System.out.println("7. Stampa report di classe");
        System.out.println("8. Stampa calendario scolastico");
        System.out.println("9. Esci");
        System.out.println("======================================");
    }

    /* =========================== OPERAZIONI ============================ */

    private static void inserisciStudente() {
        Studente[] arr = classe.getStudenti();
        int idx = firstFree(arr);
        if (idx == -1) { System.out.println("Classe piena."); return; }

        System.out.print("Nome: ");
        String nome = SC.nextLine();
        System.out.print("Cognome: ");
        String cognome = SC.nextLine();
        LocalDate nascita = readDate("Data di nascita (aaaa‑mm‑gg): ");

        Studente s = new Studente(nome, cognome, nascita);
        // completa campo nomeCognome via reflection
        try {
            Field f = Studente.class.getDeclaredField("nomeCognome");
            f.setAccessible(true);
            f.set(s, nome + " " + cognome);
        } catch (NoSuchFieldException | IllegalAccessException ignore) {}

        arr[idx] = s;
        System.out.println("Studente inserito (posizione " + (idx+1) + ").");
    }

    private static void registraPresenza() {
        Studente s = pickStudente();
        if (s == null) return;
        LocalDate d = readDate("Data (aaaa‑mm‑gg): ");
        char stato;
        while (true) {
            System.out.print("Stato [P/A]: ");
            String in = SC.nextLine().trim().toUpperCase();
            if (in.equals("P") || in.equals("A")) { stato = in.charAt(0); break; }
            System.out.println("Inserire P o A.");
        }
        int idx = firstFree(s.getPresenze());
        if (idx == -1) { System.out.println("Array presenze pieno."); return; }
        s.setPresenze(d, stato, idx);
        System.out.println("Registrato.");
    }

    private static void inserisciVoto() {
        Studente s = pickStudente();
        if (s == null) return;
        System.out.println("Materie: " + Arrays.toString(Materie.arrayMateria));
        System.out.print("Materia: ");
        String materia = SC.nextLine();
        if (Materie.indiceMateria(materia) == -1) { System.out.println("Materia non trovata."); return; }
        int voto;
        while (true) {
            System.out.print("Voto (1‑10): ");
            try {
                voto = Integer.parseInt(SC.nextLine());
                if (voto>=1 && voto<=10) break;
            } catch (NumberFormatException ignore) {}
            System.out.println("Valore non valido.");
        }
        LocalDate d = readDate("Data verifica (aaaa‑mm‑gg): ");
        s.setVoti(materia, voto, d);
        System.out.println("Voto registrato.");
    }

    private static void inserisciNota() {
        Studente s = pickStudente();
        if (s == null) return;
        LocalDate d = readDate("Data (aaaa‑mm‑gg): ");
        System.out.print("Testo: "); String testo = SC.nextLine();
        int idx = firstFree(s.getNote());
        if (idx == -1) { System.out.println("Array note pieno."); return; }
        s.setNote(d, testo, idx);
        System.out.println("Nota inserita.");
    }

    private static void inserisciCompito() {
        System.out.println("Materie: " + Arrays.toString(Materie.arrayMateria));
        System.out.print("Materia: "); String materia = SC.nextLine();
        if (Materie.indiceMateria(materia)==-1){ System.out.println("Materia non trovata."); return; }
        LocalDate consegna = readDate("Data consegna (aaaa‑mm‑gg): ");
        System.out.print("Descrizione: "); String desc = SC.nextLine();
        new Compito(desc, materia, consegna); // istanza dimostrativa
        System.out.println("Compito creato (l'oggetto non viene ancora salvato in una lista globale).");
    }

    private static void stampaReportStudente() {
        Studente s = pickStudente();
        if (s != null) printStudente(s);
    }

    private static void stampaReportClasse() {
        Arrays.stream(classe.getStudenti()).filter(Objects::nonNull).forEach(MenuPrincipale::printStudente);
    }

    /* =========================== UTILITÀ ============================ */

    private static Studente pickStudente() {
        Studente[] arr = classe.getStudenti();
        int count = 0;
        System.out.println("Elenco studenti:");
        for (int i=0;i<arr.length;i++) {
            if (arr[i]!=null) { System.out.println((i+1)+") "+arr[i].getNomeCognome()); count++; }
        }
        if (count==0) { System.out.println("Nessuno studente in classe."); return null; }
        int scelta;
        while (true) {
            System.out.print("Seleziona [1‑"+arr.length+"]: ");
            try {
                scelta = Integer.parseInt(SC.nextLine())-1;
                if (scelta>=0 && scelta<arr.length && arr[scelta]!=null) break;
            } catch (NumberFormatException ignore) {}
            System.out.println("Indice non valido.");
        }
        return arr[scelta];
    }

    private static void printStudente(Studente s) {
        System.out.println("\n----------------------------------------");
        System.out.println("STUDENTE: "+s.getNomeCognome());
        System.out.println("Data di nascita: "+s.getDataNascita());
        System.out.println("Presenze/Assenze:");
        for (Presenza p : s.getPresenze()) { if (p==null) break; System.out.println("  » "+p.getGiorno()+" -> "+p.getStato()); }
        System.out.println("Voti:");
        Voti[][] vv = s.getVoti();
        for (Voti[] row : vv) {
            for (Voti v : row) {
                if (v!=null) System.out.printf("  » %-20s %d (%s)%n", v.getMateria(), v.getVoto(), v.getData());
            }
        }
        System.out.println("Note:");
        for (Nota n : s.getNote()) { if (n==null) break; System.out.println("  » "+n.getData()+": "+n.getTesto()); }
        System.out.println("----------------------------------------");
    }

    private static <T> int firstFree(T[] arr) {
        for (int i=0;i<arr.length;i++){ if (arr[i]==null) return i; }
        return -1;
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String in = SC.nextLine();
            try {
                return LocalDate.parse(in);
            } catch (DateTimeParseException ex) {
                System.out.println("Formato non valido. Usa aaaa‑mm‑gg.");
            }
        }
    }
}
