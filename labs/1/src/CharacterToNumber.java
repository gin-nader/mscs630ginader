/**
 * file: CharacterToNumber.java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 1
 * due date: Januaray 25, 2017
 * version: 1.0
 *
 * This file contains a program that turns character strings into a number string equivalent.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CharacterToNumber.java
 *
 * This class takes character string plaintext input by a user and the output corresponds to the
 * position of the characters in the alphabet.
 * A = 0, Z = 25 etc.
 * Spaces = 26
 *
 * The user specifies how many lines of plaintext he wishes to encrypt. The user then inputs the lines in the console.
 * The output displays afterwards with the corresponding lines now encrypted.
 */
public class CharacterToNumber {

  public static void main(String[] args) {
    Scanner sc =new Scanner(System.in);

    List<String> input = new ArrayList();

    System.out.println("Enter the message you wish to encrypt:");

    while (sc.hasNext()) {
      String inputString = sc.nextLine();
      input.add(inputString);
    }

    int[] output;

    for(int j = 0; j < input.size(); j++) {
      output = str2int(input.get(j));
      for (int i = 0; i < output.length; i++) {
        System.out.print(output[i] + " ");
      }
      System.out.println();
    }
    sc.close();
  }

  /**
   * str2int
   *
   * This function takes a line of plaintext and converts each character into their corresponding number.
   * It then stores the numbers into an array and returns it.
   *
   * Parameters:
   *   plainText: the string of characters that gets converted
   *
   * Return value: the array of integers that results after being converted from characters
   */
  public static int[] str2int(String plainText) {
    final int SPACE_CHARACTER = 32;
    //This is the ASCII number for the space character
    final int CONVERSION_NUMBER = 65;
    //This is the shift from a capital ASCII character to their position in the alphabet
    int[] output = new int[plainText.length()];
    for (int i = 0; i < plainText.length(); i++) {
      int outputNumber = plainText.toUpperCase().charAt(i);
      if(outputNumber == SPACE_CHARACTER){
        outputNumber = 26;
      }
      else
        outputNumber -= CONVERSION_NUMBER;

      output[i] = outputNumber;
    }
    return output;
  }
}
