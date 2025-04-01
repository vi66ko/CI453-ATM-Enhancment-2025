// The View class creates and manages the GUI for the application.
// It doesn't know anything about the ATM itself, it just displays
// the current state of the Model, (title, output1 and output2), 
// and handles user input from the buttons and handles user input

// We import lots of JavaFX libraries (we may not use them all, but it
// saves us having to thinkabout them if we add new code)
// Java
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Timeline;
import javafx.application.Platform;
// JavaFx
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.*;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.input.KeyEvent;

class View implements EventHandler<KeyEvent> {
    int sceneHeight = 420; // Height of window pixels
    int sceneWidth = 500; // Width of window pixels
    Stage window = null;
    // variables for components of the user interface
    Label title; // Title area (not the window title)
    TextField message; // Message area, where numbers appear
    TextArea reply; // Reply area where results or info are shown
    ScrollPane scrollPane; // scrollbars around the TextArea object
    GridPane grid; // main layout grid
    TilePane buttonPane; // tiled area for buttons

    // The other parts of the model-view-controller setup
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

        // create the user interface component objects
        // The ATM is a vertical grid of four components -
        // label, two text boxes, and a tiled panel
        // of buttons

        // layout objects
        this.setWelcomingUI();
        // this.setLoginUI();
        // this.setActiveUI2();
        window.show();
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
        grid.setId("login-grid");
        HBox btnContainer = new HBox();
        btnContainer.setId("btn-container");

        // Controls
        Text logo = new Text("ATM");
        logo.setId("logo");
        GridPane.setHalignment(logo, HPos.CENTER);
        Label labelAccountNumber = new Label("Account Numbers");
        TextField accountNumberField = new TextField();
        Label labelPassword = new Label("Password");
        PasswordField passwordField = new PasswordField();
        Text feedback = new Text();
        Button btnBack = new Button("back");
        btnBack.setId("btn-back");
        Button btnLogin = new Button("login");
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
        }, 4000);

        // User input
        btnLogin.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        timer.cancel();
                        String accountNumerInput = accountNumberField.getText();
                        String passwordInput = passwordField.getText();
                        // ################
                        // moving the check to the controller so here will be not any validation or
                        // verification
                        // it will just the visual representation of the app

                        if (accountNumerInput.length() == 0 || passwordInput.length() == 0) {
                            return;
                        }
                        Debug.trace("View::setOnAction: " + accountNumerInput.length());
                        Debug.trace("View::setOnAction: " + accountNumerInput);
                        String message = controller.login(accountNumerInput, passwordInput);
                        feedback.setText(message);
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
        VBox.setVgrow(grid, Priority.ALWAYS); // Making the child to take the full width
        // HBox
        btnContainer.setSpacing(100);
        btnContainer.getChildren().addAll(btnBack, btnLogin);

        // Grid constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().add(col1);

        grid.add(logo, 0, 0);
        grid.add(labelAccountNumber, 0, 1);
        grid.add(accountNumberField, 0, 2);
        grid.add(labelPassword, 0, 3);
        grid.add(passwordField, 0, 4);
        grid.add(feedback, 0, 5);

        root.getChildren().addAll(grid, btnContainer);

        Scene loginScene = new Scene(root, this.sceneWidth, this.sceneHeight);
        loginScene.getStylesheets().add("./resources/styles/global.css");
        loginScene.getStylesheets().add("./resources/styles/login.css");
        this.window.setScene(loginScene);
    }

    public void setActiveUI2() {
        grid = new GridPane();
        grid.setId("layout"); // assign an id to be used in css file
        buttonPane = new TilePane();
        buttonPane.setId("buttons"); // assign an id to be used in css file

        // controls
        this.title = new Label("Best ATM"); // Message bar at the top for the title
        grid.add(title, 0, 0); // Add to GUI at the top

        message = new TextField(); // text field for numbers and error messages
        message.setEditable(false); // Read only (user can't type in)
        grid.add(message, 0, 1); // Add to GUI on second row

        reply = new TextArea(); // multi-line text area for instructions
        reply.setEditable(false); // Read only (user can't type in)
        scrollPane = new ScrollPane(); // create a scrolling window
        scrollPane.setContent(reply); // put the text area 'inside' the scrolling window
        grid.add(scrollPane, 0, 2); // add the scrolling window to GUI on third row

        // Buttons - these are laid out on a tiled pane, then
        // the whole pane is added to the main grid as the fourth row

        // Button labels - empty strings are for blank spaces
        // The number of button per row should match what is set in
        // the css file
        String labels[][] = {
                { "7", "8", "9", "", "Dep", "" },
                { "4", "5", "6", "", "W/D", "" },
                { "1", "2", "3", "", "Bal", "Fin" },
                { "CLR", "0", "del", "", "", "Ent" } };

        // loop through the array, making a Button object for each label
        // (and an empty text label for each blank space) and adding them to the
        // buttonPane
        // The number of button per row is set in the css file, not the array.
        for (String[] row : labels) {
            for (String label : row) {
                if (label.length() >= 1) {
                    // non-empty string - make a button
                    Button b = new Button(label);
                    b.setOnAction(this::buttonClicked); // set the method to call when pressed
                    buttonPane.getChildren().add(b); // and add to tiled pane
                } else {
                    // empty string - add an empty text element as a spacer
                    buttonPane.getChildren().add(new Text());

                }
            }
        }
        grid.add(buttonPane, 0, 3); // add the tiled pane of buttons to the grid

        // add the complete GUI to the window and display it
        Scene scene = new Scene(grid, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/atm.css"); // tell the app to use our css file
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this);
        // scene.setOnKeyPressed(this);
        this.window.setScene(scene);

    }

    public void setActiveUI() {
        // Layout
        GridPane root = new GridPane();
        // Controlls
        Button finish = new Button("finish");
        Button balance = new Button("balance");
        Button deposit = new Button("deposite");
        Button withdrow = new Button("withdrow");
        Button passReset = new Button("password resset");

        // Constrains
        root.add(balance, 0, 0);
        root.add(passReset, 0, 1);
        root.add(finish, 0, 2);
        root.add(deposit, 1, 0);
        root.add(withdrow, 1, 1);

        GridPane.setHalignment(deposit, HPos.RIGHT);
        GridPane.setHalignment(withdrow, HPos.RIGHT);
        GridPane.setFillWidth(finish, true);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        // finish.setMaxWidth(Double.MAX_VALUE);

        // Event Hanler<ActionEvent>
        finish.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            }
        });

        root.getColumnConstraints().addAll(col1, col2);
        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("./resources/styles/global.css");
        scene.getStylesheets().add("./resources/styles/atm.css");
        this.window.setScene(scene);
    }

    public void setPasswordResset() {
        GridPane root = new GridPane();
        HBox btnContainer = new HBox();
        btnContainer.setId("btn-container");
        // Should we add a field for entering the old password
        // for a security reason even though he is already logged in
        // a case could be when a user leave he app open and is not around.
        Label labelPassword = new Label("New password");
        PasswordField passwordField = new PasswordField();
        Label labelPasswordConfirmation = new Label("Password Confirmation");
        PasswordField passwordConfirmation = new PasswordField();
        Button btnConfirm = new Button("confirm");
        btnConfirm.setMinWidth(200);

        root.add(labelPassword, 0, 0);
        root.add(passwordField, 0, 1);
        root.add(labelPasswordConfirmation, 0, 2);
        root.add(passwordConfirmation, 0, 3);
        root.add(btnConfirm, 0, 5);

        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // got to the controller to reset password
                // controller verift if the password is valid base on the criterias
                // after that going to the Controller.(passworedResset) => ATM.passwordReset =>
                // Bank.paaswordRest()
                //
            }
        });

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
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

        Text goodbyeText = new Text("Fareway\nmy friend!");
        goodbyeText.setId("goodbye-text");
        root.add(goodbyeText, 0, 0);

        Scene scene = new Scene(root, this.sceneWidth, this.sceneHeight);
        scene.getStylesheets().add("goodbye.css");
        this.window.setScene(scene);
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

}
