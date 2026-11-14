
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class ConcurrentProcessor {

    static class ProcessingResult {
        String fileName;
        boolean success;
        String message;

        ProcessingResult(String fileName, boolean success, String message) {
            this.fileName = fileName;
            this.success = success;
            this.message = message;
        }
    }

    static class ScoreProcessingException extends Exception {
        ScoreProcessingException(String message) {
            super(message);
        }
    }

    static ProcessingResult processFile(Path file)
            throws ScoreProcessingException {

        String name = file.getFileName().toString();

        try {
            String content = Files.readString(file);

            if (content.contains("CORRUPT")) {
                throw new ScoreProcessingException("Corrupt score file");
            }

            if (content.isBlank()) {
                throw new ScoreProcessingException("Empty score file");
            }

            return new ProcessingResult(name, true, "Processed successfully");

        } catch (IOException e) {
            throw new ScoreProcessingException(
                "Unable to read file: " + e.getMessage()
            );
        }
    }

    static void createFiles() throws IOException {

        Path input = Path.of("Input");
        Files.createDirectories(input);

        // Generate 10,000 score files
        for (int i = 1; i <= 10000; i++) {
            Path file = input.resolve("answer" + i + ".txt");

            if (i % 1000 == 0) {
                Files.writeString(file, "CORRUPT SCORE DATA");
            } else {
                Files.writeString(file, "Student score: " + (i % 100));
            }
        }

        // Create a missing-file test reference separately
        System.out.println("Created 10000 score files.");
    }

    static void runProcessor(int poolSize) throws Exception {

        ExecutorService executor =
                Executors.newFixedThreadPool(poolSize);

        List<Future<ProcessingResult>> futures = new ArrayList<>();

        long start = System.currentTimeMillis();

        try (var files = Files.list(Path.of("Input"))) {
            files.filter(path -> path.toString().endsWith(".txt"))
                 .forEach(file ->
                    futures.add(executor.submit(() -> processFile(file)))
                 );
        }

        int success = 0;
        int failure = 0;

        for (Future<ProcessingResult> future : futures) {
            try {
                ProcessingResult result = future.get();

                if (result.success) {
                    success++;
                }

            } catch (ExecutionException e) {
                failure++;
            }
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long end = System.currentTimeMillis();

        System.out.println("\nThread pool size: " + poolSize);
        System.out.println("Successful files: " + success);
        System.out.println("Failed files: " + failure);
        System.out.println("Total files: " + (success + failure));
        System.out.println("Processing time: " + (end - start) + " ms");
    }

    public static void main(String[] args) throws Exception {

        createFiles();

        System.out.println("CONCURRENT FAULT-TOLERANT PROCESSOR");

        runProcessor(1);
        runProcessor(2);
        runProcessor(4);
        runProcessor(8);
    }
}
