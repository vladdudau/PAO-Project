import java.util.Scanner;

public class Adress {
    private String street;
    private String city;
    private String country;

    public void Adress(String street, String city, String country)
    {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public void Read(Scanner in)
    {
        System.out.println("Strada : ");
        this.street = in.nextLine();
        System.out.println("Orasul este: ");
        this.city = in.nextLine();
        System.out.println("Tara: ");
        this.country = in.nextLine();
    }


    public Adress(Scanner in)
    {
        this.Read(in);
    }

    @Override
    public String toString() {
        return "{" +
                "Strada='" + street + '\'' +
                ", Oras='" + city + '\'' +
                ", Tara='" + country + '\'' +
                '}';
    }

    public String toCSV() {
        return street +
                "," + city +
                "," + country;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Adress(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }
}
