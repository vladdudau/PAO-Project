import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingsAccount extends Account{
    private final String startDate;
    private final int interest;


    public SavingsAccount(String name, int clientId,int uniqueId) {

        super(name, clientId, uniqueId);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.startDate = formatter.format(date);
        this.interest = 2;
    }

    public SavingsAccount(String IBAN, String swift, double amount, String name, int clientId, String startDate, int interest) {
        super(IBAN, swift, amount, name, clientId);

        this.startDate = startDate;
        this.interest = interest;
    }



    @Override
    public String toString() {
        return "{" +
                "Numele contului este: " + name +
                ", IBANUL este = '" + IBAN + '\'' +
                ", BICUL este ='" + BIC + '\'' +
                ", suma ramasa este =" + amount +
                ", data de inceput = " + startDate +
                ", gradul de interes = " + interest  +
                '}';
    }

    public String toCSV() {
        return IBAN +
                "," + BIC +
                "," + amount +
                "," + name +
                "," + clientId +
                "," + cards +
                "," + startDate +
                "," + interest;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getInterest() {
        return interest;
    }


}
