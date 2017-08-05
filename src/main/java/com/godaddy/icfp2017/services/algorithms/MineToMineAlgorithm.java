package com.godaddy.icfp2017.services.algorithms;

import com.godaddy.icfp2017.models.River;
import com.godaddy.icfp2017.models.Site;
import com.godaddy.icfp2017.models.State;
import com.godaddy.icfp2017.services.Weights;
import com.google.common.base.Preconditions;
import org.jgrapht.GraphPath;
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

  static double pathWeight(final double weight, final double length, final double diameter) {
    Preconditions.checkArgument(weight <= length);
    final double smoothedOwnership = (length - weight + 1.0) / (length + 1.0);
    final double l0 = length * length;
    final double d0 = diameter * diameter;
    final double percentage = Math.min(smoothedOwnership * (l0 / d0), 1.0);
    return 1.0 + (percentage * (Weights.HighlyDesired - 1.0));
  }

  @Override
  public void iterate(final State state) {
    final FloydWarshallShortestPaths<Site, River> shortestPaths = state.getShortestPaths();

    // the longest shortest path in the graph
    final double diameter = shortestPaths.getDiameter();

    for (final Site source : state.getMines()) {
      for (final Site sink : state.getMines()) {
        if (source.getId() <= sink.getId()) {
          // traverse a mine-to-mine path in one direction
          continue;
        }

        final GraphPath<Site, River> path = shortestPaths.getPaths(source).getPath(sink);
        final double pathWeight = pathWeight(path.getWeight(), path.getLength(), diameter);
        for (final River river : path.getEdgeList()) {
          setter.apply(
              river,
              pathWeight * Math.max(1.0, getter.apply(river)));
        }
      }
    }
  }
}
