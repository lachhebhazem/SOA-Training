package webservices;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import org.glassfish.jersey.server.ResourceConfig;

public class OpenAPIConfig extends ResourceConfig {

    public OpenAPIConfig() {
        packages("webservices");     // ton package REST
        register(OpenApiResource.class);
    }
}
