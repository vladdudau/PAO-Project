

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ClientSingleton {

    private static ClientSingleton instance = null;

    final private ClientFactory clientFactory = new ClientFactory();
    private List<Client> clients = new ArrayList<Client>();

    public static ClientSingleton getInstance()
    {
        if (instance == null)
            instance = new ClientSingleton();
        return instance;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }

    private static List<String[]> getCSVColumns(String fileName){

        List<String[]> columns = new ArrayList<>();

        try(var in = new BufferedReader(new FileReader(fileName))) {

            String line;

            while((line = in.readLine()) != null ) {
                String[] fields = line.replaceAll(" ", "").split(",");
                columns.add(fields);
            }
        } catch (IOException e) {
            System.out.println("Nu s-a salvat niciun client!");
        }

        return columns;
    }

    public void loadFromCSV() {
            var columns = ClientSingleton.getCSVColumns("data/clients.csv");
            for(var fields : columns){
                Client newClient =  clientFactory.createClient(
                        fields[0],
                        fields[1],
                        fields[2],
                        fields[3],
                        fields[4],
                        new Adress(fields[5], fields[6], fields[7])
                );
                clients.add(newClient);
            }
            ClientFactory.incrementUniqueId(columns.size());

    }

    public void dumpToCSV(){
        try{
            var writer = new FileWriter("data/clients.csv");
            for(var client : this.clients){
                writer.write(client.toCSV());
                writer.write("\n");
            }
            writer.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
    }
}