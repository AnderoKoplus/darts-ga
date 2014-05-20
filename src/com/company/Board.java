package com.company;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Andero on 18.05.2014.
 */
public class Board {
    private int size;
    private int[] regions;
    private int heuristicLimit = 0;
    private int max = 0;


    public Board(int[] regionMap) {
        this.regions = regionMap.clone();
        this.size = regionMap.length;
    }

    public int getHeuristicLimit() {
        if (0 == heuristicLimit) {
            heuristicLimit = calculateHeuristicLimit();
        }
        return heuristicLimit;
    }

    private int calculateHeuristicLimit() {
        return 3 * findMaxRegion() + 1;
    }
    public int getMax() {
        if (0 == max) {
            max = calculateMax();
        }
        return max;
    }

    private int findMaxRegion() {
//        System.out.println(Arrays.toString(regions));
        int max = 0;
        for (int i = 0; i < size; i++) {
            if (max < regions[i]) {
                max = regions[i];
            }
        }
        return max;
    }

    private int calculateMax() {
        int[] valueMap = new int[getHeuristicLimit() + 1]; // to compensate 0 pos
        int max = 0;

        // write initial values and multiples
        for (int i = 0; i < size; i++) {
            if (regions[i] < 0) {
                return 0;
            }
            valueMap[regions[i]] = 1;
            valueMap[regions[i] * 2] = 2;
            valueMap[regions[i] * 3] = 3;
        }
        boolean changeMade;
        do {
            changeMade = false;
            for (int i = 1; i < valueMap.length; i++) {
                if (valueMap[i] < 3 && valueMap[i] > 0) {
                    for (int j = 0; j < size; j++) {
                        if (i + regions[j] < valueMap.length) {
                            if (valueMap[i + regions[j]] == 0) {
                                valueMap[i + regions[j]] = valueMap[i] + 1;
                                changeMade = true;
                            } else if (valueMap[i + regions[j]] > valueMap[i] + 1) {
                                valueMap[i + regions[j]] = valueMap[i] + 1;
                                changeMade = true;
                            }
                        }
                    }
                }
            }
        } while (changeMade);

        for (int i = 1; i < valueMap.length; i++) {
            if (valueMap[i] != 0) {
                max = i;
            } else {
                break;
            }
        }
        return max + 1;
    }
}
