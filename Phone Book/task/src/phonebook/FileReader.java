package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileReader {

    static List<Contact> read(String filepath) {
        File file = new File(filepath);
        List<Contact> contacts = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] contactDetails = scanner.nextLine().split(" ");

                String phoneNumber = null;
                String name;

                if (contactDetails[0].matches("\\d+")) {
                    phoneNumber = contactDetails[0];

                    if (contactDetails.length > 2) {
                        name = contactDetails[1] + " " + contactDetails[2];
                    } else {
                        name = contactDetails[1];
                    }
                } else {
                    if (contactDetails.length > 1) {
                        name = contactDetails[0] + " " + contactDetails[1];
                    } else {
                        name = contactDetails[0];
                    }
                }

                contacts.add(new Contact(phoneNumber, name));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        return contacts;
    }

    static Map<String, String> readDirectoryAsMap(List<Contact> directory) {
        Map<String, String> contacts = new HashMap<>();

        for (Contact contact : directory) {
            contacts.put(contact.getName(), contact.getPhoneNumber());
        }

        return contacts;
    }
}
