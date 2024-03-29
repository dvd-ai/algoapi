package com.diploma.algoapi.shortpath;

import com.diploma.algoapi.shortpath.algorithms.astar.AStar;
import com.diploma.algoapi.shortpath.algorithms.astar.HeuristicForNodesWithXYCoordinates;
import com.diploma.algoapi.shortpath.algorithms.astar.NodeWithXYCoordinates;
import com.diploma.algoapi.shortpath.algorithms.bellman_ford.BellmanFord;
import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;
import com.diploma.algoapi.shortpath.algorithms.dijkstra.Dijkstra;
import com.diploma.algoapi.shortpath.algorithms.dijkstra.Node;
import com.diploma.algoapi.shortpath.algorithms.floyd_warshall.FloydWarshall;
import com.diploma.algoapi.shortpath.algorithms.floyd_warshall.FloydWarshallMatrices;
import com.diploma.algoapi.shortpath.dto.*;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShortestPathService {

    public List<Path<Integer>> dijkstra(GraphDto graphDto, String source) {
        Map<String, Node> nodes = new HashMap<>();
        initNodes(graphDto.edges(), nodes);
        Dijkstra.calculateShortestPathFromSource(nodes.get(source));
        return Dijkstra.buildPaths(source, nodes);
    }

    public List<Path<Integer>> floyd(GraphDto graphDto) {
        ValueGraph<String, Integer> graph = createGraph(graphDto.edges());
        FloydWarshallMatrices floydWarshallMatrices = FloydWarshall.findShortestPaths(graph);
        return getAllPairsPaths(floydWarshallMatrices, graph.nodes());
    }

    public List<Path<Integer>> ford(GraphDto graphDto, String source) {
        ValueGraph<String, Integer> graph = createGraph(graphDto.edges());
        return BellmanFord.findShortestPath(graph, source);
    }

    public Path<Double> aStar(GraphXYDto graphXYDto, String sourceName, String destinationName) {
        Map<String, NodeWithXYCoordinates>nodes = getUniqueXYNodes(graphXYDto.nodesXY());
        ValueGraph<NodeWithXYCoordinates, Double> graph = createGraphXY(graphXYDto.edges(), nodes);

        NodeWithXYCoordinates source = nodes.get(sourceName);
        NodeWithXYCoordinates destination = nodes.get(destinationName);

        return new AStar(
                graph, source,
                destination, new HeuristicForNodesWithXYCoordinates(graph, destination)
        ).findShortestPath();
    }

    private ValueGraph<NodeWithXYCoordinates, Double> createGraphXY(
            List<Edge<Double>>edges, Map<String, NodeWithXYCoordinates>nodes
    ) {

        MutableValueGraph<NodeWithXYCoordinates, Double> graph = ValueGraphBuilder.undirected().build();
        edges
                .forEach(
                        edge -> graph.putEdgeValue(
                                nodes.get(edge.startVertexName()),
                                nodes.get(edge.endVertexName()), edge.value()
                        )
                );
        return graph;
    }

    private ValueGraph<String, Integer> createGraph(List<Edge<Integer>>edges) {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().build();
        edges
                .forEach(
                        edge -> graph.putEdgeValue(
                                edge.startVertexName(), edge.endVertexName(), edge.value()
                        )
                );
        return graph;
    }

    private void initNodes(List<Edge<Integer>> edges, Map<String, Node>nodes) {
        setUniqueNodes(edges, nodes);
        setDistances(edges, nodes);
    }

    private void setDistances(List<Edge<Integer>> edges, Map<String, Node>nodes) {
        edges.forEach(
                edge -> {
                    Node startNode = nodes.get(edge.startVertexName());
                    Node endNode = nodes.get(edge.endVertexName());
                    startNode.addDestination(endNode, edge.value());
                }
        );
    }

    private void setUniqueNodes(List<Edge<Integer>> edges, Map<String, Node>nodes) {
        edges.forEach(
                edge -> {
                    Node start = new Node(edge.startVertexName());
                    Node end = new Node(edge.endVertexName());

                    nodes.put(end.getName(), end);
                    nodes.put(start.getName(), start);
                }
        );
    }

    private Map<String, NodeWithXYCoordinates> getUniqueXYNodes(List<NodeWithXYCoordinates> nodesXY) {
        Map<String, NodeWithXYCoordinates> nodes = new HashMap<>();
        Set<NodeWithXYCoordinates> uniqueNodes = new HashSet<>(nodesXY);
        uniqueNodes.forEach(node -> nodes.put(node.getName(), node));
        return nodes;
    }

    private List<Path<Integer>> getAllPairsPaths(
            FloydWarshallMatrices floydWarshallMatrices,
            Set<String> nodes
    ) {
        List<Path<Integer>> paths = new LinkedList<>();

        for (String source : nodes) {
            for (String destination : nodes) {
                paths.add(
                        new Path<>(
                                source, destination,
                                floydWarshallMatrices.getDistance(source, destination),
                                floydWarshallMatrices.getPath(source, destination)
                        )
                );
            }
        }
        return paths;
    }

}
