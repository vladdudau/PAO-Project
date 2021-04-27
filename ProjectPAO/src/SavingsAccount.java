import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SavingsAccount extends Account{
    private final Date startDate;
    private final Date endDate;
    private final int interest;


    public SavingsAccount(String name, int clientId,int uniqueId) {

        super(name, clientId, uniqueId);

        this.startDate = new Date();
        this.interest = 2;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 1);
        this.endDate = c.getTime();
    }

    public SavingsAccount(String IBAN, String swift, double amount, String name, int clientId, Date startDate, Date endDate, int interest) {
        super(IBAN, swift, amount, name, clientId);

        this.startDate = startDate;
        this.endDate = endDate;
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
                ", data expirarii = " + endDate + '\'' +
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
                "," + (new SimpleDateFormat("yyyy-MM-dd")).format(startDate) +
                "," + (new SimpleDateFormat("yyyy-MM-dd")).format(endDate) +
                "," + interest;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getInterest() {
        return interest;
    }

    public Date getEndDate() {
        return endDate;
    }
}
