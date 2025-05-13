package obj;
import java.time.LocalDate;

public class Studente {
	//VARIABILI D'ISTANZA
	//nome e cognome
	private String nomeCognome;
	//data di nascita
	private LocalDate dataNascita;
	//array di presenze
	private Presenza[] presenze;
	//numero di presente/assenze
	private int numPresenze;
	//array di voti
	private Voti[][] voti;
	//array di note
	private Nota[] note;
	//numero di note
	private int numNote;

	//METODO COSTRUTTORE
	public Studente(String name, String surname, LocalDate p_data) {
		//data di nascita
		this.dataNascita = p_data;
		//array oggetto presenze
		this.presenze = new Presenza[200];
		//array oggetto voti
		this.voti = new Voti[10][10];
		//array oggetto note
		this.note = new Nota[200];
	}

	//METODI D'ISTANZA
	//METODI GET
	//get nomecognome
	public String getNomeCognome() {
		return this.nomeCognome;
	}
	//get data nascita
	public LocalDate getDataNascita() {
		return this.dataNascita;
	}
	//get presenze
	public Presenza[] getPresenze() {
		return this.presenze;
	}
	//get voti
	public Voti[][] getVoti() {
		return this.voti;
	}
	//get note
	public Nota[] getNote() {
		return this.note;
	}
	//get numPresenze
	public int getNumNote() {
		return this.numNote;
	}
	//get numNote
	public int getNumPresenze() {
		return this.numPresenze;
	}

	//METODI SET
	//metodo set nota
	public void setNote(LocalDate p_data, String p_testo, int p_indice) {
		//inizializzo nota
		this.note[p_indice] = new Nota(p_testo, p_data);
	}
	//set voto DA FINIRE, MANCA L'ASSEGNAIZONE DEL VOTO DENTRO ALLA MATRICE, DOBBIAMO GESTIRE GLI INDICI
	public void setVoti(String p_materia, int p_voto, LocalDate p_data) {

	}
	//set presenza
	public void setPresenze(LocalDate p_data, char p_stato, int p_indice) {
		this.presenze[p_indice] = new Presenza(p_data, p_stato);
	}
	//AVVISO: quando comunico il p_indice lo posso prendere facendo: getNum... + 1 in modo da ottenere il prossimo spazio liero nell'array/matrice

}