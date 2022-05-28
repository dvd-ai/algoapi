package com.diploma.algoapi.shortpath;

import com.diploma.algoapi.shortpath.algorithms.bellman_ford.Path;
import com.diploma.algoapi.shortpath.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/algo/shortest-path")
public class ShortestPathController {

    private final ShortestPathService shortestPathService;

    public ShortestPathController(ShortestPathService shortestPathService) {
        this.shortestPathService = shortestPathService;
    }

    @PostMapping("/dijkstra")
    public List<Path<Integer>> dijkstra(@RequestBody GraphDto graphDto, @RequestParam String source) {
        return shortestPathService.dijkstra(graphDto, source);
    }

    @PostMapping("/floyd")
    public List<Path<Integer>> floyd(@RequestBody GraphDto graphDto) {
        return shortestPathService.floyd(graphDto);
    }

    @PostMapping("/ford")
    public List<Path<Integer>> ford(@RequestBody GraphDto graphDto, @RequestParam String source) {
        return shortestPathService.ford(graphDto, source);
    }

    @PostMapping("/a-star")
    public Path<Double> aStar(@RequestBody GraphXYDto graphXYDto, @RequestParam String source,
            @RequestParam String destination
    ) {
        return shortestPathService.aStar(graphXYDto, source, destination);
    }

}
