package obj;

import java.time.LocalDate;

public class Voti {

    //VARIABILI D'ISTANZA
    //data
    private LocalDate data;
    //voto
    private int voto;
    //materia
    private String materia;

    //METODO COSTRUTTORE
    //IMPORTATE: fare controllo per il voto (0<voto<10) e per la materia (Materia)
    public Voti (String p_materia, int p_voto, LocalDate p_data) {
        //materia
        this.materia = p_materia;
        //voto
        this.voto = p_voto;
        //data
        this.data = p_data;
    }

    //METODI D'ISTANZA
    //METODI GET
    //get data
    public LocalDate getData() {
        return this.data;
    }
    //get voto
    public int getVoto() {
        return this.voto;
    }
    //get materia
    public String getMateria() {
        return materia;
    }

    //METODI SET
    //set data
    public void setData(LocalDate p_data) {
        this.data = p_data;
    }
    //set voto
    public void setVoto(int p_voto) {
        this.voto = p_voto;
    }
}
