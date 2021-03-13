package support;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.testng.SkipException;

public class Hook {

    private final World world;

    public Hook(World world) {
        this.world = world;
    }

    @Before
    public void before(Scenario scenario) {
        world.scenario = scenario;
        world.request = RestAssured.given().with().filter(new CustomLogFilter(world.scenario));
        world.request.baseUri(PropertiesFileReader.getInstance().getProperty("base_url"));
    }

    @Before("@todo")
    public void beforeWithHook() {
        throw new SkipException("This test is skipped");
    }
}
