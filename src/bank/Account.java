package bank;

import java.util.Random;
import java.text.DecimalFormat;

/**
 *
 * @author Alec Grace && Kilian Hammersmith
 */
public class Account {
    
    private final String lastName;
    private final String firstName;
    private final char sex;
    private final String dob;
    private final String ssn;
    private final String protectedSSN;
    private final String accountNum;
    private final char accountType;
    private final int personType;
    private double balance;
    private static int numOfAccounts = 0;
    private static int numOfSavings = 0;
    private static int numOfCheckings = 0;
    
    Account(String lName, String fName, char gender, String bday,
            char accType, int person, double initBalance) {
        
        lastName = lName;
        firstName = fName;
        sex = gender;
        dob = bday;
        ssn = generateSSN();
        protectedSSN = "***-**-" + ssn.substring(7);
        accountNum = generateAccountNum();
        accountType = accType;
        personType = person;
        balance = initBalance;
        
        numOfAccounts++;
        
        if (accountType == 'c') {
            numOfCheckings++;
        } else if (accountType == 's') {
            numOfSavings++;
        }
        
    }

    Account(String lName, String fName, char gender, String bday,
            char accType, int person) {
        
        lastName = lName;
        firstName = fName;
        sex = gender;
        dob = bday;
        ssn = generateSSN();
        protectedSSN = "***-**-" + ssn.substring(7);
        accountNum = generateAccountNum();
        accountType = accType;
        personType = person;
        balance = 0.0;
        
        numOfAccounts++;
        
        if (accountType == 'c') {
            numOfCheckings++;
        } else if (accountType == 's') {
            numOfSavings++;
        }
        
    }
    
    private String generateAccountNum() {
        
        Integer accNum;
        String returnNum;
        Random generate = new Random();
        
        accNum = generate.nextInt(99999) + 1;
        
        if (accNum < 10) {
            returnNum = "0000" + accNum;
        } else if (accNum < 100) {
            returnNum = "000" + accNum;
        } else if (accNum < 1000) {
            returnNum = "00" + accNum;
        } else if (accNum < 10000) {
            returnNum = "0" + accNum.toString();
        } else {
            returnNum = accNum.toString();
        }
        
        return returnNum;
    }
    
    private String generateSSN() {
        
        String socialNum, f3, s2, l4;
        Random generate = new Random();
        Integer first3, second2, last4;
        
        first3 = generate.nextInt(1000);
        second2 = generate.nextInt(100);
        last4 = generate.nextInt(10000);
        
        if (first3 < 10) {
            f3 = "00" + first3;
        } else if (first3 < 100) {
            f3 = "0" + first3;
        } else {
            f3 = first3.toString();
        }
        
        s2 = (second2 < 10) ? "0" + second2 : second2.toString();
        
        if (last4 < 10) {
            l4 = "000" + last4;
        } else if (last4 < 100) {
            l4 = "00" + last4;
        } else if (last4 < 1000) {
            l4 = "0" + last4;
        } else {
            l4 = last4.toString();
        }
        
        socialNum = f3 + "-" + s2 + "-" + l4;
        
        return socialNum;
    }
    
    public String displayAll() {
        
        String account = (this.accountType == 'c') ? "Checking" : "Saving";
        String personT;
        
        switch (this.personType) {
            case 1:
                personT = "Student";
                break;
            case 2:
                personT = "Staff";
                break;
            case 3:
                personT = "Faculty";
                break;
            default:
                personT = "Invalid identifier";
        }
        
        String info = "Name: " + this.firstName + " " + this.lastName + 
                "\nSex: " + this.sex + "\nBirthdate: " + this.dob +  
                "\nSocial Security Number: " + this.protectedSSN + 
                "\nAccount Number: " + this.accountNum + "\nAccount type: " + 
                account + "\nPerson: " + personT + "\nBalance: $" + df.format(this.balance);
        
        return info;
    }
    
    public String getAccountNum() {
        return this.accountNum;
    }
    
    private static DecimalFormat df = new DecimalFormat("0.00");
    
    public void depositAmount(double funds) {
        this.balance += (funds > 0) ? funds : 0;
        this.balance = Double.parseDouble(df.format(this.balance));
    }
    
    public double getCurrentBalance() {
        return this.balance;
    }
    
    public void withdrawAmount(double funds) {
        this.balance -= (funds <= this.balance) ? funds : 0;
        this.balance = Double.parseDouble(df.format(this.balance));
    }
    
    public void addInterest() {
        this.balance += (this.balance * .02);
        this.balance = Double.parseDouble(df.format(this.balance));
                
    }
    
    public int getPersonType() {
        return this.personType; 
    }
    
    public char getAccountType(){
        return this.accountType;
    }
    
    //Class Variable Getters
    
    public static int getNumOfAccounts() {
        return numOfAccounts;
    }
    
    public static int getNumOfCheckings() {
        return numOfCheckings;
    }
    
    public static int getNumOfSavings() {
        return numOfSavings;
    }
    
}
