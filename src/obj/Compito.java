package obj;

import java.time.LocalDate;

public class Compito {

    //VARIABILI D'ISTANZA
    //materia
    private String materia;
    //testo
    private String testo;
    //data
    private LocalDate data;

    //METODO COSTRUTTORE
    //IMPORTATE: la materia deve avere questo formato: Materia (1° lettera maiuscola)
    public Compito (String p_testo, String p_materia,LocalDate p_data) {
        //assegnaizone
        //materia
        this.materia = p_materia;
        //testo
        this.testo = p_testo;
        //data
        this.data = p_data;
    }

    //METODI D'ISTANZA
    //METODI GET
    //metodo get materia
    public String getMateria() {
        return this.materia;
    }
    //metodo get testo
    public String getTesto() {
        return this.testo;
    }
    //metodo get data
    public LocalDate getData() {
        return this.data;
    }

    //METODO SET
    //metodo set testo
    public void setTesto(String p_testo) {
        this.testo = p_testo;
    }
    //metodo set data
    public void setData(LocalDate p_data) {
        this.data = p_data;
    }
}
