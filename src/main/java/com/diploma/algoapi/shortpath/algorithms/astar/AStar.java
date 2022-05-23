package com.diploma.algoapi.shortpath.algorithms.astar;

import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;
import com.google.common.graph.ValueGraph;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("ALL")
public class AStar {

  private final Map<NodeWithXYCoordinates, AStarNodeWrapper> nodeWrappers = new HashMap<>();
  private final TreeSet<AStarNodeWrapper> table = new TreeSet<>();
  private final Set<NodeWithXYCoordinates> foundShortestPaths = new HashSet<>();

  private final ValueGraph<NodeWithXYCoordinates, Double> graph;
  private final NodeWithXYCoordinates source;
  private final NodeWithXYCoordinates target;
  private final Function<NodeWithXYCoordinates, Double> heuristic;

  public AStar(ValueGraph<NodeWithXYCoordinates, Double> graph,
               NodeWithXYCoordinates source,
               NodeWithXYCoordinates target,
               Function<NodeWithXYCoordinates, Double> heuristic) {
    
    this.graph = graph;
    this.source = source;
    this.target = target;
    this.heuristic = heuristic;
  }

  public Path<Double> findShortestPath() {
    addSource();
    return startIterations();
  }

  private Path<Double> startIterations() {

    while (!table.isEmpty()) {
      AStarNodeWrapper nodeWrapper = table.pollFirst();
      NodeWithXYCoordinates node = nodeWrapper.getNode();
      foundShortestPaths.add(node);

      if (node.equals(target)) {
        return buildPath(nodeWrapper);
      }
      iterateOverAllNeighbors(graph.adjacentNodes(node), nodeWrapper);
    }
    return null;
  }

  private void iterateOverAllNeighbors(
          Set<NodeWithXYCoordinates> neighbors,
          AStarNodeWrapper nodeWrapper
  ) {
    NodeWithXYCoordinates node = nodeWrapper.getNode();

    for (NodeWithXYCoordinates neighbor : neighbors) {
      if (foundShortestPaths.contains(neighbor)) {
        continue;
      }

      double cost = graph.edgeValue(node, neighbor).get();
      double totalCostFromStart = nodeWrapper.getTotalCostFromStart() + cost;

      AStarNodeWrapper neighborWrapper = nodeWrappers.get(neighbor);

      if (neighborWrapper == null)
        addNeighborToTable(neighbor, nodeWrapper, totalCostFromStart);

      else if (totalCostFromStart < neighborWrapper.getTotalCostFromStart())
        updateTotalCostAndPredecessor(neighborWrapper, nodeWrapper, totalCostFromStart);

    }
  }

  private void addNeighborToTable(
          NodeWithXYCoordinates node,
          AStarNodeWrapper predecessor,
          double totalCostFromStart
  ) {

    AStarNodeWrapper neighborWrapper = new AStarNodeWrapper(
            node, predecessor, totalCostFromStart, heuristic.apply(node)
    );
    nodeWrappers.put(node, neighborWrapper);
    table.add(neighborWrapper);
  }

  private void updateTotalCostAndPredecessor(
          AStarNodeWrapper neighborWrapper,
          AStarNodeWrapper predecessor,
          double lowerTotalCostFromStart
  )
  {
    table.remove(neighborWrapper);

    neighborWrapper.setTotalCostFromStart(lowerTotalCostFromStart);
    neighborWrapper.setPredecessor(predecessor);

    table.add(neighborWrapper);
  }

  private Path<Double> buildPath(AStarNodeWrapper nodeWrapper) {
    return new Path<>(
            source.getName(), target.getName(),
            nodeWrapper.getTotalCostFromStart(), buildRoute(nodeWrapper)
    );
  }

  private List<String> buildRoute(AStarNodeWrapper target) {
    if (target.getPredecessor() == null)
      return new ArrayList<>();

    List<String> route = new ArrayList<>();
    while (target != null) {
      route.add(target.getNode().getName());
      target = target.getPredecessor();
    }
    Collections.reverse(route);
    return route;
  }

  private void addSource() {
    AStarNodeWrapper sourceWrapper =
            new AStarNodeWrapper(source, null, 0.0, heuristic.apply(source));
    nodeWrappers.put(source, sourceWrapper);
    table.add(sourceWrapper);
  }

}
