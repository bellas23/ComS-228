package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
 * @author Isabelle Singh
 *
 */
public class MsgTree {

	public char payloadChar;
	public MsgTree left;
	public MsgTree right;

	/**
	 * Stores binary code for a given character when findBinCode() is called
	 */
	private static String binCode;

	/**
	 * 
	 * Constructor building the tree from a string
	 * 
	 * @param encodingString
	 */
	public MsgTree(String encodingString) {
		if (encodingString == null || encodingString.length() < 2)
			return;

		Stack<MsgTree> stk = new Stack<>();
		int currIndex = 0;
		this.payloadChar = encodingString.charAt(currIndex);
		currIndex++;
		stk.push(this);
		MsgTree current = this;

		String lastAction = "in";

		while (currIndex < encodingString.length()) {
			MsgTree nodeChar = new MsgTree(encodingString.charAt(currIndex));
			currIndex++;

			if (lastAction.equals("in")) {
				current.left = nodeChar;
				if (nodeChar.payloadChar == '^') {
					current = stk.push(nodeChar);
					lastAction = "in";

				} else {
					if (!stk.empty())
						current = stk.pop();
					lastAction = "out";
				}

			} else {
				current.right = nodeChar;
				if (nodeChar.payloadChar == '^') {
					current = stk.push(nodeChar);
					lastAction = "in";

				} else {
					if (!stk.empty())
						current = stk.pop();
					lastAction = "out";
				}
			}
		}
	}

	/**
	 * Constructor for a single node with null children
	 * 
	 * @param payloadChar
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}

	/**
	 * Decodes the given message and prints it to the console
	 * 
	 * @param codes
	 * @param msg
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println("\nMESSAGE:");
		MsgTree current = codes;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			char ch = msg.charAt(i);
			if (ch == '0') {
				current = current.left;
			} else {
				current = current.right;
			}

			if (current.payloadChar != '^') {
				findBinCode(codes, current.payloadChar, binCode = "");
				sb.append(current.payloadChar);
				current = codes;
			}
		}
		System.out.println(sb.toString());
		stats(msg, sb.toString());
	}

	/**
	 * Method to print characters and their binary codes
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		System.out.println("\ncharacter  code\n----------------");
		for (int i = 0; i < code.length(); i++) {
			char ch = code.charAt(i);
			findBinCode(root, ch, binCode = "");
			if (ch == '\n') {
				System.out.println("    \\n    " + binCode);
			} else {
				System.out.println("    " + ch + "      " + binCode);
			}
		}
	}

	/**
	 * Finds the binary code for a specific character
	 * 
	 * @param root
	 * @param ch
	 * @param binPath
	 * @return
	 */
	private static boolean findBinCode(MsgTree root, char ch, String binPath) {
		if (root != null) {
			if (root.payloadChar == ch) {
				binCode = binPath;
				return true;
			}
			return findBinCode(root.left, ch, binPath + "0") || findBinCode(root.right, ch, binPath + "1");
		}
		return false;
	}

	/**
	 * Prints message statistics
	 * 
	 * @param encodedStr
	 * @param decodedStr
	 */
	private void stats(String encodedMessage, String decodedMessage) {
		System.out.println("\nSTATISTICS:");
		System.out.println(
				String.format("Avg bits/char:\t\t%.1f", encodedMessage.length() / (double) decodedMessage.length()));
		System.out.println("Total Characters:\t" + decodedMessage.length());
		System.out.println("Space Saving:\t\t"
				+ String.format("%.1f%%", (1d - decodedMessage.length() / (double) encodedMessage.length()) * 100));
	}
}
