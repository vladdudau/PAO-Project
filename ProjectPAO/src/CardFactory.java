public class CardFactory {
    private static int uniqueId = 0;
    public Card createCard(String IBAN, String name){
        return new Card(uniqueId++,IBAN,name);
    }
}
