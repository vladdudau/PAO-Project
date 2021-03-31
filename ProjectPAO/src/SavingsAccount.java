import java.util.Calendar;
import java.util.Date;

public class SavingsAccount extends Account{
    private final Date startDate;
    private final Date endDate;
    private final int interest;

    public SavingsAccount(String name, int uniqueId) {
        super(name,uniqueId);
        this.startDate = new Date();
        this.IBAN = "ROSVACC" + uniqueId;
        this.BIC = "BICVLDSAVINGS" + uniqueId;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR,2);
        this.endDate = calendar.getTime();
        this.interest = 2;

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
