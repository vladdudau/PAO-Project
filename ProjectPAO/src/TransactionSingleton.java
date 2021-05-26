
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.text.ParseException;

public class TransactionSingleton {

    private static TransactionSingleton instance = null;
    private List<Transaction> transactions = new ArrayList<Transaction>();

    final private AccountFactory accountFactory = new AccountFactory();

    public static TransactionSingleton getInstance()
    {
        if (instance == null)
            instance = new TransactionSingleton();
        return instance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try (var in = new BufferedReader(new FileReader(fileName))) {

            String line;

            while((line = in.readLine()) != null ) {
                String[] fields = line.replaceAll(" ", "").split(",");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("Nicio tranzactie salvata!");
        }

        return columns;
    }

    public void loadFromCSV() {
        try {
            var columns = TransactionSingleton.getCSVColumns("data/transactions.csv");
            for(var fields : columns){
                var newTransaction = new Transaction(
                        fields[0],
                        fields[1],
                        Double.parseDouble(fields[2]),
                        fields[3],
                        fields[4]
                );
                transactions.add(newTransaction);
            }
        }catch (ParseException e){
            System.out.println("Nu s-au putut incarca tranzactii! - parse exception");
        } catch (Exception e) {
            System.out.println("Nu s-a putut parsa tranzactia - invalid format");
        }
    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("data/transactions.csv");
            for(var transaction : this.transactions){
                writer.write(transaction.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
