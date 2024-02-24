package commons;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void constructorTest() {
        User user = new User("andac", "andac@gmail.com", "123");
        assertNotNull(user);
        assertEquals("andac", user.getUsername());
        assertEquals("andac@gmail.com", user.getEmail());
        assertEquals("123", user.getPassword());
        assertEquals(User.Language.EN, user.getLanguage());
    }

    @Test
    public void usernameTest() {
        User user = new User("andac", "andac@gmail.com", "123");
        user.setUsername("mete");
        assertEquals("mete", user.getUsername());
    }

    @Test
    public void emailTest() throws User.EmailFormatException {
        User user = new User("andac", "andac@gmail.com", "123");
        user.setEmail("andac72@hotmail.com");
        assertEquals("andac72@hotmail.com", user.getEmail());
    }

    @Test
    public void passwordTest() {
        User user = new User("andac", "andac@gmail.com", "123");
        user.setPassword("85");
        assertEquals("85", user.getPassword());
    }

    @Test
    public void bicTest() throws User.BICFormatException {
        User user = new User("andac", "andac@gmail.com", "123");
        assertNull(user.getBIC());
        user.setBIC("AAAA1212123");
        assertEquals("AAAA1212123", user.getBIC());
    }

    @Test
    public void ibanTest() throws User.IBANFormatException {
        User user = new User("andac", "andac@gmail.com", "123");
        assertNull(user.getIBAN());
        user.setIBAN("NL11112222333344445555666677778888");
        assertEquals("NL11112222333344445555666677778888", user.getIBAN());
    }

    @Test
    public void serverURLTest() {
        User user = new User("andac", "andac@gmail.com", "123");
        assertNull(user.getServerURL());
        user.setServerURL("server.com");
        assertEquals("server.com", user.getServerURL());
    }

    @Test
    public void languageTest() {
        User user = new User("andac", "andac@gmail.com", "123");
        user.setLanguage(User.Language.NL);
        assertEquals(User.Language.NL, user.getLanguage());
    }

    @Test
    public void toStringTest() throws User.IBANFormatException, User.BICFormatException {
        User user = new User("andac", "andac@gmail.com", "123");
        user.setIBAN("NL11112222333344445555666677778888");
        user.setBIC("AAAA1122333");
        user.setServerURL("server.123");
        String result = "User Information:\nUsername: andac\nE-mail: andac@gmail.com\nServer: server.123\n" +
                "IBAN: NL11112222333344445555666677778888\nBIC: AAAA1122333\nPreferred Language: EN\n";
        assertEquals(result, user.toString());
    }

    @Test
    public void IBANFormatTest() {
        User user = new User("andac", "andac@gmail.com", "123");
        assertThrows(User.IBANFormatException.class, () -> {        user.setIBAN("123");} );
    }

    @Test
    public void BICFormatTest()  {
        User user = new User("andac", "andac@gmail.com", "123");
        assertThrows(User.BICFormatException.class, () -> {        user.setBIC("123");} );
    }

    @Test
    public void emailFormatTest()  {
        User user = new User("andac", "andac@gmail.com", "123");
        assertThrows(User.EmailFormatException.class, () -> {        user.setEmail("123");} );
    }
}