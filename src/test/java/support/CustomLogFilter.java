package support;

import io.cucumber.java.Scenario;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.LogDetail;
import io.restassured.internal.print.RequestPrinter;
import io.restassured.internal.print.ResponsePrinter;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomLogFilter implements Filter {

    private final Scenario scenario;
    private static final Logger LOGGER = LogManager.getLogger(CustomLogFilter.class);

    public CustomLogFilter(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);

        // LOG TO CONSOLE AND TO REPORT
        scenario.log(requestSpec.getMethod() + " " + requestSpec.getURI() + " => "
                + response.getStatusCode() + " " + response.getStatusLine());
        if (response.statusCode() >= 400) {
            scenario.log(new Prettifier().getPrettifiedBodyIfPossible(response, response.getBody()));
        }

        // LOG TO FILE
        LOGGER.info(scenario.getId());
        LOGGER.info(scenario.getName());
        printRequest(requestSpec);
        printResponse(response);

        return response;
    }

    private void printRequest(FilterableRequestSpecification requestSpec) {
        final PrintStream stream = new PrintStream(new ByteArrayOutputStream(), true);
        Set<String> blacklistedHeaders = new HashSet<>();
        blacklistedHeaders.add("api_key");
        blacklistedHeaders.add("Authorization");

        LOGGER.info(RequestPrinter.print(
                requestSpec, requestSpec.getMethod(), requestSpec.getURI(), LogDetail.ALL, blacklistedHeaders, stream, true));
    }

    private void printResponse(Response response) {
        final PrintStream stream = new PrintStream(new ByteArrayOutputStream());

        LOGGER.info(ResponsePrinter.print(
                response, response.getBody(), stream, LogDetail.ALL, true, Collections.emptySet()));
    }
}
