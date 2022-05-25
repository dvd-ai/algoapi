package com.diploma.algoapi;

import com.diploma.algoapi.shortpath.algorithms.dijkstra.Dijkstra;
import com.diploma.algoapi.shortpath.algorithms.dijkstra.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AlgoapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlgoapiApplication.class, args);

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

        nodeD.addDestination(nodeE, 2);//i'm here
        nodeD.addDestination(nodeF, 1);

        nodeF.addDestination(nodeE, 5);

        Set<Node>nodes  = new HashSet<>();
        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);
        nodes.add(nodeE);
        nodes.add(nodeF);

        Dijkstra.calculateShortestPathFromSource(nodeA);
//        System.out.println(nodeF.getDistance());
//        nodeF.getShortestPath().forEach(node -> System.out.println(node.getName()));

//        System.out.println("------------------------");
//        Dijkstra.buildPaths(nodeA.getName(), nodes).forEach(integerPath -> {
//            System.out.println("++++++++++++++");
//            System.out.println("source: " + integerPath.getSource() + " dest: " + integerPath.getDestination() + " cost: " + integerPath.getTotalCost() + " route: " + integerPath.getRoute());
//        });

    }

}
