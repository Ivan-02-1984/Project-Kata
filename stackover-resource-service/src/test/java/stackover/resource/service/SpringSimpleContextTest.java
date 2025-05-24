package stackover.resource.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = SpringSimpleContextTest.DockerPostgresDataSourceInitializer.class)
public class SpringSimpleContextTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected ObjectMapper objectMapper;


    private static final String DB_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;


    static {
        PostgreSQLContainer<?> tempContainer = new PostgreSQLContainer<>("postgres:14.5")
                .withDatabaseName("test")
                .withUsername("test")
                .withPassword("test");
        tempContainer.start();
        DB_URL = tempContainer.getJdbcUrl();
        DB_USERNAME = tempContainer.getUsername();
        DB_PASSWORD = tempContainer.getPassword();
    }

    @Container
    @Rule
    public PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    public static class DockerPostgresDataSourceInitializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + DB_URL,
                    "spring.datasource.username=" + DB_USERNAME,
                    "spring.datasource.password=" + DB_PASSWORD
            );
        }
    }

    @BeforeEach
    @Before
   public void logContainerInfo() {
        System.out.println("Test DB URL: " + postgreSQLContainer.getJdbcUrl());
    }
}