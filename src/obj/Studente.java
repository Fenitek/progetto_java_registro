package obj;
import utility.Materie;

import java.time.LocalDate;
import utility.*;

public class Studente {
	//VARIABILI D'ISTANZA
	//nome
	private String name;
	//cognome
	private String surname;
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
	public Studente(String _name, String _surname, LocalDate p_data) {
		//nome
		this.name = _name;
		//cognome
		this.surname = _surname;
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
	public String getNome() {
		return this.name;
	}
	public String getCognome() {
		return this.surname;
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
	//get numVoti
	public int getNumVoti (String p_materia) {
		//numero di voti della materia selezionata
		int riga = Materie.indiceMateria(p_materia);
		//prendo quanti voti ci sono in una riga
		int indiceVoti = 0;

		//controllo fine ciclo
		boolean fine = false;

		for (int i = 0; i < voti[0].length && !fine; i++) {
			if (voti[riga][i] == null) {
				indiceVoti = i;
				fine = true;
			}
		}

		return indiceVoti;
	}

	//METODI SET
	//metodo set nota
	public void setNote(LocalDate p_data, String p_testo, int p_indice) {
		//inizializzo nota
		this.note[p_indice] = new Nota(p_testo, p_data);
	}
	//set voto
	public void setVoti(String p_materia, int p_voto, LocalDate p_data) {
		//PRENDO LA RIGA DALLA CLASSE MATERIE NEL PACKAGE UTILITY IN BASE ALLA STRING CLASSE
		int riga = Materie.indiceMateria(p_materia);
		int colonna = getNumVoti(p_materia) + 1;

		//asseggno voto
		voti[riga][colonna] = new Voti(p_materia, p_voto, p_data);

	}
	//set presenza
	public void setPresenze(LocalDate p_data, char p_stato, int p_indice) {
		this.presenze[p_indice] = new Presenza(p_data, p_stato);
	}
	//AVVISO: quando comunico il p_indice lo posso prendere facendo: getNum... + 1 in modo da ottenere il prossimo spazio liero nell'array/matrice

	public void stampaInfo(){
		System.out.println("Nome studente: " + this.getNome() + "\nCognome studente: " + this.getCognome());
	}

}