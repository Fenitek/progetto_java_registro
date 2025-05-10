package obj;
import java.time.LocalDate;

public class Studente {
	private String nomeCognome;
	private LocalDate dataNascita;

	public Studente(String name, String surname, int year, int month, int day) {
		this.dataNascita = LocalDate.of(year, month, day); // dati di nascita
		this.nomeCognome = creaNome(name, surname); // nonme & cognome + dataNascita
	}

	private String creaNome(String name, String surname) { 
		// risulta es: Nome: Diego Laera | 2008-08-08
		return ("Nome: " + name + " " + surname + " | " + this.dataNascita.toString());
	}

	public void stampaInfo() {
		// stampa es: Nome: Diego Laera | 2008-08-08
		System.out.println(this.nomeCognome);
	}
}