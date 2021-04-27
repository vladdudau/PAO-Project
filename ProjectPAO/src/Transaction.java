import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    final private String fromIBAN, toIBAN;
    private final double amount;
    final private Date creationDate;
    private final String shortDescription;

    public Transaction(String fromIBAN, String toIBAN, double amount, String shortDescription) throws Exception {

        if(amount <= 0)
            throw new Exception("Suma introdusa este prea mica!");

        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.shortDescription = shortDescription;
        this.creationDate = new Date();
    }

    public Transaction(String fromIBAN, String toIBAN, double amount, String shortDescription, Date creationDate) throws Exception {
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.shortDescription = shortDescription;
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "de la=" + fromIBAN +
                ", catre=" + toIBAN +
                ", suma depusa=" + amount +
                ", scurta descriere='" + shortDescription + '\'' +
                ", Data la care a fost procesata=" + (new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss")).format(creationDate) +
                '}';
    }

    public String toCSV() {
        return fromIBAN +
                "," + toIBAN +
                "," + amount +
                "," + shortDescription +
                "," + (new SimpleDateFormat("yyyy-MM-dd h:m:i")).format(creationDate);
    }

    public double getAmount() {
        return amount;
    }

    public String getFromIBAN() {
        return fromIBAN;
    }

    public String getToIBAN() {
        return toIBAN;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}
