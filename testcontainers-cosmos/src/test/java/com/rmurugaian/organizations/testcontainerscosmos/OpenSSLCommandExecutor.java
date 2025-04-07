package com.rmurugaian.organizations.testcontainerscosmos;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class OpenSSLCommandExecutor {

    @SneakyThrows
    public static void fetchCertificateWithOpenSSL(final String host, final int port, final String outputPath) {
        final var command = String.format("openssl s_client -connect %s:%d </dev/null | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p'", host, port);

        // Execute the command using ProcessBuilder
        final var processBuilder = new ProcessBuilder("bash", "-c", command);
        processBuilder.redirectErrorStream(true); // Redirect error stream to standard output

        final var process = processBuilder.start();

        // Capture the output of the command
        final String output;
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            output = reader.lines().collect(Collectors.joining("""
                    
                    """));
        }

        // Wait for the process to complete
        final int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("Command execution failed with exit code " + exitCode);
        }

        // Write the certificate to the output file
        try (final var fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(output);
        }

        OpenSSLCommandExecutor.log.info("Certificate saved to {}", outputPath);
    }

}