package com.diploma.algoapi.shortpath.algorithms.bellman_ford;



import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraph;

import java.util.*;

public class BellmanFord {

  public static List<Path<Integer>> findShortestPath(ValueGraph<String, Integer> graph, String source) {
    Map<String, NodeWrapper> table = initTable(graph, source);
    startIterations(graph, table);
    return buildPaths(source, table);
  }

  private static void startIterations(ValueGraph<String, Integer> graph, Map<String, NodeWrapper> table) {
    int n = graph.nodes().size();
    for (int i = 0; i < n; i++) {
      boolean isLastIteration = i == n - 1;

      for (EndpointPair<String> edge : graph.edges()) {
        NodeWrapper sourceWrapper = table.get(edge.source());

        int totalCostFromStart = sourceWrapper.getTotalCostFromStart();
        if (totalCostFromStart == Integer.MAX_VALUE)
          continue;

        int totalCostToTarget = totalCostFromStart + graph.edgeValue(edge).get();

        tryUpdateTotalCostAndPredecessor(
                table.get(edge.target()), sourceWrapper,
                totalCostToTarget, isLastIteration
        );
      }
    }
  }

  private static void tryUpdateTotalCostAndPredecessor(NodeWrapper targetWrapper, NodeWrapper sourceWrapper,
                                                       int totalCostToTarget, boolean isLastIteration) {
    if (totalCostToTarget < targetWrapper.getTotalCostFromStart()) {
      if (isLastIteration) {
        throw new IllegalArgumentException("Bellman Ford: Negative cycle detected");
      }
      targetWrapper.setTotalCostFromStart(totalCostToTarget);
      targetWrapper.setPredecessor(sourceWrapper);
    }
  }


  private static List<Path<Integer>> buildPaths(String source, Map<String, NodeWrapper> table) {
    List<Path<Integer>> paths = new ArrayList<>();

    for (Map.Entry<String, NodeWrapper> entry : table.entrySet()) {
      paths.add(
              new Path<>(
                      source,
                      entry.getKey(),
                      entry.getValue().getTotalCostFromStart(),
                      buildRoute(entry.getValue())
              )
      );
    }
    return paths;
  }

  private static List<String> buildRoute(NodeWrapper target) {
    if (target.getPredecessor() == null)
      return new ArrayList<>();

    List<String> route = new ArrayList<>();
    while (target != null) {
      route.add(target.getNode());
      target = target.getPredecessor();
    }
    Collections.reverse(route);
    return route;
  }

  private static Map<String, NodeWrapper> initTable(ValueGraph<String, Integer> graph,
                                                    String source) {

    Map<String, NodeWrapper> table = new HashMap<>();

    for (String node : graph.nodes()) {
      int initialCostFromStart = Objects.equals(node, source) ? 0 : Integer.MAX_VALUE;
      NodeWrapper nodeWrapper = new NodeWrapper(node, initialCostFromStart, null);
      table.put(node, nodeWrapper);
    }
    return table;
  }

}
