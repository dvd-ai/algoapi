package com.diploma.algoapi.shortpath.algorithms.astar;

import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;
import com.google.common.graph.ValueGraph;

import java.util.*;
import java.util.function.Function;

public class AStar {

  public static Path<Double> findShortestPath(
      ValueGraph<NodeWithXYCoordinates, Double> graph, NodeWithXYCoordinates source,
      NodeWithXYCoordinates target, Function<NodeWithXYCoordinates, Double> heuristic
  ) {

    Map<NodeWithXYCoordinates, AStarNodeWrapper> nodeWrappers = new HashMap<>();
    TreeSet<AStarNodeWrapper> table = new TreeSet<>();
    Set<NodeWithXYCoordinates> foundShortestPats = new HashSet<>();//To be able to check whether we have already processed a node, i.e., found the shortest path to it, we create a HashSet:

    addSource(nodeWrappers, table, source, heuristic);
    return startIterations(graph, table, foundShortestPats, nodeWrappers, heuristic, source, target);
  }

  private static Path<Double> startIterations(
          ValueGraph<NodeWithXYCoordinates, Double> graph,
          TreeSet<AStarNodeWrapper> table,
          Set<NodeWithXYCoordinates> shortestPathFound,
          Map<NodeWithXYCoordinates, AStarNodeWrapper> nodeWrappers,
          Function<NodeWithXYCoordinates, Double> heuristic,
          NodeWithXYCoordinates source,
          NodeWithXYCoordinates target
  ) {

    while (!table.isEmpty()) {
      AStarNodeWrapper nodeWrapper = table.pollFirst();
      NodeWithXYCoordinates node = nodeWrapper.getNode();
      shortestPathFound.add(node);

      if (node.equals(target)) {
        return buildPath(nodeWrapper, source.getName(), target.getName());
      }
      iterateOverAllNeighbors(
              graph, table, shortestPathFound,
              nodeWrappers, graph.adjacentNodes(node), heuristic, nodeWrapper
      );
    }
    return null;
  }

  private static void iterateOverAllNeighbors(
          ValueGraph<NodeWithXYCoordinates, Double> graph, TreeSet<AStarNodeWrapper> table,
          Set<NodeWithXYCoordinates> foundShortestPaths,
          Map<NodeWithXYCoordinates, AStarNodeWrapper> nodeWrappers,
          Set<NodeWithXYCoordinates> neighbors,
          Function<NodeWithXYCoordinates, Double> heuristic, AStarNodeWrapper nodeWrapper
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
        addNeighborToTable(table, heuristic, nodeWrappers, neighbor, nodeWrapper, totalCostFromStart);
      else if (totalCostFromStart < neighborWrapper.getTotalCostFromStart()) {
        updateTotalCostAndPredecessor(table, neighborWrapper, nodeWrapper, totalCostFromStart);
      }
    }
  }

  private static void addNeighborToTable(
          TreeSet<AStarNodeWrapper> table,
          Function<NodeWithXYCoordinates, Double> heuristic,
          Map<NodeWithXYCoordinates, AStarNodeWrapper> nodeWrappers,
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

  private static void updateTotalCostAndPredecessor(
          TreeSet<AStarNodeWrapper> table,
          AStarNodeWrapper neighborWrapper,
          AStarNodeWrapper predecessor,
          double lowerTotalCostFromStart
  )
  // The position in the TreeSet won't change automatically;
  // we have to remove and reinsert the node.
  // Because TreeSet uses compareTo() to identity a node to remove,
  // we have to remove it *before* we change the cost!
  {
    table.remove(neighborWrapper);

    neighborWrapper.setTotalCostFromStart(lowerTotalCostFromStart);
    neighborWrapper.setPredecessor(predecessor);

    table.add(neighborWrapper);
  }

  private static Path<Double> buildPath(AStarNodeWrapper nodeWrapper,
                                        String source, String destination) {
    return new Path<>(
            source, destination,
            nodeWrapper.getTotalCostFromStart(), buildRoute(nodeWrapper)
    );
  }

  private static void addSource(
          Map<NodeWithXYCoordinates, AStarNodeWrapper> nodeWrappers,
          TreeSet<AStarNodeWrapper> table,
          NodeWithXYCoordinates source,
          Function<NodeWithXYCoordinates, Double> heuristic
  )
  {
    AStarNodeWrapper sourceWrapper =
            new AStarNodeWrapper(source, null, 0.0, heuristic.apply(source));
    nodeWrappers.put(source, sourceWrapper);
    table.add(sourceWrapper);
  }

  private static List<String> buildRoute(AStarNodeWrapper target) {
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

}
