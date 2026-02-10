package tests.search;

import api.models.Brewery;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.lang3.RandomStringUtils;
import tests.annotations.Regression;
import tests.annotations.Smoke;
import api.client.BreweryClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static common.constants.QueryParams.QUERY;
import static common.constants.ValidationValue.VALID_QUERY;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Smoke
@Regression
public class SearchBreweriesContractTests {

    private static final String SCHEMA_PATH = "schemas/search-breweries-response-schema.json";

    @Test
    @DisplayName("Contract sanity: valid request → 200 + basic response contract")
    void testSearchBreweriesContractSanity() {
        var response = BreweryClient.searchBreweries(Map.of(QUERY, VALID_QUERY));

        response.then()
                .statusCode(SC_OK)
                .contentType(JSON)
                .body(matchesJsonSchemaInClasspath(SCHEMA_PATH));

        var breweries = response.then()
                .extract()
                .as(new TypeRef<List<Brewery>>() {});

        assertThat(breweries.size(), greaterThan(0));
        assertThat(breweries.getFirst().getId(), not(blankOrNullString()));
        assertThat(breweries.getFirst().getName(), not(blankOrNullString()));
    }

    @Test
    @DisplayName("No matches → returns [] (not an error)")
    void testSearchBreweriesNoMatches() {
        var response = BreweryClient.searchBreweries(Map.of(QUERY, RandomStringUtils.randomAlphabetic(20)));

        response.then()
                .statusCode(SC_OK)
                .contentType(JSON);

        var breweries = response.then()
                .extract()
                .as(new TypeRef<List<Brewery>>() {});

        assertThat(breweries, empty());
    }
}
