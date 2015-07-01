
import javax.swing.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Font;
import util.IO;

public class SortGrades {

    public static int[] getIntData(String input) {
        String[] data = input.trim().split("\\s+");
        int x[] = new int[data.length / 2];
        for (int i = 1; i < data.length; i += 2) {
            if (data[i].matches("\\d+")) {
                x[i / 2] = Integer.parseInt(data[i]);
            } else {
                System.err.println("input error");
            }
        }
        return x;
    }

    public static String[] getStrData(String input) {
        String data[] = input.trim().split("\\s+");
        String x[] = new String[data.length / 2];
        for (int i = 0; i < data.length; i += 2) {
            x[i / 2] = data[i];
        }
        return x;
    }

    public static float getAverage(int x[]) {
        float total = 0;
        for (int i = 0; i < x.length; i++) {
            total += x[i];
        }
        return total / x.length;
    }

    public static int getHighest(int x[]) {
        int highest = x[0];
        for (int i = 0; i < x.length; i++) {
            if (x[i] > highest) {
                highest = x[i];
            }
        }
        return highest;
    }

    public static int getLowest(int x[]) {
        int lowest = x[0];
        for (int i = 0; i < x.length; i++) {
            if (x[i] < lowest) {
                lowest = x[i];
            }
        }
        return lowest;
    }

    public static void putArrayByGrades(String names[], int grades[], int count) {
        for (int i = 0; i < count; i++) {
            System.out.printf(names[i] + "%10s", grades[i] + "\n");
        }
    }

    public static void sortBySelection(String names[], int grades[], int count) {
        for (int i = 0; i < count; i++) {
            int largest = i;
            for (int j = largest + 1; j < count; j++) {
                if (grades[j] > grades[largest]) {
                    largest = j;
                }
            }
            String temp = names[largest];
            names[largest] = names[i];
            names[i] = temp;

            int temp2 = grades[largest];
            grades[largest] = grades[i];
            grades[i] = temp2;
        }
    }

    public static void sortByBubble(String names[], int grades[], int count) {
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if (grades[j + 1] > grades[j]) {
                    String temp = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = temp;

                    int temp2 = grades[j];
                    grades[j] = grades[j + 1];
                    grades[j + 1] = temp2;
                }
            }
        }
    }

    public static void main(String[] args) {
        /*
         * \\d digit 
         * \\s whitespace 
         * . any character
         * 
         * Quantifiers 
         * + 1 or more 
         * * 0 or more 
         * ? optional (0 or 1)
         */

        int i = 0;

        System.out.println("\n");

        String input = JOptionPane.showInputDialog("Enter 1 or more names and grades");

        String names[] = getStrData(input);
        int grades[] = getIntData(input);

        for (; i < names.length; i++);

        System.out.println("\n");

        sortBySelection(names, grades, i);
        putArrayByGrades(names, grades, i);


        System.exit(0);
    }
}
