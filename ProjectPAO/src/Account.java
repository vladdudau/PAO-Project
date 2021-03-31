import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Account implements Comparator<Transaction> {
    protected String IBAN;
    protected double amount;
    protected String name;
    protected String BIC;
    protected List<Transaction> transactionHistory = new ArrayList<>();
    private final CardFactory cardFactory = new CardFactory();
    protected List<Card> cards = new ArrayList<>();

    public Account(String name, int uniqueId)
    {
        this.name = name;
        this.BIC = this.generateBIC(uniqueId);
        this.IBAN = this.generateIBAN(uniqueId);
        this.amount = 0;
    }

    public Card createCard(String name)
    {
        Card newCard = cardFactory.createCard(this.IBAN,name);
        cards.add(newCard);
        return newCard;
    }

    public Transaction createTransaction(Account expeditor, Account reciever, double amount, String shorDescription) throws  Exception{
        Transaction newTransaction = new Transaction(expeditor,reciever,amount,shorDescription);
        expeditor.transactionHistory.add(newTransaction);
        reciever.transactionHistory.add(newTransaction);
        return newTransaction;
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

    public List<Card> getCards()
    {
        return cards;
    }
    public List<Transaction> getTransactionHistory()
    {
        return transactionHistory;
    }

    public List<Transaction> getTransactionHistory(int year)
    {
        List <Transaction> filteredTransactions = new ArrayList<>();
        for(var transaction : transactionHistory)
            if(transaction.getCreationDate().getYear()==year)
                filteredTransactions.add(transaction);
        return filteredTransactions;
    }

    public int compare(Transaction transaction1, Transaction transaction2)
    {
        return transaction1.getCreationDate().compareTo(transaction2.getCreationDate());
    }

    public List<Transaction> getTransactionHistory(int year,int month)
    {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for(var transaction: transactionHistory)
            if(transaction.getCreationDate().getYear()==year && transaction.getCreationDate().getMonth()==month)
                filteredTransactions.add(transaction);
        filteredTransactions.sort(this);
        return filteredTransactions;
    }

    private String generateBIC(int uniqueId)
    {
        return "BIC" + "VLD" + uniqueId;
    }
    protected String generateIBAN(int uniqueId)
    {
        return "RO04VLDG" + uniqueId;
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
}
