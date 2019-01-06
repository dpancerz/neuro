package com.dpancerz.nai.movement;

import java.io.File;

class Path {
    static final String S = File.separator;

    static final String DATA_ROOT = "D:" + S + "home" + S + "repos" + S + "edu" + S + "nai" + S + "projekt" + S + "data" + S + "activity"; // MODIFY ONLY THIS

    static final String FEATURES = DATA_ROOT + S + "features.txt";//99 tBodyAccJerk-energy()-Z
    static final String CATEGORIES_LABELS = DATA_ROOT + S + "activity_labels.txt";//"5 STANDING" - maps categories to labels

    private static final String TRAIN_SET_ROOT = DATA_ROOT + S + "train";
    static final String TRAIN_SET_VALUES = TRAIN_SET_ROOT + S + "X_train.txt";//"  2.8058569e-001 -9.9602983e-003 (...)"
    static final String TRAIN_SET_CATEGORIES = TRAIN_SET_ROOT + S + "y_train.txt";//"5" - maps to category
    static final String TRAIN_PERSON_MEASURED = TRAIN_SET_ROOT + S + "subject_train.txt"; //"13"; unused, not checking this one

    private static final String TEST_SET_ROOT = DATA_ROOT + S + "test";
    static final String TEST_SET_VALUES = TEST_SET_ROOT + S + "X_test.txt";//"  2.8058569e-001 -9.9602983e-003 (...)"
    static final String TEST_SET_CATEGORIES = TEST_SET_ROOT + S + "y_test.txt";//"5" - maps to category
    static final String TEST_PERSON_MEASURED = TEST_SET_ROOT + S + "subject_test.txt"; //"13"; unused, not checking this one

    static final String LOG_FILE_PATH = DATA_ROOT + S + "log.txt";
}
