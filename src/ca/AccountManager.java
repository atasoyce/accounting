package ca;

import java.math.BigInteger;
import java.util.ArrayList;


public class AccountManager {

    //region Instance Variables
    private ArrayList<Account> _Accounts;
    private int _NextFreeAccountNumber;
    private final int BANKCODE = 25500;
    private final String COUNTRYCODE = "AT";
    private final int NUMERICCOUNTRYCODE = 102900;
    private final String BIC = "CHRIATE1";
//endregion

//region Getter and Setter
//endregion

    //region Constructors
    public AccountManager() {
        _Accounts = new ArrayList<Account>();
        _NextFreeAccountNumber = 1;
    }
//endregion

//region Public Methods

    /**
     * Erzeugt ein neues Konto und fügt es der Liste der Konten hinzu.
     * @return IBAN des neu erzeugten Kontos
     */
    public Account createAccount() {
        Account account = new Account(calculateIban(),BIC);
        _Accounts.add(account);
        return account;
    }

    public String[] getAccountList() {
        String[] accounts = new String[_Accounts.size()];

        for (int i = 0; i < _Accounts.size(); i++) {
            accounts[i] = _Accounts.get(i).getIban();
        }
        return accounts;
    }

//endregion

//region Private Methods

    /**
     * Berechnung eines neuen IBAN mit der nächsten freien Kontonummer
     * @return IBAN als Zeichenkette
     */
    private String calculateIban() {
        String iban;
        String bban;

        bban = createBban();
        iban = COUNTRYCODE + calculateCheckSum(bban) + bban;

        return iban;
    }

    /**
     * Erzeugt auf Basis der nächsten Kontonummer und der Bankleitzahl eine zusammengesetzte
     * lange Zahl: 5 Stellen für die Bankleitzahl und 11 Stellen für die Kontonummer.
     * Anmerkung "%011d" erzeugt eine 11-stellige Zahl mit führenden Nullen
     * @return Bankleitzahl plus 11-stellige Kontonummer
     */
    private String createBban() {
        String bban;

        bban = String.format("%d", BANKCODE) + String.format("%011d", _NextFreeAccountNumber);
        _NextFreeAccountNumber++;
        return bban;
    }

    /**
     * Berechnet aus BBAN plus 6-stelligem numerischen Ländercode die zweistellige Prüfsumme.
     * Diese Zahl wird Modulo 97 gerechnet und von 98 abgezogen.
     * Anmerkung: Bei "long" geht es sich mit der Stellenanzahl nicht aus, daher muss die Java-Klasse BigInteger
     * verwendet werden. Im Konstruktor kann man die Zahl als String übergeben und dann Berechnungen durchführen
     * @param bban Bankleitzahl plus 11-stellige Kontonummer
     * @return Zwestellige Prüfziffer
     */
    private String calculateCheckSum(String bban) {
        String numberString;
        BigInteger bigNumber;
        BigInteger[] divideAndRemainder;
        int checkSum;

        // Zeichenkette mit BBAN und Ländercode (102900). Zu viele Stellen für "long"
        numberString = bban + String.format("%d",NUMERICCOUNTRYCODE);

        // Erzeugen einer BigInteger-Instanz, um den vielen Stellen rechnen zu können
        bigNumber = new BigInteger(numberString);

        // Berechnen des Ergebnisses und des Restes der Division durch 97. Braucht aber die Zahl 97
        // auch als BigInteger. Das Ergebnis wird als Array zurückgeliefert. Das Ergebnis der Division
        // ist auf Index 0, der Rest der Division auf Index 1.
        divideAndRemainder = bigNumber.divideAndRemainder(new BigInteger("97"));

        // umwandeln des Restes in eine Integer-Zahl und Subtraktion von 98
        checkSum = 98 - divideAndRemainder[1].intValue();

        // Formatieren der Prüfziffer als zweistellige Zeichenkette mit eventueller führender Null
        return String.format("%02d", checkSum);
    }
//endregion

}
