package obj;
import java.time.LocalDate;

public class Presenza {
    //VARIABILI D'ISTANZA
    //data
    private LocalDate giorno;
    //stato dello studente (A = ASSENTE, P = PRESENTE)
    private char stato;

    //METODO COSTRUTTORE
    public Presenza (LocalDate p_giorno, char p_stato) {
        //giorno/data
        this.giorno = p_giorno;
        //stato dello studente
        this.stato = p_stato;
    }

    //METODI D'ISTANZA
    //METODI GET
    //get giorno
    public LocalDate getGiorno() {
        return this.giorno;
    }
    //get stato
    public char getStato() {
        return this.stato;
    }

    //METODI SET
    //set girno
    public void setGiorno(LocalDate giorno) {
        this.giorno = giorno;
    }
    //set stato
    public void setStato(char p_stato) {
        this.stato = p_stato;
    }
}
