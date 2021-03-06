package com.godaddy.icfp2017.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties({"state"}) // offline mode includes an empty state object
public class SetupS2P implements ServerToPlayer {
  @JsonProperty("punter")
  private int punter;

  @JsonProperty("punters")
  private int punters;

  @JsonProperty("map")
  private Map map;

  @JsonIgnore // this might be for futures??
  public JsonNode getSettings() {
    return settings;
  }

  public void setSettings(final JsonNode settings) {
    this.settings = settings;
  }

  @JsonProperty("settings")
  private JsonNode settings;

  public int getPunter() {
    return punter;
  }

  public void setPunter(final int punter) {
    this.punter = punter;
  }

  public int getPunters() {
    return punters;
  }

  public void setPunters(final int punters) {
    this.punters = punters;
  }

  public Map getMap() {
    return map;
  }

  public void setMap(final Map map) {
    this.map = map;
  }
}
