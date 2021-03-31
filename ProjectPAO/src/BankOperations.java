import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankOperations {
    private final List<Client> clients = new ArrayList<>();
    private final ClientFactory clientFactory = new ClientFactory();

    public List<Client> getClients()
    {
        return clients;
    }

    private Client getClient(Scanner in) throws Exception{
        if(this.clients.size()==0)
            throw new Exception("Lista clientilor este goala!");
        if(this.clients.size()==1)
            return clients.get(0);
        System.out.println("Id Client [0-"+(this.clients.size()-1)+"]: ");
        int clientId = Integer.parseInt(in.nextLine());
        return clients.get(clientId);
    }

    private Account getAccount(Scanner in, Client client) throws Exception{
        System.out.println("Alege IBAN: ");
        var IBAN = in.nextLine();
        return client.getAccountsMap().get(IBAN);
    }

    public void createClient(Scanner in) throws ParseException{
        this.clients.add(clientFactory.createClient(in));
        System.out.println("Client adaugat");
    }

    public void showClient(Scanner in) throws Exception
    {
        var client = this.getClient(in);
        System.out.println(client.toString());
    }

    public void showClientAmount(Scanner in) throws Exception{
        var client = this.getClient(in);
        System.out.println(client.getFirstName() + " " + client.getLastName() + " are un numar total de : " + client.getTotalAmount() + " lei in conturi.");
    }

    public void showClientAccounts(Scanner in) throws Exception {
        var client = this.getClient(in);
        System.out.println(client.getAccounts().toString());
    }

    public void showClientSavingsAccounts(Scanner in) throws Exception{
        var client = this.getClient(in);
        System.out.println(client.getSavingsAccounts().toString());
    }

    public void showClientCheckingAccounts(Scanner in) throws  Exception{
        var client = this.getClient(in);
        System.out.println(client.getCheckingAccounts().toString());
    }

    public void createClientAccount(Scanner in) throws Exception {
        var client = this.getClient(in);
        System.out.println("Titular cont: ");
        String name = in.nextLine();
        client.createCustomerAccount(name);
        System.out.println("Cont creat");
    }

    public void createClientSavingsAccount(Scanner in) throws Exception {
        var client = this.getClient(in);
        System.out.println("Titular cont economii: ");
        String name = in.nextLine();
        client.createSavingsAccount(name);
        System.out.println("Cont economii creat!");
    }

    public void createClientCheckingAccount(Scanner in) throws Exception {
        var client = this.getClient(in);
        System.out.println("Titular cont checkings: ");
        String name = in.nextLine();
        client.createCheckingAccount(name);
        System.out.println("Cont checkings creat");
    }

    public void createClientCard(Scanner in) throws Exception {
        var client = this.getClient(in);
        var account = this.getAccount(in, client);
        System.out.println("Detinator card: ");
        var name = in.nextLine();
        account.createCard(name);
    }

    public void loadClientAccount(Scanner in) throws Exception {
        var client = this.getClient(in);
        System.out.println("Suma pe care vrei sa o depui?: ");
        int amount = Integer.parseInt(in.nextLine());
        client.getAccounts().get(0).setAmount(amount);
        System.out.println("Suma a fost depusa!");
    }

    public void createTransaction(Scanner in) throws Exception {
        System.out.println("De la (IBAN): ");
        var IBAN1 = in.nextLine();
        System.out.println("Catre (IBAN): ");
        var IBAN2 = in.nextLine();
        System.out.println("Suma: ");
        int amount = Integer.parseInt(in.nextLine());
        System.out.println("Descriere: ");
        String description = in.nextLine();
        Account account1 = null, account2 = null;
        for(var client: clients)
            if(client.getAccountsMap().containsKey(IBAN1))
                account1 = client.getAccountsMap().get(IBAN1);
        for(var client: clients)
            if(client.getAccountsMap().containsKey(IBAN2))
                account2 = client.getAccountsMap().get(IBAN2);
        if(account1==null || account2==null)
            throw new Exception("Nu s-a gasit IBANUL!");
        account1.createTransaction(account1, account2, amount, description);
        System.out.println("Tranzactie finalizata!");
    }

    public void closeAccount(Scanner in) throws Exception {
        var client = this.getClient(in);
        var account = this.getAccount(in, client);
        client.closeAccount(account.getIBAN());
        System.out.println("Cont inchis!");
    }

    public void showClientAccount(Scanner in) throws Exception{
        var client = this.getClient(in);
        var account = this.getAccount(in, client);
        System.out.println(account.toString());
    }

    public void showClientTransactions(Scanner in) throws Exception{
        var client = this.getClient(in);
        System.out.println("Afiseaza toate tranzactiile? (True/False)");
        boolean showAll = in.nextBoolean();
        if(showAll)
           client.getTransactionHistory();
        else{
            System.out.println("Selecteaza anul: ");
            var year = in.nextInt();
            client.getTransactionHistory(year);
        }
        System.out.println();
    }

}
