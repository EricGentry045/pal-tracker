package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class EnvController {
    private String port;
    private String memoryLimit;
    private String cfInstanceIndex;
    private String cfInstanceAddress;

    public EnvController(
            @Value("${port:NOT SET}") String port,
            @Value("${memory.limit:NOT SET}") String memoryLimit,
            @Value("${cf.instance.index:NOT SET}") String cfInstanceIndex,
            @Value("${cf.instance.addr:NOT SET}") String cfInstanceAddr) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.cfInstanceIndex = cfInstanceIndex;
        this.cfInstanceAddress = cfInstanceAddr;
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return Stream.of(new String[][] {
            {"PORT", port},
            {"MEMORY_LIMIT", memoryLimit},
            {"CF_INSTANCE_INDEX", cfInstanceIndex},
            {"CF_INSTANCE_ADDR", cfInstanceAddress},
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    }
}
