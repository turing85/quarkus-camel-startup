package de.turing85.quarkus.camel.startup.config;

import io.quarkus.runtime.StartupEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class StartupDataInserter {
  private static final Logger LOGGER = LoggerFactory.getLogger(StartupDataInserter.class);

  private final DataSource dataSource;

  public StartupDataInserter(
      @SuppressWarnings("CdiInjectionPointsInspection") DataSource dataSource) {
    this.dataSource = dataSource;
  }

  void startUp(@Observes StartupEvent startupEvent) throws IOException, SQLException {
    LOGGER.info("Importing data");
    try (InputStream inputStream = Objects.requireNonNull(getClass()
        .getClassLoader()
        .getResourceAsStream("import.sql"))) {
      String importSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      executeSql(importSql);
    }
  }

  private void executeSql(String importSql) throws SQLException {
    Connection connection = dataSource.getConnection();
    try (PreparedStatement preparedStatement = connection.prepareStatement(importSql)) {
      preparedStatement.execute();
    }
  }
}
