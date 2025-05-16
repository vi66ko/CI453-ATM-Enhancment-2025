// The View class creates and manages the GUI for the application.
// It doesn't know anything about the ATM itself, it just displays
// the current state of the Model, (title, output1 and output2), 
// and handles user input from the buttons and handles user input

// We import lots of JavaFX libraries (we may not use them all, but it
// saves us having to thinkabout them if we add new code)
// Java
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
// JavaFx
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.input.KeyEvent;

class View implements EventHandler<KeyEvent> {
    int sceneHeight = 420; // Height of window pixels
    int sceneWidth = 500; // Width of window pixels
    Stage window = null;
    // variables for components of the user interface
    public Model model;
    public Controller controller;

    // we don't really need a constructor method, but include one to print a
    // debugging message if required
    public View() {
        Debug.trace("View::<constructor>");
    }

    // start is called from Main, to start the GUI up
    // Note that it is important to create controls etc here and
    // not in the constructor (or as initialisations to instance variables),
    // because we need things to be initialised in the right order
    public void start(Stage window) {
        Debug.trace("View::start");
        this.window = window;

        String inputButtonPath = "resources/media/audio/input_button.mp3";
        File inputButtonFile = new File(inputButtonPath);

        AudioClip plonkSound = new AudioClip(inputButtonFile.toURI().toString());
        plonkSound.play();

        // create the user interface component objects
        // The ATM is a vertical filterNonDigitCharactersgrid of four components -
        // label, two text boxes, and a tiled panel
        // of buttons

        // layout objects
        this.setWelcomingUI();
        // this.setLoginUI();
        // this.setActiveUI2();
        // this.setPasswordResset();
        // this.setBalanceUI();
        // this.setGoodByeUI();

        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                controller.save();
            }
        });
        window.setResizable(false);
        window.show();
        // new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
    }

    public void handle(KeyEvent event) {
        this.controller.userKeyInput(event);
    }

    // This is how the zRew talks to the Controller
    // This method is called when a button is pressed
    // It fetches the label on the button and passes it to the controller's process
    // method
    public void buttonClicked(ActionEvent event) {
        // this line asks the event to provide the actual Button object that was clicked
        Button b = ((Button) event.getSource());
        if (controller != null) {
            String label = b.getText(); // get the button label
            Debug.trace("View::buttonClicked: label = " + label);
            // Try setting a breakpoint here
            controller.process(label); // Pass it to the controller's process method
        }
    }

    public void setLoginUI() {
        // Layout
        VBox root = new VBox();
        GridPane grid = new GridPane();
        HBox btnContainer = new HBox();

        // Controls
        Text logo = new Text("ATM");
        Label labelAccountNumber = new Label("Account Number");
        TextField accountNumberField = new TextField();
        Label labelPassword = new Label("Password");
        PasswordField passwordField = new PasswordField();
        Text feedback = new Text();
        Button btnBack = new Button("back");
        Button btnLogin = new Button("login");

        // Setting up ids
        grid.setId("login-grid");
        btnContainer.setId("btn-container");
        logo.setId("logo");
        feedback.setId("feedback");
        btnBack.setId("btn-back");
        btnLogin.setId("btn-login");

        // Timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    setWelcomingUI();
                });

            }
        }, 10000);

        this.filterNonDigitSymbols(accountNumberField);
        this.filterNonDigitSymbols(passwordField);

        btnLogin.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        timer.cancel();
                        String accountNumerInput = accountNumberField.getText();
                        String passwordInput = passwordField.getText();

                        if (accountNumerInput.length() == 0 || passwordInput.length() == 0) {
                            return;
                        }
                        Debug.trace("View::setOnAction: " + accountNumerInput.length());
                        Debug.trace("View::setOnAction: " + accountNumerInput);
                        String message = controller.login(accountNumerInput, passwordInput);

                        feedback.setText(message); // display error message to the user
                        Debug.trace("View::setOnAction: " + event);
                    }
                });

        btnBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.goToWelcomeUI();
            }
        });

        // Root Contraints
        VBox.setVgrow(grid, Priority.ALWAYS); // Make the child to take the full width

        // HBox
        btnContainer.setSpacing(100);
        btnContainer.getChildren().addAll(btnBack, btnLogin);

        // Grid constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS); // Setting up a rule constraint. To the first column to take full width.
        grid.getColumnConstraints().add(col1); // Aplly the constraints

        // Position the controls to the grid
        grid.add(logo, 0, 0);
        grid.add(labelAccountNumber, 0, 1);
        grid.add(accountNumberField, 0, 2);
        grid.add(labelPassword, 0, 3);
        grid.add(passwordField, 0, 4);
        grid.add(feedback, 0, 5);

        GridPane.setHalignment(logo, HPos.CENTER);
        GridPane.setHalignment(feedback, HPos.RIGHT);

        root.getChildren().addAll(grid, btnContainer);

        Scene loginScene = new Scene(root, this.sceneWidth, this.sceneHeight);
        loginScene.getStylesheets().add("./resources/styles/global.css");
        loginScene.getStylesheets().add("./resources/styles/login.css");

        this.window.setScene(loginScene);
    }

    public void setActiveUI() {
        // Layout
        GridPane root = new GridPane();
        // Controlls
        Button finish = new Button("Finish");
        Button balance = new Button("Balance");
        Button deposit = new Button("Deposit");
        Button withdraw = new Button("Withdraw");
        Button passReset = new Button("Password Reset");
        Button quickCash = new Button("Quick Cash");

        // Constrains
        root.add(balance, 0, 0);
        root.add(passReset, 0, 1);
        root.add(finish, 1, 2);
        root.add(deposit, 1, 0);
        root.add(withdraw, 1, 1);
        root.add(quickCash, 0, 2);

        GridPane.setHalignment(deposit, HPos.RIGHT);
        GridPane.setHalignment(withdraw, HPos.RIGHT);
        GridPane.setHalignment(finish, HPos.RIGHT);
        GridPane.setFillWidth(finish, true);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        // finish.setMaxWidth(Double.MAX_VALUE);

        balance.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToBalance();
            }

        });
        finish.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.logout();
            }
        });

        passReset.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToPasswordReset();
            }
        });

        quickCash.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToQuickCash();
            }
        });

        withdraw.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToWithdraw();
            }
        });

        deposit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToDeposit();
            }
        });

        root.getColumnConstraints().addAll(col1, col2);
        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/atm.css");
        this.window.setScene(scene);
    }

    public void setPasswordResset() {
        VBox root = new VBox();
        GridPane grid = new GridPane();
        HBox btnContainer = new HBox();

        grid.setId("grid");
        btnContainer.setId("btn-container");
        // Should we add a field for entering the old password
        // for a security reason even though he is already logged in
        // a case could be when a user leave he app open and is not around.
        Label labelPassword = new Label("New password");
        PasswordField passwordField = new PasswordField();
        Label labelPasswordConfirmation = new Label("Password Confirmation");
        PasswordField passwordConfirmation = new PasswordField();
        Text feedbackMessage = new Text();
        feedbackMessage.setId("feedback-message");
        Button btnCancel = new Button("cancel");
        Button btnConfirm = new Button("confirm");

        // Constrains
        root.getChildren().add(grid);
        VBox.setVgrow(grid, Priority.ALWAYS);
        root.getChildren().add(btnContainer);
        HBox.setHgrow(btnContainer, Priority.ALWAYS);
        btnContainer.setSpacing(100);

        grid.add(labelPassword, 0, 0);
        grid.add(passwordField, 0, 1);
        grid.add(labelPasswordConfirmation, 0, 2);
        grid.add(passwordConfirmation, 0, 3);
        grid.add(feedbackMessage, 0, 4);

        btnContainer.getChildren().addAll(btnCancel, btnConfirm);

        this.filterNonDigitSymbols(passwordField);
        this.filterNonDigitSymbols(passwordConfirmation);

        // ActionEvent handling
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.goToMainMenu();
            }

        });

        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // got to the controller to reset password
                // controller verift if the password is valid base on the criterias
                // after that going to the Controller.(passworedResset) => ATM.passwordReset =>

                Response response = controller.passwordReset(passwordField.getText(), passwordConfirmation.getText());

                if (response.isSuccessful()) {
                    setFeedbackMessageUI("Your password was successfully changed.");
                } else {
                    feedbackMessage.setText(response.getMessage());
                }
            }
        });

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("password_reset.css");
        this.window.setScene(scene);

    }

    public void setWelcomingUI() {
        GridPane root = new GridPane();
        HBox btnContainer = new HBox();
        btnContainer.setId("btn-container");

        Text greetings = new Text("Welcome\nto\n best ATM");
        greetings.setId("greeting-text");

        Button btnStart = new Button("Start");
        btnStart.setDefaultButton(true);

        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.ALWAYS);
        root.getRowConstraints().add(row1);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().add(col1);

        btnContainer.getChildren().add(btnStart);
        root.add(greetings, 0, 0);
        root.add(btnContainer, 0, 1);
        GridPane.setHalignment(greetings, HPos.CENTER);

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.goToLoginUI();
            }

        });

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/welcoming.css");
        this.window.setScene(scene);
    }

    public void setQuickCashUI() {
        // Layout
        GridPane root = new GridPane();
        // Controlls
        Button five = new Button("£5.00");
        Button ten = new Button("£10.00");
        Button thirty = new Button("£30.00");
        Button fifty = new Button("£50.00");
        Button hundred = new Button("£100.00");
        Button twenty = new Button("£20.00");
        Button amount = new Button("Enter amount");
        Button cancel = new Button("Cancel");

        // Feedback
        Text feedback = new Text("");
        feedback.setId("feedback");
        root.add(feedback, 0, 0);
        // Constrains
        root.add(five, 0, 1);
        root.add(ten, 0, 2);
        root.add(twenty, 0, 3);
        root.add(cancel, 0, 4);
        root.add(thirty, 1, 1);
        root.add(fifty, 1, 2);
        root.add(hundred, 1, 3);
        root.add(amount, 1, 4);

        GridPane.setHalignment(fifty, HPos.RIGHT);
        GridPane.setHalignment(hundred, HPos.RIGHT);
        GridPane.setHalignment(amount, HPos.RIGHT);
        GridPane.setHalignment(thirty, HPos.RIGHT);
        GridPane.setFillWidth(amount, true);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        // finish.setMaxWidth(Double.MAX_VALUE);

        // Event Hanler<ActionEvent>
        five.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Response response = controller.withdraw("5");
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }
            }
        });
        ten.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Response response = controller.withdraw("10");
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }
            }
        });
        twenty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Response response = controller.withdraw("20");
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }
            }
        });
        thirty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Response response = controller.withdraw("30");
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }
            }
        });
        fifty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Response response = controller.withdraw("50");
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }
            }
        });
        hundred.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Response response = controller.withdraw("100");
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }
            }
        });
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToMainMenu();
            }
        });

        amount.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                controller.goToWithdraw();
            }
        });

        root.getColumnConstraints().addAll(col1, col2);
        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/quick.css");
        this.window.setScene(scene);
    }

    public void setDepositUI() {
        VBox root = new VBox();
        GridPane grid = new GridPane();
        HBox btnContainer = new HBox();
        grid.setId("grid");
        btnContainer.setId("btn-container");

        Label labelDeposit = new Label("Enter the amount you would like to deposit");
        TextField depositField = new TextField();
        Button btnCancel = new Button("cancel");
        Button btnConfirm = new Button("confirm");
        // Constrains
        root.getChildren().add(grid);
        VBox.setVgrow(grid, Priority.ALWAYS);
        root.getChildren().add(btnContainer);
        HBox.setHgrow(btnContainer, Priority.ALWAYS);
        btnContainer.setSpacing(100);

        grid.add(labelDeposit, 0, 0);
        grid.add(depositField, 0, 1);

        btnContainer.getChildren().addAll(btnCancel, btnConfirm);

        this.filterNonDigitSymbols(depositField);

        // ActionEvent handling
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.goToMainMenu();
            }

        });
        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.deposit(depositField.getText());
            }
        });

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/deposit.css");
        this.window.setScene(scene);

    }

    public void setWithdraw(String argAvailableToWithdraw) {
        VBox root = new VBox();
        GridPane grid = new GridPane();
        HBox btnContainer = new HBox();

        grid.setId("grid");
        btnContainer.setId("btn-container");

        Label labelWithdraw = new Label("Enter the amount you would like to withdraw");
        TextField withdrawField = new TextField();
        Button btnCancel = new Button("cancel");
        Button btnConfirm = new Button("confirm");
        Text availableToWithdraw = new Text(argAvailableToWithdraw);
        availableToWithdraw.setId("available-to-withdraw");
        Text feedback = new Text("");

        feedback.setId("feedback");
        // Constrains
        root.getChildren().add(grid);
        VBox.setVgrow(grid, Priority.ALWAYS);
        root.getChildren().add(btnContainer);
        HBox.setHgrow(btnContainer, Priority.ALWAYS);
        btnContainer.setSpacing(100);

        grid.add(availableToWithdraw, 0, 0);
        grid.add(labelWithdraw, 0, 1);
        grid.add(withdrawField, 0, 2);
        grid.add(feedback, 0, 3);

        GridPane.setHalignment(availableToWithdraw, HPos.RIGHT);
        GridPane.setHalignment(feedback, HPos.CENTER);

        btnContainer.getChildren().addAll(btnCancel, btnConfirm);
        // ActionEvent handling

        this.filterNonDigitSymbols(withdrawField);

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.goToMainMenu();
            }
        });

        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String withdrawValue = withdrawField.getText();

                // Restricting going further if the input is empty
                if (withdrawValue.length() == 0) {
                    return;
                }

                Response response = controller.withdraw(withdrawValue);
                if (response.isSuccessful()) {
                    setBalanceUI();
                } else {
                    feedback.setText(response.getMessage());
                }

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            feedback.setText("");
                        });
                    }
                }, 4000);

            }
        });

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/withdraw.css");
        this.window.setScene(scene);

    }

    public void setBalanceUI() {
        VBox root = new VBox();
        GridPane grid = new GridPane();
        HBox btnContainer = new HBox();

        String balanceValue = this.controller.getBalance();
        Text balanceLabel = new Text("Your balance is:");
        balanceLabel.setId("balance-label");
        Text balance = new Text("£ " + balanceValue);
        balance.setId("balance-value");

        grid.setId("grid");
        btnContainer.setId("btn-container");
        // Label labelWithdraw = new Label("Enter the amount you would like to
        // withdraw");
        Button btnCancel = new Button("back");

        // Constrains
        VBox.setVgrow(grid, Priority.ALWAYS);
        root.getChildren().add(grid);

        HBox.setHgrow(btnContainer, Priority.ALWAYS);
        btnContainer.setSpacing(100);
        root.getChildren().add(btnContainer);
        ColumnConstraints col1Constrainus = new ColumnConstraints();
        col1Constrainus.setHalignment(HPos.CENTER);
        GridPane.setHalignment(balance, HPos.CENTER);

        grid.add(balanceLabel, 0, 0);
        grid.add(balance, 0, 1);

        btnContainer.getChildren().addAll(btnCancel);
        // ActionEvent handling
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.goToMainMenu();
            }

        });

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/balance.css");
        this.window.setScene(scene);

    }

    public void setGoodByeUI() {
        /*
         * Strugle
         * removing the outline of the button
         * Reference:
         * link -
         * https://stackoverflow.com/questions/6092500/how-do-i-remove-the-default-
         * border-glow-of-a-javafx-button-when-selected
         * user: user2229691 | last comment | date: answered Mar 31, 2013 at 20:19
         */
        GridPane root = new GridPane();

        Text goodbyeText = new Text("Fareway\nmy\nfriend!");
        goodbyeText.setId("goodbye-text");
        root.add(goodbyeText, 0, 0);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    setWelcomingUI();
                });
            }
        }, 1500);

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/goodbye.css");

        this.window.setScene(scene);
    }

    public void setFeedbackMessageUI(String argument) {
        GridPane root = new GridPane();
        Text message = new Text(argument);
        Button btn = new Button("OK");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setActiveUI();
            }
        });

        root.add(message, 0, 0);
        root.add(btn, 0, 1);

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/style/feedbackMessage.css");
        this.window.setScene(scene);
    }

    public void showPopupMessage(String argmunet) {
        Popup popup = new Popup();
        GridPane grid = new GridPane();
        grid.setId("popup-grid");
        Text message = new Text(argmunet);
        Button button = new Button("OK");

        grid.add(message, 0, 0);
        grid.add(button, 0, 2);

        popup.getContent().add(grid);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Debug.trace("View::showPopupMessage: Has been clicked");
                popup.hide();
            }
        });
        popup.show(this.window);
    }

    public void save() {
        model.save();
    }
    // This is how the Model talks to the View

    // This method gets called BY THE MODEL, whenever the model changes
    // It fetches th title, display1 and display2 variables from the model
    // and displays them in the GUI
    public void update() {
        // if (model != null) {
        // Debug.trace("View::update");
        // String message1 = model.title; // get the new title from the model
        // title.setText(message1); // set the message text to be the title
        // String message2 = model.display1; // get the new message1 from the model
        // message.setText(message2); // add it as text of GUI control output1
        // String message3 = model.display2; // get the new message2 from the model
        // reply.setText(message3); // add it as text of GUI control output2
        // }
    }

    private void filterNonDigitSymbols(TextField inputField) {
        inputField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // String hasCharactersRegEx = ".*[a-zA-Z].*";

                if (!newValue.matches("[\\D]") || !oldValue.matches("[\\D]")) {
                    // replacing any non digit symbol
                    inputField.setText(newValue.replaceAll("[\\D]", ""));
                }
            }
        });

    }

}
