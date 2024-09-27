import java.util.Scanner;

public class Contact {
    private static int contactCount = 1;
    private final int id;
    private String firstName ="";
    private String lastName ="";
    private int age = 0;
    private String email = "";
    private String phoneNumber = "";
    private String address = "";

    public Contact(int id, String firstName, String lastName, int age, String email, String phoneNumber, String address) {
        if (contactCount <= id) {
            contactCount = id;
        }
        this.id = contactCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        contactCount++;
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return  this.lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String contactToString = "";
        contactToString += "Id: " + this.id +  ", Full name: " + this.getFullName() + ", age: " + this.getAge() + ", email: " + this.getEmail() + ", Phone: " + this.getPhoneNumber() + ", Address: " + this.getAddress();
        return contactToString;
    }

    public static Contact getInputcontact() {
        int id = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input new contact details: ");
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        return new Contact(id, firstName, lastName, age, email, phoneNumber, address);
    }



}














