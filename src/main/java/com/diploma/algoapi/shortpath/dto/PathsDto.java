package com.diploma.algoapi.shortpath.dto;

import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;

import java.util.List;

public record PathsDto(
        List<Path<Integer>>paths
) {
}
