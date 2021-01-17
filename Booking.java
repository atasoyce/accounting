package ca;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {

    //region Instance Variables
    private String _Comment;
    private Date _Timestamp;
    private double _Amount;
    //endregion

    //region Constructors
    public Booking(String comment, double amount){
        _Comment = comment;
        _Amount = amount;
        _Timestamp = new Date();
    }
    //endregion

    //region Getter/Setter
    public String getBookingLine(){
        String returnString;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        returnString = sdf.format(_Timestamp) +
                ", " +
                String.format("%-40.40s", _Comment) +
                String.format("%,12.2f", _Amount);
        return returnString;
    }
    //endregion

    //region Public methods
    //endregion

    //region Private methods
    //endregion
}
