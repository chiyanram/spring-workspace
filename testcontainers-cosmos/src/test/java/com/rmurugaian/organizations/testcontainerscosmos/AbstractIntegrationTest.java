package com.rmurugaian.organizations.testcontainerscosmos;

import lombok.SneakyThrows;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CosmosDBEmulatorContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@SpringBootTest(classes = TestcontainersCosmosApplication.class)
@ActiveProfiles("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractIntegrationTest {

    @Container
    private static final CosmosDBEmulatorContainer COSMOS_DB_CONTAINER = new CosmosDBEmulatorContainer(DockerImageName.parse("mcr.microsoft.com/cosmosdb/linux/azure-cosmos-emulator:vnext-preview")).withCommand("--protocol", "https").waitingFor(Wait.forHttps("/").forStatusCode(200).allowInsecure()).withStartupTimeout(Duration.ofMinutes(1));


    static {
        AbstractIntegrationTest.COSMOS_DB_CONTAINER.start();
    }

    @DynamicPropertySource
    @SneakyThrows
    static void defineCosmos(final DynamicPropertyRegistry registry) {
        // Set up the trust store
        CosmosSSLUtils.setupTrustStore(AbstractIntegrationTest.COSMOS_DB_CONTAINER.getHost(), AbstractIntegrationTest.COSMOS_DB_CONTAINER.getFirstMappedPort(), AbstractIntegrationTest.COSMOS_DB_CONTAINER.getEmulatorKey());

        registry.add("spring.cloud.azure.cosmos.endpoint", AbstractIntegrationTest.COSMOS_DB_CONTAINER::getEmulatorEndpoint);
        registry.add("spring.cloud.azure.cosmos.key", AbstractIntegrationTest.COSMOS_DB_CONTAINER::getEmulatorKey);
    }

}