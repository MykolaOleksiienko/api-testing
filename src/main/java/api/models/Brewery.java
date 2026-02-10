package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brewery {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("brewery_type")
    private String breweryType;
    
    @JsonProperty("address_1")
    private String address1;
    
    @JsonProperty("address_2")
    private String address2;
    
    @JsonProperty("address_3")
    private String address3;
    
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("state_province")
    private String stateProvince;
    
    @JsonProperty("postal_code")
    private String postalCode;
    
    @JsonProperty("country")
    private String country;
    
    @JsonProperty("longitude")
    private Double longitude;
    
    @JsonProperty("latitude")
    private Double latitude;
    
    @JsonProperty("phone")
    private String phone;
    
    @JsonProperty("website_url")
    private String websiteUrl;
    
    @JsonProperty("state")
    @Deprecated
    private String state;
    
    @JsonProperty("street")
    @Deprecated
    private String street;
}
