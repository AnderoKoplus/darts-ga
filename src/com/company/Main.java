package com.company;

import java.sql.Timestamp;
import java.util.Arrays;

public class Main {
    private static int N = 25;
    private static int populationSize = 100;

    public static void main(String[] args) {
	    Population population = new Population(populationSize, N);
        population.generateRandomPopulation();
        Individual[] selectedPopulation = population.getPopulation();
        int max = 0;

//        for (int i = 0; i < 1000000; i++) {
        while (true) {
            population = new Population(selectedPopulation, populationSize, N);
            population.mutate();
            selectedPopulation = population.makeSelection();
            for (int j = 0; j < populationSize; j++) {
                if (population.getPopulation()[j].score() > max) {
                    max = population.getPopulation()[j].score();
                    java.util.Date date= new java.util.Date();
                    System.out.println(new Timestamp(date.getTime()) + " " + max + " " + Arrays.toString(population.getPopulation()[j].getDna()));
                }
            }
//            if (i % 1000 == 0) {
//                population.dump();
//            }
        }
    }
}
