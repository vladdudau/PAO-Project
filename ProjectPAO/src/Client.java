import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Client {
    private final int clientId;
    private String firstName;
    private String lastName;
    private String CNP;
    private String email;
    private String phone;
    private Adress adress;

    public Client(int clientId, String firstName, String lastName, String CNP, String email, String phone, Adress adress)
    {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.email = email;
        this.phone = phone;
        this.adress = adress;
    }

    public Client(int clientId, Scanner in) throws ParseException {
        this.clientId = clientId;
        this.Read(in);
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

    public List<Account> filterAccounts(List<Account> allAccounts){
        var accounts = new ArrayList<Account>();
        for(var account: allAccounts)
            if(account.getClientId() == this.getClientId())
                accounts.add(account);
        return accounts;
    }

    public List<SavingsAccount> filterSavingAccounts(List<SavingsAccount> allAccounts){
        var accounts = new ArrayList<SavingsAccount>();
        for(var account: allAccounts)
            if(account.getClientId() == this.getClientId())
                accounts.add(account);
        return accounts;
    }

    public List<Transaction> filterTransactions(List<Account> allAccounts, List<Transaction> allTransactions){
        var transactions = new ArrayList<Transaction>();
        var accounts = this.filterAccounts(allAccounts);
        for(var account: accounts)
            transactions.addAll(account.filterTransactions(allTransactions));
        return transactions;
    }

    public List<Transaction> filterTransactions(List<Account> allAccounts, List<Transaction> allTransactions, int year){
        var transactions = new ArrayList<Transaction>();
        var accounts = this.filterAccounts(allAccounts);
        for(var account: accounts)
            transactions.addAll(account.filterTransactions(allTransactions, year));
        return transactions;
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

    public String toCSV(){
        return clientId +
                "," + firstName +
                "," + lastName +
                "," + CNP +
                "," + email +
                "," + phone +
                "," + adress.toCSV();
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

    public int getClientId() {
        return clientId;
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
