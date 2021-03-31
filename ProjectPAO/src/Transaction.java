import java.util.Date;

public class Transaction {
    private final Account expeditor;
    private final Account reciever;
    private final double amount;
    private final Date CreationDate;
    private final String shortDescription;

    public Transaction(Account expeditor, Account reciever, double amount, String shortDescription) throws  Exception {

        if(expeditor.getAmount() < amount)
            throw new Exception("Your amount is too low! Please check again!");

        this.CreationDate = new Date();

        this.expeditor = expeditor;
        this.reciever = reciever;

        this.reciever.setAmount(this.reciever.getAmount() + amount);
        this.expeditor.setAmount(this.expeditor.getAmount() - amount);

        this.amount = amount;
        this.shortDescription = shortDescription;
    }

    public String Afisare() {
        return "{" +
                "Expeditor: " + expeditor +
                ", Destinatar = '" + reciever + '\'' +
                ", suma trimisa ='" + amount +
                ", Data tranzactiei =" + CreationDate +
                ", Scurta descriere =" + shortDescription +
                '}';
    }

    public Account getExpeditor() {
        return expeditor;
    }

    public Account getReciever() {
        return reciever;
    }

    public double getAmount() {
        return amount;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}
