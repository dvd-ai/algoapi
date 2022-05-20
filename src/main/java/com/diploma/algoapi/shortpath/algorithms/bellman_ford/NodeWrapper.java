package com.diploma.algoapi.shortpath.algorithms.bellman_ford;

/**
 * Data structure containing a node, it's total cost from the start and its predecessor.
 *
 * <p>Used by {@link BellmanFord}.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class NodeWrapper {
  private final String node;
  private int totalCostFromStart;
  private NodeWrapper predecessor;

  NodeWrapper(String node, int totalCostFromStart, NodeWrapper predecessor) {
    this.node = node;
    this.totalCostFromStart = totalCostFromStart;
    this.predecessor = predecessor;
  }

  //getters, setters
  public String getNode() {
    return node;
  }

  public void setTotalCostFromStart(int totalCostFromStart) {
    this.totalCostFromStart = totalCostFromStart;
  }

  public int getTotalCostFromStart() {
    return totalCostFromStart;
  }

  public void setPredecessor(NodeWrapper predecessor) {
    this.predecessor = predecessor;
  }

  public NodeWrapper getPredecessor() {
    return predecessor;
  }

}