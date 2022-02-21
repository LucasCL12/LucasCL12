import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import application.main;

public class sentenceLearning {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\Programas\\Programación\\SentenceSorterFi\\sentencesOrderedNoScoresNoReps.txt");
        Scanner sc;
        ArrayList<String> sentences = new ArrayList<>();

        try {
            sc = new Scanner(path, "Cp1252");
            while (sc.hasNextLine()) {
                sentences.add(sc.nextLine());
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        int rand;
        Random random = new Random();
        int lowerBound = 1000;
        int upperBound = 6000;
        int show = 10;
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.isBlank()) {
                for (int i = 0; i < show; i++) {
                    rand = random.nextInt(upperBound - lowerBound);
                    rand += lowerBound;

                    System.out.println(rand + "\t" + sentences.get(rand));
                    // System.out.println(sentences.get(rand));
                }
            } else if (userInput.equals("exit")) {
                scanner.close();
                sentences.clear();
                return;
            } else if (userInput.equals("ez")) {
                lowerBound += 1000;
                upperBound += 2000;
            } else if (userInput.equals("up")) {
                // lowerBound += 500;
                upperBound += 1000;
            } else if (userInput.equals("stay")) {
            } else if (userInput.equals("uf")) {
                lowerBound -= 1000;
                upperBound -= 1000;
            } else if (userInput.equals("?")) {
                System.out.println("Lower bound is " + lowerBound);
                System.out.println("Upper bound is " + upperBound);
                System.out.println("Showing " + show + " sentences");

            } else if (userInput.equals("set")) {
                System.out.println("New lower bound, previous was " + lowerBound);
                try {
                    lowerBound = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("New upper bound, previous was " + upperBound);
                try {
                    upperBound = Integer.parseInt(scanner.nextLine());
                    if (upperBound <= lowerBound) {
                        upperBound = lowerBound + 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("New sentences to show");
                try {
                    show = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (lowerBound < 0) {
                lowerBound = 0;
            }
            if (sentences.size() <= upperBound) {
                upperBound = sentences.size() - 1;
            }
        }
    }
}
