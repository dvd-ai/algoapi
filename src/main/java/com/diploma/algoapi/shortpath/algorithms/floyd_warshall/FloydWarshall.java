package com.diploma.algoapi.shortpath.algorithms.floyd_warshall;

import com.google.common.graph.ValueGraph;

import java.util.Optional;

public class FloydWarshall {

    public static FloydWarshallMatrices findShortestPaths(
            ValueGraph<String, Integer> graph
    ) {
        String[] nodes = getNodesNames(graph);
        FloydWarshallMatrices matrices = createAndInitMatrices(nodes, graph);
        startIterations(nodes.length, matrices);
        detectNegativeCycles(nodes.length, matrices.distances);
        return matrices;
    }

  private static String[] getNodesNames(ValueGraph<String, Integer> graph) {
     int n = graph.nodes().size();
     return graph.nodes().toArray(new String[n]);
  }

  private static FloydWarshallMatrices createAndInitMatrices(String [] nodes, ValueGraph<String, Integer> graph) {
      FloydWarshallMatrices matrices = new FloydWarshallMatrices(nodes);

      for (int i = 0; i < nodes.length; i++) {
         for (int j = 0; j < nodes.length; j++) {
            Optional<Integer> edgeValue = graph.edgeValue(nodes[i], nodes[j]);
            matrices.distances[i][j] = i == j ? 0 : edgeValue.orElse(Integer.MAX_VALUE);
            matrices.paths[i][j] = edgeValue.isPresent() ? j : -1;
         }
      }
      return matrices;
  }

  private static void startIterations(int nodesAmount, FloydWarshallMatrices matrices) {
     for (int k = 0; k < nodesAmount; k++) {
        for (int i = 0; i < nodesAmount; i++) {
          for (int j = 0; j < nodesAmount; j++) {
            int distanceViaNodeK = addDistances(matrices.distances[i][k], matrices.distances[k][j]);

            if (distanceViaNodeK < matrices.distances[i][j]) {
              matrices.distances[i][j] = distanceViaNodeK;
              matrices.paths[i][j] = matrices.paths[i][k];
            }
          }
       }
     }
  }

  private static int addDistances(int a, int b) {
      if (a == Integer.MAX_VALUE || b == Integer.MAX_VALUE) {
        return Integer.MAX_VALUE;
      }
      return a + b;
  }

  private static void detectNegativeCycles(int nodesAmount, int[][] distanceMatrix) {
      for (int i = 0; i < nodesAmount; i++) {
          if (distanceMatrix[i][i] < 0) {
             throw new IllegalArgumentException("Floyd Warshall: Graph has a negative cycle");
          }
      }
  }
}
