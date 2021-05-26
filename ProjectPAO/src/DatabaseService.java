import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class DatabaseService {
    private static DatabaseService instance = null;
    Connection connection;
    String url = "jdbc:mysql://localhost:3306/temaPAODB";
    String user = "root";
    String password = "vlad123";

    private DatabaseService() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
    }

    public static DatabaseService getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void readResources(String table) throws SQLException {
        if (table.equals("client") || table.equals("account") || table.equals("savingsaccount") || table.equals("transaction")) {
            ResultSet resultSet = executeQuery("SELECT * FROM " + table);

            if (table.equals("client"))
                resultSet = executeQuery("SELECT * FROM CLIENT JOIN adress where client.clientId = adress.clientId;");
            System.out.println("Doriti sa afisati toate datele din tabel sau doriti doar un id anume?");
            Scanner sc = new Scanner(System.in);
            var ok = "";
            System.out.println("Introduceti 1 daca da sau 2 daca nu");
            while (true) {
                ok = sc.next();
                ok = ok.strip();
                if (ok.equals("1") || ok.equals("2"))
                    break;
                else
                    System.out.println("Ai introdus o valoare gresita.Fii atent!");
            }

            if (ok.equals("1")) {
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int noColumns = rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= noColumns; i++) {
                        if (i > 1) {
                            System.out.print(",  ");
                        }
                        String value = resultSet.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + value);
                    }
                    System.out.println("");
                }
            } else {
                String primaryKeyName;
                switch (table) {
                    case "Client":
                        primaryKeyName = "clientId";
                        break;
                    case "Card":
                        primaryKeyName = "cardId";
                        break;
                    case "account":
                        primaryKeyName = "IBAN";
                        break;
                    case "transaction":
                        primaryKeyName = "toIBAN";
                        break;
                    default:
                        System.out.println("No such table.");
                        return;
                }
                System.out.println(primaryKeyName);
                System.out.println("ID la care se va face afisarea");
                String id = sc.next();
                String query = "select * from " + table + " where " + primaryKeyName + "=" + "'" + id + "';";
                System.out.println("Se va afisa contul cu IBAN-ul introdus, daca nu exista, nu se va afisa nimic");
                resultSet = executeQuery(query);
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int noColumns = rsmd.getColumnCount();
                while (resultSet.next()) {
                    for (int i = 1; i <= noColumns; i++) {
                        if (i > 1) {
                            System.out.print(",  ");
                        }
                        String value = resultSet.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + value);
                    }
                    System.out.println("");
                }


            }
        }
        else{
            System.out.println("Tabel inexistent");
            return;
        }
    }

    public void editResource(String table, HashMap<String, String> fields, String primaryKey) throws SQLException {
        String primaryKeyName;
        switch(table) {
            case "client":
                primaryKeyName = "clientId";
                break;
            case "card":
                primaryKeyName = "cardId";
                break;
            case "account":
                primaryKeyName = "IBAN";
                break;
            case "savingsaccount":
                primaryKeyName = "IBAN";
                break;
            case "transaction":
                primaryKeyName = "toIBAN";
                break;
            default:
                System.out.println("No such table.");
                return;
        }
        System.out.println("ID la care se va incerca updatarea");
        Scanner sc = new Scanner(System.in);
        String id  = sc.next();

        System.out.println(id);
        String query = "update " + table + " set";
        for(String fieldName: fields.keySet()) {
            query = query + " " + fieldName + " = '" + fields.get(fieldName) +   "' ,"  ;
        }
        query = query.substring(0, query.length() - 1);
        query = query + " where " + primaryKeyName + " = " + " '" + id + "'";
        System.out.println(query);
        executeUpdate(query);
    }

    public void deleteResource(String table, String primaryKey) throws SQLException {
        String primaryKeyName;
        switch(table) {
            case "client":
                primaryKeyName = "clientId";
                break;
            case "card":
                primaryKeyName = "cardId";
                break;
            case "account":
                primaryKeyName = "IBAN";
                break;
            case "savingsaccount":
                primaryKeyName = "IBAN";
                break;
            case "Transaction":
                primaryKeyName = "transactionId";
                break;
            default:
                System.out.println("No such table.");
                return;
        }
        System.out.println(table + " " + primaryKeyName);
        System.out.println("Id ul dupa care vom sterge");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        String query =  "delete from " + table + " where " + primaryKeyName +  " = " + "'" + id + "';";
        System.out.println(query);
        executeUpdate(query);
        if(table.equals("client"))
        {
            String query2 = "delete from adress where clientId = " + "'" + id + "'";
            executeUpdate(query2);
        }
    }

}