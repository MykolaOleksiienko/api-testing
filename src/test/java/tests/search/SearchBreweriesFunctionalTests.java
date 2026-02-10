package tests.search;

import api.models.Brewery;
import io.restassured.common.mapper.TypeRef;
import tests.annotations.Regression;
import api.client.BreweryClient;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.constants.QueryParams.PER_PAGE;
import static common.constants.QueryParams.QUERY;
import static common.constants.ValidationValue.VALID_QUERY;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Regression
public class SearchBreweriesFunctionalTests {
    private static final int MAX_PER_PAGE = 200;
    private static final int DEFAULT_PER_PAGE = 50;

    @Test
    @DisplayName("Pagination boundary maximum")
    void testSearchBreweriesPerPageAtMaximum() {
        Map<String, Object> params = new HashMap<>();
        params.put(QUERY, VALID_QUERY);
        params.put(PER_PAGE, MAX_PER_PAGE);

        var response = BreweryClient.searchBreweries(params);

        var breweries = response.then()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .extract()
                .as(new TypeRef<List<Brewery>>() {});

        assertThat(breweries.size(), lessThanOrEqualTo(MAX_PER_PAGE));
    }

    @Test
    @DisplayName("Default Per Page behavior")
    void testSearchBreweriesDefaultPerPage() {
        Map<String, Object> params = new HashMap<>();
        params.put(QUERY, VALID_QUERY);

        var response = BreweryClient.searchBreweries(params);

        var breweries = response.then()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .extract()
                .as(new TypeRef<List<Brewery>>() {});

        assertThat(breweries.size(), greaterThan(0));
        assertThat(breweries.size(), lessThanOrEqualTo(DEFAULT_PER_PAGE));
    }
}
