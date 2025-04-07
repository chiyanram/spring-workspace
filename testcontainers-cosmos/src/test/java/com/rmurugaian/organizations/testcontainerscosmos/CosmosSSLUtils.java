package com.rmurugaian.organizations.testcontainerscosmos;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Slf4j
public class CosmosSSLUtils {

    public static void setupTrustStore(final String emulatorHost, final int emulatorPort, final String keyStorePassword) throws Exception {
        // Step 1: Create a temporary directory for the KeyStore
        final var tempFolder = Files.createTempDirectory("cosmos-emulator-keystore");
        final var certFile = tempFolder.resolve("cosmos-emulator.cert");
        final var keyStoreFile = tempFolder.resolve("azure-cosmos-emulator.keystore");

        // Step 2: Fetch and save the emulator certificate
        OpenSSLCommandExecutor.fetchCertificateWithOpenSSL(emulatorHost, emulatorPort, certFile.toString());

        // Step 3: Load the certificate into a KeyStore
        final var keyStore = CosmosSSLUtils.createKeyStoreFromCertificate(certFile.toString(), keyStorePassword);

        // Step 4: Save the KeyStore to a file
        try (final var keyStoreOut = new FileOutputStream(keyStoreFile.toFile())) {
            keyStore.store(keyStoreOut, keyStorePassword.toCharArray());
        }

        // Step 5: Configure JVM to use the new KeyStore as the trust store
        System.setProperty("javax.net.ssl.trustStore", keyStoreFile.toString());
        System.setProperty("javax.net.ssl.trustStorePassword", keyStorePassword);
        System.setProperty("javax.net.ssl.trustStoreType", "PKCS12");

        CosmosSSLUtils.log.info("TrustStore configured with certificate from: {}", certFile);
    }

    @SneakyThrows
    private static KeyStore createKeyStoreFromCertificate(
            final String certPath,
            final String keyStorePassword) {

        // Load the PEM-formatted certificate
        final var cf = CertificateFactory.getInstance("X.509");
        try (final var fis = new FileInputStream(certPath)) {
            final var cert = (X509Certificate) cf.generateCertificate(fis);
            // Create a new KeyStore and add the certificate
            final var keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, keyStorePassword.toCharArray());
            keyStore.setCertificateEntry("cosmos-emulator", cert);
            return keyStore;
        }
    }

}