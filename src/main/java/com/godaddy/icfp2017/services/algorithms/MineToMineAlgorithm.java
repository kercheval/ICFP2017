package com.godaddy.icfp2017.services.algorithms;

import com.godaddy.icfp2017.models.River;
import com.godaddy.icfp2017.models.Site;
import com.godaddy.icfp2017.models.State;
import com.godaddy.icfp2017.services.algorithms.GraphAlgorithm;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;

final public class MineToMineAlgorithm extends BaseAlgorithm {
  private final Getter getter;
  private final Setter setter;

  public MineToMineAlgorithm(
      final GraphAlgorithm.Getter getter,
      final GraphAlgorithm.Setter setter) {
    this.getter = getter;
    this.setter = setter;
  }

  @Override
  public void iterate(final State state) {
    final FloydWarshallShortestPaths<Site, River> shortestPaths = state.getShortestPaths();

    for (final Site source : state.getMines()) {
      for (final Site sink : state.getMines()) {
        if (source == sink) {
          continue;
        }

        for (final River river : shortestPaths
            .getPaths(source)
            .getPath(sink)
            .getEdgeList()) {
          setter.apply(
              river,
              1.1 * Math.max(1.0, getter.apply(river)));
        }
      }
    }
  }
}
