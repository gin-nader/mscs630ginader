/**
 * file: AESCipher.Java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 5
 * due date: April 19, 2017
 * version: 1.0
 *
 * This file contains a program that implements the AES security encryption algorithm. It takes a user submitted key
 * from DriverAES and performs 10 rounds on the key to create 11 round keys. The first round key is the user submitted
 * key. It involves sboxes and round constants to perform the algorithm. It then uses the keys to perform the AES
 * algorithm by using other methods that XOR the key with the plaintext, use nibble substitution, shift rows, and mix
 * columns
 */



/**
 * Cipher AES
 *
 * This class takes a 128bit key from the DriverAES class and performs the AES encryption algorithm on it. The end
 * result is 11 keys, where the first key is the original key taken from the user. In order to properly use the
 * algorithm, the class has two helper functions called aesRcon and aesSbox. These two functions contain static tables
 * that help create the AES round keys. It then uses the keys to XOR with the plaintext, and it will also use nibble
 * substitution, shift rows, and mix columns.
 */
public class AESCipher {

  public static String[] aesRoundKeys(String kh){
    int[][] hexMatrix = new int[4][4];
    String[] returnArray = new String[11];
    returnArray[0] = kh;

    for(int round = 1; round < 11; round++) {
      String hexOut = "";
      int begin = 0;
      int end = 2;
      for (int row = 0; row < 4; row++) {
        for (int col = 0; col < 4; col++) {
            hexMatrix[col][row] = Integer.parseInt(kh.substring(begin, end), 16);
            begin += 2;
            end += 2;
          }
        }
        int[] currentColumn = new int[4];
        for (int row = 0; row < 4; row++) {
          for (int col = 0; col < 4; col++) {
            currentColumn[col] = hexMatrix[col][3];
          }
        }

      for (int subRound = 1; subRound <= 4; subRound++) {
        int[] firstColumn = new int[4];
        for (int row = 0; row < 4; row++) {
          for (int col = 0; col < 4; col++) {
            firstColumn[col] = hexMatrix[col][subRound - 1];
          }
        }

        if (subRound == 1) {
          int first = currentColumn[0];
          for (int i = 1; i < currentColumn.length; i++) {
            currentColumn[i - 1] = currentColumn[i];
          }
          currentColumn[currentColumn.length - 1] = first;
          currentColumn = aesSBox(currentColumn);
          currentColumn[0] = currentColumn[0] ^ aesRcon(round);

          for (int i = 0; i < currentColumn.length; i++) {
            currentColumn[i] = currentColumn[i] ^ firstColumn[i];
            hexOut += String.format("%02x", currentColumn[i]);
          }
        }else {
          for (int i = 0; i < currentColumn.length; i++) {
            currentColumn[i] = currentColumn[i] ^ firstColumn[i];
            hexOut += String.format("%02x", currentColumn[i]).toUpperCase();
          }
          kh = hexOut;
        }
      }
      returnArray[round] = hexOut.toUpperCase();
    }
    return returnArray;
  }

  /**
   * Source: https://github.com/rishidewan33/Advanced-Encryption-Standard-Algorithm/blob/master/src/AES.java
   *
   * aesRcon
   *
   * This function takes in the current round from the above method that is determined in the first for loop.
   * Each round determines one of the round constants below. Round 1 relates to the first in the set, round 2 relates
   * to the second, and so on. It then returns the round constant
   *
   * Parameters:
   *   round: This is the round that is determined by the first for loop above.
   *
   * Return value: The round constant in the rcon array that is found by using the round parameter.
   */
  private static int aesRcon(int round){
    int[] rcon = {0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a,
        0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39,
        0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
        0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8,
        0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
        0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
        0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b,
        0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
        0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94,
        0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
        0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
        0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f,
        0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
        0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63,
        0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd,
        0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb};
    return rcon[round];
  }

  /**
   * http://stackoverflow.com/questions/7259099/java-aes-encrypt-with-specified-key
   *
   * aesSBox
   *
   * This function takes in the current column from the above method, and it replaces all of the values in that column
   * with their corresponding Sbox hex value. To find the correct Sbox hex value, it uses the decimal form of the value
   * in the current column as the index.
   *
   * Parameters:
   *   col: An int array that is the current column from the main method.
   *
   * Return value: The int array the replaces the current column values with their respective Sbox hex values.
   */
  private static int[] aesSBox(int[] col){
    int[] sbox = {0x63,0x7c,0x77,0x7b,0xf2,0x6b,0x6f,0xc5,0x30,0x01,0x67,0x2b,0xfe,0xd7,0xab,0x76,
        0xca,0x82,0xc9,0x7d,0xfa,0x59,0x47,0xf0,0xad,0xd4,0xa2,0xaf,0x9c,0xa4,0x72,0xc0,
        0xb7,0xfd,0x93,0x26,0x36,0x3f,0xf7,0xcc,0x34,0xa5,0xe5,0xf1,0x71,0xd8,0x31,0x15,
        0x04,0xc7,0x23,0xc3,0x18,0x96,0x05,0x9a,0x07,0x12,0x80,0xe2,0xeb,0x27,0xb2,0x75,
        0x09,0x83,0x2c,0x1a,0x1b,0x6e,0x5a,0xa0,0x52,0x3b,0xd6,0xb3,0x29,0xe3,0x2f,0x84,
        0x53,0xd1,0x00,0xed,0x20,0xfc,0xb1,0x5b,0x6a,0xcb,0xbe,0x39,0x4a,0x4c,0x58,0xcf,
        0xd0,0xef,0xaa,0xfb,0x43,0x4d,0x33,0x85,0x45,0xf9,0x02,0x7f,0x50,0x3c,0x9f,0xa8,
        0x51,0xa3,0x40,0x8f,0x92,0x9d,0x38,0xf5,0xbc,0xb6,0xda,0x21,0x10,0xff,0xf3,0xd2,
        0xcd,0x0c,0x13,0xec,0x5f,0x97,0x44,0x17,0xc4,0xa7,0x7e,0x3d,0x64,0x5d,0x19,0x73,
        0x60,0x81,0x4f,0xdc,0x22,0x2a,0x90,0x88,0x46,0xee,0xb8,0x14,0xde,0x5e,0x0b,0xdb,
        0xe0,0x32,0x3a,0x0a,0x49,0x06,0x24,0x5c,0xc2,0xd3,0xac,0x62,0x91,0x95,0xe4,0x79,
        0xe7,0xc8,0x37,0x6d,0x8d,0xd5,0x4e,0xa9,0x6c,0x56,0xf4,0xea,0x65,0x7a,0xae,0x08,
        0xba,0x78,0x25,0x2e,0x1c,0xa6,0xb4,0xc6,0xe8,0xdd,0x74,0x1f,0x4b,0xbd,0x8b,0x8a,
        0x70,0x3e,0xb5,0x66,0x48,0x03,0xf6,0x0e,0x61,0x35,0x57,0xb9,0x86,0xc1,0x1d,0x9e,
        0xe1,0xf8,0x98,0x11,0x69,0xd9,0x8e,0x94,0x9b,0x1e,0x87,0xe9,0xce,0x55,0x28,0xdf,
        0x8c,0xa1,0x89,0x0d,0xbf,0xe6,0x42,0x68,0x41,0x99,0x2d,0x0f,0xb0,0x54,0xbb,0x16};
    for(int i = 0; i < col.length; i++){
      col[i] = sbox[col[i]];
    }
    return col;
  }

  /**
   * http://stackoverflow.com/questions/7259099/java-aes-encrypt-with-specified-key
   *
   * AESStateXOR
   *
   * This function takes in two 4x4 matrices. One matrix is the plaintext or state of the text depending on the round
   * in AES. It also takes the corresponding round key. It then XORs the text matrix with the key matrix and stores
   * it in the return matrix which the method returns.
   *
   * Parameters:
   *   sHex: This is the user input plaintext matrix or the state of the text depending on the AES round
   *   keyHEX: This is the corresponding round key in a matrix
   *
   * Return value: The 4x4 matrix 2d array that stores the result
   */
  public static int[][] AESStateXOR(int[][] sHex, int[][] keyHex){
    int[][] returnMatrix = new int[4][4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        returnMatrix[row][col] = sHex[row][col] ^ keyHex[row][col];
      }
    }
    return returnMatrix;
  }

  /**
   *
   * AESNibbleSub
   *
   * This function takes in the plaintext or current state of the text depending on the AES round. It then takes
   * that matrix and breaks it up into 4 columns. It then takes each column and runs it through the aesSBox method
   * which returns the column after the hex has been substituted using the S Box. It then combines the four columns
   * and stores them in the result matrix which it then returns.
   *
   * Parameters:
   *   inStateHex: A 4x4 matrix of the plaintext or current state of the hex
   *
   * Return value: The result matrix which contains the inStateHex after it has been nibble substituted
   */
  public static int[][] AESNibbleSub(int[][] inStateHex){
    int[] firstColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        firstColumn[col] = inStateHex[col][0];
      }
    }

    firstColumn = aesSBox(firstColumn);

    int[] secondColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        secondColumn[col] = inStateHex[col][1];
      }
    }

    secondColumn = aesSBox(secondColumn);

    int[] thirdColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        thirdColumn[col] = inStateHex[col][2];
      }
    }

    thirdColumn = aesSBox(thirdColumn);

    int[] fourthColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        fourthColumn[col] = inStateHex[col][3];
      }
    }

    fourthColumn = aesSBox(fourthColumn);

    int[][] resultMatrix = new int[4][4];
    for (int i = 0; i < 4; i++)
    {
      resultMatrix[i][0] = firstColumn[i];
      resultMatrix[i][1] = secondColumn[i];
      resultMatrix[i][2] = thirdColumn[i];
      resultMatrix[i][3] = fourthColumn[i];
    }
    return resultMatrix;
  }

  /**
   *
   * AESShiftRow
   *
   * This function takes the plaintext or current state of the text based on the AES round. It then separates each
   * row of the matrix and shifts them depending on the row. The first row does not get shifted at all. The second
   * row gets shifted once, the third row gets shift twice, and the fourth row gets shifted three times. It then
   * stores the rows in the result matrix and returns it.
   *
   * Parameters:
   *   inStateHex: This is the 4x4 matrix for the current state of the hex
   *
   * Return value: The result matrix which contains the newly shifted rows
   */
  public static int[][] AESShiftRow(int[][] inStateHex){
    int[][] resultMatrix = new int[4][4];

    int[] firstRow = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        firstRow[row] = inStateHex[0][row];
      }
    }

    int[] secondRow = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        secondRow[row] = inStateHex[1][row];
      }
    }

    int first = secondRow[0];
    for (int i = 1; i < secondRow.length; i++) {
      secondRow[i - 1] = secondRow[i];
    }
    secondRow[secondRow.length - 1] = first;

    int[] thirdRow = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        thirdRow[row] = inStateHex[2][row];
      }
    }

    first = thirdRow[0];
    int second = thirdRow[1];
    int third = thirdRow[2];
    int fourth = thirdRow[3];

    thirdRow[0] = third;
    thirdRow[1] = fourth;
    thirdRow[2] = first;
    thirdRow[3] = second;

    int[] fourthRow = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        fourthRow[row] = inStateHex[3][row];
      }
    }

    first = fourthRow[0];
    second = fourthRow[1];
    third = fourthRow[2];
    fourth = fourthRow[3];

    fourthRow[0] = fourth;
    fourthRow[1] = first;
    fourthRow[2] = second;
    fourthRow[3] = third;

    for (int i = 0; i < 4; i++)
    {
      resultMatrix[0][i] = firstRow[i];
      resultMatrix[1][i] = secondRow[i];
      resultMatrix[2][i] = thirdRow[i];
      resultMatrix[3][i] = fourthRow[i];
    }
    return resultMatrix;
  }

  /**
   * multiply by 2 and multiply by 3 look up tables taken from here:
   * https://en.wikipedia.org/wiki/Rijndael_mix_columns
   * This is also where I learned the mix columns algorithm
   *
   * AESMixColumn
   *
   * This function takes in the plaintext matrix or the current state of the text depending on the AES round. It then
   * uses an algorithm to mix the columns which involves arithmetic in Galois field. For this method, it takes one
   * column at a time and solves the algorithm using a 4x4 matrix to produce a result matrix. It also uses a lookup
   * table for multiply by 2 and multiply by 3 math. It then combines the four result columns and stores it in the
   * 4x4 result matrix.
   *
   * Parameters:
   *   inStateHex: This is the current state of the text based on the AES round
   *
   * Return value: This is the result matrix which contains the newly mixed columns
   */
  public static int[][] AESMixColumn(int[][] inStateHex){
    int[][] resultMatrix = new int[4][4];
    int[] multiplyBy2 = {0x00,0x02,0x04,0x06,0x08,0x0a,0x0c,0x0e,0x10,0x12,0x14,0x16,0x18,0x1a,0x1c,0x1e,
        0x20,0x22,0x24,0x26,0x28,0x2a,0x2c,0x2e,0x30,0x32,0x34,0x36,0x38,0x3a,0x3c,0x3e,
        0x40,0x42,0x44,0x46,0x48,0x4a,0x4c,0x4e,0x50,0x52,0x54,0x56,0x58,0x5a,0x5c,0x5e,
        0x60,0x62,0x64,0x66,0x68,0x6a,0x6c,0x6e,0x70,0x72,0x74,0x76,0x78,0x7a,0x7c,0x7e,
        0x80,0x82,0x84,0x86,0x88,0x8a,0x8c,0x8e,0x90,0x92,0x94,0x96,0x98,0x9a,0x9c,0x9e,
        0xa0,0xa2,0xa4,0xa6,0xa8,0xaa,0xac,0xae,0xb0,0xb2,0xb4,0xb6,0xb8,0xba,0xbc,0xbe,
        0xc0,0xc2,0xc4,0xc6,0xc8,0xca,0xcc,0xce,0xd0,0xd2,0xd4,0xd6,0xd8,0xda,0xdc,0xde,
        0xe0,0xe2,0xe4,0xe6,0xe8,0xea,0xec,0xee,0xf0,0xf2,0xf4,0xf6,0xf8,0xfa,0xfc,0xfe,
        0x1b,0x19,0x1f,0x1d,0x13,0x11,0x17,0x15,0x0b,0x09,0x0f,0x0d,0x03,0x01,0x07,0x05,
        0x3b,0x39,0x3f,0x3d,0x33,0x31,0x37,0x35,0x2b,0x29,0x2f,0x2d,0x23,0x21,0x27,0x25,
        0x5b,0x59,0x5f,0x5d,0x53,0x51,0x57,0x55,0x4b,0x49,0x4f,0x4d,0x43,0x41,0x47,0x45,
        0x7b,0x79,0x7f,0x7d,0x73,0x71,0x77,0x75,0x6b,0x69,0x6f,0x6d,0x63,0x61,0x67,0x65,
        0x9b,0x99,0x9f,0x9d,0x93,0x91,0x97,0x95,0x8b,0x89,0x8f,0x8d,0x83,0x81,0x87,0x85,
        0xbb,0xb9,0xbf,0xbd,0xb3,0xb1,0xb7,0xb5,0xab,0xa9,0xaf,0xad,0xa3,0xa1,0xa7,0xa5,
        0xdb,0xd9,0xdf,0xdd,0xd3,0xd1,0xd7,0xd5,0xcb,0xc9,0xcf,0xcd,0xc3,0xc1,0xc7,0xc5,
        0xfb,0xf9,0xff,0xfd,0xf3,0xf1,0xf7,0xf5,0xeb,0xe9,0xef,0xed,0xe3,0xe1,0xe7,0xe5};
    int[] multiplyBy3 = {0x00,0x03,0x06,0x05,0x0c,0x0f,0x0a,0x09,0x18,0x1b,0x1e,0x1d,0x14,0x17,0x12,0x11,
        0x30,0x33,0x36,0x35,0x3c,0x3f,0x3a,0x39,0x28,0x2b,0x2e,0x2d,0x24,0x27,0x22,0x21,
        0x60,0x63,0x66,0x65,0x6c,0x6f,0x6a,0x69,0x78,0x7b,0x7e,0x7d,0x74,0x77,0x72,0x71,
        0x50,0x53,0x56,0x55,0x5c,0x5f,0x5a,0x59,0x48,0x4b,0x4e,0x4d,0x44,0x47,0x42,0x41,
        0xc0,0xc3,0xc6,0xc5,0xcc,0xcf,0xca,0xc9,0xd8,0xdb,0xde,0xdd,0xd4,0xd7,0xd2,0xd1,
        0xf0,0xf3,0xf6,0xf5,0xfc,0xff,0xfa,0xf9,0xe8,0xeb,0xee,0xed,0xe4,0xe7,0xe2,0xe1,
        0xa0,0xa3,0xa6,0xa5,0xac,0xaf,0xaa,0xa9,0xb8,0xbb,0xbe,0xbd,0xb4,0xb7,0xb2,0xb1,
        0x90,0x93,0x96,0x95,0x9c,0x9f,0x9a,0x99,0x88,0x8b,0x8e,0x8d,0x84,0x87,0x82,0x81,
        0x9b,0x98,0x9d,0x9e,0x97,0x94,0x91,0x92,0x83,0x80,0x85,0x86,0x8f,0x8c,0x89,0x8a,
        0xab,0xa8,0xad,0xae,0xa7,0xa4,0xa1,0xa2,0xb3,0xb0,0xb5,0xb6,0xbf,0xbc,0xb9,0xba,
        0xfb,0xf8,0xfd,0xfe,0xf7,0xf4,0xf1,0xf2,0xe3,0xe0,0xe5,0xe6,0xef,0xec,0xe9,0xea,
        0xcb,0xc8,0xcd,0xce,0xc7,0xc4,0xc1,0xc2,0xd3,0xd0,0xd5,0xd6,0xdf,0xdc,0xd9,0xda,
        0x5b,0x58,0x5d,0x5e,0x57,0x54,0x51,0x52,0x43,0x40,0x45,0x46,0x4f,0x4c,0x49,0x4a,
        0x6b,0x68,0x6d,0x6e,0x67,0x64,0x61,0x62,0x73,0x70,0x75,0x76,0x7f,0x7c,0x79,0x7a,
        0x3b,0x38,0x3d,0x3e,0x37,0x34,0x31,0x32,0x23,0x20,0x25,0x26,0x2f,0x2c,0x29,0x2a,
        0x0b,0x08,0x0d,0x0e,0x07,0x04,0x01,0x02,0x13,0x10,0x15,0x16,0x1f,0x1c,0x19,0x1a};

    int[] firstColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        firstColumn[col] = inStateHex[col][0];
      }
    }

    resultMatrix[0][0] = multiplyBy2[firstColumn[0]] ^ multiplyBy3[firstColumn[1]] ^ firstColumn[2] ^ firstColumn[3];
    resultMatrix[1][0] = firstColumn[0] ^ multiplyBy2[firstColumn[1]] ^ multiplyBy3[firstColumn[2]] ^ firstColumn[3];
    resultMatrix[2][0] = firstColumn[0] ^ firstColumn[1] ^ multiplyBy2[firstColumn[2]] ^ multiplyBy3[firstColumn[3]];
    resultMatrix[3][0] = multiplyBy3[firstColumn[0]] ^ firstColumn[1] ^ firstColumn[2] ^ multiplyBy2[firstColumn[3]];

    int[] secondColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        secondColumn[col] = inStateHex[col][1];
      }
    }

    resultMatrix[0][1] = multiplyBy2[secondColumn[0]] ^ multiplyBy3[secondColumn[1]] ^ secondColumn[2] ^
                                                                                              secondColumn[3];
    resultMatrix[1][1] = secondColumn[0] ^ multiplyBy2[secondColumn[1]] ^ multiplyBy3[secondColumn[2]] ^
                                                                                                    secondColumn[3];
    resultMatrix[2][1] = secondColumn[0] ^ secondColumn[1] ^ multiplyBy2[secondColumn[2]] ^
                                                                                      multiplyBy3[secondColumn[3]];
    resultMatrix[3][1] = multiplyBy3[secondColumn[0]] ^ secondColumn[1] ^ secondColumn[2] ^
                                                                                      multiplyBy2[secondColumn[3]];

    int[] thirdColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        thirdColumn[col] = inStateHex[col][2];
      }
    }

    resultMatrix[0][2] = multiplyBy2[thirdColumn[0]] ^ multiplyBy3[thirdColumn[1]] ^ thirdColumn[2] ^ thirdColumn[3];
    resultMatrix[1][2] = thirdColumn[0] ^ multiplyBy2[thirdColumn[1]] ^ multiplyBy3[thirdColumn[2]] ^ thirdColumn[3];
    resultMatrix[2][2] = thirdColumn[0] ^ thirdColumn[1] ^ multiplyBy2[thirdColumn[2]] ^ multiplyBy3[thirdColumn[3]];
    resultMatrix[3][2] = multiplyBy3[thirdColumn[0]] ^ thirdColumn[1] ^ thirdColumn[2] ^ multiplyBy2[thirdColumn[3]];

    int[] fourthColumn = new int[4];
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        fourthColumn[col] = inStateHex[col][3];
      }
    }

    resultMatrix[0][3] = multiplyBy2[fourthColumn[0]] ^ multiplyBy3[fourthColumn[1]] ^ fourthColumn[2] ^
                                                                                              fourthColumn[3];
    resultMatrix[1][3] = fourthColumn[0] ^ multiplyBy2[fourthColumn[1]] ^ multiplyBy3[fourthColumn[2]] ^
                                                                                            fourthColumn[3];
    resultMatrix[2][3] = fourthColumn[0] ^ fourthColumn[1] ^ multiplyBy2[fourthColumn[2]] ^
                                                                                      multiplyBy3[fourthColumn[3]];
    resultMatrix[3][3] = multiplyBy3[fourthColumn[0]] ^ fourthColumn[1] ^ fourthColumn[2] ^
                                                                                  multiplyBy2[fourthColumn[3]];

    return resultMatrix;
  }

  /**
   *
   * AES
   *
   * This function uses all of the above functions to perform the actual AES algorithm. This is the only function that
   * DriverAES calls, and it takes in the user submitted plain text and key. It then will perform the algorithm by
   * XORing the key with the plaintext then going through 11 rounds of nibble substitution, shifting rows, and
   * mixing columns. It will not mix columns on the last round. It then stores the result in a string which is the
   * newly created cipher text.
   *
   * Parameters:
   *   pTextHex: the plaintext entered by the user
   *   keyHex: the 128 bit key entered by the user
   *
   * Return value: This is the ciphertext created by encrypting the user's plaintext.
   */
  public static String AES(String pTextHex, String keyHex){
    String cTextHex = "";
    String[] roundKeysHex = aesRoundKeys(keyHex);

    int[][] sHex = new int[4][4];
    int begin = 0;
    int end = 2;
    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        sHex[col][row] = Integer.parseInt(pTextHex.substring(begin, end), 16);
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

    int[][] outStateHex = new int[4][4];
    outStateHex = AESStateXOR(sHex, keyMatrix);

    for(int i = 1; i < 11; i++){
      outStateHex = AESNibbleSub(outStateHex);
      outStateHex = AESShiftRow(outStateHex);
      if(i < 10) {
        outStateHex = AESMixColumn(outStateHex);
      }
      begin = 0;
      end = 2;
      for (int row = 0; row < 4; row++) {
        for (int col = 0; col < 4; col++) {
          keyMatrix[col][row] = Integer.parseInt(roundKeysHex[i].substring(begin, end), 16);
          begin += 2;
          end += 2;
        }
      }
      outStateHex = AESStateXOR(outStateHex, keyMatrix);
    }

    for (int row = 0; row < 4; row++) {
      for (int col = 0; col < 4; col++) {
        cTextHex += String.format("%02x", outStateHex[col][row]).toUpperCase();
      }
    }
    return cTextHex;
  }
}
