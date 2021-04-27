import java.text.ParseException;
import java.util.*;

public class BankOperations {

    private final Map<String, Account> accountsMap = new HashMap<>();
    private List<Account> accounts = new ArrayList<>();
    private List<SavingsAccount> savingsAccounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();


    private final ClientFactory clientFactory = new ClientFactory();
    private final AccountFactory accountFactory = new AccountFactory();


    private Client getClientFromInput(Scanner in) throws Exception{
        if(this.clients.size()==0)
            throw new Exception("Niciun client adaugat!");
        if(this.clients.size()==1)
            return clients.get(0);
        System.out.println("Client id [0-"+(this.clients.size()-1)+"]: ");
        int clientId = Integer.parseInt(in.nextLine());
        return clients.get(clientId);
    }

    public void linkAccounts(){
        for(var account: this.accounts)
            this.accountsMap.put(account.getIBAN(), account);
    }

    public void createClient(Scanner in) throws ParseException {
        Client newClient = clientFactory.createClient(in);
        this.clients.add(newClient);
        this.accounts.add(accountFactory.createAccount(newClient.getFirstName() + " " + newClient.getLastName(), newClient.getClientId()));
        System.out.println("Client adaugat");
    }

    public void getClient(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        System.out.println(client.toString());
    }

    private Account getAccountFromInput(Scanner in, Client client) throws Exception {
        List<Account> clientAccounts = client.filterAccounts(this.accounts);
        System.out.println("Conturile clientului: " + clientAccounts);
        System.out.println("Alege IBAN: ");
        var IBAN = in.nextLine();
        if(!this.accountsMap.containsKey(IBAN))
            throw new Exception("IBAN Invalid!");
        var account = accountsMap.get(IBAN);;
        if(account.getClientId() != client.getClientId())
            throw new Exception("IBAN-ul nu este asociat cu niciun cont");
        return account;
    }

    public void getClientAmount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        var clientAccounts = client.filterAccounts(this.accounts);
        double totalAmount = 0;
        for(var account: clientAccounts)
            totalAmount += account.getAmount();
        System.out.println(client.getFirstName() + " " + client.getLastName() + " are un numar total de: " + totalAmount + " lei in conturi.");
    }

    public void getClientAccounts(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        List<Account> clientAccounts = client.filterAccounts(this.accounts);
        System.out.println(clientAccounts.toString());
    }

    public void getClientSavingsAccounts(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        List<SavingsAccount> clientSavingsAccounts = client.filterSavingAccounts(this.savingsAccounts);
        System.out.println(clientSavingsAccounts.toString());
    }

    public void createClientAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        System.out.println("Titular cont: ");
        String name = in.nextLine();
        Account newAccount = this.accountFactory.createAccount(name, client.getClientId());
        accounts.add(newAccount);
        accountsMap.put(newAccount.getIBAN(), newAccount);
        System.out.println("Cont creeat");
    }

    public void createClientSavingsAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        System.out.println("Titular cont economii: ");
        String name = in.nextLine();
        SavingsAccount newSavingsAccount = this.accountFactory.createSavingsAccount(name, client.getClientId());
        this.savingsAccounts.add(newSavingsAccount);
        System.out.println("Cont economii creeat");
    }

    public void createClientCard(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);
        System.out.println("Detinator card: ");
        var name = in.nextLine();
        account.addCard(name);
    }

    public void loadClientAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        System.out.println("Cati bani vrei sa depui?: ");
        int amount = Integer.parseInt(in.nextLine());
        var clientAccounts = client.filterAccounts(this.accounts);
        clientAccounts.get(0).setAmount(amount);
        System.out.println("Suma a fost depusa!");
    }

    public void createTransaction(Scanner in) throws Exception {
        System.out.println("De la (IBAN): ");
        var IBAN1 = in.nextLine();
        System.out.println("Catre (IBAN): ");
        var IBAN2 = in.nextLine();
        System.out.println("Suma: ");
        int amount = in.nextInt();
        System.out.println("Scurta descriere: ");
        String description = in.nextLine();

        Account account1 = null, account2 = null;

        if(accountsMap.containsKey(IBAN1))
            account1 = accountsMap.get(IBAN1);
        if(accountsMap.containsKey(IBAN2))
            account2 = accountsMap.get(IBAN2);

        if(IBAN1.equals(IBAN2))
            throw new Exception("Nu poti trimite catre acelasi cont");
        if(account1==null || account2==null)
            throw new Exception("Nu a fost gasit IBAN-ul!");
        if(account1.getAmount() < amount)
            throw new Exception("Fonduri insuficiente!");

        account1.setAmount(account1.getAmount() - amount);
        account2.setAmount(account2.getAmount() + amount);

        this.transactions.add(new Transaction(IBAN1, IBAN2, amount, description));
        System.out.println("Tranzactie cu succes");
    }

    public void closeAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);

        if(client.filterAccounts(this.accounts).size()<=1)
            throw new Exception("Trebuie sa fie macar un cont asociat pentru a putea inchide!");
        if(account.getAmount()!=0)
            throw new Exception("Mai aveti bani in cont!Trebuie sa i scoateti pentru a putea inchide!");
        this.accountsMap.remove(account.getIBAN());
        this.accounts.remove(account);

        System.out.println("Cont inchis!");
    }

    public void getClientAccount(Scanner in) throws Exception{
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);
        System.out.println(account.toString());
    }

    public void getClientTransactions(Scanner in) throws Exception{
        var client = this.getClientFromInput(in);
        System.out.println("Arata toate tranzactiile? (True/False)");
        boolean showAll = in.nextBoolean();
        if(showAll) {
            System.out.println(client.filterTransactions(accounts, transactions));
        }
        else{
            System.out.println("Selecteaza anul: ");
            var year = in.nextInt();
            System.out.println(client.filterTransactions(accounts, transactions, year));
        }
        System.out.println();
    }

    public List<Client> getClients() {
        return clients;
    }
    public List<Account> getAccounts() {
        return accounts;
    }
    public List<SavingsAccount> getSavingsAccounts() {
        return savingsAccounts;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setClients(List<Client> clients){
        this.clients = clients;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public void setSavingsAccounts(List<SavingsAccount> savingsAccounts) {
        this.savingsAccounts = savingsAccounts;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
