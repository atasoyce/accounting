package ca;


import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMenu {

    public void start(){
        int selectedMenu;
        Account myAccount = null;
        AccountManager am = new AccountManager();

        do {
            writeMenu();
            selectedMenu = readMenuSelection();

            switch (selectedMenu) {
                case 0:         // Beenden
                    System.exit(0);
                    break;
                case 1:         // Konto anlegen
                    myAccount = am.createAccount();
                    System.out.println();
                    System.out.println("Das neue Konto hat folgenden IBAN: " + myAccount.getIban());
                    break;
                case 2:         // Kontostand anzeigen
                    if(myAccount != null) {
                        showBalance(myAccount);
                    } else {
                        System.out.println();
                        System.out.println("Sie müssen zuerst ein Konto anlegen oder auswählen!");
                    }
                    break;
                case 3:         // Geld einzahlen
                    if(myAccount != null) {
                        depositMoney(myAccount);
                    } else {
                        System.out.println();
                        System.out.println("Sie müssen zuerst ein Konto anlegen oder auswählen!");
                    }
                    break;
                case 4:         // Geld abheben
                    if (myAccount != null){
                        payOutMoney(myAccount);
                    } else {
                        System.out.println();
                        System.out.println("Sie müssen zuerst ein Konto anlegen oder auswählen!");
                    }
                    break;
                case 5:         // Kontobewegungen anzeigen
                    if (myAccount != null){
                        printBookings(myAccount);
                        showBalance(myAccount);
                    } else {
                        System.out.println();
                        System.out.println("Sie müssen zuerst ein Konto anlegen!");
                    }
                    break;
                default:
                    System.out.println();
                    System.out.println("Bitte treffen Sie eine gültige Auswahl!");
            }
        }
        while(selectedMenu != 0);
    }

    //region Private methods
    private void writeMenu() {
        System.out.println();
        System.out.println();
        System.out.println("Auswahlmöglichkeiten:");
        System.out.println();
        System.out.println("1...Neues Konto anlegen");
        System.out.println("2...Kontostand anzeigen");
        System.out.println("3...Geld einzahlen");
        System.out.println("4...Geld beheben");
        System.out.println("5...Kontobewegungen ausgeben");
        System.out.println("0...Beenden");
        System.out.println();
        System.out.print("Bitte treffen Sie eine Auswahl: ");
    }

    private int readMenuSelection(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    private double readDoubleFromConsole(String prompt){
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt + ": ");
        return sc.nextDouble();
    }

    private String readStringFromConsole(String prompt){
        Scanner sc = new Scanner(System.in);
        System.out.print(prompt + ": ");
        return sc.next();
    }

    private void depositMoney(Account account){
        System.out.println();
        String comment = readStringFromConsole("Bitte geben Sie den Text für die Buchungszeile ein");
        double amount = readDoubleFromConsole("Bitte geben Sie den einzuzahlenden Betrag ein");
        account.addTransaction(comment,amount);
    }

    private void payOutMoney(Account account){
        System.out.println();
        String comment = readStringFromConsole("Bitte geben Sie den Text für die Buchungszeile ein");
        double amount = -readDoubleFromConsole("Bitte geben Sie den auszuzahlenden Betrag ein");
        if (account.addTransaction(comment,amount) != 0){
            System.out.println("Der gewünschte Betrag kann nicht abgehoben werden. Limit überschritten.");
        };
    }

    private void printBookings(Account account){
        String[] bookings = account.getBookingList();
        System.out.println();

        for (String booking : bookings) {
            System.out.println(booking);
        }

        /* entspricht der gewohnten for-Schleife. Beide kann man verwenden
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println(bookings.get(i));
        }
        */

    }

    private void showBalance(Account account) {
        System.out.println();
        System.out.println("Der aktuelle Kontostand beläuft sich auf: " +
                String.format("%,.2f",account.getBalance()));
    }
    //endregion
}

