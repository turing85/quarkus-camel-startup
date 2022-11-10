package de.turing85.quarkus.camel.startup.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;
import org.apache.camel.impl.event.CamelContextInitializingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class DatasourceRegisterer {
  private static final Logger LOGGER = LoggerFactory.getLogger(DatasourceRegisterer.class);

  public static final String DATASOURCE_ID = "datasource";

  private final DataSource dataSource;

  public DatasourceRegisterer(
      @SuppressWarnings("CdiInjectionPointsInspection") DataSource dataSource) {
    this.dataSource = dataSource;
  }

  void startUp(@Observes CamelContextInitializingEvent event) {
    LOGGER.info("Registering datasource");
    event.getContext().getRegistry().bind(DATASOURCE_ID, dataSource);
  }
}
