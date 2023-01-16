package phonebook;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String DIRECTORY_FILEPATH = "/Users/kirill/Desktop/directory.txt";
    private static final String FIND_FILEPATH = "/Users/kirill/Desktop/find.txt";

    private static List<Contact> directory = FileReader.read(DIRECTORY_FILEPATH);
    private static List<Contact> find = FileReader.read(FIND_FILEPATH);


    public static void main(String[] args) {

        System.out.println("Start searching (linear search)...");
        long searchStart = System.currentTimeMillis();

        int found = Algorithms.linearSearch(directory, find);

        long searchEnd = System.currentTimeMillis();

        long linearSearchTime = searchEnd - searchStart;
        System.out.printf("Found %d / %d entities. Time taken: %s%n",
                found, find.size(), formatTime(linearSearchTime));

        System.out.println();

        System.out.println("Start searching (bubble sort + jump search)...");
        long sortStart = System.currentTimeMillis();

        boolean isBubbleSortFinished = Algorithms.bubbleSort(directory, linearSearchTime);

        long sortEnd = System.currentTimeMillis();

        long sortingTime = sortEnd - sortStart;

        if (isBubbleSortFinished) {
            found = 0;
            searchStart = System.currentTimeMillis();

            for (Contact contact : find) {
                if (Algorithms.jumpSearch(directory, contact) > -1) {
                    found++;
                }
            }

            searchEnd = System.currentTimeMillis();
        }

        long searchingTime = searchEnd - searchStart;

        System.out.printf("Found %d / %d entities. Time taken: %s%n",
                found, find.size(), formatTime(sortingTime + searchingTime));

        System.out.printf("Sorting time: %s", formatTime(sortingTime));
        System.out.println(isBubbleSortFinished ? "" : " - STOPPED, moved to linearSearch");
        System.out.printf("Searching time: %s%n", formatTime(searchingTime));

        System.out.println();

        // Refreshing the directory after bubble sort...
        directory = FileReader.read(DIRECTORY_FILEPATH);

        System.out.println("Start searching (quick sort + binary search)...");
        sortStart = System.currentTimeMillis();

        Algorithms.quickSort(directory, 0, directory.size() - 1);

        sortEnd = System.currentTimeMillis();

        sortingTime = sortEnd - sortStart;

        found = 0;

        searchStart = System.currentTimeMillis();

        for (Contact contact : find) {
            if (Algorithms.binarySearch(directory, contact) > -1) {
                found++;
            }
        }

        searchEnd = System.currentTimeMillis();

        searchingTime = searchEnd - searchStart;

        System.out.printf("Found %d / %d entities. Time taken: %s%n",
                found, find.size(), formatTime(sortingTime + searchingTime));
        System.out.printf("Sorting time: %s%n", formatTime(sortingTime));
        System.out.printf("Searching time: %s%n", formatTime(searchingTime));

        System.out.println();

        System.out.println("Start searching (hash table)...");

        long createStart = System.currentTimeMillis();

        // Creating the directory as a hash map...
        Map<String, String> directoryMap = FileReader.readDirectoryAsMap(directory);

        long createEnd = System.currentTimeMillis();

        long creatingTime = createEnd - createStart;

        searchStart = System.currentTimeMillis();

        found = 0;
        for (Contact contact : find) {
            if (directoryMap.containsKey(contact.getName())) {
                found++;
            }
        }

        searchEnd = System.currentTimeMillis();

        searchingTime = searchEnd - searchStart;

        System.out.printf("Found %d / %d entities. Time taken: %s%n",
                found, find.size(), formatTime(creatingTime + searchingTime));
        System.out.printf("Creating time: %s%n", formatTime(creatingTime));
        System.out.printf("Searching time: %s", formatTime(searchingTime));
    }

    private static String formatTime(long millis) {
        Duration duration = Duration.ofMillis(millis);

        int minutes = duration.toMinutesPart();
        int seconds = duration.toSecondsPart();
        millis = duration.toMillisPart();

        return String.format("%d min. %d sec. %d ms.",
                minutes, seconds, millis);
    }
}