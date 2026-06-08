import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * NotesApp - A text-based notes manager using Java File I/O
 * Demonstrates: FileWriter, FileReader, BufferedReader, BufferedWriter
 */
public class NotesApp {

    // File where all notes are saved
    private static final String NOTES_FILE = "notes.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("========================================");
        System.out.println("       Welcome to Java Notes App        ");
        System.out.println("========================================");

        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addNote(scanner);
                    break;
                case "2":
                    viewAllNotes();
                    break;
                case "3":
                    searchNote(scanner);
                    break;
                case "4":
                    deleteNote(scanner);
                    break;
                case "5":
                    clearAllNotes();
                    break;
                case "6":
                    System.out.println("\nGoodbye! Your notes are saved in \"" + NOTES_FILE + "\".");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid option. Please try again.\n");
            }
        }

        scanner.close();
    }

    // ─────────────────────────────────────────────
    //  MENU
    // ─────────────────────────────────────────────
    private static void printMenu() {
        System.out.println("\n-------- MENU --------");
        System.out.println("1. Add a new note");
        System.out.println("2. View all notes");
        System.out.println("3. Search notes");
        System.out.println("4. Delete a note");
        System.out.println("5. Clear all notes");
        System.out.println("6. Exit");
        System.out.println("----------------------");
    }

    // ─────────────────────────────────────────────
    //  1. ADD NOTE — uses FileWriter (append mode)
    // ─────────────────────────────────────────────
    private static void addNote(Scanner scanner) {
        System.out.print("\nEnter your note: ");
        String note = scanner.nextLine().trim();

        if (note.isEmpty()) {
            System.out.println("[!] Note cannot be empty.");
            return;
        }

        // Timestamp for each note
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String noteEntry = "[" + timestamp + "] " + note;

        // FileWriter in append mode (true = append, false = overwrite)
        try (FileWriter fw = new FileWriter(NOTES_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(noteEntry);
            bw.newLine(); // write OS-appropriate line separator

            System.out.println("[✔] Note saved successfully!");

        } catch (IOException e) {
            System.out.println("[✘] Error saving note: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  2. VIEW ALL NOTES — uses FileReader + BufferedReader
    // ─────────────────────────────────────────────
    private static void viewAllNotes() {
        System.out.println("\n======== All Notes ========");

        File file = new File(NOTES_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("(No notes found. Start by adding one!)");
            return;
        }

        // FileReader reads characters; BufferedReader reads line by line efficiently
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            int count = 1;

            while ((line = br.readLine()) != null) {
                System.out.println(count + ". " + line);
                count++;
            }

            System.out.println("===========================");
            System.out.println("Total notes: " + (count - 1));

        } catch (IOException e) {
            System.out.println("[✘] Error reading notes: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  3. SEARCH NOTES — reads file, filters lines
    // ─────────────────────────────────────────────
    private static void searchNote(Scanner scanner) {
        System.out.print("\nEnter keyword to search: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        if (keyword.isEmpty()) {
            System.out.println("[!] Search keyword cannot be empty.");
            return;
        }

        File file = new File(NOTES_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("(No notes to search.)");
            return;
        }

        System.out.println("\n-- Search Results for \"" + keyword + "\" --");
        int found = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(keyword)) {
                    System.out.println(lineNumber + ". " + line);
                    found++;
                }
                lineNumber++;
            }

        } catch (IOException e) {
            System.out.println("[✘] Error searching notes: " + e.getMessage());
        }

        if (found == 0) {
            System.out.println("No notes matched your search.");
        } else {
            System.out.println("Found " + found + " matching note(s).");
        }
    }

    // ─────────────────────────────────────────────
    //  4. DELETE A NOTE — read all, rewrite without deleted line
    // ─────────────────────────────────────────────
    private static void deleteNote(Scanner scanner) {
        // First, show all notes
        viewAllNotes();

        File file = new File(NOTES_FILE);
        if (!file.exists() || file.length() == 0) return;

        System.out.print("\nEnter the note number to delete (0 to cancel): ");
        int targetLine;
        try {
            targetLine = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid number.");
            return;
        }

        if (targetLine == 0) {
            System.out.println("Delete cancelled.");
            return;
        }

        // Read all lines into a list
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("[✘] Error reading notes: " + e.getMessage());
            return;
        }

        if (targetLine < 1 || targetLine > lines.size()) {
            System.out.println("[!] Note number out of range.");
            return;
        }

        // Remove the selected line (1-based index → 0-based)
        String removed = lines.remove(targetLine - 1);

        // Rewrite the file without the deleted line (overwrite mode: false)
        try (FileWriter fw = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("[✔] Deleted: " + removed);

        } catch (IOException e) {
            System.out.println("[✘] Error deleting note: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    //  5. CLEAR ALL NOTES — overwrite with empty content
    // ─────────────────────────────────────────────
    private static void clearAllNotes() {
        System.out.print("\nAre you sure you want to delete ALL notes? (yes/no): ");
        // (We'll use System.console() if available, else Scanner is already consumed above)
        Scanner confirm = new Scanner(System.in);
        // Note: reusing the outer scanner would be cleaner; shown separately for clarity
        String answer = confirm.nextLine().trim().toLowerCase();

        if (!answer.equals("yes")) {
            System.out.println("Clear cancelled.");
            return;
        }

        // Open FileWriter in overwrite mode (append=false), write nothing → empties file
        try (FileWriter fw = new FileWriter(NOTES_FILE, false)) {
            System.out.println("[✔] All notes cleared.");
        } catch (IOException e) {
            System.out.println("[✘] Error clearing notes: " + e.getMessage());
        }
    }
}
