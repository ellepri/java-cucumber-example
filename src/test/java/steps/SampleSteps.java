package steps;

import io.cucumber.java.en.When;
import support.World;

public class SampleSteps {

    private final World world;

    public SampleSteps(World world) {
        this.world = world;
    }

    @When("I make a valid GET request to {string}")
    public void iMakeAValidGETRequestTo(String url) {
        world.response = world.request.when().get(url);
    }
}
