package model.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MetadataScenarios {

    private String filename;

    @JsonProperty("scenarios")
    private List<MetadataScenario> scenarios = null;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<MetadataScenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<MetadataScenario> scenarios) {
        this.scenarios = scenarios;
    }
}
