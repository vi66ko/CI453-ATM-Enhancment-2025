// BankAccount class
// This class has instance variables for the account number, password and balance, and methods
// to withdraw, deposit, check balance etc.

// This class contains methods which you need to complete to make the basic ATM work.
// Tutors can help you get this part working in lab sessions. 

// If you choose the ATM for your project, you should make other modifications to 
// the system yourself, based on similar examples we will cover in lectures and labs.

import java.math.BigDecimal;
import java.util.ArrayList;

// Custome

public class BankAccount {
    private int accNumber = 0;
    private String accountNumber = null;
    private String accPasswd = null;
    private BigDecimal balance = new BigDecimal(0);
    private String firstName = null;
    private String lastName = null;
    private String address = null;

    private ArrayList<String> emails = new ArrayList<>();

    public BankAccount() { // an empty construtor

    }

    public BankAccount(int accountNumber, String password) {
        this.accNumber = accountNumber;
        this.accPasswd = password;
    }

    public BankAccount(int accountNumber, String password, BigDecimal balance) {
        this.accNumber = accountNumber;
        this.accPasswd = password;
        this.balance = balance;
    }

    public BankAccount(String firstName, String lastName, String address, String email, String accountNumber,
            String password, BigDecimal balanc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emails.add(email);
        this.accountNumber = accountNumber;
        this.accPasswd = password;

    }

    public int getaccNumber() {
        return accNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAccNumber() {
        return accNumber;
    }

    public String getAccPasswd() {
        return accPasswd;
    }

    // ===================????????????????????==========================
    // Where is the beset place to do security password?
    // here; bank; model; controller?
    public boolean setPassword(String newPasswod) {
        this.accPasswd = newPasswod;
        return true;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addEmail(String email) {
        this.emails.add(email);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
