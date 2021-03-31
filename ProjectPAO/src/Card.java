import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class Card {
    private final int cardId;
    private final int CVV;
    private String number;
    private String name;
    private String IBAN;
    private final Date expirationDate;
    static private final Set<String> usedNumbers = new HashSet<>();


    public Card(int cardId, String IBAN, String name){
        this.cardId = cardId;
        this.IBAN = IBAN;
        this.name = name;
        this.number = generateCardNumber();

        while(usedNumbers.contains(this.number))
            this.number = this.generateCardNumber();
        usedNumbers.add(this.number);

        this.CVV = genererateCVV();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 4);
        this.expirationDate = calendar.getTime();
    }

    private String generateCardNumber() {
        byte[] vec = new byte[16];
        Random r = new Random();
        r.nextBytes(vec);
        return new String(vec, StandardCharsets.UTF_8);
    }

    private int genererateCVV(){
        var random = new Random();
        return 100 + random.nextInt(899);
    }

    public void Read(Scanner in){
        System.out.println("IBAN: ");
        this.IBAN = in.nextLine();
        System.out.println("Titular card: ");
        this.name = in.nextLine();
    }

    public int getCardId() {
        return cardId;
    }

    public int getCVV() {
        return CVV;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}
