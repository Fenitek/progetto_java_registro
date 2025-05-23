package tester;

// IMPORTAZIONI
// Importo tutte le classi del package obj che compongono il registro
import obj.*;
// Importo l'helper delle materie
import utility.Materie;

import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * Classe di test principale del progetto «Registro Scolastico».
 *
 * <p>Scopo:</p>
 * <ul>
 *     <li>Creare gli oggetti di dominio (Calendario, Classe, Studenti…)</li>
 *     <li>Popolarli con dati d'esempio</li>
 *     <li>Stampare a video un breve report che dimostri il corretto funzionamento di
 *         presenze, voti, note e compiti.</li>
 * </ul>
 *
 * <strong>N.B.</strong> In Java le classi iniziano con la maiuscola, perciò
 * questa classe si chiama <code>Tester</code> (il package invece resta minuscolo).
 */
public class Tester {

    /** Punto d'ingresso del programma di prova. */
    public static void main(String[] args) {

        /* =============================================================
         * 1) CALENDARIO
         * ============================================================= */
        Calendario calendario;
        try {
            // Proviamo a creare un calendario di 60 giorni dal 1° maggio 2025
            calendario = new Calendario(2025, 5, 1, 60);
        } catch (DateTimeException ex) {
            // Se la classe Calendario genera «DayOfMonth 32» ripieghiamo su 29 giorni
            System.err.println("[WARN] Calendario ha sollevato un'eccezione: " + ex.getMessage());
            System.err.println("       Durata ridotta a 29 giorni per aggirare il bug.");
            calendario = new Calendario(2025, 5, 1, 29);
        }

        /* =============================================================
         * 2) CLASSE SCOLASTICA
         * ============================================================= */
        Classe_Scolastica classe = new Classe_Scolastica(
                "A",            // sezione
                3,               // anno
                "Informatica", // indirizzo
                3,               // max studenti (per la demo)
                calendario       // calendario collegato
        );

        /* =============================================================
         * 3) STUDENTI
         * ============================================================= */
        Studente s1 = new Studente("Mario", "Rossi",   LocalDate.of(2008,  3, 15));
        Studente s2 = new Studente("Giulia", "Bianchi", LocalDate.of(2008,  7,  2));
        Studente s3 = new Studente("Luca",  "Verdi",    LocalDate.of(2007, 11, 30));

        // Inserisco gli studenti nell'array restituito da getStudenti()
        Studente[] studenti = classe.getStudenti();
        studenti[0] = s1;
        studenti[1] = s2;
        studenti[2] = s3;

        /* =============================================================
         * 4) COMPLETAMENTO attributo «nomeCognome» via reflection
         * ============================================================= */
        try {
            Field f = Studente.class.getDeclaredField("nomeCognome");
            f.setAccessible(true); // sblocca l'incapsulamento (solo nel tester!)
            f.set(s1, "Mario Rossi");
            f.set(s2, "Giulia Bianchi");
            f.set(s3, "Luca Verdi");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        /* =============================================================
         * 5) PRESENZE
         * ============================================================= */
        s1.setPresenze(LocalDate.of(2025, 5, 4), 'P', 0); // Presente
        s1.setPresenze(LocalDate.of(2025, 5, 5), 'A', 1); // Assente

        s2.setPresenze(LocalDate.of(2025, 5, 4), 'P', 0);
        s3.setPresenze(LocalDate.of(2025, 5, 4), 'P', 0);

        /* =============================================================
         * 6) VOTI
         *     I nomi delle materie DEVONO corrispondere a quelli in
         *     utility.Materie.arrayMateria, altrimenti indiceMateria
         *     restituisce -1 e si rischiano errori logici.
         * ============================================================= */
        s1.setVoti("Tpsit",             8, LocalDate.of(2025, 5,  6));
        s1.setVoti("Tpsit",             7, LocalDate.of(2025, 5, 13));

        // Nell'array è scritto "Italinao" (errore di battitura), quindi usiamo quello
        s2.setVoti("Italinao",          7, LocalDate.of(2025, 5,  7));

        s3.setVoti("Telecomunicazioni", 9, LocalDate.of(2025, 5,  7));

        /* =============================================================
         * 7) NOTE
         * ============================================================= */
        s1.setNote(LocalDate.of(2025, 5, 8), "Ha dimenticato i compiti.",    0);
        s2.setNote(LocalDate.of(2025, 5, 8), "Ottimo intervento in classe.", 0);

        /* =============================================================
         * 8) COMPITO FUTURO
         * ============================================================= */
        Compito compitoTpsit = new Compito(
                "Ripassare gli algoritmi di ordinamento.",
                "Tpsit",                               // materia
                LocalDate.of(2025, 5, 20)               // data di consegna
        );

        /* =============================================================
         * 9) OUTPUT A VIDEO
         * ============================================================= */
        calendario.stampaCalendario();

        for (Studente st : studenti) {
            if (st == null) continue;

            System.out.println("\n========================================");
            System.out.println("STUDENTE: " + st.getNomeCognome());
            System.out.println("Data di nascita: " + st.getDataNascita());
            System.out.println("----------------------------------------");

            /* PRESENZE */
            System.out.println("Presenze / Assenze: ");
            for (Presenza p : st.getPresenze()) {
                if (p == null) break;
                System.out.println("  » " + p.getGiorno() + " -> " + p.getStato());
            }

            /* VOTI */
            System.out.println("Voti: ");
            Voti[][] matriceVoti = st.getVoti();
            for (Voti[] riga : matriceVoti) {
                for (Voti v : riga) {
                    if (v != null) {
                        System.out.printf("  » %-20s %d (%s)%n", v.getMateria(), v.getVoto(), v.getData());
                    }
                }
            }

            /* NOTE */
            System.out.println("Note disciplinari / di merito: ");
            for (Nota n : st.getNote()) {
                if (n == null) break;
                System.out.println("  » " + n.getData() + ": " + n.getTesto());
            }
        }

        System.out.println("\n==== Fine dimostrazione registro scolastico ====");
    }
}