package steps;

import io.cucumber.java.en.When;
import support.PropertiesFileReader;
import support.World;

public class SampleWithPropertiesFileSteps {

    private final World world;

    public SampleWithPropertiesFileSteps(World world) {
        this.world = world;
    }

    @When("I send valid GET request to {string}")
    public void iSendValidGETRequestTo(String endpoint) {
        world.response = world.request.when().get(PropertiesFileReader.getInstance().getProperty(endpoint));
    }
}
