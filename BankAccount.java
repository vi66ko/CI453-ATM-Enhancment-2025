
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
import type.AccountType;

public class BankAccount {
    private AccountType type = null;
    private int accountNumber = 0;
    private String accPasswd = null;
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal overdraftLimit = new BigDecimal(0);
    private String firstName = null;
    private String lastName = null;
    private String address = null;

    private ArrayList<String> emails = new ArrayList<>();

    public BankAccount() { // an empty construtor

    }

    public BankAccount(int accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.accPasswd = password;
    }

    public BankAccount(int accountNumber, String password, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accPasswd = password;
        this.balance = balance;
    }

    public BankAccount(AccountType type, String firstName, String lastName, String address, String email,
            int accountNumber,
            String password, BigDecimal balance) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emails.add(email);
        this.accountNumber = accountNumber;
        this.accPasswd = password;
        this.balance = balance;
    }

    // Getters
    public AccountType getType() {
        return type;
    }

    public int getAccNumber() {
        return this.accountNumber;
    }

    public String getAccPasswd() {
        return accPasswd;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
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

    // Setters
    public void setType(AccountType type) {
        this.type = type;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setOverdraftLimit(BigDecimal overdraft) {
        this.overdraftLimit = overdraft;
    }

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

}
