package phonebook;

import java.util.Collections;
import java.util.List;

// Sorting and searching algorithms live here :)
public class Algorithms {

    static int linearSearch(List<Contact> directory, List<Contact> find) {
        int found = 0;
        for (Contact toFind : find) {
            for (Contact contact : directory) {
                if (toFind.equals(contact)) {
                    found++;
                    break;
                }
            }
        }

        return found;
    }

    static int jumpSearch(List<Contact> directory, Contact contact) {
        int size = directory.size();
        int step = (int) Math.floor(Math.sqrt(size));
        int previousPosition = 0;

        while (directory.get(Math.min(step, size) - 1).compareTo(contact) < 0) {
            previousPosition = step;
            step += (int) Math.floor(Math.sqrt(size));

            if (previousPosition >= size) {
                return -1;
            }
        }

        while (directory.get(previousPosition).compareTo(contact) < 0) {
            previousPosition++;
            if (previousPosition == Math.min(step, size)) {
                return -1;
            }
        }

        if (directory.get(previousPosition).compareTo(contact) == 0) {
            return previousPosition;
        }

        return -1;
    }

    static boolean bubbleSort(List<Contact> directory, long linearSearchTime) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < directory.size(); i++) {
            if (System.currentTimeMillis() - start > linearSearchTime * 10) {
                return false;
            }

            for (int j = 0; j < directory.size() - i - 1; j++) {
                if (directory.get(j).compareTo(directory.get(j + 1)) > 0) {
                    Contact contact = directory.get(j);
                    directory.set(j, directory.get(j + 1));
                    directory.set(j + 1, contact);
                }
            }
        }

        return true;
    }

    static void quickSort(List<Contact> directory, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(directory, high, low);

            quickSort(directory, low, partitionIndex - 1);
            quickSort(directory, partitionIndex + 1, high);
        }
    }

    private static int partition(List<Contact> directory, int high, int low) {
        Contact pivotContact = directory.get(high);
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            if (directory.get(j).compareTo(pivotContact) < 0) {
                i++;
                Collections.swap(directory, i, j);
            }
        }

        Collections.swap(directory, i + 1, high);
        return i + 1;
    }

    static int binarySearch(List<Contact> directory, Contact contact) {
        int left = 0;
        int right = directory.size() - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            if (directory.get(middle).compareTo(contact) == 0) {
                return middle;
            } else if (directory.get(middle).compareTo(contact) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return -1;
    }
}