package bank;

import java.util.*;

/**
 * 
 * @author Alec Grace && Kilian Hammersmith
 *
 */

public class Driver {
    
    public static void main(String args[]) throws InterruptedException {
        
        String[] names = new String[30];
        names = Utilities.randomNames(30);
        Random generator = new Random();
        String lname, fname, bday;
        char gender, accountType;
        double balance;
        Account[] currentCustomers = new Account[5];
        
      //Generate 5 random students
        for (int i = 0; i < 5; i++) {
            lname = names[i*2];
            fname = names[(i*2)+1];
            gender = (generator.nextInt(2) > 0) ? 'm' : 'f';
            bday = Utilities.generateBday();
            accountType = (i < 3) ? 'c' : 's';
            balance = generator.nextDouble() + generator.nextInt(1000);
            Account tempAccount = new Account(lname, fname, gender, bday, accountType,
                                1, balance);
            
            currentCustomers[i] = tempAccount;
        }
        //Generate 5 random staff
        for (int i = 5; i < 10;i++) {
            lname = names[(i*2)];
            fname = names[(i*2) + 1];
            gender = (generator.nextInt(2) > 0) ? 'm' : 'f';
            bday = Utilities.generateBday();
            accountType = (i < 8) ? 'c' : 's';
            balance = generator.nextDouble() + generator.nextInt(10000);
            Account tempAccount = new Account(lname, fname, gender, bday, accountType,
                                2, balance);
            
            currentCustomers = Utilities.safeAdd(currentCustomers, tempAccount);
        }
        //Generate 5 random faculty
        for (int i = 10; i < 15; i++) {
            lname = names[i*2];
            fname = names[(i*2) + 1];
            gender = (generator.nextInt(2) > 0) ? 'm' : 'f';
            bday = Utilities.generateBday();
            accountType = (i < 13) ? 'c' : 's';
            balance = generator.nextDouble() + generator.nextInt(10000);
            Account tempAccount = new Account(lname, fname, gender, bday, accountType,
                                3, balance);
            
            currentCustomers = Utilities.safeAdd(currentCustomers, tempAccount);
        }
        
        Scanner sc = new Scanner(System.in);
        boolean goodInput, bypass;
        int userChoice;
        String rawInput, menu = "1. Display all accounts" +
                    "\n2. Total number of accounts" + 
                    "\n3. Open account with initial deposit" +
                    "\n4. Open account without initial deposit" + 
                    "\n5. Add interest to all accounts" + 
                    "\n6. Display low balance student accounts (balance < $100)" +
                    "\n7. Display all employee accounts with balance > $5000" + 
                    "\n8. Search for account by last name" +
                    "\n9. Display savings accounts sorted by first name" +
                    "\n10. Display all accounts sorted by account number" +
                    "\n0. Exit\n";
        
        
        do {
            
            goodInput = false;
            bypass = false;
            userChoice = 0;
            
            while (!goodInput) {

                try {

                    System.out.println("\n----------Menu----------\n");
                    System.out.println(menu);
                    System.out.println("Enter choice: ");
                    
                    rawInput = sc.nextLine();
                    userChoice = Integer.parseInt(rawInput);

                    if (userChoice >= 0 && userChoice <= 10) {
                        goodInput = true;
                    } else {
                        System.out.println("Please enter an integer between 0 and 10");
                        goodInput = false;
                    }
                } catch (java.lang.NumberFormatException e) {
                    System.out.println("Please enter a number");
                    goodInput = false;
                }

            }

            switch (userChoice) {

                //Display all customers and their information
                case 1:
                    Utilities.displayAll(currentCustomers);
                    break;
                //Display the number of a specific account (user's choice)
                case 2:
                    int[] accountInfo = Utilities.accountCounting();
                    String accType;
                    
                    switch (accountInfo[0]) {
                        case 1:
                            accType = "checking";
                            break;
                        case 2:
                            accType = "saving";
                            break;
                        case 0: 
                            accType = null;
                            bypass = true;
                            break;
                        case -1:
                            accType = "-ERROR: Account class-";
                            break;
                        default:
                            accType = "undefined";
                            break;
                    }
                    
                    if (!bypass)
                        System.out.println("There are " + accountInfo[1] + " " + accType + " accounts"
                            + " in the system.");
                    
                    break;
                //Add a new customer and give them an initial balance
                case 3:
                    Account newBalanceCust = Utilities.accountWBalance();
                    Utilities.safeAdd(currentCustomers, newBalanceCust);
                    break;
                //Add a new customer without initial balance
                case 4:
                    Account noBalanceCust = Utilities.accountWOBalance();
                    Utilities.safeAdd(currentCustomers, noBalanceCust);
                    break;
                // Add interest to all accounts (2%)
                case 5:
                    Utilities.addInterestToAll(currentCustomers);
                    System.out.println("Completed.");
                    break;
                //Display all student accounts with under $100
                case 6:
                    System.out.println(Utilities.getLowStudentAccounts(currentCustomers));
                    break;
                //Display all employee (faculty & staff) accounts with over $5000
                case 7:
                    System.out.println(Utilities.getHighEmployeeAccounts(currentCustomers));
                    break;
                //Search for a specific account by last name (linear search)
                case 8:
                    Account target = Utilities.searchLinearLastName(currentCustomers);
                    if (target != null) {
                        int nextChoice = Utilities.accountOptions();
                        switch (nextChoice) {
                        //Check balance
                            case 1:
                                System.out.println("Current balance: $" + Utilities.df.format(target.getCurrentBalance()));
                                break;
                        //Withdraw money        
                            case 2:
                                double withdrawAmount = Utilities.getUserDouble("Enter amount to withdraw: ");
                                Utilities.makeWithdrawal(target, withdrawAmount);
                                break;
                        //Deposit money
                            case 3:
                                double depositAmount = Utilities.getUserDouble("Enter amount to deposit: ");
                                Utilities.makeDeposit(target, depositAmount);
                                break;
                        //Add interest
                            case 4:
                                target.addInterest();
                                System.out.println("Successfully added interest to " + target.getLastName() + "'s account.");
                                break;
                        //Delete account 
                            case 5:
                                Utilities.removeAccount(currentCustomers, target);
                                System.out.println("Successfully removed " + target.getLastName() + "'s account.");
                                break;
                        //Back to main menu        
                            case 0:
                                bypass = true;
                                continue;
                           default:
                                System.out.println("Error with \"nextChoice\" variable...");
                        }
                    } else {
                        System.out.println("That customer does not exist in our database.");
                    }
                    break;
                //Display all saving accounts by bubble sort on first name
                case 9:
                    Utilities.bubbleSortAccounts(currentCustomers);
                    break;
                //Selection sort by first name
                case 10:
                    Utilities.selectionSortAccounts(currentCustomers);
                    Thread.sleep(2500);
                    Utilities.displayAll(currentCustomers);
                    break;
                //Terminate the program
                case 0:
                    System.exit(1);
                default:
                    break;
                    
            }       
            
        } while (Utilities.playAgain(bypass));
        
        sc.close();
    }
}

