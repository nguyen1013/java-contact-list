import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Person {
    private static int personCount = 0;
    public int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phoneNumber;
    private String address;

    public Person(String firstName, String lastName, int age, String email, String phoneNumber, String address) {
        personCount++;
        this.id = personCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
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
        String personToString = "";
        personToString += "Id: " + this.id +  ", Full name: " + this.getFullName() + ", age: " + this.getAge() + ", email: " + this.getEmail() + ", Phone: " + this.getPhoneNumber() + ", Address: " + this.getAddress();
        return personToString;
    }

    public static Person getInputPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter address: ");
        String address = scanner.nextLine();
        return new Person(firstName, lastName, age, email, phoneNumber, address);
    }

    public void writePersonToFile(Person person, String fileName) throws IOException {
        // Write person to file
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(person.getFirstName() + " " + person.getLastName() + "\n");
            bufferedWriter.write(person.getAge() + "\n");
            bufferedWriter.write(person.getEmail() + "\n");
            bufferedWriter.write(person.getPhoneNumber() + "\n");
            bufferedWriter.write(person.getAddress() + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writePersonToJsonFile(Person person, File file) throws IOException {
        // Write person to file
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}














