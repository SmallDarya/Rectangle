
package com.smolienko.areacalculator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author d_smolienko
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("1st rectangle");
        System.out.println("Enter begin and end points of the left boundary of the first rectangle. (Use comma for real numbers)");
        System.out.print("beginning X: ");
        double beginFirstX = readValueFromScanner(in);
        System.out.print("beginning Y: ");
        double beginFirstY = readValueFromScanner(in);
        System.out.print("ending X: ");
        double endFirstX = readValueFromScanner(in);
        System.out.print("ending Y: ");
        double endFirstY = readValueFromScanner(in);
        System.out.println("Enter width of rectangle: ");
        double widthFirst = readValueFromScanner(in);
        System.out.println();

        System.out.println("2nd rectangle");
        System.out.println("Enter begin and end points of the left boundary of the second rectangle.  (Use comma for real numbers)");
        System.out.print("beginning X: ");
        double beginSecondX = readValueFromScanner(in);
        System.out.print("beginning Y: ");
        double beginSecondY = readValueFromScanner(in);
        System.out.print("ending X: ");
        double endSecondX = readValueFromScanner(in);
        System.out.print("ending Y: ");
        double endSecondY = readValueFromScanner(in);
        System.out.println("Enter width of rectangle: ");
        double widthSecond = readValueFromScanner(in);
        Rectangle r1 = new Rectangle(new Point(beginFirstX, beginFirstY), new Point(endFirstX, endFirstY), widthFirst);
        Rectangle r2 = new Rectangle(new Point(beginSecondX, beginSecondY), new Point(endSecondX, endSecondY), widthSecond);

        System.out.print("Intersection area is ");
        System.out.println(r1.getCrossArea(r2));

        System.out.print("For the first rectangle area is ");
        System.out.println(r1.getArea());

        System.out.print("For the second rectangle area is ");
        System.out.println(r2.getArea());
    }

    private static double readValueFromScanner(Scanner in) {
        do {
            try {
                double value = in.nextDouble();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input, please try again.");
                in.next();
            }
        } while (true);
    }
    
}
