// Copyright© by Fin

import java.io.*;
import java.util.Scanner;

public class Main {

    static char[] charset;

    static String lettersInput;
    static String minLength;
    static String maxLength;
    static String before;
    static String after;
    static String amount;
    static String output;
    static String print;

    static String onlyIntegerAndOutOfRange = "Only Integer and > 0 and < 2147483647!";
    static String outOfRange = "> 0 and < 2147483647!";
    static String seeYou = "\nSee you next time!";

    static long count = 0L;

    static boolean isAmountEnabled = true;
    static boolean writeToFile = false;
    static boolean writeToConsole = false;

    static File file;
    static FileWriter fileWriter;

    static StringBuilder stringBuilder;

    public static void main(String[] args) throws IOException {
        file = new File("output.txt");
        fileWriter = new FileWriter(file);

        System.out.println("- BruteForceListGenerator -\n     - Version 1.0 -\n   - by getQueryString -\n");
        start();
    }

    static void start() throws IOException {
        System.out.println("Characters: ");
        Scanner input = new Scanner(System.in);
        lettersInput = input.nextLine();

        if (lettersInput.isEmpty()) {
            start();
        } else if (lettersInput.equals("exit")) {
            System.out.println(seeYou);
            System.exit(0);
        }
        charset = lettersInput.toCharArray();
        minLength();
    }

    static void minLength() throws IOException {
        try {
            System.out.println("Min length: ");
            Scanner minL = new Scanner(System.in);
            minLength = minL.nextLine();

            if (minLength.equals("exit")) {
                System.out.println(seeYou);
                System.exit(0);
            }

            if (minLength.equals("back") || minLength.equals("start")) {
                start();
            }

            if (Integer.parseInt(minLength) <= 0) {
                System.out.println(outOfRange);
                minLength();
            }
            maxLength();
        } catch (NumberFormatException nfe) {
            System.out.println(onlyIntegerAndOutOfRange);
            minLength();
        }
    }

    static void maxLength() throws IOException {
        try {
            System.out.println("Max length: ");
            Scanner maxL = new Scanner(System.in);
            maxLength = maxL.nextLine();

            switch (maxLength) {
                case "exit" -> {
                    System.out.println(seeYou);
                    System.exit(0);
                }
                case "back" -> minLength();
                case "start" -> start();
            }

            if (Integer.parseInt(maxLength) <= 0) {
                System.out.println(outOfRange);
                minLength();
            }

            if (Integer.parseInt(minLength) > Integer.parseInt(maxLength)) {
                System.out.println("Minimum can't be greater than maximum!");
                maxLength();
            }
            strBefore();
        } catch (NumberFormatException nfe) {
            System.out.println(onlyIntegerAndOutOfRange);
            maxLength();
        }
    }

    static void strBefore() throws IOException {
        System.out.println("Before the String: ");
        Scanner strBefore = new Scanner(System.in);
        before = strBefore.nextLine();

        strAfter();
    }

    static void strAfter() throws IOException {
        System.out.println("After the string: ");
        Scanner strAfter = new Scanner(System.in);
        after = strAfter.nextLine();

        amount();
    }

    static void amount() throws IOException {
        try {
            System.out.println("Amount: ");
            Scanner strAmount = new Scanner(System.in);
            amount = strAmount.nextLine();

            switch (amount) {
                case "exit" -> {
                    System.out.println(seeYou);
                    System.exit(0);
                }
                case "back" -> strAfter();
                case "start" -> start();
            }

            if (amount.length() == 0) {
                isAmountEnabled = false;
            } else if (Integer.parseInt(amount) <= 0 || Integer.parseInt(amount) == Integer.MAX_VALUE) {
                System.out.println(outOfRange);
                amount();
            }
            writeToFile();
        } catch (NumberFormatException nfe) {
            System.out.println(onlyIntegerAndOutOfRange);
            amount();
        }
    }

    static void writeToFile() throws IOException {
        System.out.println("Write to file? (y/n): ");
        Scanner strOutput = new Scanner(System.in);
        output = strOutput.nextLine();

        switch (output) {
            case "exit" -> {
                System.out.println(seeYou);
                System.exit(0);
            }
            case "back" -> amount();
            case "start" -> start();
        }

        if (output.equals("y")) {
            writeToFile = true;
            writeToConsole();
        } else {
            writeToConsole = true;
            next();
        }
    }

    static void writeToConsole() throws IOException {
        System.out.println("Print to console? (y/n) - Much slower!: ");
        Scanner strPrintInConsole = new Scanner(System.in);
        print = strPrintInConsole.nextLine();

        switch (print) {
            case "exit" -> {
                System.out.println(seeYou);
                System.exit(0);
            }
            case "back" -> writeToFile();
            case "start" -> start();
        }

        if (print.equals("y")) {
            writeToConsole = true;
        } else {
            System.out.println("Please wait...");
        }
        next();
    }

    static void next() throws IOException {
        for (int length = Integer.parseInt(minLength); length < Integer.parseInt(maxLength) + 1; length++) {
            generate("", length);
        }
        exit(count);
    }

    static void generate(String str, int length) throws IOException {
        if (length == 0) {
            if (writeToFile) {
                fileWriter.write(before + str + after + "\n");
            }

            if (writeToConsole) {
                System.out.println(before + str + after);
            }
            count++;
        } else {
            for (char c : charset) {
                generate(str + c, length - 1);
            }
        }

        if (writeToFile && isAmountEnabled && count == Integer.parseInt(amount)) {
            exit(count);
        }

        if (isAmountEnabled && count == Integer.parseInt(amount)) {
            exit(count);
        }
    }

    static void exit(long count) throws IOException {
        if (writeToFile) {
            fileWriter.flush();
            fileWriter.close();
        }

        if (isAmountEnabled) {
            System.out.println("\nFinished: " + count + "/" + amount + " were generated.");
        } else {
            System.out.println("\nFinished: " + count + " were generated.");
        }
        System.exit(0);
    }
}