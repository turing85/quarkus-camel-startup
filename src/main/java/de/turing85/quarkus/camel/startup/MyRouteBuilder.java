package de.turing85.quarkus.camel.startup;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.platformHttp;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sql;

import de.turing85.quarkus.camel.startup.config.DatasourceRegisterer;
import io.vertx.core.http.HttpMethod;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

@SuppressWarnings("unused")
public class MyRouteBuilder extends RouteBuilder {
  @Override
  public void configure() {
    from(platformHttp("/animals")
        .httpMethodRestrict(HttpMethod.GET.name()))
        .to(sql("SELECT * FROM animal")
            .dataSource("#" + DatasourceRegisterer.DATASOURCE_ID))
        .marshal().json(JsonLibrary.Jackson);
  }
}
