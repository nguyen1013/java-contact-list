import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Person> persons;
    public static void main(String[] args) throws IOException {
        persons = new ArrayList<Person>();
        Person person1 = new Person("Jack", "Sparow", 30, "jack@mail.fi", "0411234567", "Vaasa, Finland");
        Person person2 = new Person("David", "James", 28, "jame@mail.fi", "0421234568", "Turku, Finland" );
        Person person3 = new Person("Maria", "Anna", 26, "anna@mail.fi", "0412345679", "Helsinki, Finland");
        addPerson(person1);
        addPerson(person2);
        addPerson(person3);

        printPersons();

        Person searchPerson = getPersonByFirstName("jack");
        if (searchPerson != null) {
            System.out.println(searchPerson.toString());
        } else {
            System.out.println("Person not found");
        }

        File file = new File("contact.json");

        for (Person person : persons) {
            Person.writePersonToJsonFile(person, file);
        }

    }

    public static void addPerson(Person person) {
        persons.add(person);
    }

//    public static void removePerson(Person person) {
//        persons.remove(person);
//    }

    public static void printPersons() {
        for (Person person : persons) {
            System.out.println(person.toString());
        }
    }

    public static Person getPersonByFirstName(String firstNameToSearch) {
        firstNameToSearch = firstNameToSearch.toLowerCase();
        for (Person person : persons) {
            if (person.getFirstName().toLowerCase().equals(firstNameToSearch)) {
                return person;
            }
        }
        return null;
    }

    public static Person getPersonByLastName(String lastNameToSearch) {
        lastNameToSearch = lastNameToSearch.toLowerCase();
        for (Person person : persons) {
            if (person.getLastName().toLowerCase().equals(lastNameToSearch)) {
                return person;
            }
        }
        return null;
    }

    public static Person getPersonByEmail(String emailToSearch) {
        for (Person person : persons) {
            if (person.getEmail().equals(emailToSearch)) {
                return person;
            }
        }
        return null;
    }

    public static Person getPersonByPhone(String phoneToSearch) {
        for (Person person : persons) {
            if (person.getPhoneNumber().equals(phoneToSearch)) {
                return person;
            }
        }
        return null;
    }


}
