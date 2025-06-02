package obj;

import java.time.LocalDate;

public class NotaClasse {
    //VARIABILI D'ISTANZA
    //testo
    private String testo;
    //data
    private LocalDate data;

    //METODO COSTRUTTORE
    public NotaClasse (String p_testo, LocalDate p_data) {
        //assegnaizone
        //data
        this.data = p_data;
        //tetso
        this.testo = p_testo;
    }

    //METODI D'ISTANZA
    //METODI GET
    //metodo get testo
    public String getTesto () {
        return this.testo;
    }
    //metodo get data
    public LocalDate getData() {
        return this.data;
    }

    //METODI SET
    //metodo set testo
    public void setTesto(String p_testo) {
        this.testo = p_testo;
    }
    //metodo set data
    public void setData(LocalDate p_data) {
        this.data = p_data;
    }
}
