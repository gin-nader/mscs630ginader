/**
 * file: PlaintextToHex.Java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 3
 * due date: March 1, 2017
 * version: 1.0
 *
 * This file contains a program that takes user input of a substitution character and a line of plaintext. It then
 * takes that plaintext and runs it through a function that converts the plaintext into individual integers and stores
 * it in a 2d array that acts as a 4x4 matrix. It the displays the 2d array and converts the integers into hex.
 */
import java.util.Scanner;

/**
 * Plaintext To Hex
 *
 * This class takes a user submitted substitution character and a line of plaintext. This plaintext is ran through the
 * getHexMatP function. If the line of plaintext is less than or equal to 16 characters long then it calls the function
 * once and it will result in only one 4x4 matrix. The matrix is always 16 two digit hex characters, so if the
 * plaintext is not 16 characters long than it fills in the rest of the space with the substitution character.
 *
 * If the plaintext is longer than 16 characters, then it calls the function multiple times and breaks the plaintext
 * down into multiple substrings. Each substring is ran through the function and results in a separate matrix per
 * substring.
 */
public class PlaintextToHex {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    char substitutionChar = sc.next().charAt(0);
    //eats new line character
    sc.nextLine();
    String plaintext = sc.nextLine();
    int begin = 0;
    int end = 16;
    if (plaintext.length() < end) {
      end = plaintext.length();
    }
    for (int i = 0; i < (double) plaintext.length() / 16; i++) {
      int[][] hexMatrix = getHexMatP(substitutionChar, plaintext.substring(begin, end));
      begin += 16;
      end += 16;
      if (end > plaintext.length()) {
        end = plaintext.length();
      }
      for (int row = 0; row < 4; row++) {
        for (int col = 0; col < 4; col++) {
          System.out.print(Integer.toHexString(hexMatrix[row][col]).toUpperCase() + " ");
        }
        System.out.println();
      }
      System.out.println();
    }
  }

  /**
   * getHexMatP
   *
   * This function takes the substitution character s, and the plaintext p as parameters. If the string or substring
   * is exactly 16 characters in length then if fills the matrix completely. If it is not 16 characters long then the
   * substitution character fills in the rest of the 2d array. The plaintext gets stored in column-major order.
   *
   * Parameters:
   *   s: The user submitted substitution character used if a string is not 16 characters long
   *   p: The user submitted plaintext that gets stored in the 2d array
   *
   * Return value: The 2d array that contains the plaintext.
   */
  public static int[][] getHexMatP(char s, String p) {
    int[][] hexMatrix = new int[4][4];
    int count = 0;

    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        if (count >= p.length()) {
          hexMatrix[col][row] = s;
        } else {
          hexMatrix[col][row] = p.charAt(count);
          count++;
        }
      }
    }
    return hexMatrix;
  }
}
