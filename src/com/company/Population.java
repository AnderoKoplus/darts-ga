package com.company;

import com.sun.javafx.collections.transformation.SortedList;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by andero on 20.05.2014.
 */
public class Population {
    private final int size;
    private Individual[] population;
    private int dnaLength;
    private int selectionSize = 10;
    private double mutationRate = 0.9;

    public Population(int size, int dnaLength) {
        this.size = size;
        this.dnaLength = dnaLength;
        population = new Individual[this.size];
    }

    public Population(Individual[] selectedPopulation, int size, int dnaLength) {
        this(size, dnaLength);
        System.arraycopy(selectedPopulation, 0, population, 0, selectedPopulation.length);
        breed(selectedPopulation.length, size - selectedPopulation.length);
    }

    private void breed(int initialPopulation, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            Individual a = population[getRandomIndividual(0, initialPopulation)];
            Individual b = population[getRandomIndividual(0, initialPopulation)];
            population[initialPopulation + i] = crossOver(a, b);
        }
    }

    public void generateRandomPopulation() {
        for (int i = 0; i < size; i++) {
            population[i] = new Individual(dnaLength);
        }
    }

    public Individual crossOver(Individual a, Individual b) {
        int crossoverPoint = a.getRandomDnaSlicePoint();
        int[] newDna = new int[dnaLength];
        System.arraycopy(a.getDna(), 0, newDna, 0, crossoverPoint);
        System.arraycopy(b.getDna(), crossoverPoint, newDna, crossoverPoint, dnaLength - crossoverPoint);
//        System.out.println(crossoverPoint);
//        System.out.println(Arrays.toString(newDna));
        return new Individual(newDna);
    }

    private int getRandomIndividual(int min, int max) {
        return min + (int)(Math.random() * ((max - min)));
    }

    public void mutate() {
        for (int i = selectionSize; i < size; i++) {
//            if (Math.random() < mutationRate) {
                this.population[i].mutate();
//            }
        }
    }

    public void dump() {
        System.out.println("----------");
        for (int i = 0; i < size; i++) {
//            System.out.println(i + ") " + population[i].getDnaDump() + " Score: " + population[i].getSurvivalScore());
            System.out.println(i + ") " + population[i].getDnaDump() + " Score: " + population[i].score() + " SScore" + population[i].getSurvivalScore());
        }
    }

    public Individual[] makeSelection() {
//        Individual[] selection = new Individual[selectionSize];
        double[] diversityMap = new double[size];
        double[] diversityBase = new double[dnaLength];
        int[] diversityScores = new int[dnaLength];

        double[] scoreMap = new double[size];
        int[] scores = new int[size];

        int scoreMax = 0;
        for (int i = 0; i < size; i++) {
            Individual individual = population[i];
            if (scoreMax < individual.score()) {
                scoreMax = individual.score();
            }
            scores[i] = individual.score();
            for (int j = 0; j < dnaLength; j++) {
                int[] dna = individual.getDna();
                diversityScores[j] += dna[j];
            }
        }
        for (int i = 0; i < size; i++) {
            scoreMap[i] = (double)scores[i] / (double)scoreMax;
        }

        for (int i = 0; i < dnaLength; i++) {
            diversityBase[i] += (double)diversityScores[i] / (double)size;
        }

        double diversityMax = 0;
        for (int i = 0; i < size; i++) {
            double diversitySum = 0;
            for (int j = 0; j < dnaLength; j++) {
                diversitySum += Math.abs((float)population[i].getDna()[j] - diversityBase[j]);
            }
            diversityMap[i] = diversitySum;
            if (diversitySum > diversityMax) {
                diversityMax = diversitySum;
            }
        }
        for (int i = 0; i < size; i++) {
            if (0 != diversityMax) {
                diversityMap[i] = diversityMap[i] / diversityMax;
            } else {
                diversityMap[i] = 0;
            }
        }

//        dump();
//        System.out.println(Arrays.toString(scoreMap));
//        System.out.println(Arrays.toString(diversityMap));
//        System.exit(-1);

        for (int i = 0; i < size; i++) {
            population[i].assignSurvivalScore(Math.pow(scoreMap[i], 2) + Math.pow(diversityMap[i], 2));
        }

//
//        System.out.println(Arrays.toString(scores));
//        System.out.println(Arrays.toString(scoreMap));
//
//        System.out.println(Arrays.toString(diversityScores));
//        System.out.println(Arrays.toString(diversityBase));
//        System.out.println(Arrays.toString(diversityMap));
//        selection = new SortedList<Individual>(new IndividualComparator());
//        System.out.println("MaxScore: " + scoreMax + " DiversityMax: " + diversityMax);
        Arrays.sort(population, new IndividualComparator());
        return Arrays.copyOfRange(population, 0, selectionSize);
    }

    public Individual[] getPopulation() {
        return population;
    }
}
