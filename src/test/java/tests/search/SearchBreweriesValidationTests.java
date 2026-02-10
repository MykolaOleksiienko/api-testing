package tests.search;

import tests.annotations.Regression;
import api.client.ApiClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static api.endpoints.BreweryEndpoint.SEARCH_BREWERIES;
import static org.apache.http.HttpStatus.SC_UNPROCESSABLE_ENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Regression
public class SearchBreweriesValidationTests {

    @Test
    @DisplayName("Required parameter: missing query")
    void testSearchBreweriesWithoutQueryParameter() {
        var response = ApiClient.get(SEARCH_BREWERIES, Map.of());

        int statusCode = response.getStatusCode();
        var responseBody = response.getBody().asString();

        assertEquals(SC_UNPROCESSABLE_ENTITY, statusCode, String.format(
                "Expected 422 status code for missing required 'query' parameter, but got %d. Response body: %s",
                statusCode,
                responseBody
        ));
    }
}
