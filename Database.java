import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

// Exceptions
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

// Custom
import type.AccountType;

/**
 * * File structure
 * type,
 * accountNumber,
 * accountPassword,
 * balance,
 * overdraftLimit,
 * firstName,
 * lastName,
 * address,
 * email,
 * numberOfLoginTries
 * 
 *
 */
public class Database {
    private final String delimiter = "|";

    // private ArrayList<String> transactions = null;
    private Bank bank = null;
    private File accounts = null;
    private File transactions = null;

    private String rootDir = "db";

    public Database() {
        System.out.println("DATABASE");
        this.initiated();
    }

    private File createFile(String fileName) {
        File file = new File(this.rootDir + "/" + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                Debug.trace("Database::creatFile: The file " + fileName + " already exist.");
            }

        } catch (IOException error) {
            System.out.println("An IOException error occurred.");
            error.printStackTrace();
        }
        return file;

    }

    public Boolean writeDate() {
        return false;
    }

    // public BankAccount getAccounts() { }

    /**
     * If there is not any data it will be generate by this method.
     */
    private void initiated() {
        this.accounts = this.createFile("accounts.txt");
        this.transactions = this.createFile("transactions.txt");
        System.out.println("|||||||||||||||||||||- Database -|||||||||||||||||||||||");

        if ((accounts.length() == 0)) {
            this.generateAccounts();
        }
        if (transactions.length() == 0) {
            // generate
        }

    }

    public void loadAcounts(Bank bank) {
        String accountType; // This type is only to find out the what is the type in if statment
        int accountNumber;
        String accountPassword;
        BigDecimal balance;
        BigDecimal overdraftLimit;
        String firstName;
        String lastName;
        String address;
        String email;
        Byte numberOfLoginTries;

        try {
            Scanner scanner = new Scanner(this.accounts);

            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                String[] fields = record.split("\\|");

                accountType = fields[0];
                accountNumber = Integer.valueOf(fields[1]);
                accountPassword = fields[2];
                balance = new BigDecimal(fields[3]);
                overdraftLimit = new BigDecimal(fields[4]);
                firstName = fields[5];
                lastName = fields[6];
                address = fields[7];
                email = fields[8];
                numberOfLoginTries = Byte.valueOf(fields[9]);

                if (AccountType.BASIC.toString().equals(accountType)) {
                    bank.addBankAccount(
                            new BankAccount(AccountType.BASIC, accountNumber, accountPassword, balance, overdraftLimit,
                                    firstName, lastName, address, email, numberOfLoginTries));

                } else if (AccountType.PREMIUM.toString().equals(accountType)) {
                    new BankAccount(AccountType.PREMIUM, accountNumber, accountPassword, balance, overdraftLimit,
                            firstName, lastName, address, email, numberOfLoginTries);
                }
            }

            scanner.close();
        } catch (FileNotFoundException error) {
            System.out.println("An File Not dound Exception");
            error.printStackTrace();
        }

    }

    /**
     * It will generate data into the file.
     */
    private void generateAccounts() {
        try {
            FileWriter fileWriter = new FileWriter(this.accounts, true);
            fileWriter.write(
                    AccountType.BASIC
                            + "|10001|11|300|0|Emiliy|Carter|12 Rosewood Lane, Brighton, West Sussex, BN11 1AA, United Kingdom|emily.carter@examplemail.com|3|\n");
            fileWriter.write(
                    AccountType.BASIC
                            + "|10002|11|100|0|David|Carter|12 Rosewood Lane, Brighton, West Sussex, BN11 1AA, United Kingdom|david.carter@examplemail.com|3|\n");
            fileWriter.write(
                    AccountType.PREMIUM
                            + "|10003|11|800|0|Mufasa|Carter|12 Rosewood Lane, Brighton, West Sussex, BN11 1AA, United Kingdom|mufasa.carter@examplemail.com|3|\n");

            fileWriter.close();
            Debug.trace("Successfully generated dummy data");
        } catch (IOException error) {
            System.out.println("An IOException error occurred.");
            error.printStackTrace();

        }
    }

    private void generateTransactions() {
    }

}
