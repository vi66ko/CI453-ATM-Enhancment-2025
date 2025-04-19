
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
    private String accountPassword = null;
    private BigDecimal balance = new BigDecimal(0);
    private BigDecimal overdraftLimit = new BigDecimal(0);
    private String firstName = null;
    private String lastName = null;
    private String address = null;
    private String email = null;
    private byte numberOfLoginTries = 3;

    public BankAccount() { // an empty construtor

    }

    public BankAccount(int accountNumber, String password) {
        this.accountNumber = accountNumber;
        this.accountPassword = password;
    }

    public BankAccount(int accountNumber, String password, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountPassword = password;
        this.balance = balance;
    }

    public BankAccount(AccountType type, String firstName, String lastName, String address, String email,
            int accountNumber,
            String password, BigDecimal balance) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.accountNumber = accountNumber;
        this.accountPassword = password;
        this.balance = balance;
    }

    /**
     * This life purpose if this constructor is only to populating the data from the
     * Database
     */
    public BankAccount(AccountType type, int accountNumber, String accountPassword, BigDecimal balance,
            BigDecimal overdraftLimit, String firstName, String lastName, String address, String email,
            Byte numberOfLoginTries) {
        this.type = type;
        this.accountNumber = accountNumber;
        this.accountPassword = accountPassword;
        this.balance = balance;
        this.overdraftLimit = overdraftLimit;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.numberOfLoginTries = numberOfLoginTries;
    }

    /**
     * I need a method that return all the fields & method that accept all the
     * fields
     * to poplulate the object
     */
    public Object[] getAllFields() {
        return new Object[] {
                this.type,
                this.accountNumber,
                this.accountPassword,
                this.balance,
                this.overdraftLimit,
                this.firstName,
                this.lastName,
                this.address,
                this.email,
                this.numberOfLoginTries
        };
    }

    // Getters
    public AccountType getType() {
        return type;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    public String getEmail() {
        return email;
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

    public byte getNumberOfLoginTries() {
        return numberOfLoginTries;
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
        this.accountPassword = newPasswod;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumberOfLoginTries(byte numberOfLoginTrys) {
        this.numberOfLoginTries = numberOfLoginTrys;
    }

}
