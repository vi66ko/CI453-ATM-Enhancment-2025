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
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||");

        if ((accounts.length() == 0)) {
            this.generateAccounts();
        }
        if (transactions.length() == 0) {
            // generate
        }

    }

    public void loadAcounts(Bank bank) {
        try {
            Scanner scanner = new Scanner(this.accounts);

            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                String[] fields = record.split("\\|");
                String accountType = fields[0];
                int accountNumber = Integer.valueOf(fields[5]);

                if (AccountType.BASIC.toString().equals(accountType)) {
                    bank.createBasicAccount(fields[1], fields[2], fields[3], fields[4], accountNumber, fields[6],
                            new BigDecimal(fields[7]));

                } else if (AccountType.PREMIUM.toString().equals(accountType)) {
                    bank.createBasicAccount(fields[1], fields[2], fields[3], fields[4], accountNumber, fields[6],
                            new BigDecimal(fields[7]));
                }
            }

            scanner.close();
        } catch (

        FileNotFoundException error) {
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
                            + "|Emiliy|Carter|12 Rosewood Lane, Brighton, West Sussex, BN11 1AA, United Kingdom|emily.carter@examplemail.com|10001|11|300\n");
            fileWriter.write(
                    AccountType.BASIC
                            + "|David|Carter|12 Rosewood Lane, Brighton, West Sussex, BN11 1AA, United Kingdom|david.carter@examplemail.com|10002|11|800\n");
            fileWriter.write(
                    AccountType.PREMIUM
                            + "|Mufasa|Carter|12 Rosewood Lane, Brighton, West Sussex, BN11 1AA, United Kingdom|Mufas.carter@examplemail.com|10003|11|400\n");

            fileWriter.close();
        } catch (IOException error) {
            System.out.println("An IOException error occurred.");
            error.printStackTrace();

        }
    }

    private void generateTransactions() {
    }

}
