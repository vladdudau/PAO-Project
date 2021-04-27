import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean sfarsit = false;
        BankOperations bankOperations = new BankOperations();

        ClientSingleton.getInstance().loadFromCSV();
        AccountSingleton.getInstance().loadFromCSV();
        SavingsAccountSingleton.getInstance().loadFromCSV();
        TransactionSingleton.getInstance().loadFromCSV();
        Audit audit = new Audit();

        bankOperations.setClients(ClientSingleton.getInstance().getClients());
        bankOperations.setAccounts(AccountSingleton.getInstance().getAccounts());
        bankOperations.setSavingsAccounts(SavingsAccountSingleton.getInstance().getSavingsAccounts());
        bankOperations.setTransactions(TransactionSingleton.getInstance().getTransactions());
        bankOperations.linkAccounts();

        List<String> availableCommands = Arrays.asList("creeaza_client", "creeaza_card_client", "informatii_client", "depozit_client", "conturi_client", "depunde_in_cont", "creeaza_tranzactie", "creeaza_cont", "creeaza_cont_economii", "inchide_cont", "istoric_tranzactii", "sfarsit");

        while (!sfarsit){
            System.out.println("Insert command: (help - see commands)");
            String command = in.nextLine().toLowerCase(Locale.ROOT);
            try{
                switch (command) {
                    case "creeaza_client" ->  bankOperations.createClient(in);
                    case "creeaza_card_client" ->  bankOperations.createClientCard(in);
                    case "informatii_client" ->  bankOperations.getClient(in);
                    case "depozit_client" ->  bankOperations.getClientAmount(in);
                    case "conturi_client" ->  bankOperations.getClientAccounts(in);
                    case "cont_client" ->  bankOperations.getClientAccount(in);
                    case "cont_economii" ->  bankOperations.getClientSavingsAccounts(in);
                    case "depunde_in_cont" ->  bankOperations.loadClientAccount(in);
                    case "creeaza_tranzactie" ->  bankOperations.createTransaction(in);
                    case "creeaza_cont" ->  bankOperations.createClientAccount(in);
                    case "creeaza_cont_economii" ->  bankOperations.createClientSavingsAccount(in);
                    case "inchide_cont" ->  bankOperations.closeAccount(in);
                    case "istoric_tranzactii" ->  bankOperations.getClientTransactions(in);
                    case "ajutor" -> System.out.println(availableCommands.toString());
                    case "sfarist" -> sfarsit = true;
                }
                if(availableCommands.contains(command))
                    audit.logAction(command);
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }

        ClientSingleton.getInstance().setClients( bankOperations.getClients());
        AccountSingleton.getInstance().setAccounts( bankOperations.getAccounts());
        SavingsAccountSingleton.getInstance().setSavingsAccounts( bankOperations.getSavingsAccounts());
        TransactionSingleton.getInstance().setTransactions( bankOperations.getTransactions());

        ClientSingleton.getInstance().dumpToCSV();
        AccountSingleton.getInstance().dumpToCSV();
        SavingsAccountSingleton.getInstance().dumpToCSV();
        TransactionSingleton.getInstance().dumpToCSV();
    }
}