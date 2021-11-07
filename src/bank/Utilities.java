/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Alec Grace && Kilian Hammersmith
 */
public class Utilities {
    
    private static Scanner scan = new Scanner(System.in);
    
    protected final static DecimalFormat df = new DecimalFormat("0.00");
    
    public static String[] randomNames(int num) {
        String[] listOfNames = new String[num];
        Random generator = new Random();
        int rand, lines = 0;
        ArrayList<String> allNames = new ArrayList<String>();
        String curLine, name;
        
        try {
            //Must enter complete file path of wherever the project is being compiled... 
            //ask about how to make this universal next class
            Scanner reader = new Scanner(new File("/home/alec/eclipse-workspace/cs213_Final/src/bank/" + 
                    "baby-names.csv"));
            
            while (reader.hasNextLine()) {
                curLine = reader.nextLine();
                name = curLine.split(",")[1];
                allNames.add(name);
                lines++;
            }
            
            reader.close();
            
            for (int i = 0; i < num; i++) {
                rand = generator.nextInt(lines);
                listOfNames[i] = allNames.get(rand).replaceAll("\"", "");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
                
        return listOfNames;
    }
    
    public static void displayAll(Account[] people) {
        
        for (Account acct : people) {
            if (acct != null) {
                System.out.println("\n\n" + acct.displayAll());
            }
        }
        
    }
    
    public static void makeDeposit(Account[] people, String accNum, double deposit) {
        
        boolean successful = false;
        
        if (deposit > 0) {
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
        } else {
            System.out.println("Must enter positive value for deposit.");
        }
        
    }
    
    public static void makeDeposit(Account person, double deposit) {
        
        if (deposit > 0) {
            person.depositAmount(deposit);
            System.out.println("Successfully deposited $" + deposit + 
                    " to " + person.getLastName() + "'s account.");
        } else {
            System.out.println("Must enter positive value for deposit.");
        }
        
    }
    
    public static String getValidAccount() {
        
        boolean valid = false;
        String rawInput, account = "";
        
        while (!valid) {
            try {
                System.out.println("Enter account number: ");
                rawInput = scan.nextLine();
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
    
    public static double getUserDouble(String message) {
        
        double returnDouble = 0.0;
        boolean valid = false;
        String rawInput, convertToMoney;
        
        while (!valid) {
            try {
                System.out.println(message);
                rawInput = scan.nextLine();
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
        
        if (withdrawal > 0) {
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
        } else {
            System.out.println("Must enter positive value to withdraw.");
        }
    }
    
    public static void makeWithdrawal(Account person, double withdraw) {
        
        if (withdraw > 0) {
            person.withdrawAmount(withdraw);
            System.out.println("Successfully withdrew $" + withdraw +
                    " to " + person.getLastName() + "'s account.");
        } else {
            System.out.println("Must enter positive value to withdraw.");
        }
    }

    public static void addInterestToAll(Account[] people){
        for(int i = 0 ; i < people.length; i++){
            if (people[i] != null)
                people[i].addInterest();
        }
    }

    public static int[] accountCounting() {
        
        boolean goodInput = false;
        int secondChoice = 0;
        int[] returnList = new int[2];
        String raw;
        
        while (!goodInput) {
            try {
                
                System.out.println("Select account type to count: " +
                                "\n 1. Checking" +
                                "\n 2. Saving" + 
                                "\n 0. Back to main menu" +
                                "\n\nChoice: ");
                
                raw = scan.nextLine();
                secondChoice = Integer.parseInt(raw);
                
                if (secondChoice >= 0 && secondChoice < 3) {
                    goodInput = true;
                } else {
                    System.out.println("Please enter an integer between 0 and 2");
                    goodInput = false;
                }
                
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Please enter a number");
            }
        }
        
                
        if (secondChoice == 1) {
            returnList[1] = Account.getNumOfCheckings();
        } else if (secondChoice == 2) {
            returnList[1] = Account.getNumOfSavings();
        } else {
            returnList[1] = -1;
        }
        returnList[0] = secondChoice;
        
        return returnList;
    }
    
    public static boolean playAgain(boolean bypass) {
        if (!bypass) {
            System.out.println("Would you like to perform another action? (Y/N)");
            String playAgain = scan.nextLine().toLowerCase();
            playAgain = (playAgain.length() == 0) ? "n" : playAgain;
            
            char choice = playAgain.charAt(0);
            
            return (choice == 'y');
        } else {
            return true;
        }
    }
    
    public static String generateBday() {
        String bday, dayString, monthString;
        Random random = new Random();
        Integer month, day, year;
        month = random.nextInt(11) + 1;
        year = random.nextInt(100) + 1903;
        
        //check for months with 31 days
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            day = random.nextInt(30) + 1;
        } else if (month == 2) { //check for Feb.
            day = (year % 4 == 0) ? random.nextInt(27) + 1 : random.nextInt(28) + 1;
        } else {//months left over have 30 days
            day = random.nextInt(29) + 1;
        }
        
        dayString = (day < 10) ? "0" + day.toString() : day.toString();
        monthString = (month < 10) ? "0" + month.toString() : month.toString();
        bday = monthString + "/" + dayString + "/" + year.toString();
        
        return bday;
    }
    
    public static Account[] doubleArraySize(Account[] currentList) {
        Account[] newList = new Account[currentList.length * 2];
        
        for (int i = 0; i < currentList.length; i++) {
            newList[i] = currentList[i];
        }
        
        return newList;
    }

    public static Account accountWBalance() {
        boolean goodName = false, goodChar = false, goodBday = false, 
                validPerson = false, validBalance = false;
        char userGender, accType;
        String bday, accTypeString, personType, balanceString, lName, fName;
        int day, month, year, person = 0;
        double balance = 0;
        
        //Get first and last name
        do {
            System.out.println("Enter first name: ");
            fName = scan.nextLine();
            if (fName.length() > 0)
                goodName = true;
        } while (!goodName);
        
        goodName = false;
        do {
            System.out.println("Enter last name: ");
            lName = scan.nextLine();
            if (lName.length() > 0) 
                goodName = true;
        } while (!goodName);
        
        //Get gender
        do {
            System.out.println("Enter gender (m/f): ");
            userGender = scan.nextLine().toLowerCase().charAt(0);
            
            if (userGender == 'm' || userGender == 'f') {
                goodChar = true;
            } else {
                System.out.println("Please enter 'm' or 'f'...");
                goodChar = false;
            }
        } while (!goodChar);
        
        //Get birthday
        do {
            System.out.println("Please enter valid birthday in format (MM/DD/YYYY): ");
            bday = scan.nextLine();
            
            if (bday.matches("\\d{2}[/]\\d{2}[/]\\d{4}")) {
                month = Integer.parseInt(bday.substring(0,2));
                day = Integer.parseInt(bday.substring(3,5));
                year = Integer.parseInt(bday.substring(6,10));
                
                // make sure valid month, day, and year (age has to be over 18 and under 121)
                if ((month > 0 && month < 13) && (day > 0 && day < 32) && (year > 1900 && year < 2004)) {
                    goodBday = true;
                    
                    //check for Feb. special days
                    if (month == 2 && day == 29) {
                        if ((year % 4) != 0) { //leap year
                            goodBday = false;
                        }
                    } else if (month == 2 && day > 28) {
                        goodBday = false;
                    }
                    
                    //check for 30 day months
                    if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (day > 30) {
                            goodBday = false;
                        }
                    }
                }
                
            } else {
                goodBday = false;
            }
        } while (!goodBday);
        
        //Get account type
        do {
            System.out.println("Enter account type (checking/saving): ");
            accTypeString = scan.nextLine();
            accTypeString.toLowerCase();
            accType = accTypeString.charAt(0);
            
            try {
                if (accType == 'c' || accType == 's') {
                    goodChar = true;
                } else {
                    System.out.println("Account type must start with 'c' or 's'...");
                    goodChar = false;
                }
            } catch (java.lang.StringIndexOutOfBoundsException e) {
                // TODO Auto-generated catch block
                System.out.println("Must enter some input...");
            }
        } while (!goodChar);
        
        //Get person type
        do {
            System.out.println("What is your status:" +
                            "\n1. Student" +
                            "\n2. Staff" +
                            "\n3. Faculty" +
                            "\n\nChoice (1-3): ");
            personType = scan.nextLine();
            try {
                person = Integer.parseInt(personType);
                if (person > 0 && person < 4) {
                    validPerson = true;
                } else {
                    System.out.println("Must enter value between 1 and 3...");
                    validPerson = false;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Must be an integer...");
                validPerson = false;
            }
        } while (!validPerson);
        
        //Get initial balance
        do {
            System.out.println("Enter initial balance for account: ");
            balanceString = scan.nextLine();
            try {
                balance = Double.parseDouble(balanceString);
                if (balance > 0) {
                    validBalance = true;
                } else {
                    System.out.println("Must enter positive balance...");
                    validBalance = false;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Must enter number value...");
                validBalance = false;
            }
        } while (!validBalance);
        
        return new Account(lName, fName, userGender, bday, accType, person, balance);
    }
    
    public static Account accountWOBalance() {
        boolean goodName = false, goodChar = false, goodBday = false, 
                validPerson = false;
        char userGender, accType;
        String bday, accTypeString, personType, lName, fName;
        int day, month, year, person = 0;
        
        //Get first and last name
        do {
            System.out.println("Enter first name: ");
            fName = scan.nextLine();
            if (fName.length() > 0)
                goodName = true;
        } while (!goodName);
        
        goodName = false;
        do {
            System.out.println("Enter last name: ");
            lName = scan.nextLine();
            if (lName.length() > 0) 
                goodName = true;
        } while (!goodName);
        
        //Get gender
        do {
            System.out.println("Enter gender (m/f): ");
            userGender = scan.nextLine().toLowerCase().charAt(0);
            
            if (userGender == 'm' || userGender == 'f') {
                goodChar = true;
            } else {
                System.out.println("Please enter 'm' or 'f'...");
                goodChar = false;
            }
        } while (!goodChar);
        
        //Get birthday
        do {
            System.out.println("Please enter valid birthday in format (MM/DD/YYYY): ");
            bday = scan.nextLine();
            
            if (bday.matches("\\d{2}[/]\\d{2}[/]\\d{4}")) {
                month = Integer.parseInt(bday.substring(0,2));
                day = Integer.parseInt(bday.substring(3,5));
                year = Integer.parseInt(bday.substring(6,10));
                
                // make sure valid month, day, and year (age has to be over 18 and under 121)
                if ((month > 0 && month < 13) && (day > 0 && day < 32) && (year > 1900 && year < 2004)) {
                    goodBday = true;
                    
                    //check for Feb. special days
                    if (month == 2 && day == 29) {
                        if ((year % 4) != 0) { //leap year
                            goodBday = false;
                        }
                    } else if (month == 2 && day > 28) {
                        goodBday = false;
                    }
                    
                    //check for 30 day months
                    if (month == 4 || month == 6 || month == 9 || month == 11) {
                        if (day > 30) {
                            goodBday = false;
                        }
                    }
                }
                
            } else {
                goodBday = false;
            }
        } while (!goodBday);
        
        //Get account type
        do {
            System.out.println("Enter account type (checking/saving): ");
            accTypeString = scan.nextLine();
            accTypeString.toLowerCase();
            accType = accTypeString.charAt(0);
            if (accType == 'c' || accType == 's') {
                goodChar = true;
            } else {
                System.out.println("Account type must start with 'c' or 's'...");
                goodChar = false;
            }
        } while (!goodChar);
        
        //Get person type
        do {
            System.out.println("What is your status:" +
                            "\n1. Student" +
                            "\n2. Staff" +
                            "\n3. Faculty" +
                            "\n\nChoice (1-3): ");
            personType = scan.nextLine();
            try {
                person = Integer.parseInt(personType);
                if (person > 0 && person < 4) {
                    validPerson = true;
                } else {
                    System.out.println("Must enter value between 1 and 3...");
                    validPerson = false;
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Must be an integer...");
                validPerson = false;
            }
        } while (!validPerson);
        
        return new Account(lName, fName, userGender, bday, accType, person);
    }
    
    public static Account[] safeAdd(Account[] originalList, Account newAcc) {
        
        for (int i = 0; i < originalList.length; i++) {
            if (originalList[i] == null) {
                originalList[i] = newAcc;
                return originalList;
            }
        }
        
        originalList = doubleArraySize(originalList);
        return safeAdd(originalList, newAcc);
        
    }

    public static String getLowStudentAccounts(Account[] people) {
        String lowAccounts = "";
        
        for (Account person : people) {
            if (person != null) {
                if (person.getCurrentBalance() < 100 && person.getPersonType() == 1) {
                    lowAccounts += "\n" + person.displayAll() + "\n\n";
                }
            }
        }

        if (lowAccounts.compareTo("") == 0) {
            return "No low balance student accounts.";
        } else {
            return lowAccounts;
        }
    }
    
    public static String getHighEmployeeAccounts(Account[] people) {
        String highAccounts = "";
        
        for (Account person : people) {
            if (person != null) {
                if (person.getPersonType() > 1 && person.getCurrentBalance() > 5000)
                    highAccounts += "\n" + person.displayAll() + "\n\n";
            }
        }
        
        if (highAccounts.compareTo("") == 0) {
            return "No employee accounts with balance > $5000.";
        } else {
            return highAccounts;
        }
    }

    public static Account searchLinearLastName(Account[] people) {
        Account foundTarget = null;
        System.out.println("Enter last name of customer to search for: ");
        String lName = scan.nextLine();
        
        for (Account person : people) {
            if (person != null) {
                if (lName.compareToIgnoreCase(person.getLastName()) == 0)
                    foundTarget = person;
            }
        }
        
        return foundTarget;
    }

    public static int accountOptions() {
        int choice = 0;
        String rawChoice;
        boolean goodChoice = false, goodRaw = false;
        
        System.out.println("\nWhat operation would you like to perform on this account?" +
                        "\n\n1. Check balance" +
                        "\n2. Withdraw money" +
                        "\n3. Deposit money" +
                        "\n4. Add interest" + 
                        "\n5. Close (delete) account" +
                        "\n0. Back to main menu" +
                        "\n\nEnter Choice: ");
         do {   
            try {
                do {
                    rawChoice = scan.nextLine();
                    choice = Integer.parseInt(rawChoice);
                    
                    if (choice >=0 && choice < 6) {
                        goodChoice = true;
                        goodRaw = true;
                    } else {
                        System.out.println("Please enter integer between 0 and 5");
                        goodChoice = false;
                    }
                } while (!goodChoice);
            } catch (java.lang.NumberFormatException e) {
                System.out.println("Please enter Integer...");
                goodRaw = false;
            }
         } while (!goodRaw);
         
        return choice;
    }

    public static void removeAccount(Account[] people, Account accountForRemoval) {
        
        for (int i = 0; i < people.length; i++) {
            if (people[i] != null && 
                    people[i].getAccountNum() == accountForRemoval.getAccountNum()) {
                people[i] = null;
            }
        }
    }

    public static void bubbleSortAccounts(Account[] people) {
        
        int end = people.length - 1;
        Account tempAccount;
        boolean bypass = false;
        
        for (int i = 0; i < people.length - 1; i++) {
            for (int j = 0; j < end; j++) {
                bypass = false;
                if (people[j+1] != null && 
                        people[j].getFirstName().compareToIgnoreCase(people[j+1].getFirstName()) > 0) {
                    tempAccount = people[j+1];
                    people[j+1] = people[j];
                    people[j] = tempAccount;
                } else if (people[j+1] == null) {
                    bypass = true;
                }
            }

            if (!bypass) {
                end--;
            }
        }
    }
}
