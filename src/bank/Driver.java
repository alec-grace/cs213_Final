package bank;

import java.util.*;

public class Driver {
    
    public static void main(String args[]) {
        
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
            balance = generator.nextDouble() + generator.nextInt(100000);
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
            accountType = (i < 3) ? 'c' : 's';
            balance = generator.nextDouble() + generator.nextInt(100000);
            Account tempAccount = new Account(lname, fname, gender, bday, accountType,
                                2, balance);
            
            if (i == currentCustomers.length) {
                currentCustomers = Utilities.doubleArraySize(currentCustomers);
            }
            currentCustomers[i] = tempAccount;
        }
        //Generate 5 random faculty
        for (int i = 10; i < 15; i++) {
            lname = names[i*2];
            fname = names[(i*2) + 1];
            gender = (generator.nextInt(2) > 0) ? 'm' : 'f';
            bday = Utilities.generateBday();
            accountType = (i < 3) ? 'c' : 's';
            balance = generator.nextDouble() + generator.nextInt(100000);
            Account tempAccount = new Account(lname, fname, gender, bday, accountType,
                                3, balance);
            
            if (i == currentCustomers.length) {
                currentCustomers = Utilities.doubleArraySize(currentCustomers);
            }
            currentCustomers[i] = tempAccount;
        }
        
        Scanner sc = new Scanner(System.in);
        boolean goodInput, bypass;
        int userChoice;
        String rawInput, menu = "1. Display all accounts" +
                    "\n2. Total number of accounts" + 
                    "\n3. Open account with initial deposit" +
                    "\n4. Open account without initial deposit" + 
                    "\n5. Add interest to all accounts" + 
                    "\n6. Display low balance accounts (balance < $100)" +
                    "\n7. Display all employee accounts with balance > $5000" + 
                    "\n8. Search for account by last name" +
                    "\n9. Display savings accounts sorted by first name" +
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

                    if (userChoice >= 0 && userChoice < 7) {
                        goodInput = true;
                    } else {
                        System.out.println("Please enter an integer between 0 and 6");
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
                    int[] accountInfo = Utilities.accountCounting();
                    String accType;
                    
                    switch (accountInfo[0]) {
                        case 1:
                            accType = "checking";
                            break;
                        case 2:
                            accType = "saving";
                            break;
                        case 3: 
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
                case 3:
//                    currentCustomers = Utilities.addAccount(currentCustomers, Utilities.accountWBalance());
                    Account newGuy = Utilities.accountWBalance();
                    newGuy.displayAll();
                    break;
                case 0:
                    System.exit(1);
                default:
                    break;
                    
                    
                /*
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
                    */
            }       
            
        } while (Utilities.playAgain(bypass));
        
        sc.close();
    }
}

