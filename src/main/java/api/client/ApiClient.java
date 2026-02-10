package api.client;


import api.endpoints.BreweryEndpoint;
import config.ConfigLoader;
import api.specifications.ApiRequestSpecBuilder;
import api.specifications.ApiResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URL = ConfigLoader.getBaseUrl();

    public static Response get(BreweryEndpoint endpoint, Map<String, Object> queryParams) {
        RequestSpecification requestSpec = ApiRequestSpecBuilder.getRequestSpecWithQueryParams(BASE_URL, queryParams);
        ResponseSpecification responseSpec = ApiResponseSpecBuilder.getDefaultResponseSpec();

        return given()
                .spec(requestSpec)
                .when()
                .get(endpoint.getPath())
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }

    public static Response post(BreweryEndpoint endpoint, Object body) {
        RequestSpecification requestSpec = ApiRequestSpecBuilder.getDefaultRequestSpec(BASE_URL);
        ResponseSpecification responseSpec = ApiResponseSpecBuilder.getDefaultResponseSpec();

        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(endpoint.getPath())
                .then()
                .spec(responseSpec)
                .extract()
                .response();
    }
}
