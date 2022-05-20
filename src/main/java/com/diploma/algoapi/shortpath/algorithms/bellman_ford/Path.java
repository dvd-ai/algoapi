package com.diploma.algoapi.shortpath.algorithms.bellman_ford;

import java.util.List;

public class Path {
    private String source;
    private String destination;
    private int totalCost;
    private List<String> route;

    public Path(String source, String destination, int totalCost, List<String> route) {
        this.source = source;
        this.destination = destination;
        this.totalCost = totalCost;
        this.route = route;
    }
    //getters, setters
    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getRoute() {
        return route;
    }

    public void setRoute(List<String> route) {
        this.route = route;
    }
}
