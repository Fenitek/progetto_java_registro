package obj;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class Classe_Scolastica {
    private final int MAX_STUDENTI;
    private ArrayList<Studente> studenti = new ArrayList<>();
    static Scanner in = new Scanner(System.in); //per la configurazione degli studenti
    private final Calendario calendario;

    private String sezione;
    private int classe;
    private String indirizzo;


    public Classe_Scolastica(String _sezione, int _classe, String _indirizzo, int _maxStudenti, Calendario _calendario) {
        this.MAX_STUDENTI = _maxStudenti;
        this.studenti = new ArrayList<>(studenti);
        this.calendario = _calendario;

        //inizializzazione della classe
        this.sezione = _sezione;
        this.classe = _classe;
        this.indirizzo = _indirizzo;
    }

    public String getSezione() {
        return sezione;
    }

    public int getClasse() {
        return classe;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public ArrayList<Studente> inizializzaAlunni(int maxStudenti, Calendario calendario, Scanner in) {
        ArrayList<Studente> studenti = new ArrayList<>();

        for (int i = 0; i < maxStudenti; i++) {
            System.out.print("Inserisci il nome dello studente: ");
            String nome = in.nextLine().trim();

            System.out.print("Inserisci il cognome dello studente: ");
            String cognome = in.nextLine().trim();

            int anno, mese, giorno;
            LocalDate dataDiNascita;

            // ---- ANNO ----
            int annoCorrente = calendario.getAnnoOggi();
            while (true) {
                System.out.print("Anno di nascita (1900-" + (annoCorrente - 1) + "): ");
                String tmp = in.nextLine();
                try {
                    anno = Integer.parseInt(tmp);
                    if (anno >= 1900 && anno < annoCorrente) break;
                } catch (NumberFormatException ignored) { }
                System.out.println("Valore non valido, riprova.");
            }

            // ---- MESE ----
            while (true) {
                System.out.print("Mese di nascita (1-12): ");
                String tmp = in.nextLine();
                try {
                    mese = Integer.parseInt(tmp);
                    if (mese >= 1 && mese <= 12) break;
                } catch (NumberFormatException ignored) { }
                System.out.println("Valore non valido, riprova.");
            }

            // ---- GIORNO ----
            while (true) {
                System.out.print("Giorno di nascita: ");
                String tmp = in.nextLine();
                try {
                    giorno = Integer.parseInt(tmp);
                    dataDiNascita = LocalDate.of(anno, mese, giorno);
                    break;
                } catch (NumberFormatException | DateTimeException e) {
                    System.out.println("Data non valida per quel mese, riprova.");
                }
            }

            // === CREAZIONE STUDENTE ===
            Studente studente = new Studente(nome, cognome, dataDiNascita);
            studenti.add(studente);
            System.out.println("Studente inserito con successo.\n");
        }
        return studenti;
    }

    public void stampaClasse(){
        //stampa delle informazioni della classe
        System.out.println("Classe: " + this.classe + " "+  this.sezione + " " + this.indirizzo);
        System.out.println("================================================");
        System.out.println("Alunni: ");
        if (this.studenti.isEmpty()) {
            System.out.println("Non esiste alcun studente.");
        } else {
            for (Studente s : this.studenti) {

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
                        if (n == null) {
                            break;
                        }
                        System.out.println(n.getData() + ": " + n.getTesto());
                    }
                    System.out.println("------------------------------");

            }
        }
    }

    public void addStudente(Studente s) {
        studenti.add(s);
    }

    public ArrayList<Studente> getStudenti() {
        return studenti;
    }

    public int getMAX_STUDENTI(){
        return this.MAX_STUDENTI;
    }

    public Calendario getCalendario() {
        return calendario;
    }
}