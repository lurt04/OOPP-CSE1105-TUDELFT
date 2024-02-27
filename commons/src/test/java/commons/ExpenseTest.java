package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {
    @Test
    void ConstructorTest() {
        User user = new User("ivan","test","password");
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.add(user);
        Expense expense = new Expense("Drinks", 3.29, payingParticipants);
        assertEquals("Drinks", expense.getName());
        assertEquals(3.29, expense.getAmount());
        assertEquals(payingParticipants, expense.getPayingParticipants());

    }

    @Test
    void PartialConstructorTest() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals("Drinks", expense.getName());
        assertEquals(3.29, expense.getAmount());
    }

    @Test
    void getName() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals("Drinks", expense.getName());
    }

    @Test
    void setName() {
        Expense expense = new Expense("Drinks", 3.29);
        expense.setName("Food");
        assertEquals("Food",expense.getName());
    }


    @Test
    void getAmount() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals(3.29, expense.getAmount());
    }

    @Test
    void setAmount() {
        Expense expense = new Expense("Drinks", 3.29);
        expense.setAmount(3);
        assertEquals(3,expense.getAmount());
    }

    @Test
    void getPayingParticipants() {
        User user = new User("ivan","test","password");
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.add(user);
        Expense expense = new Expense("Drinks", 3.29,payingParticipants);
        assertEquals(payingParticipants, expense.getPayingParticipants());
    }

    @Test
    void setPayingParticipants() {
        User user = new User("ivan","test","password");
        List<User> payingParticipants = new ArrayList<>();
        payingParticipants.add(user);
        Expense expense = new Expense("Drinks", 3.29);
        expense.setPayingParticipants(payingParticipants);
        assertEquals(payingParticipants, expense.getPayingParticipants());
    }

    @Test
    void testEquals() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense2 = new Expense("Drinks", 3.29);
        assertEquals(expense1, expense2);
    }
    @Test
    void testNotEquals() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense3 = new Expense("Food", 15);
        assertNotEquals(expense1, expense3);
    }

    @Test
    void testHashCode() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense2 = new Expense("Drinks", 3.29);
        assertEquals(expense1.hashCode(), expense2.hashCode());
    }

    @Test
    void testNotEqualsHashCode() {
        Expense expense1 = new Expense("Drinks", 3.29);
        Expense expense3 = new Expense("Food", 15);
        assertNotEquals(expense1.hashCode(), expense3.hashCode());
    }
    @Test
    void testToString() {
        Expense expense = new Expense("Drinks", 3.29);
        assertEquals("Expense{name='Drinks', amount=3.29, payingParticipants=[]}", expense.toString());
    }
}