package com.company;

import java.util.Arrays;

/**
 * Created by andero on 20.05.2014.
 */
public class Individual {
    private int dnaLength;
    private int[] dna;
    private int score = 0;
    private double survivalScore;


    public Individual(int dnaLength) {
        this.dnaLength = dnaLength;
        dna = new int[dnaLength];
        randomizeDna();
    }

    public Individual(int[] dna) {
        this.dnaLength = dna.length;
        this.dna = dna;
    }

    private void randomizeDna() {
        // not so random :)
        for (int i = 0; i < dnaLength; i++) {
            if (i == 0) {
                dna[i] = 1;
            } else {
                dna[i] = 1;
            }
        }
    }

    public void mutate() {
        int i = getRandomDnaPoint();
        int step = 1;

        if (Math.random() > 0.5) {
            if (dna[i - 1] * 3 + 1 < dna[i]) {
                // avoid
            } else {
                dna[i] += step;
            }
        } else {
            if (dna[i - 1] >= dna[i]) {
                // avoid
            } else {
                dna[i] -= step;
            }
        }
//        System.out.println(Arrays.toString(dna));
    }

    public int getRandomDnaPoint() {
        return getRandomPointInDna(1, dnaLength - 1);
    }

    private int getRandomPointInDna(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public int getFitness() {
        return 0;
    }

    public double getDiversity() {
        return 0.0;
    }

    public String getDnaDump() {
        return Arrays.toString(dna);
    }

    public int score() {
        if (0 == score) {
            Board board = new Board(dna);
            score = board.getMax();
        }
        return score;
    }

    public int[] getDna() {
        return dna;
    }

    public void assignSurvivalScore(double v) {
        this.survivalScore = v;
    }

    public double getSurvivalScore() {
        return survivalScore;
    }

    public int getRandomDnaSlicePoint() {
        return getRandomPointInDna(1, dnaLength - 1);
    }
}
