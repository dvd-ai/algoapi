package com.diploma.algoapi.shortpath.dto;

import com.diploma.algoapi.shortpath.algorithms.astar.NodeWithXYCoordinates;

import java.util.List;

public record GraphXYDto (
        List<NodeWithXYCoordinates> nodesXY,
        List<Edge>edges
){

}
