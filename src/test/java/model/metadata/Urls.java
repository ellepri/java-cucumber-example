package model.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Urls {

    @JsonProperty("int")
    private String intUrl;
    private String test;
    private String stage;
    private String live;

    public String getIntUrl() {
        return intUrl;
    }

    public void setIntUrl(String intUrl) {
        this.intUrl = intUrl;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getLive() {
        return live;
    }

    public void setLive(String live) {
        this.live = live;
    }
}
