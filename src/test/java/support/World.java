package support;

import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class World {

    public RequestSpecification request;
    public Response response;
    public Scenario scenario;
}
