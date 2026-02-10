package api.endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BreweryEndpoint {
    BREWERIES("/breweries"),
    SEARCH_BREWERIES("/breweries/search");

    private final String path;
}
