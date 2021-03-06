package com.godaddy.icfp2017.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.godaddy.icfp2017.services.algorithms.Algorithms;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.HashMap;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

public class River extends DefaultWeightedEdge implements Serializable {
  private static final long serialVersionUID = 7773166057698250129L;

  @JsonProperty("source")
  private int source;

  @JsonProperty("target")
  private int target;

  @JsonIgnore
  private transient ConcurrentHashMap<Algorithms, Double> algorithmWeights;

  @JsonProperty("claimedBy")
  private int claimedBy = -1;

  @JsonIgnore
  private ConcurrentHashMap<Site, Integer> maxEnemyPathFromSites = new ConcurrentHashMap<>();

  public River() {
  }

  public River(final int source, final int target) {
    this.source = source;
    this.target = target;
  }

  public Integer getSource() {
    return source;
  }

  public void setSource(final int source) {
    this.source = source;
  }

  public Integer getTarget() {
    return target;
  }

  public void setTarget(final int target) {
    this.target = target;
  }

  public ConcurrentHashMap<Site, Integer> getMaxEnemyPathFromSites() {
    return maxEnemyPathFromSites;
  }

  public void setMaxEnemyPathFromSites(final ConcurrentHashMap<Site, Integer> paths) {
    this.maxEnemyPathFromSites = paths;
  }

  public ConcurrentHashMap<Algorithms, Double> getAlgorithmWeights() {
    if (algorithmWeights == null) {
      algorithmWeights = new ConcurrentHashMap<>();
    }

    return algorithmWeights;
  }

  public int getClaimedBy() {
    return claimedBy;
  }

  public void setClaimedBy(int claimedBy) {
    this.claimedBy = claimedBy;
  }

  @JsonIgnore
  public boolean isClaimed() {
    return this.claimedBy >= 0;
  }

  @Override
  public boolean equals(final Object obj) {
    return obj instanceof River &&
           ((River) obj).source == source &&
           ((River) obj).target == target;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(source, target);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
                      .add("source", source)
                      .add("target", target)
                      .toString();
  }
}
