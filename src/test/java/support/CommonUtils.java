package support;

import com.google.gson.Gson;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.TestCase;
import model.metadata.MetadataScenario;
import model.metadata.MetadataScenarios;
import model.metadata.Urls;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

public final class CommonUtils {

    private CommonUtils() {
    }

    public static Urls scenarioMetadataFromJSON(Scenario scenario)
            throws IOException, NoSuchFieldException, IllegalAccessException {
        String featureFilepath = scenario.getId().replace("file:", "").split(":")[0];

        String scenarioName;

        String keyWord = testCaseFromScenario(scenario).getKeyword();
        switch (keyWord) {
            case "Scenario Outline":
                // Read .feature file
                String testName = Files.readAllLines(Paths.get(featureFilepath)).get(scenario.getLine() - 1);
                scenarioName = scenario.getName() + " - " + testName.replaceAll("\\s", "");
                break;
            case "Scenario":
                scenarioName = scenario.getName();
                break;
            default:
                throw new IllegalArgumentException("Invalid gherkin keyword: " + keyWord);
        }

        String metadataFilepath = metadataFilepath(featureFilepath);
        // return .feature.json fields
        MetadataScenarios metadataScenarios = new Gson().fromJson(new FileReader(metadataFilepath), MetadataScenarios.class);

        Urls scenarioUrls = null;
        for (MetadataScenario metadataScenario : metadataScenarios.getScenarios()) {
            if (metadataScenario.getScenario().equals(scenarioName)) {
                scenarioUrls = metadataScenario.getUrls();
            }
        }

        if (scenarioUrls == null) {
            throw new IllegalArgumentException(
                    "\nCreate scenario metadata " + "'" + scenarioName + "'\n" + " in " + metadataFilepath);
        }

        return scenarioUrls;
    }

    private static TestCase testCaseFromScenario(Scenario scenario) throws NoSuchFieldException, IllegalAccessException {
        Field dField = scenario.getClass().getDeclaredField("delegate");
        dField.setAccessible(true);
        TestCaseState tcs = (TestCaseState) dField.get(scenario);

        Field testCaseField = tcs.getClass().getDeclaredField("testCase");
        testCaseField.setAccessible(true);

        return (TestCase) testCaseField.get(tcs);
    }

    private static String metadataFilepath(String path) {
        File file = new File(path);
        return file.getParent() + "/metadata/" + file.getName() + ".json";
    }

    public static String getBearerToken(String username, String password, String loginPageUrl) {
        WebDriver driver = new HtmlUnitDriver(false);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        driver.get(loginPageUrl);

        // code to get the Bearer token

        String bearerToken = "Bearer ";
        driver.quit();

        return bearerToken;
    }
}
