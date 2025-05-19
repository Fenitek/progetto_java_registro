package utility;

public class Materie {
    //VARIABILI
    //arrray di indici
    public static String[] nomeMateria = {"Italinao","Matematica","Informatica","Ginnastica","Storia","Inglese","Educaizone civica","Tpsit","Sistemi e reti","Telecomunicazioni"};

    //metodo che cerca l'indice in base alla classe
    public static int indiceMateria (String p_materia) {
        //indice che ritornerò, se è uguale a -1 vuoldire che non ho trovato la materia, lo comunicherò all'utente
        int indice = -1;

        //variabile di controllo se ho trovato
        boolean trovato = false;
        for (int i = 0; i < nomeMateria.length && !trovato; i++){
            //contorllo ingorando maiuscole e minusocle la stringa data come parametro e quella nell'array
            if (nomeMateria[i].equalsIgnoreCase(p_materia)) {
                trovato = true;
                indice = i;
            }
        }

        return indice;
    }

}
