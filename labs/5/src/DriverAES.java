/**
 * file: DriverAES.Java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 4
 * due date: April 5, 2017
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

    String[] roundKeysHex = aesCipher.aesRoundKeys(keyHex);

    for(int i = 0; i < roundKeysHex.length; i++) {
      System.out.println(roundKeysHex[i]);
    }

    int[][] shexMatrix = new int[4][4];
    int begin = 0;
    int end = 2;
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        shexMatrix[col][row] = Integer.parseInt(sHex.substring(begin, end), 16);
        begin += 2;
        end += 2;
      }
    }

    int[][] keyMatrix = new int[4][4];
    int begin2 = 0;
    int end2 = 2;
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        keyMatrix[col][row] = Integer.parseInt(roundKeysHex[0].substring(begin2, end2), 16);
        begin2 += 2;
        end2 += 2;
      }
    }


    int[][] outStateHex = aesCipher.AESStateXOR(shexMatrix, keyMatrix);

    outStateHex = aesCipher.AESNibbleSub(outStateHex);
    outStateHex = aesCipher.AESShiftRow(outStateHex);
    outStateHex = aesCipher.AESMixColumn(outStateHex);
  }
}
