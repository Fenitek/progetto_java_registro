package obj;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class Classe_Scolastica {
    private final int MAX_STUDENTI;
    private ArrayList<Studente> studenti = new ArrayList<>();
    static Scanner in = new Scanner(System.in);
    private final Calendario calendario;

    public Classe_Scolastica(String _sezione, int _classe, String _indirizzo, int _maxStudenti, Calendario _calendario) {
        this.MAX_STUDENTI = _maxStudenti;
        this.studenti = new ArrayList<>(studenti);
        this.calendario = _calendario;

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