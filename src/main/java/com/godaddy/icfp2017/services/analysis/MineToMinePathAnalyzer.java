package com.godaddy.icfp2017.services.analysis;

import com.godaddy.icfp2017.models.River;
import com.godaddy.icfp2017.models.Site;
import com.godaddy.icfp2017.models.State;
import com.google.common.collect.ImmutableList;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

public class MineToMinePathAnalyzer extends BaseAnalyzer {

  @Override
  public void analyze(State state) {
    DijkstraShortestPath<Site, River> shortestPath = new DijkstraShortestPath<>(state.getGraph());
    final ImmutableList<GraphPath<Site, River>> paths = state
        .getMines()
        .stream()
        .flatMap(source -> state
            .getMines()
            .stream()
            // traverse a mine-to-mine path in one direction
            .filter(sink -> source.getId() < sink.getId())
            .map(sink -> shortestPath.getPath(source, sink))
            .filter(path -> path != null))
        .collect(ImmutableList.toImmutableList());

    state.setMineToMinePaths(paths);
  }
}
