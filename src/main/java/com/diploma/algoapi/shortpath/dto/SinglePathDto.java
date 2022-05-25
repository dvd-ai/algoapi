package com.diploma.algoapi.shortpath.dto;

import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;

public record SinglePathDto(
        Path<Double>path
) {
}
