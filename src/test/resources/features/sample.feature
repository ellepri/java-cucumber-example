Feature: API validation

  Scenario: The one who returns valid response
    Given I have set the request headers
      | api_key | api_key |
#    And user is signed in
#      | email@email |
    And I set the query parameters
      | a  | a |
    When I make a valid GET request to "/add_your_endpoint"
    Then response status code is 200
    And content is playable item
    And response body conforms to "sample_schema.json" schema
