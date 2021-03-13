Feature: API validation / metadata file
  Using metadata json file to get the endpoint

  Scenario: The one who returns valid response
    Given the endpoint exists in metadata
#    And user is signed in
#      | email@email |
    And I have set the request headers
      | api_key      | api_key |
    And I set the query parameters
      | a  | a |
    When I make a valid GET request
    Then response status code is 200
    And content is playable item
    And response body conforms to "sample_schema.json" schema
