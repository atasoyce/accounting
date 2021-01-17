package ca;


import sun.java2d.pipe.RegionSpanIterator;

import java.beans.PropertyVetoException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Account {

    //region Instance Variables
    private double _Balance;
    private String _Iban;
    private String _Bic;
    private double _InterestRate;
    private ArrayList<Booking> _Bookings;
    //endregion

    //region Getter/Setter
    public String getBic() { return _Bic; }
    public Double getInterestRate() { return _InterestRate; }
    public double getBalance(){
        return _Balance;
    }
    public String getIban() {
        return _Iban.substring(0,4) + " " + _Iban.substring(4,8) + " " + _Iban.substring(8,12) + " " +
                _Iban.substring(12,16) + " " + _Iban.substring(16);
    }
    public void setInterestRate(double interestRate) { _InterestRate = interestRate; }
    //endregion

    //region Constructors
    public Account(String iban, String bic) {
        _Iban = iban;
        _Bic = bic;
        _Balance = 0;
        _InterestRate = 0;
        _Bookings = new ArrayList<Booking>();
    }
    //endregion

    //region Public Methods

    /**
     * Transaktion eines Kontos (Einzahlung/Auszahlung)
     * @param comment Kommentartext der Buchungszeile
     * @param amount Positiver Betrag entspricht einer Einzahlun, negativer Betrag einer Abhebung bzw. Auszahlung
     * @return Liefert 1, wenn keine Kontodeckung gegeben ist, ansonsten 0
     */
    public int addTransaction(String comment, double amount){
        if ( (_Balance + amount ) < 0) {
            return 1;
        }
        _Balance += amount;
        Booking myBooking = new Booking(comment,amount);
        _Bookings.add(myBooking);
        return 0;
    }

    /**
     * Liefert eine Liste von allen getätigten Buchungen
     * @return ArrayList mit Strings mit den einzelnen Buchungen
     */
    public String[] getBookingList() {
        String[] bookings = new String[_Bookings.size()];
        for (int i = 0; i < _Bookings.size(); i++) {
            bookings[i] = _Bookings.get(i).getBookingLine();
        }
        return bookings;
    }
    //endregion

    //region Private Methods
    //endregion
}
