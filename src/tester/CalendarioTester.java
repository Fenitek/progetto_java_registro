package tester;

import obj.*;

import java.time.LocalDate;

public class CalendarioTester {
    public static void main(String[] args) {
        Calendario cal = new Calendario(2025, 05, 10, 10);
        LocalDate biologiaDate = LocalDate.of(2025, 05, 11);
        Compito biologia1 = new Compito("Studiare tutto", "Biologia", biologiaDate);
        cal.stampaCalendario();
    }


}
