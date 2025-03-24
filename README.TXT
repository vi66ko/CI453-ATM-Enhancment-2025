atm-lab project

The atm code creates the ATM, but the methods in the Bank and BankAccount 
classes which actually do the banking functions are incomplete.
The lab class exercise (with tutor support) is to complete the BankAccount.




There are four places in the code you need to change:
-   in the Bank class, the login method needs code to find a bank account, 
    given a username and password
-   in the BankAccount class, the three methods withdraw, deposit and balance
    need code added to make them work properly.



---------------------------------------------------------------------------
ATM:
	had the session if user is logged or not,

---------------------------------------------------------------------------
TODO:

- a way to exit from changing a password
buttons: left(cancel) right(login)

- what to be the time for the goodbye message
1.5sec

- the controller verify the if the data is valid
- the model verify if account exist helpfull for: login, creating account 
- if user click start and is inactive to go back to the welcome page.



client => controller[ATM] => Bank

-----------------------------------------------------
DONE: 

- welcoming page to disapaer 
button ot the right left corner  START
-----------------------------------------------------

-----------------------------------------------------
Questions:

login || password resset
1. gui =>
2. CONTROLLER(validate the input meet the criteria) =>
3. MODEL(vcerify if the user exist and the password match) =>
4. Bank


# Thougsht:

- Now we used scene to implement different GUI the other way can be usign StackPane, but that mean many active GUI which can result on increase of a memory usage.
- the controller is the ATM itself is the bridge between the bank and the client.


Rolle:

View => displaing the GUI and portal for I/O
Controller => to validate input and pass it to the models
Model => ATM, BANK
	ATM => user use it to interact with his account in the bank
	how to verify a user in the bank model 



# Reference

For timer: 
Source https://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
Source2 https://stackoverflow.com/questions/26916640/javafx-not-on-fx-application-thread-when-using-timer






