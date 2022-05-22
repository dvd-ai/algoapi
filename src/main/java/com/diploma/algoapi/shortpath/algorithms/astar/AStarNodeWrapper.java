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

  private void calculateCostSum() {
    this.costSum = this.totalCostFromStart + this.minimumRemainingCostToTarget;
  }

  public NodeWithXYCoordinates getNode() {
    return node;
  }

  public void setPredecessor(AStarNodeWrapper predecessor) {
    this.predecessor = predecessor;
  }

  public AStarNodeWrapper getPredecessor() {
    return predecessor;
  }

  public void setTotalCostFromStart(double totalCostFromStart) {
    this.totalCostFromStart = totalCostFromStart;
    calculateCostSum();
  }

  public double getTotalCostFromStart() {
    return totalCostFromStart;
  }

  @Override
  public int compareTo(AStarNodeWrapper other) {
    int compare = Double.compare(this.costSum, other.costSum);
    if (compare == 0) {
      compare = node.compareTo(other.node);
    }
    return compare;
  }

  // Not overriding equals() and hashcode(), to use Object's methods.
  // Object's methods use object identity, which is much faster.
  // It's sufficient as within the algorithm, we have only one AStarNodeWrapper
  // instance per node.

  @Override
  public String toString() {
    return "AStarNodeWrapperForTreeSet{"
        + "node="
        + node
        + ", predecessor="
        + (predecessor != null ? predecessor.getNode().toString() : "")
        + ", totalCostFromStart="
        + totalCostFromStart
        + ", minimumRemainingCostToTarget="
        + minimumRemainingCostToTarget
        + ", costSum="
        + costSum
        + '}';
  }
}
