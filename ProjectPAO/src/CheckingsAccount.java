public class CheckingsAccount extends Account{
    private final int maxAllowed;

    public CheckingsAccount(String name, int uniqueId) {
        super(name,uniqueId);
        this.maxAllowed = 5000;
        this.IBAN = "ROCHECKINGS" + uniqueId;
        this.BIC = "BICVLDCHCKINGS" + uniqueId;
    }

    @Override
    public String toString() {
        return "{" +
                "Numele contului este: " + name +
                ", IBANUL este = '" + IBAN + '\'' +
                ", BICUL este ='" + BIC + '\'' +
                ", suma ramasa este = " + amount +
                ", suma maxima pe care o poti scoate: " + maxAllowed +
                '}';
    }

    public int getMaxAllowed() {
        return maxAllowed;
    }
}
