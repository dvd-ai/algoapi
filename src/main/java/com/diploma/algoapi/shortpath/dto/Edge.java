package com.diploma.algoapi.shortpath.dto;

public record Edge<N>(
        String startVertexName,
        String endVertexName,
        N value
){
}
