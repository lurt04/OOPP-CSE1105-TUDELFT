package commons;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String title;
    private int amountOfParticipants;

    private String eventCode;

    /*
    not in use yet, for when the Expense class has been made
     */
    private List<Object> expenses;
    private int numberOfExpenses;

    /*
      Constructor for the Event class
    */
    public Event(String title, int amountOfParticipants, String eventCode) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
        this.eventCode = eventCode;
        this.numberOfExpenses = 0;
        this.expenses = new ArrayList<>();
    }


    public Event(String title, int amountOfParticipants) {
        this.title = title;
        this.amountOfParticipants = amountOfParticipants;
    }

    /*
    gets the title of the event
     */
    public String getTitle() {
        return title;
    }

    /*
    lets the user set the title for the event
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /*
    shows the amount of participants
     */
    public int getAmountOfParticipants() {
        return amountOfParticipants;
    }

    /*
    lets the user input the amount of participants
     */
    public void setAmountOfParticipants(int amountOfParticipants) {
        this.amountOfParticipants = amountOfParticipants;
    }

    /*
    lets the user see the event code for the event and share it eventually
     */
    public String getEventCode() {
        return eventCode;
    }

    /*
    lets the user add expenses to the event
     */
    public void addExpense(Object expense){
        expenses.add(expense);
        numberOfExpenses++;
    }

    /*
    lets the user remove some expenses that the user wants
    throws exception if the expense is not in the event
     */
    public Object removeExpense(Object expense) throws Exception {
        if(!expenses.contains(expense)){
            throw new Exception("This expense does not exist");
        }
        return expenses.remove(expense);
    }

    /*
    edits or sets the expense based on the oldExpense index
    the newExpense will have the same location as the oldExpense
    throws exception if the expense is not in the event
    returns the newExpense
     */
    public Object setExpense(Object oldExpense, Object newExpense) throws Exception {
        if(!expenses.contains(oldExpense)){
            throw new Exception("This expense does not exist");
        }
        int index = expenses.indexOf(oldExpense);
        expenses.set(index, newExpense);
        return newExpense;
    }
}
