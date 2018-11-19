package com.dpancerz.nai;

public class Numbers {
    private static final char OPTICAL_REPRESENTATION_OF_ONE = '@';
    private static final char OPTICAL_REPRESENTATION_OF_ZERO = ' ';

    // @formatter:off
    private static final String ONE =
                "  @ "
              + "  @ "
              + "  @ "
              + "  @ "
              + "  @ "
              + "  @ ";
    private static final String TWO =
                "@@@ "
              + "   @"
              + "  @ "
              + " @  "
              + "@   "
              + "@@@@";
    private static final String THREE =
                "@@@ "
              + "   @"
              + " @@@"
              + "   @"
              + "   @"
              + "@@@@";
    private static final String FOUR =
                "@   "
              + "@   "
              + "@ @ "
              + "@@@@"
              + "  @ "
              + "  @ ";
    private static final String FIVE =
                "@@@@"
              + "@   "
              + "@@@ "
              + "   @"
              + "   @"
              + "@@@ ";
    // @formatter:on

    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        numbers.train();
    }

    private void train() {
        train(1, ONE);
        train(2, TWO);
        train(3, THREE);
        train(4, FOUR);
        train(5, FIVE);
    }

    private void train(int expectedResult, String trainingObject) {

    }
}
