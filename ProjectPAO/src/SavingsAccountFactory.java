public class SavingsAccountFactory {
    private static int uniqueId = 0;

    public SavingsAccount createSavingsAccount(String name){
        return new SavingsAccount(name, uniqueId++);
    }
}