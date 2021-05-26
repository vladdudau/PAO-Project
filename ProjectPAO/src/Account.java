import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Account implements Comparator<Transaction> {
    protected String IBAN;
    protected String BIC;
    protected double amount;
    protected String name;
    protected int clientId;
    private final CardFactory cardFactory = new CardFactory();
    protected List<Card> cards = new ArrayList<>();

    public Account(String IBAN, String swift, double amount, String name, int clientId){
        this.IBAN = IBAN;
        this.BIC = swift;
        this.amount = amount;
        this.name = name;
        this.clientId = clientId;
    }

    public Account(String name, int clientId, int uniqueId) {
        this.IBAN = this.generateIBAN(uniqueId);
        this.BIC = this.generateBIC(uniqueId);
        this.amount = 0;
        this.name = name;
        this.clientId = clientId;
    }




    @Override
    public String toString() {
        return "{" +
                "Numele contului este: " + name +
                ", IBANUL este = '" + IBAN + '\'' +
                ", BICUL este ='" + BIC + '\'' +
                ", suma ramasa este =" + amount +
                '}';
    }

    public String toCSV() {
        return IBAN +
                "," + BIC +
                "," + amount +
                "," + name +
                "," + clientId;
    }

    public List<Transaction> filterTransactions(List<Transaction> allTransactions){
        List<Transaction> transactions = new ArrayList<>();
        for(var transaction: allTransactions)
        {
//            System.out.println(transaction);
//            System.out.println(this.IBAN);
            if(transaction.getFromIBAN().equals(this.IBAN) || transaction.getToIBAN().equals(this.IBAN))
                transactions.add(transaction);
        }

        return transactions;
    }

    public List<Transaction> filterTransactions(List<Transaction> allTransactions, int year) {
        List<Transaction> transactions = new ArrayList<>();
        for (var transaction : allTransactions)
        {
            int x = transaction.getYear();
            if((transaction.getFromIBAN().equals(this.IBAN) || transaction.getToIBAN().equals(this.IBAN)) && transaction.getYear()==year)
                transactions.add(transaction);
        }

        return transactions;
    }

    public void addCard(String name){
        Card newCard = cardFactory.createCard(this.IBAN, name);
        cards.add(newCard);
    }

    public List<Card> getCards()
    {
        return cards;
    }



    public int compare(Transaction transaction1, Transaction transaction2)
    {
        return transaction1.getCreationDate().compareTo(transaction2.getCreationDate());
    }



    protected String generateBIC(int clientId)
    {
        return "BIC" + "VLD" + clientId*2;
    }
    protected String generateIBAN(int clientId)
    {
        return "RO04VLDG" + clientId*2;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public int getClientId() {
        return clientId;
    }
}
