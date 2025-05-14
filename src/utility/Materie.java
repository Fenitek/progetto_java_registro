package utility;

public class Materie {
    //VARIABILI
    //arrray di indici
    String[] nomeMateria = {"Italinao","Matematica","Informatica","Ginnastica","Storia","Inglese","Educaizone civica","Tpsit","Sistemi e reti","Telecomunicazioni"};

    //metodo che cerca l'indice in base alla classe
    public int indiceMateria (String p_materia) {
        //indice che ritornerò, se è uguale a -1 vuoldire che non ho trovato la materia, lo comunicherò all'utente
        int indice = -1;

        boolean trovato = false;
        for (int i = 0; i < nomeMateria.length && !trovato; i++){
            if (nomeMateria[i].equalsIgnoreCase(p_materia)) {
                trovato = true;
                indice = i;
            }
        }

        return indice;
    }

}
