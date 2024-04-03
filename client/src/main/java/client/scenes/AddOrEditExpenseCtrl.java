package client.scenes;

import client.utils.ServerUtils;
import commons.Event;
import commons.Expense;
import commons.ExpenseTag;
import commons.User;
import jakarta.ws.rs.WebApplicationException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import javax.inject.Inject;

import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddOrEditExpenseCtrl implements Initializable {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;
    private Expense expense;

    @FXML
    private ComboBox<ExpenseTag> expenseTag;
    @FXML
    private TextField howMuch;
    @FXML
    private ComboBox currency;

    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<User> payer;
    @FXML
    private TextField whatFor;
    @FXML
    private DatePicker when;
    @FXML
    private CheckBox allParticipants;
    @FXML
    private CheckBox someParticipants;
    @FXML
    private VBox someParticipantsSelector;

    /**
     * Constructor
     *
     * @param server   serverUtils
     * @param mainCtrl mainCtrl
     * @param event    event of expense
     */
    @Inject
    public AddOrEditExpenseCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
    }

    /**
     * during each page load, makes sure
     * the participant combobox display only usernames
     */
    public void initialize() {
        payer.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user.getUsername();
            }

            @Override
            public User fromString(String string) {
                return null;
            }
        });
    }
    /**
     * Checkbox method for allParticipants
     * if-clause is there to check only when the checkbox is
     * selected and not when it is de-selected.
     */
    @FXML
    private void allParticipantsPay() {
        if (allParticipants.isSelected()) {
            someParticipants.setSelected(false);
            someParticipantsSelector.setVisible(false);
        }
    }

    /**
     * Checkbox method for some participant
     * if-clause is there to check only when the checkbox is
     * selected anc not when it is de-selected.
     */
    @FXML
    private void someParticipantsPay() {
        if (someParticipants.isSelected()) {
            allParticipants.setSelected(false);
            someParticipantsSelector.setVisible(true);
        }
    }

    /**
     * Set expense
     *
     * @param expense to set
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     * Cancel add/edit
     */
    public void cancel() {
        clearFields();
        mainCtrl.showEventInfo(event);
    }

    /**
     * Confirm add/edit
     */
    public void ok() {
        if (expense == null) {
            expense = getExpense();
            try {
                Expense temp = server.addExpense(getExpense());
                expense.setId(temp.getId());
            } catch (WebApplicationException e) {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }
            List<Expense> expenses = new ArrayList<>(event.getExpenses());
            if (!expenses.contains(expense)) {
                expenses.add(expense);
            }
            event.setExpenses(expenses);
            server.updateEvent(event);
            clearFields();
            mainCtrl.showEventInfo(event);
        } else {
            selectedExpense();
        }

    }

    private void selectedExpense() {
        try {
            List<Expense> expenses = new ArrayList<>(event.getExpenses());
            expenses.remove(expense);
            expense.setExpenseTag(expenseTag.getValue());
            expense.setAmount(Double.parseDouble(howMuch.getText()));
            expense.setName(whatFor.getText());
            expense.setPayer(payer.getValue());
            expense.setPayingParticipants(selectedParticipants());
            server.updateExpense(expense);
            expenses.add(expense);
            event.setExpenses(expenses);
            server.updateEvent(event);
        } catch (WebApplicationException e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Get expense for adding a new expense
     *
     * @return expense
     */
    private Expense getExpense() {
        var p = new Expense(event);
        p.setPayer(payer.getValue());
        p.setName(whatFor.getText());
        p.setAmount(Double.parseDouble(howMuch.getText()));
        p.setExpenseTag(expenseTag.getValue());
        p.setExpenseDate(when.getValue());
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.addAll(selectedParticipants());
        p.setPayingParticipants(payingParticipants);
        return p;
    }

    /**
     * IMPORTANT
     * This method should be changed
     * for now it returns all participants but it should be only
     * the selected ones
     *
     * @return paying participants
     */
    private List<User> selectedParticipants() {
        if (allParticipants.isSelected()) {
            return event.getParticipants();
        }
        else {
            List <User> selected = new ArrayList<>();
            for(Node n : someParticipantsSelector.getChildren()) {
                if(((CheckBox) n).isSelected()) {
                    String text = ((CheckBox) n).getText();
                    int index = text.indexOf("(id: ");
                    long id = Long.parseLong(text.substring(index +5, text.length() -1));
                    selected.add(server.getUserById(id));
                }
            }
            return selected;

        }
    }

    /**
     * Clears fields
     */
    private void clearFields() {

    }

    /**
     * @param e key event
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
            default:
                break;
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Setup method
     *
     * @param event   event where the expense exists
     * @param expense expense to add or edit
     */
    public void setup(Event event, Expense expense) {
        this.event = event;
        this.expense = expense;
        expenseTag.getItems().setAll(event.getExpenseTags());
        someParticipantsSelector.setVisible(false);
        someParticipantsSelector.getChildren().clear();
        for (User u : event.getParticipants()) {
            someParticipantsSelector.getChildren()
                    .add(new CheckBox(u.getUsername() + "(id: " + u.getUserID() + ")"));
        }
        howMuch.setText("");
        whatFor.setText("");
        when.setValue(null);
        someParticipants.setSelected(false);
        allParticipants.setSelected(false);
        payer.setItems(FXCollections.observableList(event.getParticipants()));
        payer.setValue(event.getParticipants().get(0));
        if(expense == null) {
            okButton.setText(mainCtrl.getBundle().getString("add"));
        }
        else {
            editExistingExpenseSetup(expense);
        }
    }

    /**
     * Setup for editing an existing expense
     * @param expense expense to edit
     */
    private void editExistingExpenseSetup(Expense expense) {
        okButton.setText("Edit");
        payer.setValue(expense.getPayer());
        howMuch.setText(String.valueOf(expense.getAmount()));
        when.setValue(expense.getDate());
        boolean notAllPay = false;
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.addAll(expense.getPayingParticipants());
        if(payingParticipants.size() < event.getParticipants().size()) {
            someParticipantsPay();
            notAllPay = true;
        }
        if(notAllPay) {
            for(User u : payingParticipants) {
                for(Node n : someParticipantsSelector.getChildren()) {
                    if(((CheckBox) n).getText().equals(u)) {
                        ((CheckBox) n).setSelected(true);
                    }
                }
            }
            someParticipants.setSelected(true);
            someParticipantsPay();
        } else {
            allParticipants.setSelected(true);
            allParticipantsPay();
        }
    }
}

