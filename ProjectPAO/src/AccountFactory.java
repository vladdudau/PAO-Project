public class AccountFactory {
    private static int uniqueId = 0;

    public Account createAccount(String name){
        return new Account(name, uniqueId++);
    }
}
