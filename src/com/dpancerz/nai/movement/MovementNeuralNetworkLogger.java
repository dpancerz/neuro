package com.dpancerz.nai.movement;

import com.dpancerz.nai.base.config.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.Path;

class MovementNeuralNetworkLogger implements Logger {
    private final String filePath;
    private final boolean isFileAccessible;

    MovementNeuralNetworkLogger(String filePath) {
        this.filePath = filePath;
        this.isFileAccessible = tryMakeFileAccessible(filePath);
    }

    private boolean tryMakeFileAccessible(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            return Files.isWritable(path)
                    || path.toFile().setWritable(true);
        }
        try {
            return Files.createFile(path)
                    .toFile()
                    .setWritable(true);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void log(String message) {
        System.out.println(message);

        if (isFileAccessible) {
            writeToFile(message);
        }
    }

    private void writeToFile(String message) {
        try {
            Files.write(
                    Paths.get(filePath),
                    (message + "\n").getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
