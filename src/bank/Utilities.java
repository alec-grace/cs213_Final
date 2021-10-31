/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author Alec Grace && Kilian Hammersmith
 */
public class Utilities {
    
    public static boolean playAgain() {
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Would you like to perform another action? (Y/N)");
        String userChoice = scan.nextLine().toLowerCase();
        userChoice = (userChoice.length() == 0) ? "n" : userChoice;
        
        char choice = userChoice.charAt(0);
        
        return choice == 'y';
        
    }
    
    public static void displayAll(Account[] people) {
        
        for (Account acct : people) {
            System.out.println("\n\n" + acct.displayAll());
        }
        
    }
    
    public static void makeDeposit(Account[] people, String accNum, double deposit) {
        
        boolean successful = false;
        
        for (Account acct : people) {
            if (accNum.equals(acct.getAccountNum())) {
                acct.depositAmount(deposit);
                successful = true;
            }
        }
        
        if (successful) {
            System.out.println("Successfully deposited $" + deposit + 
                    " to account number: " + accNum);
        } else {
            System.out.println("Account number: " + accNum + 
                    " does not exist in our database.");
        }
    }
    
    public static String getValidAccount() {
        
        boolean valid = false;
        Scanner sc = new Scanner(System.in);
        String rawInput, account = "";
        
        while (!valid) {
            try {
                System.out.println("Enter account number: ");
                rawInput = sc.nextLine();
                Integer.parseInt(rawInput);

                if (rawInput.length() == 5) {
                    account = rawInput;
                    valid = true;
                } else {
                    System.out.println("\nPlease enter valid account number");
                    valid = false;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Please only enter integers");
                valid = false;
            }
        }
        
        return account;
    }
    
    private final static DecimalFormat df = new DecimalFormat("0.00");
    
    public static double getUserDouble(String message) {
        
        double returnDouble = 0.0;
        boolean valid = false;
        String rawInput, convertToMoney;
        Scanner sc = new Scanner(System.in);
        
        while (!valid) {
            try {
                System.out.println(message);
                rawInput = sc.nextLine();
                returnDouble = Double.parseDouble(rawInput);
                valid = true;                        
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Please enter double value");
                valid = false;
            }
        }
        
        convertToMoney = df.format(returnDouble);
        returnDouble = Double.parseDouble(convertToMoney);
        
        return returnDouble;
    }
    
    public static void makeWithdrawal(Account[] people, String accNum, double withdrawal) {
        
        boolean successful = false;
        
        for (Account acct : people) {
            if (accNum.equals(acct.getAccountNum())) {
                if (withdrawal <= acct.getCurrentBalance()) {
                    acct.withdrawAmount(withdrawal);
                    System.out.println("Successfully withdrew $" + df.format(withdrawal) +
                            " from account number: " + accNum);
                } else {
                    System.out.println("Sorry, there is not enough money in account number: " + 
                            accNum + " to withdraw $" + df.format(withdrawal) + ".");
                }
                successful = true;
            }
        }
        
        if (!successful) {
            System.out.println("Account number: " + accNum + 
                    " does not exist in our database.");
        }
    }

    public static void addInterestToAll(Account[] people){
        for(int i = 0 ; i < people.length; i++){
            people[i].addInterest();
        }
    }

    public static int accountCounting() {
        
        boolean goodInput = false;
        int secondChoice = 0;
        String raw;
        Scanner sc = new Scanner(System.in);
        
        while (!goodInput) {
            try {
                
                System.out.println("Select account type to count: " +
                                "\n 1. Checking" +
                                "\n 2. Saving" + 
                                "\n 3. Back to main menu" +
                                "\n\nChoice: ");
                
                raw = sc.nextLine();
                secondChoice = Integer.parseInt(raw);
                
                if (secondChoice > 0 && secondChoice < 4) {
                    goodInput = true;
                } else {
                    System.out.println("Please enter an integer between 1 and 3");
                    goodInput = false;
                }
                
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
        
        sc.close();
        
        if (secondChoice == 1) {
            return Account.getNumOfCheckings();
        } else if (secondChoice == 2) {
            return Account.getNumOfSavings();
        } else {
            return -1;
        }
        
    }
    
}
