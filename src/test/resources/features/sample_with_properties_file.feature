Feature: API validation / properties file
  Using properties file to get the endpoint

  Scenario: The one who returns valid response
    Given I have set the request headers
      | api_key | api_key |
    And I set the query parameters
      | a  | a |
#    And user is signed in
#      | email@email |
    When I send valid GET request to "sample_endpoint"
    Then response status code is 200
    And content is playable item
    And response body conforms to "sample_schema.json" schema
