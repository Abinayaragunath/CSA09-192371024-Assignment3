
import java.io.IOException;

public class ExaminationPortal {

    // Custom checked exception
    static class SubmissionStorageException extends Exception {
        SubmissionStorageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // Custom unchecked exception
    static class InvalidSubmissionException extends RuntimeException {
        InvalidSubmissionException(String message) {
            super(message);
        }
    }

    static class Storage {
        static void save(String fileName) throws IOException {
            if (fileName.equals("answer02.pdf")) {
                throw new IOException("Disk quota exceeded");
            }
            System.out.println("Saved " + fileName);
        }
    }

    static class SubmissionService {
        static void submit(String fileName)
                throws SubmissionStorageException {

            try {
                Storage.save(fileName);
            } catch (IOException e) {
                throw new SubmissionStorageException(
                    "Could not store " + fileName, e
                );
            }
        }
    }

    static class SubmissionController {
        static void handleSubmission(String fileName) {
            try {
                if (fileName == null || fileName.isBlank()) {
                    throw new InvalidSubmissionException(
                        "Invalid submission file"
                    );
                }

                SubmissionService.submit(fileName);

            } catch (SubmissionStorageException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Cause: " + e.getCause().getMessage());

            } catch (InvalidSubmissionException e) {
                System.out.println("Validation Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SubmissionController.handleSubmission("answer01.pdf");
        SubmissionController.handleSubmission("answer02.pdf");
        SubmissionController.handleSubmission("");
    }
}
