package com.quizdeck.analysis.inputs;

import java.util.Comparator;

/**
 * Sort Guesses by arrival time
 */
public class GuessArrivalComparator implements Comparator<Guess> {

    public GuessArrivalComparator(boolean increasingOrder) {
        INCREASING_ORDER = increasingOrder;
    }

    public boolean isIncreasingOrder() {
        return INCREASING_ORDER;
    }

    @Override
    public int compare(Guess g1, Guess g2) {
        if(isIncreasingOrder())
            return compareIncreasing(g1, g2);
        return compareIncreasing(g2, g1);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    private int compareIncreasing(Guess g1, Guess g2) {
        if(g1.getTimeStamp() < g2.getTimeStamp())
            return -1;
        if(g1.getTimeStamp() > g2.getTimeStamp())
            return 1;
        return 0;
    }

    private final boolean INCREASING_ORDER;
}
