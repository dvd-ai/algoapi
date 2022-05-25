package com.diploma.algoapi.shortpath.algorithms.floyd_warshall;

import java.util.*;


public class FloydWarshallMatrices {

    private final int nodesAmount;
    private final String[] nodes;
    private final Map<String, Integer> nodeNameToIndex;

    final int[][] distances;
    final int[][] paths;

  public FloydWarshallMatrices(String[] nodes) {
      this.nodesAmount = nodes.length;
      this.nodes = nodes;

      this.nodeNameToIndex = new HashMap<>();
      for (int i = 0; i < nodesAmount; i++) {
          this.nodeNameToIndex.put(nodes[i], i);
      }

      this.distances = new int[nodesAmount][nodesAmount];
      this.paths = new int[nodesAmount][nodesAmount];
  }

  /**
   * Returns the cost from source to destination nodes, referenced by their names.
   *
   * @param source the source node name
   * @param dest the destination node name
   * @return the cost from source to destination node
   */
  public int getDistance(String source, String dest) {
    return distances[nodeNameToIndex.get(source)][nodeNameToIndex.get(dest)];
  }

  /**
   * Returns the shortest path from source to destination nodes, referenced by their names.
   *
   * @param source the source node name
   * @param dest the destination node name
   * @return the shortest path from source to destination node, if it exists; an empty optional
   *     otherwise
   */
  //update in diploma: optional is gone
  public List<String> getPath(String source, String dest) {
      int i = nodeNameToIndex.get(source);
      int j = nodeNameToIndex.get(dest);

      if (paths[i][j] == -1) {
            return Collections.emptyList();
      }

      List<String> path = new ArrayList<>();
      path.add(nodes[i]);

      while (i != j) {
          i = paths[i][j];
          path.add(nodes[i]);
      }

      return path;
  }

  /** Prints the cost and successor matrices to the console. */
  public void print() {
    printCosts();
    printSuccessors();
  }

  private void printCosts() {
    System.out.println("Costs:");

    printHeader();

    for (int rowNo = 0; rowNo < nodesAmount; rowNo++) {
      System.out.printf("%5s", nodes[rowNo]);

      for (int colNo = 0; colNo < nodesAmount; colNo++) {
        int cost = distances[rowNo][colNo];
        if (cost == Integer.MAX_VALUE) System.out.print("    ∞");
        else System.out.printf("%5d", cost);
      }
      System.out.println();
    }
  }

  private void printSuccessors() {
    System.out.println("Successors:");

    printHeader();

    for (int rowNo = 0; rowNo < nodesAmount; rowNo++) {
      System.out.printf("%5s", nodes[rowNo]);

      for (int colNo = 0; colNo < nodesAmount; colNo++) {
        int successor = paths[rowNo][colNo];
        String nextNode = successor != -1 ? nodes[successor] : "-";
        System.out.printf("%5s", nextNode);
      }
      System.out.println();
    }
  }

  private void printHeader() {
    System.out.print("     ");
    for (int colNo = 0; colNo < nodesAmount; colNo++) {
      System.out.printf("%5s", nodes[colNo]);
    }
    System.out.println();
  }
}
