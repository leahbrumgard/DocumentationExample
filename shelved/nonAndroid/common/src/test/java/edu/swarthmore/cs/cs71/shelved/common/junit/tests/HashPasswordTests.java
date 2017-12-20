package edu.swarthmore.cs.cs71.shelved.common.junit.tests;


import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleUser;
import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class HashPasswordTests {

    @Test
    public void testPasswordHash() {
        SimpleUser user = new SimpleUser();
        user.setSalt();
        user.setEmail("leah");
        user.setPassword("badpassword");
        String salt = user.getSalt();
        String password = user.getPassword();

        String enteredCorrectPw = "badpassword";
        String hashedCorrectEntered = BCrypt.hashpw(enteredCorrectPw, salt);

        String enteredWrongPw = "wrongpassword";
        String hashedWrongEntered = BCrypt.hashpw(enteredWrongPw, salt);


        Assert.assertEquals(password, hashedCorrectEntered);
        Assert.assertNotEquals(password, hashedWrongEntered);
    }

    @Test
    public void testChangePassword() {
        SimpleUser user = new SimpleUser();
        user.setSalt();
        user.setEmail("leah");
        user.setPassword("badpassword");
        String salt = user.getSalt();
        String password = user.getPassword();

        user.changePassword("badpassword", "newpassword");

        String newPw = "newpassword";
        String newSalt = user.getSalt();

        String hashedNewPw = BCrypt.hashpw(newPw, newSalt);

        Assert.assertEquals(hashedNewPw, user.getPassword());
        Assert.assertNotEquals(password, user.getPassword());
    }
}
