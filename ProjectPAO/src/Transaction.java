import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    final private String fromIBAN, toIBAN;
    private final double amount;
    final private String creationDate;
    private final String shortDescription;

    public Transaction(String fromIBAN, String toIBAN, double amount, String shortDescription) throws Exception {

        if(amount <= 0)
            throw new Exception("Suma introdusa este prea mica!");

        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.shortDescription = shortDescription;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.creationDate = formatter.format(date);
    }

    public Transaction(String fromIBAN, String toIBAN, double amount, String creationDate,String shortDescription) throws Exception {
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
                ", Data la care a fost procesata=" + creationDate +
                '}';
    }

    public String toCSV() {
        return fromIBAN +
                "," + toIBAN +
                "," + amount +
                "," + shortDescription +
                "," + creationDate;
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

    public String getCreationDate() {
        return creationDate;
    }
    public int getYear()
    {
        String[] sir = creationDate.split("/");
        int year = Integer.parseInt(sir[2]);
        return year;
    }
    public String getShortDescription() {
        return shortDescription;
    }
}
