package com.diploma.algoapi.shortpath.algorithms.astar;

/**
 * Data structure containing a node, its predecessor, its total cost from the start, its minimum
 * remaining cost, and the cost sum.
 *
 * <p>Used by {@link AStar}
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class AStarNodeWrapper implements Comparable<AStarNodeWrapper> {
  private final NodeWithXYCoordinates node;
  private AStarNodeWrapper predecessor;
  private double totalCostFromStart;
  private final double minimumRemainingCostToTarget;
  private double costSum;

  public AStarNodeWrapper(
      NodeWithXYCoordinates node,
      AStarNodeWrapper predecessor,
      double totalCostFromStart,
      double minimumRemainingCostToTarget) {
    this.node = node;
    this.predecessor = predecessor;
    this.totalCostFromStart = totalCostFromStart;
    this.minimumRemainingCostToTarget = minimumRemainingCostToTarget;
    calculateCostSum();
  }

  @Override
  public int compareTo(AStarNodeWrapper other) {
    int compare = Double.compare(this.costSum, other.costSum);
    if (compare == 0) {
      compare = node.compareTo(other.node);
    }
    return compare;
  }

  public void setTotalCostFromStart(double totalCostFromStart) {
    this.totalCostFromStart = totalCostFromStart;
    calculateCostSum();
  }

  private void calculateCostSum() {
    this.costSum = this.totalCostFromStart + this.minimumRemainingCostToTarget;
  }

  //getters, setters
  public NodeWithXYCoordinates getNode() {
    return node;
  }

  public void setPredecessor(AStarNodeWrapper predecessor) {
    this.predecessor = predecessor;
  }

  public AStarNodeWrapper getPredecessor() {
    return predecessor;
  }



  public double getTotalCostFromStart() {
    return totalCostFromStart;
  }


}
