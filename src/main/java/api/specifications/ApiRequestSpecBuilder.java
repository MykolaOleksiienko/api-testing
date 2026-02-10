package api.specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiRequestSpecBuilder {

    public static RequestSpecification getDefaultRequestSpec(String baseUrl) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getRequestSpecWithQueryParams(String baseUrl, Map<String, Object> queryParams) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);

        if (queryParams != null && !queryParams.isEmpty()) {
            specBuilder.addQueryParams(queryParams);
        }

        return specBuilder.build();
    }
}
