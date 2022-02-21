package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		Path path = Paths.get("D:\\Programas\\Programación\\SentenceSorterFi\\Fin100kWords.txt");
		HashMap<String, Integer> scores = new HashMap<>();

		Scanner scanner;
		String wordAux;
		int score = 1;
		try {
			scanner = new Scanner(path);
			while (scanner.hasNext()) {
				wordAux = scanner.nextLine();
				wordAux = wordAux.trim();
				scores.put(wordAux, score);
				score++;
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ONLY IF I WANT TO USE MORE SENTENCES I WILL CHANGE THIS CODE
		// OTHERWISE IT IS FULLY FUNCTIONAL
		/*
		 * try {
		 * 
		 * File folder = new
		 * File("D:\\Programas\\Programación\\SentenceSorterFi\\txts\\"); File[]
		 * listOfFiles = folder.listFiles();
		 * 
		 * 
		 * File fout = new File(
		 * "D:\\Programas\\Programación\\SentenceSorterFi\\sentencesOutputedOtherFileName.txt"
		 * ); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new
		 * FileOutputStream(fout), "Cp1252"));
		 * 
		 * 
		 * for (File file : listOfFiles) { if (file.isFile()) {
		 * 
		 * path = Paths.get(file.getPath());
		 * 
		 * Scanner sc= new Scanner(path, "Cp1252"); sc.useDelimiter("");
		 * 
		 * String mainString = ""; String previousString = ""; String[] arr; int sum;
		 * int words; int end; String[] countingString;
		 * 
		 * while (sc.hasNext()) { if (mainString.equals("?") || mainString.equals(".")
		 * || mainString.equals("!") || mainString.equals("*")) { writer.newLine(); }
		 * mainString = sc.next(); if ((int) mainString.charAt(0) != 13 && (int)
		 * mainString.charAt(0) != 10) { writer.write(mainString); } }
		 * 
		 * sc.close(); } }
		 * 
		 * 
		 * writer.close();
		 * 
		 * } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		try {
			String str;
			String[] strCount;
			path = Paths.get("D:\\Programas\\Programación\\SentenceSorterFi\\sentencesInput.txt");
			Scanner sc = new Scanner(path, "Cp1252");
			ArrayList<Sentence> sentences = new ArrayList<>();

			int sum;
			int words;

			while (sc.hasNextLine()) {
				str = sc.nextLine();
				//Not very elegant, should use the replaceAll method 
				strCount = str.toLowerCase().replace("\t", "").replace(".", "").replace(",", "").replace(";", "")
						.replace("!", "").replace("?", "").replace("\"", "").replace("\'", "").replace("»", "")
						.replace("*", "").replace("-", "").replace("_", "").trim().split(" ");
				sum = 0;
				words = 0;
				for (int i = 0; i < strCount.length; i++) {
					strCount[i] = strCount[i].replace(".", "").replace(",", "").replace(";", "").replace("!", "")
							.replace("?", "").replace("\"", "").replace("\'", "").replace("»", "").replace("*", "")
							.replace("-", "").replace("_", "").trim();
					if (!strCount[i].isBlank()) {
						if (scores.get(strCount[i]) == null) {
							if (sum >= 120000) {
								sum += 1000;
							}
							sum += 120000;
							words++;
						} else {
							sum += scores.get(strCount[i]);
							words++;
						}
					}
				}
				if (words > 0) {
					sentences.add(new Sentence(str, sum / words));
				}
			}
			sc.close();

			// If I wanted to remove duplicates, remember to hash the string in the class
			// Sentence when inserting in the set
			/*
			 * for (Sentence sentence : sentences) { removeDuplicates.add(sentence); }
			 * ArrayList<Sentence> sentences = new ArrayList<>(); for (Sentence sentence :
			 * removeDuplicates) { sentences.add(sentence); }
			 */

			Collections.sort(sentences,
					Comparator.comparing((Sentence s) -> s.getScore()).thenComparing((Sentence s) -> s.getString()));

			// Printing in console
			/*
			 * for (Sentence sentence : sentences) { Thread.sleep(50);
			 * System.out.println(sentence.getScore());
			 * System.out.println(sentence.getString()); }
			 */

			String previous = "";

			// In case I change something in the file sentencesOrderedNoReps execute it again
			//File fout = new File("D:\\Programas\\Programación\\SentenceSorterFi\\sentencesOrderedNoScoresNoReps.txt");
			File fout = new
			File("D:\\Programas\\Programación\\SentenceSorterFi\\sentenceFileDifferentName.txt");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout), "Cp1252"));
			for (Sentence sentence : sentences) {
				if (previous.equals(sentence.getString())) {

				} else {
					previous = sentence.getString();
					//I don't need the score
					//writer.write(String.valueOf(sentence.getScore()));
					//writer.newLine();
					writer.write(sentence.getString());
					writer.newLine();
				}
			}
			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
