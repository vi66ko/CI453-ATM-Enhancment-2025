// BankAccount class
// This class has instance variables for the account number, password and balance, and methods
// to withdraw, deposit, check balance etc.

// This class contains methods which you need to complete to make the basic ATM work.
// Tutors can help you get this part working in lab sessions. 

// If you choose the ATM for your project, you should make other modifications to 
// the system yourself, based on similar examples we will cover in lectures and labs.
public class BankAccount
{
    public int accNumber = 0;
    public int accPasswd = 0;
    public int balance = 100;
    
    public BankAccount()
    { // an empty construtor
    
    }
    
    public BankAccount(int n, int p, int b)
    {
        this.accNumber  = n;
        this.accPasswd = p;
        this.balance = b;

    }
    
    public int getaccNumber(){
        return accNumber;
    }
    public int getaccPassword(){
        return accPasswd;
    }
    // withdraw money from the account. Return true if successful, or 
    // false if the amount is negative, or less than the amount in the account 
    public boolean withdraw( int amount ) 
    { 
        Debug.trace( "BankAccount::withdraw: amount =" + amount ); 
        if (amount <= 0){
            return false;
        }
        else{
            balance -= amount;
        }

        // CHANGE CODE HERE TO WITHDRAW MONEY FROM THE ACCOUNT
        return true;
    }
    
    // deposit the amount of money into the account. Return true if successful,
    // or false if the amount is negative 
    public boolean deposit( int amount )
    { 
        Debug.trace( "LocalBank::deposit: amount = " + amount ); 
        // CHANGE CODE HERE TO DEPOSIT MONEY INTO THE ACCOUNT
        if (amount <= 0){
        
        }
        else{
            balance += amount;
        }
        
        return false;
    }

    // Return the current balance in the account
    public int getBalance() 
    { 
        Debug.trace( "LocalBank::getBalance" ); 

        return balance;
    }
}
