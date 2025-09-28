package com.jeltechnologies.mcp.sl.departures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SiteRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteRepository.class);

    private final ObjectMapper objectMapper;

    private final static Path FILE_PATH = Paths.get(System.getProperty("java.io.tmpdir") + "/transporttimes/sites.json");

    public SiteRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Site> load() throws IOException {
        List<Site> result = null;
        if (Files.exists(FILE_PATH)) {
            try (var reader = Files.newBufferedReader(FILE_PATH, StandardCharsets.UTF_8)) {
                result = objectMapper.readValue(reader, new TypeReference<>() {
                });
            }
        }
        LOGGER.debug("Loaded sites from file " + FILE_PATH.toString());
        return result;
    }

    public void save(List<Site> sites) throws IOException {
        if (sites != null) {
            Files.createDirectories(FILE_PATH.getParent());
            try (var writer = Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValue(writer, sites);
            }
            LOGGER.debug("Saved sites to file " + FILE_PATH.toString());
        }
    }
}
