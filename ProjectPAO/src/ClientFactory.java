import java.text.ParseException;
import java.util.Scanner;

public class ClientFactory {
    private static int uniqueId = 0;

    public static void incrementUniqueId(int inc) {
        ClientFactory.uniqueId += inc;
    }

    public Client createClient(String firstName, String lastName, String CNP, String email, String phone, Adress adress){
        return new Client(uniqueId++ , firstName,lastName,CNP,email,phone,adress);
    }
    public Client createClient(Scanner in) throws ParseException{
        return new Client(uniqueId++, in);
    }
}
