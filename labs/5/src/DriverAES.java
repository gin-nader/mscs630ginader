/**
 * file: DriverAES.Java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 5
 * due date: April 19, 2017
 * version: 1.0
 *
 * This file contains a program that calls AESCipher using the aesRoundKeys method. It uses a user inputted key as the
 * parameter. The method then returns 11 round keys using the AES algorithm
 */
import java.util.Scanner;

/**
 * Driver AES
 *
 * This class takes a user submitted 128 bit key in hex. It then passes that key to aesCipher.java using the aesRoundKeys
 * method. The aesRoundkeys method takes the first key and performs AES rounds on it 10 times. The first round key is the
 * same as the user submitted key. The method then returns an array of Strings that contains all 11 keys. Driver AES then
 * displays all 11 keys to the user.
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
