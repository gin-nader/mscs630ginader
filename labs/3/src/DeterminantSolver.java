/**
 * file: DeterminantSolver.java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 3
 * due date: March 1, 2017
 * version: 1.0
 *
 * This file contains a program that takes a modulus, a size, and a size n square modular matrix as input. It then
 * calculates the determinant of the matrix.
 */

import java.util.Scanner;


/**
 * Determinant Solver
 *
 * This class takes user submitted integers m, n, and a size n square modular matrix. m determines the modulus used, n
 * determines the size of the matrix, and the matrix is then ran through an algorithm to find it's determinant. The
 * integers inputted to make up the matrix are stored in a 2d array.
 *
 * The purpose of this is that an easy way to find out if a matrix is invertible is to take its determinant and see if
 * the gcd with m equals one. So, finding the determinant is the first step in the process. The determinant is the only
 * output for this program.
 */
public class DeterminantSolver {


  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    // gets mod
    int m = sc.nextInt();
    // gets size of array
    int n = sc.nextInt();
    int [][] matrix = new int[n][n];

    while (sc.hasNext()) {
      for (int row = 0; row < n; row++){
        for(int col = 0; col < n; col++){
          matrix[row][col] = sc.nextInt();
        }
      }
    }
    System.out.println(cofModDet(m, matrix));
  }

  /**
   * cofModDet
   *
   * This function takes a modulus and a 2d array and it determines the determinant for the matrix. It then returns the
   * determinant. If the matrix is only 1x1 then it just returns the only value in the matrix. If it is a 2x2 matrix
   * then it uses the formula of ad - bc. If it is a 3x3 matrix then it uses a recursive algorithm to find the
   * determinant using Laplace expansion also known as cofactor expansion.
   *
   * Parameters:
   *   m: This determines the modulus used for the matrix
   *   A: This is the matrix in the form of a 2d array
   *
   * Return value: The determinant that is calculated for that matrix using the algorithm below
   */
  public static int cofModDet(int m, int[][] A){
    int n = A.length;
    int det = -1;
    if(m < 1){
      System.out.println("m must be greater than 0");
      return -1;
    }

    if(n == 1){
      det = A[0][0] % m;
    }

    if(n == 2){
      int first = A[0][0] * A[1][1];
      int second = A[0][1] * A[1][0];
      int result = (first - second) % m;
      det = result;
    }

    int sign = -1;
    if(n >= 3){
      for(int col1 = 0; col1 < n; col1++) {
        int[][] subMatrix = new int[n - 1][n - 1];
        for (int row = 1; row < n; row++) {
          int subCol = 0;
          for(int currentCol = 0; currentCol < n; currentCol++){
            if(currentCol != col1) {
              subMatrix[row - 1][subCol] = A[row][currentCol];
              subCol++;
            }
          }
          sign = -sign;
          det += sign * A[0][col1] * cofModDet(m, subMatrix);
        }
      }
    }
    if(det % m < 0){
      return det % m + m;
    }
    return det % m;
  }
}
