package com.godaddy.icfp2017.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.godaddy.icfp2017.models.serialization.BinaryStateDeserializer;
import com.godaddy.icfp2017.models.serialization.BinaryStateSerializer;
import com.godaddy.icfp2017.services.algorithms.Algorithms;
import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.SimpleWeightedGraph;

@JsonSerialize(using = BinaryStateSerializer.class)
@JsonDeserialize(using = BinaryStateDeserializer.class)
public class State {
  @JsonProperty
  private int punter;

  @JsonProperty
  private SimpleWeightedGraph<Site, River> graph;

  @JsonProperty
  private SimpleWeightedGraph<Site, River> claimedGraph;

  public Integer getLastTime(Algorithms algorithm) {
    return lastTime.get(algorithm);
  }

  public State() {
    lastTime = new HashMap<>();
  }

  public void setLastTime(Algorithms algorithm, Integer lastTime) {
    this.lastTime.put(algorithm, lastTime);
  }

  @JsonProperty
  private HashMap<Algorithms, Integer> lastTime;

  @JsonProperty
  private int moveCount;

  @JsonProperty
  private ImmutableSet<Site> mines;

  private transient FloydWarshallShortestPaths<Site, River> shortestPaths;

  public int getPunter() {
    return punter;
  }

  public void setPunter(final int punter) {
    this.punter = punter;
  }

  public SimpleWeightedGraph<Site, River> getGraph() {
    return graph;
  }

  public SimpleWeightedGraph<Site, River> getClaimedGraph() {
    return claimedGraph;
  }

  public void setGraph(final SimpleWeightedGraph<Site, River> graph) {
    this.graph = graph;
  }

  public void setClaimedGraph(final SimpleWeightedGraph<Site, River> graph) {
    this.claimedGraph = graph;
  }

  public int getMoveCount() {
    return moveCount;
  }

  public void setMoveCount(final int moveCount) {
    this.moveCount = moveCount;
  }

  public void setMines(final ImmutableSet<Site> mines) {
    this.mines = mines;
  }

  public ImmutableSet<Site> getMines() {
    return mines;
  }

  public void setShortestPaths(final FloydWarshallShortestPaths<Site, River> shortestPaths) {
    this.shortestPaths = shortestPaths;
  }

  public FloydWarshallShortestPaths<Site, River> getShortestPaths() {
    return shortestPaths;
  }
}
