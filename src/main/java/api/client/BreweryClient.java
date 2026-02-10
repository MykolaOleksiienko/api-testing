package api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Map;

import static api.endpoints.BreweryEndpoint.BREWERIES;
import static api.endpoints.BreweryEndpoint.SEARCH_BREWERIES;


public class BreweryClient {

    @Step("Search breweries with query params: {0}")
    public static Response searchBreweries(Map<String, Object> queryParams) {
        return ApiClient.get(SEARCH_BREWERIES, queryParams);
    }

    @Step("List breweries with query params: {0}")
    public static Response listBreweries(Map<String, Object> queryParams) {
        return ApiClient.get(BREWERIES, queryParams);
    }
}
