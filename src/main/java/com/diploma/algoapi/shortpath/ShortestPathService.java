package com.diploma.algoapi.shortpath;

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

    public PathsDto dijkstra(GraphDto graphDto, String source) {
        Map<String, Node> nodes = new HashMap<>();
        initNodes(graphDto.edges(), nodes);
        Dijkstra.calculateShortestPathFromSource(nodes.get(source));
        return new PathsDto(Dijkstra.buildPaths(source, nodes));
    }

    public PathsDto floyd(GraphDto graphDto) {
        ValueGraph<String, Integer> graph = createGraph(graphDto);
        FloydWarshallMatrices floydWarshallMatrices = FloydWarshall.findShortestPaths(graph);
        return new PathsDto(getAllPairsPaths(floydWarshallMatrices, graph.nodes()));
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

    public PathsDto ford(GraphDto graphDto, String source) {
        return new PathsDto(null);
    }

    public SinglePathDto aStar(GraphXYDto graphXYDto, String source, String destination) {
        return new SinglePathDto(null);
    }

    private ValueGraph<String, Integer> createGraph(GraphDto graphDto) {
        MutableValueGraph<String, Integer> graph = ValueGraphBuilder.directed().build();
        graphDto.edges()
                .forEach(
                        edge -> graph.putEdgeValue(
                                edge.startVertexName(), edge.endVertexName(), edge.value()
                        )
                );
        return graph;
    }

    private void initNodes(List<Edge> edges, Map<String, Node>nodes) {
        setUniqueNodes(edges, nodes);
        setDistances(edges, nodes);
    }

    private void setDistances(List<Edge> edges, Map<String, Node>nodes) {
        edges.forEach(
                edge -> {
                    Node startNode = nodes.get(edge.startVertexName());
                    Node endNode = nodes.get(edge.endVertexName());
                    startNode.addDestination(endNode, edge.value());
                }
        );
    }

    private void setUniqueNodes(List<Edge> edges, Map<String, Node>nodes) {
        edges.forEach(
                edge -> {
                    Node start = new Node(edge.startVertexName());
                    Node end = new Node(edge.endVertexName());

                    nodes.put(end.getName(), end);
                    nodes.put(start.getName(), start);
                }
        );
    }
}
