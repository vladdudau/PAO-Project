import java.text.ParseException;
import java.util.*;

public class Client {
    private int clientId;
    private String firstName;
    private String lastName;
    private String CNP;
    private String email;

    public List<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }

    public List<CheckingsAccount> getCheckingAccounts() {
        return checkingAccounts;
    }

    private String phone;
    private Adress adress;

    private final List<Account> accounts = new ArrayList<>();
    private final Map<String, Account> accountsMap = new HashMap<>();
    private final List<SavingsAccount> savingsAccounts = new ArrayList<>();
    private final List<CheckingsAccount> checkingAccounts = new ArrayList<>();
    private final AccountFactory accountFactory = new AccountFactory();
    private final SavingsAccountFactory savingsAccountFactory = new SavingsAccountFactory();
    private final CheckingsAccountFactory checkingsAccountFactory = new CheckingsAccountFactory();

    public Client(int clientIdId, String firstName, String lastName, String CNP, String email, String phone, Adress adress)
    {
        this.clientId = clientIdId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
    }

    public void Read(Scanner in) throws  ParseException{
        System.out.println("Prenume: ");
        this.firstName=in.nextLine();
        System.out.println("Nume: ");
        this.lastName=in.nextLine();
        System.out.println("CNP: ");
        this.CNP=in.nextLine();
        System.out.println("Email: ");
        this.email=in.nextLine();
        System.out.println("Numar de telefon: ");
        this.phone=in.nextLine();
        System.out.println("Adresa: ");
        this.adress =  new Adress(in);
    }

    public void createCustomerAccount(String name){
        Account newAccount = this.accountFactory.createAccount(name);
        this.accounts.add(newAccount);
        this.accountsMap.put(newAccount.getIBAN(), newAccount);
    }

    public void createSavingsAccount(String name)
    {
        SavingsAccount newSavingsAccount = this.savingsAccountFactory.createSavingsAccount(name);
        this.savingsAccounts.add(newSavingsAccount);
    }

    public void createCheckingAccount(String name)
    {
        CheckingsAccount newCheckingAccount = this.checkingsAccountFactory.createCheckingAccount(name);
        this.checkingAccounts.add(newCheckingAccount);
    }

    public void closeAccount(String IBAN) throws Exception{
        if(this.accounts.size()<=1)
            throw new Exception("Trebuie sa fie macar un cont asociat cu un client!");
        if(!this.accountsMap.containsKey(IBAN))
            throw new Exception("IBAN invalid!");
        Account accountToBeClosed = this.accountsMap.get(IBAN);
        if(accountToBeClosed.getAmount()!=0)
            throw new Exception("Suma din cont trebuie sa fie 0!");
        accounts.remove(accountToBeClosed);
        accountsMap.remove(IBAN);
    }

    public double getTotalAmount()
    {
        double totalAmount = 0;
        for(Account account : accounts)
            totalAmount += account.getAmount();
        return totalAmount;
    }

    @Override
    public String toString() {
        return "{" +
                "idClient=" + clientId +
                ", prenume='" + firstName + '\'' +
                ", nume='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                ", email='" + email + '\'' +
                ", numar de telefon='" + phone + '\'' +
                ", adresa =" + adress.toString() +
                '}';
    }


    public List<Transaction> getTransactionHistory(){
        List<Transaction> transactions = new ArrayList<>();
        for(Account account : this.accounts)
            transactions.addAll(account.getTransactionHistory());
        for(Transaction transaction : transactions)
            System.out.println(transaction.Afisare());
        return transactions;
    }


    public List<Transaction> getTransactionHistory(int year)
    {
        List<Transaction> transactions = new ArrayList<>();
        for(Account account :this.accounts)
            transactions.addAll(account.getTransactionHistory(year));
        for(Transaction transaction : transactions)
            System.out.println(transaction.Afisare());
        return transactions;
    }

    public Client(int uniqueId, Scanner in) throws ParseException{
        this.clientId = uniqueId;
        this.Read(in);
        this.createCustomerAccount(this.firstName + " " + this.lastName);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Map<String, Account> getAccountsMap() {
        return accountsMap;
    }

    public void setUserId(int userId) {
        this.clientId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAddress(Adress adress) {
        this.adress = adress;
    }

}
