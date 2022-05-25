package com.diploma.algoapi.shortpath;

import com.diploma.algoapi.shortpath.dto.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/algo/shortest-path")
public class ShortestPathController {

    private final ShortestPathService shortestPathService;

    public ShortestPathController(ShortestPathService shortestPathService) {
        this.shortestPathService = shortestPathService;
    }

    @PostMapping("/dijkstra")
    public PathsDto dijkstra(@RequestBody GraphDto graphDto, @RequestParam String source) {
        return shortestPathService.dijkstra(graphDto, source);
    }

    @PostMapping("/floyd")
    public PathsDto floyd(@RequestBody GraphDto graphDto) {
        return shortestPathService.floyd(graphDto);
    }

    @PostMapping("/ford")
    public PathsDto ford(@RequestBody GraphDto graphDto, @RequestParam String source) {
        return shortestPathService.ford(graphDto, source);
    }

    @PostMapping("/a-star")
    public SinglePathDto aStar(
            @RequestBody GraphXYDto graphXYDto,
            @RequestParam String source,
            @RequestParam String destination
    ) {
        return shortestPathService.aStar(graphXYDto, source, destination);
    }

}
