package tester;

import obj.*;
import utility.Materie;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuPrincipale {

    // ---------------------------- STATE ----------------------------
    private static final Scanner SC = new Scanner(System.in);
    private static final DateTimeFormatter DF = DateTimeFormatter.ISO_LOCAL_DATE;

    private static Calendario calendario;
    private static Classe_Scolastica classe;
    private static final List<Compito> COMPITI = new ArrayList<>();

    // ----------------------------- MAIN -----------------------------
    public static void main(String[] args) {
        configuraRegistro();
        menuLoop();
        System.out.println("\nGrazie per aver utilizzato il registro. Arrivederci!");
    }

    // ======================= CONFIGURAZIONE =======================

    /**
     * Permette di inserire i dati fondamentali per iniziare a utilizzare il registro:
     * anno scolastico, sezione, anno di corso, indirizzo, numero massimo studenti.
     */
    private static void configuraRegistro() {
        System.out.println("===== CONFIGURAZIONE REGISTRO =====\n");

        int anno;
        // Inserimento dell'anno scolastico (deve essere >= 1900)
        while (true) {
            System.out.print("Anno scolastico: ");
            try {
                anno = Integer.parseInt(SC.nextLine());
                if (anno >= 1900) break;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Valore non valido.");
        }

        // Inizializzazione del calendario scolastico a partire dal 12 settembre
        int giorno = 12, mese = 9, durata = 250;
        while (true) {
            try {
                calendario = new Calendario(anno, mese, giorno, durata);
                break;
            } catch (DateTimeException ex) {
                durata--;
            }
        }

        // Inserimento dati della classe
        System.out.print("Sezione (es. A,B,C): ");
        String sezione = SC.nextLine().trim().toUpperCase();

        int annoCorso;
        while (true) {
            System.out.print("Anno di corso (1-5): ");
            try {
                annoCorso = Integer.parseInt(SC.nextLine());
                if (annoCorso >= 1 && annoCorso <= 5) break;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Valore non valido.");
        }

        System.out.print("Indirizzo (es. Liceo, Informatica): ");
        String indirizzo = SC.nextLine();

        int maxStud;
        while (true) {
            System.out.print("Numero massimo studenti: ");
            try {
                maxStud = Integer.parseInt(SC.nextLine());
                if (maxStud > 0) break;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Valore non valido.");
        }

        // Crea la classe scolastica con i dati raccolti
        classe = new Classe_Scolastica(sezione, annoCorso, indirizzo, maxStud, calendario);
        System.out.println("\nRegistro creato con successo!\n");
    }

    // ========================== MENU LOOP ==========================

    /**
     * Ciclo principale che mostra il menu e gestisce le operazioni dell'utente.
     */
    private static void menuLoop() {
        boolean attivo = true;
        while (attivo) {
            stampaMenu(); // Mostra il menu delle scelte disponibili
            System.out.print("Scelta: ");
            switch (SC.nextLine()) {
                case "1" -> inserisciStudente();
                case "2" -> registraPresenza();
                case "3" -> inserisciVoto();
                case "4" -> inserisciNota();
                case "5" -> inserisciCompito();
                case "6" -> reportSingolo();
                case "7" -> reportClasse();
                case "8" -> calendarioConCompiti();
                case "9" -> attivo = false; // Esce dal programma
                default -> System.out.println("Opzione non valida.");
            }
            System.out.println();
        }
    }

    /**
     * Mostra a schermo il menu delle operazioni disponibili.
     */
    private static void stampaMenu() {
        System.out.println("===== MENU =====");
        System.out.println("1) Nuovo studente");
        System.out.println("2) Presenza / assenza");
        System.out.println("3) Nuovo voto");
        System.out.println("4) Nuova nota");
        System.out.println("5) Nuovo compito");
        System.out.println("6) Report studente");
        System.out.println("7) Report classe");
        System.out.println("8) Calendario + compiti");
        System.out.println("9) Esci");
    }

    // ========================= OPERAZIONI =========================

    /**
     * Permette di inserire un nuovo studente nella classe, se c'è posto.
     */
    private static void inserisciStudente() {
        ArrayList<Studente> lista = classe.getStudenti();
        if (lista.size() >= classe.getMAX_STUDENTI()) {
            System.out.println("Classe piena.");
            return;
        }
        System.out.print("Nome: ");
        String nome = SC.nextLine();
        System.out.print("Cognome: ");
        String cog = SC.nextLine();
        LocalDate nascita = readDate("Nascita (yyyy-MM-dd): ", false);
        Studente s = new Studente(nome, cog, nascita);
        lista.add(s);
        System.out.println("Studente inserito.");
    }

    /**
     * Registra una presenza o assenza per uno studente specifico in una certa data.
     */
    private static void registraPresenza() {
        Studente s = pickStudente();
        if (s == null) return; //studente non trovato
        LocalDate d = readDate("Data (yyyy-MM-dd): ", true);
        System.out.print("Stato (P/A): ");
        char stato = SC.nextLine().trim().toUpperCase().charAt(0);
        int pos = firstFree(s.getPresenze());
        if (pos == -1) {
            System.out.println("Array pieno.");
            return;
        }
        s.setPresenze(d, stato, pos);
        System.out.println("Presenza registrata.");
    }

    /**
     * Permette di inserire un nuovo voto per uno studente in una materia, indicando anche la data.
     */
    private static void inserisciVoto() {
        Studente s = pickStudente();
        if (s == null) return;
        System.out.println("Materie: ");
        for (String m : Materie.arrayMateria) System.out.print(m + " ");
        System.out.println();
        System.out.print("Materia: ");
        String mat = SC.nextLine();
        System.out.print("Voto (1-10): ");
        int voto = Integer.parseInt(SC.nextLine());
        LocalDate d = readDate("Data verifica (yyyy-MM-dd): ", true);
        s.setVoti(mat, voto, d);
        System.out.println("Voto inserito.");
    }

    /**
     * Inserisce una nota disciplinare o informativa per uno studente.
     */
    private static void inserisciNota() {
        Studente s = pickStudente();
        if (s == null) return;
        LocalDate d = readDate("Data (yyyy-MM-dd): ", true);
        System.out.print("Nota: ");
        String testo = SC.nextLine();
        int pos = firstFree(s.getNote());
        if (pos == -1) {
            System.out.println("Array pieno.");
            return;
        }
        s.setNote(d, testo, pos);
        System.out.println("Nota inserita.");
    }

    /**
     * Permette di inserire un nuovo compito assegnato alla classe.
     */
    private static void inserisciCompito() {
        System.out.print("Materia: ");
        String mat = SC.nextLine();
        LocalDate cons = readDate("Consegna (yyyy-MM-dd): ", true);
        System.out.print("Descrizione: ");
        String desc = SC.nextLine();
        COMPITI.add(new Compito(desc, mat, cons));
        System.out.println("Compito registrato.");
    }

    /**
     * Stampa il report di uno studente selezionato, mostrando presenze, voti e note.
     */
    private static void reportSingolo() {
        Studente s = pickStudente();
        if (s != null){
            s.stampaInfo();
        }
    }

    /**
     * Stampa un report di tutti gli studenti della classe.
     */
    private static void reportClasse() {
        if (classe.getStudenti().isEmpty()) {
            System.out.println("Non esiste alcun studente.");
        } else {
            for (Studente s : classe.getStudenti()) {
                stampaStudente(s);
            }
        }
    }

    // =============== CALENDARIO + COMPITI FILTRATI ===============

    /**
     * Mostra il calendario scolastico e filtra i compiti per giorno, settimana e mese selezionati.
     */
    private static void calendarioConCompiti() {
        calendario.stampaCalendario();
        if (COMPITI.isEmpty()) {
            System.out.println("Nessun compito registrato.");
            return;
        }
        LocalDate d = readDate("Data da filtrare (yyyy-MM-dd): ", true);

        WeekFields wf = WeekFields.of(Locale.getDefault());
        int week = d.get(wf.weekOfWeekBasedYear());

        System.out.println("\nCompiti del giorno:");
        for (Compito c : COMPITI) if (c.getData().equals(d)) printCompito(c);
        System.out.println("\nCompiti della settimana:");
        for (Compito c : COMPITI) if (c.getData().get(wf.weekOfWeekBasedYear()) == week) printCompito(c);
        System.out.println("\nCompiti del mese:");
        for (Compito c : COMPITI) if (c.getData().getMonth() == d.getMonth()) printCompito(c);
    }

    // ========================= UTILITIES =========================

    /**
     * Permette di selezionare uno studente dalla lista tramite il suo numero.
     */
    private static Studente pickStudente() {
        ArrayList<Studente> lista = classe.getStudenti();
        if (lista.isEmpty()) {
            System.out.println("Nessuno studente presente.");
            return null;
        }
        //stampa per cognome
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ") " + lista.get(i).getNome() + " " + lista.get(i).getCognome());
        }
        //chiedo il numero dello studente da stampare
        System.out.print("Seleziona numero: ");
        int idx;
        try {
            idx = Integer.parseInt(SC.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
            return null;
        }
        if (idx < 0 || idx >= lista.size()) {
            System.out.println("Indice errato.");
            return null;
        }
        //torna l'indice della lista
        return lista.get(idx);
    }

    /**
     * Stampa tutte le informazioni di uno studente: presenze, voti, note.
     */
    private static void stampaStudente(Studente s) {
        System.out.println("------------------------------");
        System.out.println(s.getNome() + " " + s.getCognome() + " (" + s.getDataNascita() + ")");

        System.out.println("Presenze:");
        for (Presenza p : s.getPresenze()) {
            if (p == null) break;
            System.out.println(p.getGiorno() + " -> " + p.getStato());
        }

        System.out.println("Voti:");
        Voti[][] vv = s.getVoti();
        for (Voti[] row : vv) {
            for (Voti v : row) {
                if (v != null) System.out.println(v.getMateria() + " " + v.getVoto() + " (" + v.getData() + ")");
            }
        }

        System.out.println("Note:");
        for (Nota n : s.getNote()) {
            if (n == null) break;
            System.out.println(n.getData() + ": " + n.getTesto());
        }
        System.out.println("------------------------------");
    }

    /**
     * Stampa le informazioni di un compito a schermo.
     */
    private static void printCompito(Compito c) {
        System.out.println(" • [" + c.getData() + "] " + c.getMateria() + " - " + c.getTesto());
    }

    /**
     * Trova il primo posto libero in un array (presenze, voti, note).
     */
    private static <T> int firstFree(T[] arr) {
        for (int i = 0; i < arr.length; i++) if (arr[i] == null) return i;
        return -1;
    }

    /**
     * Permette di leggere una data dal terminale usando il formato standard, con verifica.
     * Se la data inserita non è valida, chiede di nuovo all'utente.
     *
     * @param prompt              Messaggio di richiesta all'utente
     * @param checkAnnoScolastico true se vuoi controllare che la data sia nell'anno scolastico, false se vuoi accettare qualsiasi data
     * @return Data inserita
     */
    private static LocalDate readDate(String prompt, boolean checkAnnoScolastico) {
        while (true) {
            System.out.print(prompt);
            String txt = SC.nextLine();
            try {
                LocalDate d = LocalDate.parse(txt, DF);
                if (checkAnnoScolastico) {
                    // Controllo che la data sia interna al calendario scolastico
                    if (d.isBefore(calendario.getInizioAnno()) || d.isAfter(calendario.getFineAnno())) {
                        System.out.println("Data non all'interno dell'anno scolastico.");
                        continue;
                    }
                }
                return d;
            } catch (DateTimeParseException ex) {
                System.out.println("Formato data non valido. Inserire come yyyy-MM-dd.");
            }
        }
    }
}