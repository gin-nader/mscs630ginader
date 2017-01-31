/**
 * file: ExtendedEuclideanAlgorithm.java
 * author: Tom Ginader
 * course: MSCS 630
 * assignment: lab 2
 * due date: February 8, 2017
 * version: 1.0
 *
 * This file contains a program that takes user submitted integers a and b, and outputs their GCD, and the x and y
 * that is obtained after using the extended Euclidean algorithm.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ExtendedEuclideanAlgorithm
 *
 * This class takes user submitted integers a and b as input. A user can input multiple lines as long as they each only
 * have two integers per line, separated by a space. The integers must also be greater than 0, and the GCD between the
 * two integers must be really small. This means a and b must be relatively prime.
 *
 * The two integers are then used in the euclidAlgExt method below to find the GCD between the two integers. The GCD is
 * then outputted to the console. The method also finds the x and y values for the given input. The output is in the
 * order of "GCD x y".
 */
public class ExtendedEuclideanAlgorithm {

    public static void main(String[] args){
        Scanner sc =new Scanner(System.in);

        List<String> input = new ArrayList();

        System.out.println("Enter two integers greater than 0:");

        while (sc.hasNext()) {
            String inputString = sc.nextLine();
            input.add(inputString);
        }

        long[] output;

        int a;
        int b;
        String[] inputNumbers;

        for(int j = 0; j < input.size(); j++) {
            inputNumbers = input.get(j).split("\\s");
            a = Integer.parseInt(inputNumbers[0]);
            b = Integer.parseInt(inputNumbers[1]);
            output = euclidAlgExt(a, b);
            for (int i = 0; i < output.length; i++) {
                System.out.print(output[i] + " ");
            }
            System.out.println();
        }
        sc.close();
    }

    /**
     * euclidAlgExt
     *
     * This function takes the integers from the main method, and calculates their GCD, x, and y values using the Extended
     * Euclidean Algorithm. It checks to make sure the integers are greater than 0, and if a is less than b, then a and
     * b are switched. It calculates q and r first, then loops the algorithm until r equals 0. It then returns d(GCD),
     * x, and y in an array of longs.
     *
     * Parameters:
     *   a: The first of two integers required for the algorithm
     *   b: The second of the two integers required for the algorithm
     *
     * Return value: The array of longs that returns d, x, and y after the algorithm is complete.
     */
    public static long[] euclidAlgExt(long a, long b){
        if(a <= 0 && b <= 0){
            System.out.println("Your integers must be greater than 0.");
            return null;
        }

        long[] answer = new long[3];
        long temp = a;

        if(a < b){
            a = b;
            b = temp;
        }

        long q;
        long r = -1;
        long x = 0, finalY = 0;
        long y = 1, finalX = 1;
        long d = -1;
        final int FINAL_R = 0;

        while(r != 0){
            q = a/b;
            r = a - (q * b);

            a = b;
            b = r;

            temp = x;
            x = finalX - q * x;
            finalX = temp;

            temp = y;
            y = finalY - q * y;
            finalY = temp;

            // Need the remainder right before the last iteration to get the GCD.
            if(r > FINAL_R){
                d = r;
            }
        }

        answer[0] = d;
        answer[1] = finalX;
        answer[2] = finalY;

        return answer;
    }
}
