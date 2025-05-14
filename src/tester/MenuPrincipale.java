package tester;

import obj.*;

public class MenuPrincipale {


    public static void main(String[] args) {
        Calendario cal = new Calendario(2025, 05, 10, 10);
        Classe_Scolastica classe1 = new Classe_Scolastica("A", 3, "informatica", 20, cal);
        classe1.inizializzaAlunni();

        System.out.println(classe1.getStudenti()[0].getNomeCognome());
        System.out.println(classe1.getStudenti()[0].getDataNascita());
    }


}



