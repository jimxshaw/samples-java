
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
    
    public static String putArray(String names[], int grades[]) {
        String result = "";
        for (int i = 0; i < names.length; i++)
            result += String.format("%-10s%4d\n", names[i], grades[i]);
        return result;
    }

    public static void sortBySelectionGrades(String names[], int grades[]) {
        for (int i = 0; i < grades.length; i++) {
            int largest = i;
            for (int j = largest + 1; j < grades.length; j++) {
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
    
    public static void sortBySelectionNames(String names[], int grades[]) {
        for (int i = 0; i < names.length; i++) {
            int largest = i;
            for (int j = largest + 1; j < names.length; j++) {
                if (names[j].compareTo(names[largest]) < 0) {
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

    public static String[] sortByBubbleGrades1(String names[], int grades[]) {
        for (int i = 0; i < grades.length - 1; i++) {
            for (int j = 0; j < grades.length - 1; j++) {
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
        return names;
    }
    
    public static int[] sortByBubbleGrades2(String names[], int grades[]) {
        for (int i = 0; i < grades.length - 1; i++) {
            for (int j = 0; j < grades.length - 1; j++) {
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
        return grades;
    }
    
    public static String[] sortByBubbleNames1(String names[], int grades[]) {
        for (int i = 0; i < names.length - 1; i++) {
            for (int j = 0; j < names.length - 1; j++) {
                if (names[j + 1].compareTo(names[j]) < 0) {
                    String temp = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = temp;
                    
                    int temp2 = grades[j];
                    grades[j] = grades[j + 1];
                    grades[j + 1] = temp2;
                }
            }
        }
        return names;
    }
    
    public static int[] sortByBubbleNames2(String names[], int grades[]) {
        for (int i = 0; i < names.length - 1; i++) {
            for (int j = 0; j < names.length - 1; j++) {
                if (names[j + 1].compareTo(names[j]) < 0) {
                	String temp = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = temp;
                	
                    int temp2 = grades[j];
                    grades[j] = grades[j + 1];
                    grades[j + 1] = temp2;
                }
            }
        }
        return grades;
    }
    
    

    public static void main(String[] args) {
        /* Regex
         *  \\d digit 
         *  \\s whitespace 
         *  . any character
         * 
         * Quantifiers 
         *  + 1 or more 
         *  * 0 or more 
         *  ? optional (0 or 1)
         */
        
        // Ask the user for input
        String input = JOptionPane
                .showInputDialog("Enter 1 or more names and grades");
        
        // Even elements, names, are placed in the names array
        String names[] = getStrData(input);
        // Odd elements, grades, are parsed to integers and are 
        // placed in the grades array
        int grades[] = getIntData(input);
        
        IO.showMessage("NAME ORDER\n" + putArray(sortByBubbleNames1(names, grades), sortByBubbleNames2(names, grades)) 
        		+ "\nGRADE ORDER\n" + putArray(sortByBubbleGrades1(names, grades), sortByBubbleGrades2(names, grades)) 
        		+ String.format("\nAverage:%6.2f \nHighest:%4d \nLowest:%4d",        
                getAverage(grades), getHighest(grades), getLowest(grades)), 
                "GRADE BREAKDOWN");
        
        
        System.exit(0);
    }
}
