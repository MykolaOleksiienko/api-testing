# API Testing Project - Open Brewery DB

## Overview

This project contains automated tests for Open Brewery DB API, covering:
- **Search Breweries** endpoint (`GET /v1/breweries/search`) - implemented with 5 automated test scenarios
- **List Breweries** endpoint (`GET /v1/breweries`) - test approach and design analysis documented

## Search Breweries - Test Suite

### Implemented Test Scenarios

| Scenario                                      | Expected result                                                                                   | Scenario type       | Test design technique                        | Test type               |
|-----------------------------------------------|---------------------------------------------------------------------------------------------------|---------------------|----------------------------------------------|-------------------------|
| Valid request with query parameter            | 200 status code, valid JSON array, schema validation passes, first item has non-blank id and name | Positive            | Contract testing, Equivalence partitioning   | Smoke, Integration      |
| Missing required query parameter              | 422 validation error                                                                              | Negative            | Specification-based testing                  | Smoke, Integration      |
| Search query with no matches                  | 200 status code, empty array []                                                                   | Negative (business) | Equivalence partitioning                     | Smoke, Integration      |
| per_page at maximum (200)                     | 200 status code, result size <= 200                                                               | Positive            | Boundary Value Analysis                      | Regression, Integration |
| Default per_page behavior (without parameter) | 200 status code, result size <= 50                                                                | Positive            | Specification-based testing (default values) | Regression, Integration |

Tests are organized by functionality:

- `SearchBreweriesContractTests` - Contract and schema validation tests
- `SearchBreweriesValidationTests` - Parameter validation and negative tests
- `SearchBreweriesFunctionalTests` - Functional behavior tests (pagination, defaults)

### Additional Test Scenarios (Recommended for Complete Test Suite)

| Scenario                                        | Expected result                               | Scenario type         | Test design technique                         | Test type  |
|-------------------------------------------------|-----------------------------------------------|-----------------------|-----------------------------------------------|------------|
| per_page above maximum (201)                    | 422 validation error                          | Negative              | Boundary Value Analysis (invalid upper bound) | Regression |
| per_page = 0 or negative                        | 422 validation error                          | Negative              | Boundary Value Analysis (invalid lower bound) | Regression |
| Invalid parameter type (per_page=abc, page=xyz) | 422 validation error                          | Negative              | Negative testing, Robustness                  | Regression |
| Very large page value                           | 200 status code, empty array, no server error | Negative (robustness) | Error guessing                                | Regression |
| Case-insensitive search (beer vs BEER)          | 200 status code, consistent behavior          | Positive              | Equivalence Partitioning                      | Regression |
| Special characters / URL encoding in query      | 200 status code, valid JSON response          | Positive              | Error guessing                                | Regression |
| Pagination consistency (page=1 vs page=2)       | Different result sets if enough data exists   | Positive              | Specification-based testing                   | Regression |
| Response content validation for nullable fields | Fields accept null without breaking contract  | Positive              | Contract testing                              | Regression |
| Performance check (lightweight)                 | Response time within acceptable range         | Positive              | Non-functional testing                        | Regression |


## List Breweries - Test Suite

### Test Suite

| Scenario                                            | Expected result                                                            | Scenario type         | Test design technique                         | Test type               |
|-----------------------------------------------------|----------------------------------------------------------------------------|-----------------------|-----------------------------------------------|-------------------------|
| Basic list without parameters                       | 200 status code, default pagination (50 results, page 1), valid JSON array | Positive              | Specification-based testing                   | Smoke, Integration      |
| Filter by type (by_type=micro)                      | 200 status code, all results have brewery_type=micro                       | Positive              | Equivalence Partitioning                      | Smoke, Integration      |
| Filter by city (by_city)                            | 200 status code, all results match specified city                          | Positive              | Equivalence Partitioning                      | Regression, Integration |
| Filter by country (by_country)                      | 200 status code, all results match specified country                       | Positive              | Equivalence Partitioning                      | Regression, Integration |
| Filter by name (by_name)                            | 200 status code, all results match specified name                          | Positive              | Equivalence Partitioning                      | Regression, Integration |
| Filter by state (by_state)                          | 200 status code, all results match specified state                         | Positive              | Equivalence Partitioning                      | Regression, Integration |
| Filter by postal code (by_postal) - 5-digit         | 200 status code, all results match postal code                             | Positive              | Equivalence Partitioning                      | Regression, Integration |
| Filter by postal code (by_postal) - postal+4 format | 200 status code, all results match postal+4 code                           | Positive              | Equivalence Partitioning                      | Regression, Integration |
| Filter by IDs (by_ids)                              | 200 status code, results match specified IDs                               | Positive              | State-based Testing                           | Regression, Integration |
| Sort by distance (by_dist)                          | 200 status code, results sorted by distance from coordinates               | Positive              | State-based Testing                           | Regression, Integration |
| Sort by field (sort=name:asc)                       | 200 status code, results sorted ascending by name                          | Positive              | Specification-based testing                   | Regression, Integration |
| Sort by multiple fields (sort=city:asc,name:desc)   | 200 status code, results sorted by multiple fields                         | Positive              | Specification-based testing                   | Regression, Integration |
| Filter combination (by_city + by_type)              | 200 status code, results match both filters                                | Positive              | Pairwise Testing                              | Regression, Integration |
| Pagination (page=1 vs page=2)                       | Different result sets if enough data exists                                | Positive              | Specification-based testing                   | Regression, Integration |
| per_page at maximum (200)                           | 200 status code, result size <= 200                                        | Positive              | Boundary Value Analysis                       | Regression, Integration |
| per_page above maximum (201)                        | 422 validation error                                                       | Negative              | Boundary Value Analysis (invalid upper bound) | Regression              |
| per_page = 0 or negative                            | 422 validation error                                                       | Negative              | Boundary Value Analysis (invalid lower bound) | Regression              |
| Invalid parameter type (per_page=abc)               | 422 validation error                                                       | Negative              | Negative testing, Robustness                  | Regression              |
| Invalid parameter combination (by_dist + sort)      | 422 validation error or by_dist takes precedence                           | Negative              | Negative testing                              | Regression              |
| Invalid coordinate format for by_dist               | 422 validation error                                                       | Negative              | Negative testing                              | Regression              |
| Invalid ID format for by_ids                        | 422 validation error or empty result                                       | Negative              | Negative testing                              | Regression              |
| Very large page value                               | 200 status code, empty array, no server error                              | Negative (robustness) | Error guessing                                | Regression              |
| Empty result set for filter                         | 200 status code, empty array []                                            | Negative (business)   | State-based Testing                           | Regression, Integration |
| Special characters in filter values                 | 200 status code, valid JSON response                                       | Positive              | Robustness Testing                            | Regression              |
| Case sensitivity for string filters                 | Consistent behavior (case-sensitive or case-insensitive)                   | Positive              | Robustness Testing                            | Regression              |
| Contract validation (schema)                        | Response matches JSON schema, required fields present                      | Positive              | Contract testing                              | Smoke, Integration      |

## List Breweries - Test Automation Approach

### Approach

For the **List Breweries** endpoint (`GET /v1/breweries`), I would apply a combinatorial testing approach with focus on critical paths. 
Given 11 optional parameters, full combinatorial testing is impractical. Instead, I would use:

- **Pairwise Testing** - Test combinations of 2 parameters to cover interaction defects
- **State-based Testing** - Group tests by filter type (geographic, type-based, ID-based)
- **Boundary Value Analysis** - For numeric parameters (page, per_page)
- **Equivalence Partitioning** - For categorical parameters (by_type values)

### Test Design Techniques

**Contract Testing:**
- JSON Schema validation using `list-breweries-response-schema.json`
- Response structure validation (array, required fields, nullable fields)
- Content-Type and status code validation

**Boundary Value Analysis:**
- `per_page`: 0, 1, 50 (default), 200 (max), 201 (above max)
- `page`: 0, 1 (default), large values (999999)
- `by_dist`: Valid coordinates, invalid format, out-of-range values

**Equivalence Partitioning:**
- `by_type`: Valid types (micro, nano, regional, brewpub, planning, contract, proprietor, closed), invalid type, deprecated types
- `by_postal`: 5-digit format, postal+4 format, invalid format
- `by_state`: Full state names, abbreviations (should fail), states with spaces

**Negative Testing:**
- Invalid parameter types (e.g., `per_page=abc`, `page=xyz`)
- Invalid parameter combinations (e.g., `by_dist` with `sort` - mutually exclusive)
- Invalid coordinate format for `by_dist`
- Invalid ID format for `by_ids`

**State-based Testing:**
- Empty result sets for filters with no matches
- Single result vs multiple results
- Filter combinations that return overlapping vs distinct results

**Robustness Testing:**
- Special characters in filter values (spaces, underscores, URL encoding)
- Very long filter values
- Case sensitivity for string filters (by_city, by_country, by_name, by_state)

### Estimated Effort

**Test Implementation: 15-22 hours**
- Contract tests: 2-3 hours
- Individual filter tests: 4-6 hours (8 filter parameters)
- Combination tests: 3-4 hours (pairwise combinations)
- Pagination and sorting: 2-3 hours
- Validation and edge cases: 3-4 hours
- Smoke tests: 1-2 hours

## Technologies Used

- Java 21
- JUnit 5
- REST Assured 5.4.0
- Jackson (JSON processing)
- JSON Schema Validator
- Allure (test reporting)
- Lombok
