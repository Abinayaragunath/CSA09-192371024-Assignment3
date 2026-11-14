
import java.io.*;
import java.nio.file.*;

public class SalesReportDemo {

    static void createInputFiles() throws IOException {
        Files.createDirectories(Path.of("Input"));
        Files.createDirectories(Path.of("Output"));

        Files.writeString(Path.of("Input/sales.txt"),
                "Laptop,50000\nPhone,20000\nTablet,15000\n");

        Files.writeString(Path.of("Input/sales-error.txt"),
                "Laptop,50000\nERROR\nTablet,15000\n");
    }

    // Traditional try-catch-finally
    static void manualProcessing(String input, String output) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = Files.newBufferedReader(Path.of(input));
            writer = Files.newBufferedWriter(Path.of(output));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("ERROR")) {
                    throw new IOException("Corrupt sales record");
                }
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Manual processing successful.");

        } catch (IOException e) {
            System.out.println("Manual processing error: " + e.getMessage());

        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                System.out.println("Manual resources closed.");
            } catch (IOException e) {
                System.out.println("Manual closing error: " + e.getMessage());
            }
        }
    }

    // Automatic resource management
    static void automaticProcessing(String input, String output) {
        try (
            BufferedReader reader =
                Files.newBufferedReader(Path.of(input));
            BufferedWriter writer =
                Files.newBufferedWriter(Path.of(output))
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("ERROR")) {
                    throw new IOException("Corrupt sales record");
                }

                writer.write(line);
                writer.newLine();
            }

            System.out.println("Try-with-resources processing successful.");

        } catch (IOException e) {
            System.out.println(
                "Try-with-resources error: " + e.getMessage()
            );
        }

        System.out.println("Try-with-resources closed automatically.");
    }

    public static void main(String[] args) {

        try {
            createInputFiles();

            System.out.println("TEST 1: MANUAL RESOURCE MANAGEMENT");
            manualProcessing(
                "Input/sales.txt",
                "Output/manual-report.txt"
            );

            System.out.println("\nTEST 2: TRY-WITH-RESOURCES");
            automaticProcessing(
                "Input/sales.txt",
                "Output/automatic-report.txt"
            );

            System.out.println("\nTEST 3: FAILURE DURING PROCESSING");
            automaticProcessing(
                "Input/sales-error.txt",
                "Output/error-report.txt"
            );

        } catch (IOException e) {
            System.out.println("Setup error: " + e.getMessage());
        }
    }
}
