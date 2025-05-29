package utility;

import obj.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class UtilityMenu {
    /**
     * Permette di selezionare uno studente dalla lista tramite il suo numero.
     */
    public static Studente pickStudente(Classe_Scolastica _classe, Scanner SC) {
        ArrayList<Studente> lista = _classe.getStudenti();
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
    public static void stampaStudente(Studente s) {
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
    public static void printCompito(Compito c) {
        System.out.println(" • [" + c.getData() + "] " + c.getMateria() + " - " + c.getTesto());
    }

    /**
     * Trova il primo posto libero in un array (presenze, voti, note).
     */
    public static <T> int firstFree(T[] arr) {
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
    public static LocalDate readDate(String prompt, boolean checkAnnoScolastico, Scanner SC, DateTimeFormatter DF, Calendario _calendario) {
        while (true) {
            System.out.print(prompt);
            String txt = SC.nextLine();
            try {
                LocalDate d = LocalDate.parse(txt, DF);
                if (checkAnnoScolastico) {
                    // Controllo che la data sia interna al calendario scolastico
                    if (d.isBefore(_calendario.getInizioAnno()) || d.isAfter(_calendario.getFineAnno())) {
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
