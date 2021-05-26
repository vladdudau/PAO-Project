import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class BankOperations {

    private final Map<String, Account> accountsMap = new HashMap<>();
    private List<Account> accounts = new ArrayList<>();
    private List<SavingsAccount> savingsAccounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private List<Client> clients = new ArrayList<>();
    DatabaseService databaseService = DatabaseService.getInstance();

    private final ClientFactory clientFactory = new ClientFactory();
    private final AccountFactory accountFactory = new AccountFactory();
    public BankOperations() throws SQLException {
    }

    public void getTransactionFromDatabase() throws Exception
    {
        String interogare = "Select * from transaction";
        ResultSet resultSet = databaseService.executeQuery(interogare);
        while(resultSet.next())
        {
            String fromIBAN = resultSet.getString(1);
            String toIBAN = resultSet.getString(2);
            Double amount = resultSet.getDouble(3);
            String creationDate = resultSet.getString(4);
            String shortDescription = resultSet.getString(5);
            Transaction transaction = new Transaction(fromIBAN,toIBAN,amount,creationDate,shortDescription);
            transactions.add(transaction);
        }
    }

    public void getSavingsAccountFromDatabase() throws Exception
    {
        String interogare = "SELECT * FROM savingsaccount";
        ResultSet resultSet = databaseService.executeQuery(interogare);
        while (resultSet.next())
        {

            String IBAN = resultSet.getString(1);
            String BIC = resultSet.getString(2);
            String amount = resultSet.getString(3);
            Double suma = Double.parseDouble(amount);
            String name = resultSet.getString(4);
            int clientId = resultSet.getInt(5);
            String startDate = resultSet.getString(6);
            int interest = resultSet.getInt(7);
            SavingsAccount savingAccount = new SavingsAccount(IBAN,BIC,suma,name,clientId,startDate,interest);
            savingsAccounts.add(savingAccount);
        }
    }

    public void getAccountFromDatabase() throws  Exception
    {
        String interogare = "SELECT * FROM account";
        ResultSet resultSet = databaseService.executeQuery(interogare);
        while (resultSet.next())
        {

            String IBAN = resultSet.getString(1);
            String BIC = resultSet.getString(2);
            String amount = resultSet.getString(3);
            Double suma = Double.parseDouble(amount);
            String name = resultSet.getString(4);
            int clientId = resultSet.getInt(5);
            Account account = new Account(IBAN,BIC,suma,name,clientId);
            accounts.add(account);
            accountsMap.put(IBAN,account);
        }
    }
    public void getClientsFromDatabase() throws Exception{
       String interogare = "SELECT * FROM CLIENT JOIN adress where client.clientId = adress.clientId";
       ResultSet resultSet = databaseService.executeQuery(interogare);
       while(resultSet.next())
       {
           int clientId = resultSet.getInt(1);
           String firstName = resultSet.getString(2);
           String lastName = resultSet.getString(3);
           String CNP = resultSet.getString(4);
           String phone = resultSet.getString(5);
           String street = resultSet.getString(6);
           String city = resultSet.getString(7);
           String country = resultSet.getString(8);
           Adress adress = new Adress(street,city,country);
           Client client = new Client(clientId,firstName,lastName,CNP,phone,adress);
           clients.add(client);
       }

    }
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

    public void createClient(Scanner in) throws ParseException, SQLException {
        Client newClient = clientFactory.createClient(in);
        this.clients.add(newClient);
        String clientId = String.valueOf(newClient.getClientId());
        var firstName = newClient.getFirstName();
        var lastName = newClient.getLastName();
        var CNP = newClient.getCNP();
        var phone = newClient.getPhone();
        var adress = newClient.getAdress();
        var street = adress.getStreet();
        var city = adress.getCity();
        var country = adress.getCountry();
        System.out.println("Client adaugat");
        String interogare = "Select count(clientId) from client";
        ResultSet resultSet = databaseService.executeQuery(interogare);
        resultSet.next();
        String nr = resultSet.getString(1);
        clientId = "'" + nr + "'";
        firstName = "'" + firstName + "'";
        lastName = "'" + lastName + "'";
        CNP = "'" + CNP + "'";
        phone = "'" + phone + "'";
        String query1 = "insert into client(clientId, firstName, lastName, CNP, phone) values (" + clientId + " ,  " + firstName + " , "+ lastName +" , "+ CNP  +" , " + phone +")";
        System.out.println(query1);
        databaseService.executeUpdate(query1);

        street = "'" + street + "'";
        city = "'" + city + "'";
        country = "'" + country + "'";
        String query2 = "insert into adress(street,city,country,clientId) values (" + street + " , + " + city + " , "+ country +" , " + clientId + ")";
        databaseService.executeUpdate(query2);
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
        if(clients.size()<0)
        {
            System.out.println("Nu avem inca clienti in baza de date");
            return;
        }
        var client = this.getClientFromInput(in);
        System.out.println("Titularul contului este: " + client.getLastName());
        Account newAccount = this.accountFactory.createAccount(client.getLastName() , client.getClientId());
        int nr_conturi = accounts.size() + savingsAccounts.size();
        var IBAN = newAccount.getIBAN();
        IBAN = newAccount.generateIBAN(nr_conturi);
        newAccount.setIBAN(IBAN);
        var BIC = newAccount .getBIC();
        BIC = newAccount.generateBIC(nr_conturi);
        newAccount.setBIC(BIC);
        String amount = String.valueOf(newAccount.getAmount());
        var name = newAccount.getName();
        String clientId = String.valueOf(client.getClientId());
        accounts.add(newAccount);
        accountsMap.put(newAccount.getIBAN(), newAccount);
        System.out.println("Cont creat");

        clientId = "'" + clientId + "'";
        name = "'" + name + "'";
        IBAN = "'" + IBAN + "'";
        BIC = "'" + BIC + "'";
        String query = "insert into account(IBAN, BIC, amount, name, clientID) values (" + IBAN + " , + " + BIC + " , "+ amount +" , "+ name +" , "+ clientId +")";
        databaseService.executeUpdate(query);

    }

    public void createClientSavingsAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        System.out.println("Titular cont economii este: " + client.getLastName());
        SavingsAccount newSavingsAccount = this.accountFactory.createSavingsAccount(client.getLastName(), client.getClientId());
        System.out.println("Cont economii creeat");
        int nr_conturi = accounts.size() + savingsAccounts.size();
        var IBAN = newSavingsAccount.getIBAN();
        IBAN = newSavingsAccount.generateIBAN(nr_conturi+1);
        newSavingsAccount.setIBAN(IBAN);
        var BIC = newSavingsAccount .getBIC();
        BIC = newSavingsAccount.generateBIC(nr_conturi+1);
        newSavingsAccount.setBIC(BIC);
        String amount = String.valueOf(newSavingsAccount.getAmount());
        var name = newSavingsAccount.getName();
        String clientId = String.valueOf(client.getClientId());
        clientId = "'" + clientId + "'";
        name = "'" + name + "'";
        IBAN = "'" + IBAN + "'";
        BIC = "'" + BIC + "'";
        String startDate = String.valueOf(newSavingsAccount.getStartDate());
        String interest = String.valueOf(newSavingsAccount.getInterest());
        startDate = "'" + startDate + "'";
        interest = "'" + interest + "'";
        String query = "insert into savingsaccount(IBAN, BIC, amount, name, clientID,startDate,interest) values (" + IBAN + " , + " + BIC + " , "+ amount +" , "+ name +" , "+ clientId + " , " + startDate + " , " + interest + ")";
        System.out.println(query);
        databaseService.executeUpdate(query);
        System.out.println(newSavingsAccount);
        this.savingsAccounts.add(newSavingsAccount);
    }

    public void createClientCard(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);
        System.out.println("Detinator card este : " + client.getLastName());
        account.addCard(client.getLastName());
    }

    public void loadClientAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        System.out.println("Cati bani vrei sa depui?: ");
        double amount = Double.parseDouble(in.nextLine());
        Scanner sc = new Scanner(System.in);
        System.out.println("In ce cont doresti sa incarci? ");
        var IBAN = sc.next();
        var clientAccounts = client.filterAccounts(this.accounts);
        for(int i = 0; i < clientAccounts.size();++i)
            if (clientAccounts.get(i).getIBAN().equals(IBAN))
            {
                clientAccounts.get(i).setAmount(amount);
                String interogare = "Select amount from account where IBAN =" + "'" + IBAN + "'";
                System.out.println(interogare);
                ResultSet resultSet = databaseService.executeQuery(interogare);
                resultSet.next();
                double suma = resultSet.getDouble(1);
                suma = suma + amount;
                String sumaa = String.valueOf(suma);
                sumaa = "'" + sumaa + "'";
                interogare = "Update account set amount =" + sumaa + "where IBAN = " + "'" + IBAN + "'";
                System.out.println(interogare);
                databaseService.executeUpdate(interogare);
                var fromIBAN = "Depunere in bancomat";
                fromIBAN = "'" + fromIBAN + "'";
                var toIBAN = IBAN;
                toIBAN = "'" + toIBAN + "'";
                var valoare = sumaa;
                var shortDescription = "depunere";
                shortDescription = "'" + shortDescription + "'";
                Transaction transaction = new Transaction(fromIBAN,toIBAN,suma,shortDescription);
                var creationDate = transaction.getCreationDate();
                creationDate = "'" + creationDate + "'";
                interogare = "insert into transaction(fromIBAN,toIBAN,amount,creationDate,shortDescription) values (" + fromIBAN + " ,  " + toIBAN + " , "+ sumaa +" , "+ creationDate +" , "+ shortDescription + ")";
                databaseService.executeUpdate(interogare);
            }
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
        System.out.println(account1);
        System.out.println(account2);
        if(IBAN1.equals(IBAN2))
            throw new Exception("Nu poti trimite catre acelasi cont");
        if(account1==null || account2==null)
            throw new Exception("Nu a fost gasit IBAN-ul!");
        if(account1.getAmount() < amount)
            throw new Exception("Fonduri insuficiente!");

        account1.setAmount(account1.getAmount() - amount);
        account2.setAmount(account2.getAmount() + amount);

        String interogare = "Select amount from account where IBAN =" + "'" + IBAN1 + "'";
        System.out.println(interogare);
        ResultSet resultSet = databaseService.executeQuery(interogare);
        resultSet.next();
        double suma = resultSet.getDouble(1);
        suma = suma - amount;
        String sumaa = String.valueOf(suma);
        sumaa = "'" + sumaa + "'";
        interogare = "Update account set amount =" + sumaa + "where IBAN = " + "'" + IBAN1 + "'";
        System.out.println(interogare);
        databaseService.executeUpdate(interogare);
        interogare = "Select amount from account where IBAN =" + "'" + IBAN2 + "'";
        System.out.println(interogare);
        resultSet = databaseService.executeQuery(interogare);
        resultSet.next();
        suma = resultSet.getDouble(1);
        suma = suma + amount;
        sumaa = String.valueOf(suma);
        sumaa = "'" + sumaa + "'";
        interogare = "Update account set amount =" + sumaa + "where IBAN = " + "'" + IBAN2 + "'";
        System.out.println(interogare);
        databaseService.executeUpdate(interogare);
        var fromIBAN = "'" + IBAN1 + "'";
        var toIBAN = IBAN2;
        toIBAN = "'" + toIBAN + "'";
        var valoare = sumaa;
        var shortDescription = "Transfer bancar";
        shortDescription = "'" + shortDescription + "'";
        Transaction transaction = new Transaction(fromIBAN,toIBAN,suma,shortDescription);
        var creationDate = transaction.getCreationDate();
        creationDate = "'" + creationDate + "'";
        interogare = "insert into transaction(fromIBAN,toIBAN,amount,creationDate,shortDescription) values (" + fromIBAN + " ,  " + toIBAN + " , "+ sumaa +" , "+ creationDate +" , "+ shortDescription + ")";
        databaseService.executeUpdate(interogare);
        this.transactions.add(new Transaction(IBAN1, IBAN2, amount, description));
        System.out.println("Tranzactie cu succes");
    }

    public void closeAccount(Scanner in) throws Exception {
        var client = this.getClientFromInput(in);
        var account = this.getAccountFromInput(in, client);
        System.out.println(client.filterAccounts(this.accounts).size());
        if(client.filterAccounts(this.accounts).size()<1)
            throw new Exception("Trebuie sa fie macar un cont asociat pentru a putea inchide!");
        if(account.getAmount()!=0)
            throw new Exception("Mai aveti bani in cont!Trebuie sa i scoateti pentru a putea inchide!");
        this.accountsMap.remove(account.getIBAN());
        this.accounts.remove(account);
        var IBAN = account.getIBAN();
        String interogare = "delete from account where IBAN= " + "'" + IBAN + "'";
        databaseService.executeUpdate(interogare);
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

    // option 13: Read ELements
    public void readFromTable() throws SQLException {
        System.out.println("Ai apasat pe afisarea elementelor dintr-un tabel: \n");
        System.out.println("Tabelul de pe care doriti vedeti afisarile: ");
        Scanner sc = new Scanner(System.in);
        String table = sc.next();
        databaseService.readResources(table);
    }

    public void editFromTable() throws SQLException {
        System.out.println("Ai apasat pe editeaza tabel. \n");
        System.out.println("Tabelul in care doriti sa editati: ");
        Scanner sc = new Scanner(System.in);
        String table = sc.next();
        System.out.println("Cheia primara din acel tabel: ");
        String primaryKey = sc.next();
        System.out.println("Campurile pe care doriti sa le editati separate prin spatiu. (Exemplu: name Vlad clientId 2");
        sc.nextLine();
        String line = sc.nextLine();
        String[] splited = line.split("\\s+");
        HashMap<String, String> fields = new HashMap<>();
        for(int i = 0; i < splited.length; i = i + 2) {
            fields.put(splited[i], splited[i+1]);
        }
        System.out.println(fields);
        databaseService.editResource(table, fields, primaryKey);
    }
    public void deleteFromTable() throws SQLException {
        System.out.println("Ati apasat pe stergere: \n");
        System.out.println("Tabelul in care doriti sa faceti stergerea: ");
        Scanner sc = new Scanner(System.in);
        String table = sc.next();
        System.out.println("Cheia primara din acel tabel: ");
        String primaryKey = sc.next();
        databaseService.deleteResource(table, primaryKey);
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
