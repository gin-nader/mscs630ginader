/**
 * file: EuclideanAlgorithm.java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 2
 * due date: February 8, 2017
 * version: 1.0
 *
 * This file contains a program that takes user submitted integers a and b, and outputs their GCD.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * EuclideanAlgorithm
 *
 * This class takes user submitted integers a and b as input. A user can input multiple lines as long as they each only
 * have two integers per line, separated by a space. The integers must also be greater than 0.
 *
 * The two integers are then used in the euclidAlg method below to find the GCD between the two integers. The GCD is
 * then outputted to the console.
 */
public class EuclideanAlgorithm {

  public static void main(String[] args){
    Scanner sc =new Scanner(System.in);

    List<String> input = new ArrayList();

    System.out.println("Enter two integers greater than 0:");

    while (sc.hasNext()) {
      String inputString = sc.nextLine();
      input.add(inputString);
    }

    long output;

    int a = 0;
    int b = 0;
    String[] inputNumbers = new String[input.size()*2];

    for(int j = 0; j < input.size(); j++) {
      inputNumbers = input.get(j).split("\\s");
      a = Integer.parseInt(inputNumbers[0]);
      b = Integer.parseInt(inputNumbers[1]);
      output = euclidAlg(a, b);
      System.out.println(output);
    }
    sc.close();

    //System.out.println("\nGCD = " + euclidAlg(270,192));

  }

  /**
   * euclidAlg
   *
   * This function takes the integers from the main method, and calculates their GCD using the Euclidean Algorithm.
   * It checks to make sure the integers are greater than 0, and if a is less than b, then a and b are switched.
   * It calculates q and r first, then loops the algorithm until r equals 0. It then returns b, which equals the GCD of
   * the two integers.
   *
   * Parameters:
   *   a: The first of two integers required for the algorithm
   *   b: The second of the two integers required for the algorithm
   *
   * Return value: The integer that is the GCD of both a and b.
   */
  public static long euclidAlg(long a, long b){
    if(a <= 0 && b <= 0){
      System.out.println("Your integers must be greater than 0.");
      return -1;
    }


    if(a < b){
      long temp = 0;
      temp = a;
      a = b;
      b = temp;
    }

    long q = a/b;
    long r = a - (q * b);

    while(r != 0){
      a = b;
      b = r;
      q = a/b;
      r = a - (q * b);
    }

    return b;
  }
}
