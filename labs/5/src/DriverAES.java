/**
 * file: DriverAES.Java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 5
 * due date: April 19, 2017
 * version: 1.0
 *
 * This file contains a program that calls AESCipher using the AES method. It uses a user inputted plaintext and
 * key as the parameters. The method then will then perform the AES encryption algorithm which will return the
 * corresponding ciphertext.
 */
import java.util.Scanner;

/**
 * Driver AES
 *
 * This class takes a user submitted plain text and 128 bit key in hex. It then passes that text and key to the
 * aesCipher class using the AES method. The AES created 11 round keys, and performs the AES algorithm by XORing the
 * key with the text, using nibble substitution, shifting rows, and mixing columns for 11 rounds. It will the return
 * the ciphertext for the user's plaintext.
 */
public class DriverAES {

  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    String keyHex = sc.nextLine();
    String sHex = sc.nextLine();
    AESCipher aesCipher = new AESCipher();
    String cTextHex = aesCipher.AES(sHex, keyHex);
    System.out.println(cTextHex);
  }
}
