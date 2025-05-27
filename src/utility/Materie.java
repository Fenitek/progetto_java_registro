package utility;

public class Materie {
    //VARIABILI
    //arrray di indici
    public static String[] arrayMateria= {"Italiano","Matematica","Informatica","Ginnastica","Storia","Inglese","Educazione civica","Tpsit","Sistemi e reti","Telecomunicazioni"};

    //metodo che cerca l'indice in base alla materia
    public static int indiceMateria (String p_materia) {
        //indice che ritornerò, se è uguale a -1 vuoldire che non ho trovato la materia, lo comunicherò all'utente
        int indice = -1;

        //variabile di controllo se ho trovato
        boolean trovato = false;
        for (int i = 0; i < arrayMateria.length && !trovato; i++){
            //contorllo ingorando maiuscole e minusocle la stringa data come parametro e quella nell'array
            if (arrayMateria[i].equalsIgnoreCase(p_materia)) {
                trovato = true;
                indice = i;
            }
        }

        return indice;
    }

    //metodo che cerca la materia in base all'indice, può servire per cercare voti di determinate materie sapendo l'indice
    public static String nomeMateria (int p_indice) {
        //prendo indice materia partendo dalla sringa
        String nomeM = arrayMateria[p_indice];

        return nomeM;
    }

}
