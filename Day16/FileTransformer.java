import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileTransformer {

    public static void main(String[] args) {
        Path inputPath = Paths.get("input1.txt");
        Path outputPath = Paths.get("output1.txt");

        ensureSampleInput(inputPath);

        try {
            transformFile(inputPath, outputPath);
            System.out.println("Transformation complete. Output written to: " + outputPath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void transformFile(Path input, Path output) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(
                     output,
                     StandardCharsets.UTF_8,
                     StandardOpenOption.CREATE,
                     StandardOpenOption.TRUNCATE_EXISTING
             )) {

            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();

                if (trimmed.isEmpty()) {
                    continue;
                }

                String outputLine = trimmed.toUpperCase();

                writer.write(outputLine);
                writer.newLine();
                lineNumber++;
            }


        }
    }

    private static void ensureSampleInput(Path input) {
        if (Files.exists(input)) return;

        try (BufferedWriter bw = Files.newBufferedWriter(
                input, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE_NEW
        )) {
            bw.write("  hello world  ");
            bw.newLine();
            bw.write("java i/o with buffering");
            bw.newLine();
            bw.write("");
            bw.newLine();
            bw.write("Accented: café résumé jalapeño");
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create sample input file: " + e.getMessage(), e);
        }
    }

}
