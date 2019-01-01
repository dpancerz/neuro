package com.dpancerz.nai.movement;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class MovementDataSetExtractor {
    private final String path;

    MovementDataSetExtractor(String path) {
        this.path = path;
    }

    Stream<String> getInput() {
        try {
            return Files.lines(Paths.get(path));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
