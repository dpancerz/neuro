package com.dpancerz.nai.movement;

import java.util.stream.Stream;

class FileStreamer {

    Stream<String> streamFromFile(String filePath) {
        return new MovementDataSetExtractor(filePath)
                .getInput()
                .filter(this::isNotBlank);
    }

    private boolean isNotBlank(String s) {
        return !s.isBlank();
    }
}
