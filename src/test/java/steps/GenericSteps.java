package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.asserts.SoftAssert;
import support.CommonUtils;
import support.PropertiesFileReader;
import support.World;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;

public class GenericSteps {

    private final World world;
    private String url;

    public GenericSteps(World world) {
        this.world = world;
    }

    @Given("the endpoint exists in metadata")
    public void theRmsTheEndpointExistsInMetadata() throws IOException, NoSuchFieldException, IllegalAccessException {
        url = CommonUtils.scenarioMetadataFromJSON(world.scenario).getTest();
    }

    @And("I set the query parameters")
    public void iSetTheQueryParameters(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);

        for (List<String> columns : rows) {
            world.request.given().queryParam(columns.get(0), columns.get(1));
        }
    }

    @And("I set the path parameters")
    public void iSetThePathParameters(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);

        for (List<String> columns : rows) {
            world.request.given().pathParam(columns.get(0), columns.get(1));
        }
    }

    @And("I have set the request headers")
    public void iHaveSetTheRequestHeaders(DataTable table) {
        List<List<String>> rows = table.asLists(String.class);

        for (List<String> columns : rows) {
            if (columns.get(0).equals("api_key")) {
                world.request.given().header("api_key", PropertiesFileReader.getInstance().getProperty("api_key"));
            } else {
                world.request.given().header(columns.get(0), columns.get(1));
            }
        }
    }

    @When("I make a valid GET request")
    public void iMakeAValidGETRequest() {
        world.response = world.request.when().get(url);
    }

    @Then("response status code is {int}")
    public void responseStatusCodeIs(int statusCode) {
        assertEquals(world.response.getStatusCode(), statusCode);
    }

    @And("content is playable item")
    public void contentIsPlayableItem() {
        SoftAssert softAssert = new SoftAssert();

//        POJOItem pojoItem = world.response.as(POJOItem.class);
//
//        softAssert.assertEquals(pojoItem.getType(), "item", "Type");
//        softAssert.assertEquals(pojoItem.getUris().size(), 1, "Uris");
        softAssert.assertAll();
    }

    @And("user is signed in")
    public void userIsSignedIn(DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);

        String username = null;
        for (List<String> columns : rows) {
            username = columns.get(0);
        }

        String password = PropertiesFileReader.getInstance().getProperty("password");
        String token = CommonUtils.getBearerToken(username, password, "https://example.com");

        world.request.given().header("Authorization", token);
    }

    @And("response body conforms to {string} schema")
    public void responseBodyConformsToSchema(String fileName) {
        File file = new File("src/test/resources/schemas/" + fileName);
        world.response.then().assertThat().body(matchesJsonSchema(file));
    }
}
