public class CheckingsAccountFactory {
    private static int uniqueId = 0;

    public CheckingsAccount createCheckingAccount(String name){
        return new CheckingsAccount(name, uniqueId++);
    }
}
