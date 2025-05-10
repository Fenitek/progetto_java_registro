package obj;
import java.time.LocalDate;

public class Presenza {
    public class Stato {
        private LocalDate giorno;
        private char stato;

        public Stato (LocalDate p_giorno, char p_stato) {
            this.giorno = p_giorno;
            this.stato = p_stato;
        }

        public LocalDate getGiorno() {
            return giorno;
        }

        public char getStato() {
            return stato;
        }
    }

}
