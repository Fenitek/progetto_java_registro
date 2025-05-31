package obj;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class Calendario {

    //commendo di Alexx08Green
	 // variabili principali del calendario
    private LocalDate data; // data: anno-mese-giorno
    private final int GIORNI_CALENDARIO; //giorni dell'inizializzazione massimi del calendario
    private LocalDate[] calendarioDate; // calendario usato per la stampa dei giorni.

    //dati del giorno di oggi
    private int giornoOggi;
    private int meseOggi;
    private int annoOggi;
    
 // codici base ANSI - per gestire i colori
    public static final String RESET = "\u001B[0m";
    public static final String RED   = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";

    // COSTRUTTORE
    public Calendario(int _anno, int _mese, int _giorno, int _calendarioGiorni) {
        this.data = LocalDate.of(_anno, _mese, _giorno);
        this.GIORNI_CALENDARIO = _calendarioGiorni;
        this.calendarioDate = new LocalDate[GIORNI_CALENDARIO]; //inizializzo l'array del calendario
        inizializzaCalendario();

        // salvo i dati mese giorno ed anno.
        this.giornoOggi = data.getDayOfMonth();
        this.meseOggi = data.getMonthValue();
        this.annoOggi = data.getYear();
        
    }
    
    //Inizializzo l'array che gestisce i giorni.
    private void inizializzaCalendario() {

        LocalDate current = this.data;
        for (int i = 0; i < GIORNI_CALENDARIO; i++) {
            this.calendarioDate[i] = current;
            //aggiungiamo giorni fino ad arrivare all'ultimo giorno del calendario desiderato
            current = current.plusDays(1);
        }

    }

    
    public LocalDate getDataOggi() {
    	return this.data;
    }


    public int getAnnoOggi() {                // se ti serve solo l'anno
        return annoOggi;
    }

    public int getMeseOggi() {                // opzionale
        return meseOggi;
    }

    public int getGiornoOggi() {              // opzionale
        return giornoOggi;
    }

    public LocalDate[] getDataCalendario() {
    	return this.calendarioDate;
    }
    
    public void setDataCalendario(LocalDate[] _calendarioDate) {
    	this.calendarioDate = _calendarioDate;
    }
    
    public void setDataOggi(LocalDate _data) {
    	this.data = _data;
    }
	
    
//    public void stampaCalendario() {
//    	System.out.println(this.data.getYear() + " "+ this.data.getMonth());
//    	for(int i = 0; i<this.GIORNI_CALENDARIO; i++) {
//    		System.out.println(this.calendarioDate[i].getDayOfMonth() + " " + this.calendarioDate[i].getDayOfWeek());
//    	}
//    }

    public void stampaCalendario() {
        // Intestazione: mese e anno
        YearMonth ym = YearMonth.of(annoOggi, meseOggi);
        String meseNome = ym.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN); // testo in italiano
        System.out.printf("     %s %d%n", meseNome, ym.getYear());

        // Header giorni
        System.out.printf( "Lu Ma Me Gi Ve Sa Do " + RESET + "%n");

        // Calcolo offset del primo giorno
        LocalDate primo = ym.atDay(1);
        int offset =  primo.getDayOfWeek().getValue(); // 1=Lu … 7=Do

        // Spazi vuoti prima del 1° giorno, in base al primo giorno del mese, l'offset è diverso.
        for(int i = 1; i < offset; i++) {
            System.out.print("   ");
        }

        // Stampa tutti i giorni
        for(int giorno = 1; giorno <= ym.lengthOfMonth(); giorno++) {
            String format = "%2d ";

            if (giorno == giornoOggi) {
                // se è il giorno speciale, applica il colore ROSSO
                System.out.printf(RED + format + RESET, giorno);
            } else {
                // altrimenti stampa normalmente
                System.out.printf(format, giorno);
            }

            if ((giorno + offset - 1) % 7 == 0) {
                System.out.println();
            }
        }
        System.out.println();  // linea finale
    }

    // Restituisce la prima data del calendario scolastico (data di inizio)
    public LocalDate getInizioAnno() {
        return this.calendarioDate[0];
    }

    // Restituisce l'ultima data del calendario scolastico (data di fine)
    public LocalDate getFineAnno() {
        return this.calendarioDate[this.calendarioDate.length - 1];
    }

}
