import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean end = false;
        BankOperations bankOperations = new BankOperations();

        while (!end){
            System.out.println("Buna ziua! Ce operatiune doriti sa efectuati? : ");
            String command = in.nextLine().toLowerCase(Locale.ROOT);
            try{
                switch (command) {
                    case "creaza_client" -> bankOperations.createClient(in);
                    case "creaza_card_client" -> bankOperations.createClientCard(in);
                    case "afiseaza_client" -> bankOperations.showClient(in);
                    case "afiseaza_depozit_client" -> bankOperations.showClientAmount(in);
                    case "afiseaza_conturi_client" -> bankOperations.showClientAccounts(in);
                    case "afiseaza_conturi_economii" -> bankOperations.showClientSavingsAccounts(in);
                    case "afiseaza_conturi_checkings" -> bankOperations.showClientCheckingAccounts(in);
                    case "afiseaza_anumit_cont" -> bankOperations.showClientAccount(in);
                    case "depune_in_cont" -> bankOperations.loadClientAccount(in);
                    case "creaza_tranzactie" -> bankOperations.createTransaction(in);
                    case "creaza_cont_economii" -> bankOperations.createClientSavingsAccount(in);
                    case "creaza_cont" -> bankOperations.createClientAccount(in);
                    case "creaza_cont_checkings" -> bankOperations.createClientCheckingAccount(in);
                    case "inchide_cont" -> bankOperations.closeAccount(in);
                    case "afiseaza_tranzactii" -> bankOperations.showClientTransactions(in);
                    case "end" -> end = true;
                }
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }
}