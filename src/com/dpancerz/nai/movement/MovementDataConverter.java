package com.dpancerz.nai.movement;

import com.dpancerz.nai.base.config.TrainingDataConverter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;

import static com.dpancerz.nai.movement.MovementDataFacade.SPACE;
import static com.dpancerz.nai.movement.Path.*;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

class MovementDataConverter implements TrainingDataConverter<MovementType> {
    private final int outputLength;
    private final Map<Integer, MovementType> outputCategories;
    private final DecimalFormat format;
    private final FileStreamer streamer;

    MovementDataConverter(Set<MovementType> outputCategories, FileStreamer streamer) {
        this.outputLength = outputCategories.size();
        this.outputCategories = asMap(outputCategories);
        this.streamer = streamer;
        this.format = new DecimalFormat("0.#######E000",
                new DecimalFormatSymbols(Locale.ENGLISH));
        format.setParseBigDecimal(true);
    }

    private Map<Integer, MovementType> asMap(Set<MovementType> outputCategories) {
        return outputCategories.stream()
                .collect(toMap(
                        MovementType::getCategory,
                        Function.identity()));
    }

    Map<double[], double[]> extractTrainingSet() {
        return extractDataSet(TRAIN_SET_VALUES, TRAIN_SET_CATEGORIES);
    }

    Map<double[], double[]> extractTestSet() {
        return extractDataSet(TEST_SET_VALUES, TEST_SET_CATEGORIES);
    }

    private Map<double[], double[]> extractDataSet(String valuesFilePath,
                                                   String categoriesFilePath) {
        List<double[]> inputData = inputData(valuesFilePath);
        List<double[]> categories = categories(categoriesFilePath);

        return buildInputToOutputMap(inputData, categories);
    }

    private List<double[]> inputData(String filePath) {
        return streamer.streamFromFile(filePath)
                .map(String::trim)
                .map(this::toInput)
                .collect(toList());
    }

    private List<double[]> categories(String filePath) {
        return streamer.streamFromFile(filePath)
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .mapToObj(this::toOutput)
                .collect(toList());
    }

    private Map<double[], double[]> buildInputToOutputMap(List<double[]> inputData,
                                                          List<double[]> categories) {
        sizePrecondition(inputData, categories);

        Map<double[], double[]> result = new HashMap<>();
        for (int i = 0; i < inputData.size(); i++) {
            result.put(inputData.get(i), categories.get(i));
        }
        return result;
    }

    private void sizePrecondition(List<double[]> inputData, List<double[]> categories) {
        if (inputData.size() != categories.size()) {
            throw new IllegalArgumentException("not matching results with data");
        }
    }

    @Override
    public double[] toInput(String trainingObject) {
        return Arrays.stream(trainingObject.split(SPACE))
                .filter(s -> !s.isBlank())
                .map(this::parse)
                .mapToDouble(BigDecimal::doubleValue)
                .toArray();
    }

    private BigDecimal parse(String source) {
        try {
            return (BigDecimal) format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    @Override
    public double[] toOutput(int expectedResult) {
        double[] expectedOutput = new double[outputLength];
        expectedOutput[expectedResult - 1] = 1.0;
        return expectedOutput;
    }

    @Override
    public MovementType fromOutputToCategory(double[] result) {
        int max = findMax(result);
        return Optional
                .ofNullable(outputCategories.get(max))
                .orElseThrow(() -> new IllegalArgumentException(format(
                        "could not find category for '%s', read as '%s', outputCategories: '%s'",
                        Arrays.toString(result), max, outputCategories)));
    }

    @Override
    public int findMax(double[] result) {
        int indexOfMax = 1; // IMPORTANT! apply to master as well
        double max = result[0];
        for (int i = 0; i < result.length - 1; i++) {// 00001 -> 5, 00100 -> 3
            if (max < result[i + 1]) {
                max = result[i + 1];
                indexOfMax = i + 1;
            }
        }
        return indexOfMax;
    }
}
