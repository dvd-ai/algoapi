package com.diploma.algoapi;

import com.diploma.algoapi.shortpath.algorithms.dijkstra.Dijkstra;
import com.diploma.algoapi.shortpath.algorithms.dijkstra.Graph;
import com.diploma.algoapi.shortpath.algorithms.dijkstra.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlgoapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgoapiApplication.class, args);

        //https://www.baeldung.com/java-dijkstra

        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        nodeA.addDestination(nodeB, 10);
        nodeA.addDestination(nodeC, 15);

        nodeB.addDestination(nodeD, 12);
        nodeB.addDestination(nodeF, 15);

        nodeC.addDestination(nodeE, 10);

        nodeD.addDestination(nodeE, 2);
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Graph graph = new Graph();

        graph.addNode(nodeA);
        graph.addNode(nodeB);
        graph.addNode(nodeC);
        graph.addNode(nodeD);
        graph.addNode(nodeE);
        graph.addNode(nodeF);

        Dijkstra.calculateShortestPathFromSource(nodeA);
        System.out.println(nodeF.getDistance());
        nodeF.getShortestPath().forEach(node -> System.out.println(node.getName()));
    }

}
