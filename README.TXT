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

- Security: To not store the password as a plain text [Optional: using slat]
- Should find a way to manage caneling the timer in View.java for this may need to improve the response of the login.

- what to be the time for the goodbye message
1.5sec

- the controller verify the if the data is valid
- the model verify if account exist helpfull for: login, creating account 
- if user click start and is inactive to go back to the welcome page.
- finding how we are going to remove or chagne email as they are inside ArraList that mean user can have couple emails.



client => controller[ATM] => Bank

-----------------------------------------------------
DONE: 

- welcoming page to disapaer 
button ot the right left corner  START
- a way to exit from changing a password
buttons: left(cancel) right(login)

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


# Articles
UI/UX Case Study: ATM machines and mobile application of Financial entities.
https://medium.com/@UltimateMachineUi/1-ui-ux-case-study-atm-machines-and-mobile-application-of-financial-entities-e6191de7680a
ATM UI for Banking Self-Service System
https://right.studio/projects/atm-interface/
# Reference

For timer: 
Source https://stackoverflow.com/questions/4044726/how-to-set-a-timer-in-java
Source2 https://stackoverflow.com/questions/26916640/javafx-not-on-fx-application-thread-when-using-timer

Currency
https://stackoverflow.com/questions/34677644/how-to-use-comparison-operators-like-on-bigdecimal
https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/Currency.html
"It is recommended to use BigDecimal class while dealing with Currency or monetary values as it provides better handling of floating point numbers and their operations."




