package de.turing85.quarkus.camel.startup;

import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.platformHttp;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.sql;

import de.turing85.quarkus.camel.startup.config.DatasourceRegisterer;
import io.vertx.core.http.HttpMethod;
import org.apache.camel.builder.RouteBuilder;

@SuppressWarnings("unused")
public class MyRouteBuilder extends RouteBuilder {
  @Override
  public void configure() {
    from(platformHttp("/animals")
        .httpMethodRestrict(HttpMethod.GET.name()))
        .to(sql("SELECT id FROM animal")
            .dataSource("#" + DatasourceRegisterer.DATASOURCE_ID))
        .process(exchange -> System.out.println(exchange.getIn().getBody()));
  }
}
