import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {
    static List<Contact> contacts;
    static File file = new File("contacts.json");

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        contacts = getInputContactsFromJsonFile(file); // import contacts list from json file if exists

        while(true) {
            System.out.print("Do you want to add new contact? (y/n) ");
            if (checkInputAnswer(scanner)) break;
            Contact newContact = Contact.getInputcontact();
            contacts.add(newContact);
            appendContactToJsonFile(newContact, file);
            System.out.println("contact added successfully!");
        }

        printContacts();

        while (true) {
            System.out.println("Do you want to search for a contact? (y/n)");
            if (checkInputAnswer(scanner)) break;

            System.out.println("Enter the search criteria (name, id, email, phone): ");
            String searchBy = scanner.nextLine().toLowerCase();
            System.out.println("Enter the search value: ");
            String searchValue = scanner.nextLine();
            searchContact(searchBy, searchValue);
        }

        while (true) {
            System.out.println("Do you want to remove a contact? (y/n)");
            if (checkInputAnswer(scanner)) break;

            System.out.println("Enter the remove criteria (id, email): ");
            String removeBy = scanner.nextLine().toLowerCase();
            System.out.println("Enter the remove value: ");
            String removeValue = scanner.nextLine();

            Contact contactToRemove = null;

            if (removeBy.equals("id")) {
                contactToRemove = removeContactById(Integer.parseInt(removeValue));
            } else if (removeBy.equals("email")) {
                contactToRemove = removeContactByEmail(removeValue);
            }
            if (contactToRemove != null) {
                System.out.println("contact removed successfully! " + contactToRemove.toString());
            } else {
                System.out.println("contact not found!");
            }

        }
    }

    private static boolean checkInputAnswer(Scanner scanner) {
        String removeAnswer = scanner.nextLine().toLowerCase();

        while (!removeAnswer.equals("y") && !removeAnswer.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' or 'n'");
            removeAnswer = scanner.nextLine().toLowerCase();
        }

        return removeAnswer.equals("n");
    }

    public static Contact removeContactById(int idToRemove) throws IOException {
        Contact contactToRemove = getContactById(idToRemove);
        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            updateJsonFile(file, (ArrayList<Contact>) contacts);
        } else {
            return null;
        }
        return contactToRemove;
    }

    public static Contact removeContactByEmail(String email) throws IOException {
        Contact contactToRemove = getContactByEmail(email);
        if (contactToRemove != null) {
            contacts.remove(contactToRemove);
            updateJsonFile(file, (ArrayList<Contact>) contacts);
        } else {
            return null;
        }
        return contactToRemove;
    }

    public static void printContacts() {
        for (Contact contact : contacts) {
            System.out.println(contact.toString());
        }
    }

    public static void searchContact(String searchBy, String searchValue) {
        Contact searchContact;
        ArrayList<Contact> searchResults = new ArrayList<>();
        switch (searchBy) {
            case "name":
                searchResults = searchContactByName(searchValue);
                break;
            case "id":
                searchContact = getContactById(Integer.parseInt(searchValue));
                searchResults.add(searchContact);
                break;
            case "email":
                searchContact = getContactByEmail(searchValue);
                searchResults.add(searchContact);
                break;
            case "phone":
                searchContact = getContactByPhone(searchValue);
                searchResults.add(searchContact);
                break;
            default:
                System.out.println("Invalid search criteria");
        }

        for (Contact contact : searchResults) {
            if (contact == null) {
                System.out.println("Contact not found!");
                break;
            }
            System.out.println(contact.toString());
        }
    }

    public static ArrayList<Contact> searchContactByName(String name) {
        ArrayList<Contact> searchResults = new ArrayList<>();
        name = name.toLowerCase();
        for (Contact contact : contacts) {
            if (contact.getFirstName().toLowerCase().equals(name) || contact.getLastName().toLowerCase().equals(name)) {
                searchResults.add(contact);
            }
        }
        return searchResults;
    }

    public static Contact getContactById(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public static Contact getContactByEmail(String emailToSearch) {
        for (Contact contact : contacts) {
            if (contact.getEmail().equals(emailToSearch)) {
                return contact;
            }
        }
        return null;
    }

    public static Contact getContactByPhone(String phoneToSearch) {
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phoneToSearch)) {
                return contact;
            }
        }
        return null;
    }

    public static void appendContactToJsonFile(Contact newContact, File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode;
            // Check if the file exists and is not empty
            if (file.exists() && file.length() != 0) {
                rootNode = objectMapper.readTree(file);
            } else {
                // Create a new root node if the file is empty or doesn't exist
                rootNode = objectMapper.createObjectNode();
                ((ObjectNode) rootNode).putArray("contacts");
            }
            // Get the contacts array from the JSON object
            ArrayNode contactsArray = (ArrayNode) rootNode.path("contacts");
            // Create a new contact node
            ObjectNode contactNode = objectMapper.valueToTree(newContact);
            // Add the new contact to the contacts array
            contactsArray.add(contactNode);
            // Write the updated JSON back to the file
            objectMapper.writeValue(file, rootNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Contact> getInputContactsFromJsonFile(File file) {
        ArrayList<Contact> contacts = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = null;
            if (file.exists() && file.length() != 0) {
                rootNode = objectMapper.readTree(file);
                ArrayNode contactsArray = (ArrayNode) rootNode.path("contacts");
                if (!contactsArray.isEmpty()) {
                    for (JsonNode contact : contactsArray) {
                        // Extract the fields from each contact node
                        int newId = contact.path("id").asInt();  // Retrieve id from contact node
                        String newFirstName = contact.path("firstName").asText();
                        String newLastName = contact.path("lastName").asText();
                        int newAge = contact.path("age").asInt();
                        String newEmail = contact.path("email").asText();
                        String newPhoneNumber = contact.path("phoneNumber").asText();
                        String newAddress = contact.path("address").asText();

                        Contact newContact = new Contact(newId, newFirstName, newLastName, newAge, newEmail, newPhoneNumber, newAddress);
                        contacts.add(newContact);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public static void updateJsonFile(File file, ArrayList<Contact> updatedContacts) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(file);
        ArrayNode contactsArray = objectMapper.createArrayNode();

        for (Contact contact : updatedContacts) {
            JsonNode contactNode = objectMapper.valueToTree(contact);
            contactsArray.add(contactNode);
        }
        ((ObjectNode) rootNode).set("contacts", contactsArray);
        objectMapper.writeValue(file, rootNode);
    }
}














