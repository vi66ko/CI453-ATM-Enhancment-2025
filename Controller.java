import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

// The ATM controller is quite simple - the process method is passed
// the label on the button that was pressed, and it calls different
// methods in the model depending what was pressed.
public class Controller {
    public Model model;
    public View view;

    // we don't really need a constructor method, but include one to print a
    // debugging message if required
    public Controller() {
        Debug.trace("Controller::<constructor>");
    }

    // This is how the View talks to the Controller
    // AND how the Controller talks to the Model
    // This method is called by the View to respond to some user interface event
    // The controller's job is to decide what to do. In this case it uses a switch
    // statement to select the right method in the Model
    public void process(String action) {
        Debug.trace("Controller::process: action = " + action);
        switch (action) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                model.processNumber(action);
                break;
            case "CLR":
                model.processClear();
                break;
            case "Ent":
                model.processEnter();
                break;
            case "W/D":
                model.processWithdraw();
                break;
            case "Dep":
                model.processDeposit();
                break;
            case "Bal":
                model.processBalance();
                break;
            case "Fin":
                model.processFinish();
                break;
            case "del":
                model.clearCharacter();
                break;
            default:
                model.processUnknownKey(action);
                break;
        }
    }

    public void userKeyInput(KeyEvent event) {
        System.out.println("Hola from the controller");
        Debug.trace("VIEW::handle: keypressed: " + event.getCode());
        Debug.trace("VIEW::handle: getCharacter(): " + event.getCharacter());
        Debug.trace("VIEW::handle: getText(): " + event.getText());
        // this.process(event.getText());
        KeyCode pressedKey = event.getCode();
        System.out.println("Displaying the type of the variable");
        System.out.println(event.getCode().getClass().getName());

        switch (pressedKey) {
            case NUMPAD1:
            case DIGIT1:
                this.process("1");
                break;
            case NUMPAD2:
            case DIGIT2:
                this.process("2");
                break;
            case NUMPAD3:
            case DIGIT3:
                this.process("3");
                break;
            case NUMPAD4:
            case DIGIT4:
                this.process("4");
                break;
            case NUMPAD5:
            case DIGIT5:
                this.process("5");
                break;
            case NUMPAD6:
            case DIGIT6:
                this.process("6");
                break;
            case NUMPAD7:
            case DIGIT7:
                this.process("7");
                break;
            case NUMPAD8:
            case DIGIT8:
                this.process("8");
                break;
            case NUMPAD9:
            case DIGIT9:
                this.process("9");
                break;
            case NUMPAD0:
            case DIGIT0:
                this.process("0");
                break;
            default:
                break;
        }

    }

    public void goToWelcomeUI() {
        view.setWelcomingUI();
    }

    public void goToLoginUI() {
        // There is not need to go to the model as there is not any business logic
        // but just swiching GUI
        // and is here instead directly in the view, in this way the view only building
        // the GUI and the controller is controll it.
        view.setLoginUI();
    }

    public String login(String accountNumber, String password) {
        // making the security cehck if the fields are only numbers
        int accountNumberInt = 0;
        try {
            accountNumberInt = Integer.parseInt(accountNumber);
        } catch (Exception error) {

            System.out.println("---- Exception ----");

            System.out.println(error.getClass().getSimpleName());
            System.out.println(error.getClass());
            System.out.println(error);
            System.out.println("---- / Exception ----");
            return "Invalid account number.";
        }

        String hasCharactersRegEx = ".*[a-zA-Z].*";

        // if (accountNumber.matches(hasCharactersRegEx) ||
        // password.matches(hasCharactersRegEx)) {
        // return "This is the return from the moduel";
        // }
        return model.login(accountNumberInt, password);

    }
}
