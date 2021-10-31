package bank;

import java.util.*;

public class Driver {
    
public static void main(String args[]) {
        
        Account stu1 = new Account("Grace", "Alec", 'm', "12/06/1999", 's',
                                    1);
        Account stu2 = new Account("Hammersmith", "Kilian", 'm', "6/02/1998", 'c',
                                    1, 100.0);
        Account fac1 = new Account("Prasad", "Rajesh", 'm', "12/31/2024", 's',
                                    2);
        Account fac2 = new Account("Traynor", "Carol", 'f', "1/01/2024", 'c',
                                    2, 100.0);
        Account staff = new Account("Milbury", "Mike", 'm', "6/17/1952", 's',
                                    3, 420.69);
        
        Account[] currentCustomers = {stu1, stu2, fac1, fac2, staff};
        Scanner sc = new Scanner(System.in);
        String rawInput;
        
        do {
            boolean goodInput = false;
            int userChoice = 0;
            while (!goodInput) {

                try {

                    System.out.println("----------Menu----------");
                    System.out.println("1. Display all accounts" + 
                            "\n2. Make a deposit to an account" + 
                            "\n3. Make a withdrawal from an account" + 
                            "\n4. Add interest to all accounts" + 
                            "\n5. Display total number of accounts" + 
                            "\n6. Exit" +
                            "\n\nEnter choice: ");

                    rawInput = sc.nextLine();
                    userChoice = Integer.parseInt(rawInput);

                    if (userChoice > 0 && userChoice < 7) {
                        goodInput = true;
                    } else {
                        System.out.println("Please enter an integer between 1 and 6");
                        goodInput = false;
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Please enter a number");
                    goodInput = false;
                }

            }

            switch (userChoice) {

                case 1:
                    Utilities.displayAll(currentCustomers);
                    break;
                case 2:
                    String depositAccount = Utilities.getValidAccount();
                    double deposit = Utilities.getUserDouble("Enter an amount to deposit: " );
                    Utilities.makeDeposit(currentCustomers, depositAccount, deposit);
                    break;                    
                case 3:
                    String withdrawAccount = Utilities.getValidAccount();
                    double withdrawal = Utilities.getUserDouble("Enter an amount to withdraw: ");
                    Utilities.makeWithdrawal(currentCustomers, withdrawAccount, withdrawal);
                    break;
                case 4:
                    Utilities.addInterestToAll(currentCustomers);
                    break;
                case 5:
                    Utilities.numOfAccounts(currentCustomers);
                    break;
                case 6:
                    System.exit(0);
            }
        } while (Utilities.playAgain());
    }
    
}
