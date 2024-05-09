package edu.iastate.cs228.hw4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Isabelle Singh
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Please enter a filename to decode:");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();
		sc.close();

		StringBuilder dataConstruct = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				dataConstruct.append(line).append("\n");
			}
		}

		String data = dataConstruct.toString().trim();
		int lastNL = data.lastIndexOf('\n');
		String encodingPatt = data.substring(0, lastNL);
		String binCode = data.substring(lastNL).trim();

		Set<Character> chars = new HashSet<>();
		char[] encodingPattArr = encodingPatt.toCharArray();
		for (int i = 0; i < encodingPattArr.length; i++) {
			char currentChar = encodingPattArr[i];
			if (currentChar != '^') {
				chars.add(currentChar);
			}
		}

		StringBuilder charSetStringBuilder = new StringBuilder();
		for (int i = 0; i < chars.size(); i++) {
			char c = (char) chars.toArray()[i];
			charSetStringBuilder.append(String.valueOf(c));
		}
		String charSetString = charSetStringBuilder.toString();

		MsgTree root = new MsgTree(encodingPatt);
		MsgTree.printCodes(root, charSetString);
		root.decode(root, binCode);
	}
}