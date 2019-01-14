package de.struktuhr.backgrounddemo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public class DemoServiceProcessImpl implements DemoService {

    private final static Logger log = LoggerFactory.getLogger(DemoServiceProcessImpl.class);

    @Value("${app.ext.dir}")
    private String extDir;

    @Value("${app.ext.command}")
    private String extCommand;

    @Value("${app.work.dir}")
    private String workDir;


    @Override
    public String service(String input) {
        String output = null;

        // Contruct Paths
        final Charset cs = Charset.forName("UTF-8");
        final String uuid = UUID.randomUUID().toString();

        final Path pathIn = Paths.get(workDir, uuid  + "-in.txt");
        final Path pathOut = Paths.get(workDir, uuid  + "-out.txt");

        try {
            // Write payload to file
            Files.writeString(pathIn, input, cs);

            // Call external Process
            int status = callExternalProcess(pathIn.toString(), pathOut.toString());

            if (status == 0) {
                output = Files.readString(pathOut, cs);
            }
            else {
                throw new RuntimeException("Bad status " + status);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            cleanup(pathIn, pathOut);
        }

        return output;
    }

    private void cleanup(Path pathIn, Path pathOut) {
        saveCleanup(pathIn);
        saveCleanup(pathOut);
    }

    private void saveCleanup(Path path) {
        try {
            Files.deleteIfExists(path);
            log.info("File {} deleted", path.toString());
        }
        catch (IOException e) {
            log.warn("Cannot delete file  {}. {}", path.toString(), e.getMessage());
        }
    }

    private int callExternalProcess(String fileIn, String fileOut) throws IOException, InterruptedException {
        final String command = String.format(extCommand, fileIn, fileOut);

        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows()) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        builder.directory(new File(extDir));
        Process process = builder.start();
        StreamContentOutputter streamContentOutputter = new StreamContentOutputter(process.getInputStream(),
                s -> log.info("External Process {}", s));

        Executors.newSingleThreadExecutor().submit(streamContentOutputter);
        return process.waitFor();
    }

    private boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName.toLowerCase().startsWith("windows");
    }

    private static class StreamContentOutputter implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamContentOutputter(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

}
