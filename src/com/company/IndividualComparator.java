package com.company;

import java.util.Comparator;

/**
 * Created by andero on 20.05.2014.
 */
public class IndividualComparator implements Comparator<Individual> {

    @Override
    public int compare(Individual o1, Individual o2) {
        if (o1.getSurvivalScore() > o2.getSurvivalScore()) {
            return -1;
        }
        if (o1.getSurvivalScore() < o2.getSurvivalScore()) {
            return 1;
        }
        return 0;
    }
}
