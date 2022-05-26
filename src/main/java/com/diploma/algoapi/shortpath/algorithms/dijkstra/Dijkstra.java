package com.diploma.algoapi.shortpath.algorithms.dijkstra;

import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;

import java.util.*;
import java.util.stream.Collectors;

public class Dijkstra {

    public static void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    private static Node getLowestDistanceNode(Set <Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public static List<Path<Integer>> buildPaths(String source, Map<String, Node>nodes) {
        List<Path<Integer>> paths = new ArrayList<>();
        for (Map.Entry<String, Node> node : nodes.entrySet()) {
            paths.add(new Path<>(source, node.getKey(), node.getValue().getDistance(), buildRoute(node.getValue())));
        }
        return paths;
    }

    private static List<String> buildRoute(Node node) {
        List<String>route = node.getShortestPath()
                .stream()
                .map(Node::getName)
                .collect(Collectors.toList());
        route.add(node.getName());
        return route;
    }
}
