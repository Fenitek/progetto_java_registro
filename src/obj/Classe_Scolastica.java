package obj;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class Classe_Scolastica {
    private final int MAX_STUDENTI;
    private Studente[] classe;
    static Scanner in = new Scanner(System.in);
    private final Calendario calendario;

    public Classe_Scolastica(String _sezione, int _classe, String _indirizzo, int _maxStudenti, Calendario _calendario) {
        this.MAX_STUDENTI = _maxStudenti;
        this.classe = new Studente[MAX_STUDENTI];
        this.calendario = _calendario;

    }

    public Studente[] inizializzaAlunni() {
        for (int i = 0; i < MAX_STUDENTI; i++) {

            System.out.print("Inserisci il nome dello studente: ");
            String nome = in.nextLine().trim();

            System.out.print("Inserisci il cognome dello studente: ");
            String cognome = in.nextLine().trim();

            /*Data di nascita*/
            int anno, mese, giorno;
            LocalDate dataDiNascita;

            // ---- ANNO ----
            int annoCorrente = calendario. getAnnoOggi();  // anno
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

            // ---- GIORNO (verifico col mese scelto) ----
            while (true) {
                System.out.print("Giorno di nascita: ");
                String tmp = in.nextLine();
                try {
                    giorno = Integer.parseInt(tmp);
                    // Creo la data per verificare se il giorno esiste davvero
                    dataDiNascita = LocalDate.of(anno, mese, giorno);
                    break;          // se non lancia eccezione la data è valida
                } catch (NumberFormatException | DateTimeException e) {
                    System.out.println("Data non valida per quel mese, riprova.");
                }
            }

            /* =====================  CREAZIONE STUDENTE  ===================== */
            classe[i] = new Studente(nome, cognome, dataDiNascita);
            System.out.println("Studente inserito con successo.\n");
        }

        return classe;
    }


    public Studente[] getStudenti(){
        return classe;
    }

    public int getMAX_STUDENTI(){
        return this.MAX_STUDENTI;
    }

    public Calendario getCalendario() {
        return calendario;
    }
}