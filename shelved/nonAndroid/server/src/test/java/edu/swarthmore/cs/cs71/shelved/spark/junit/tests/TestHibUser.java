package edu.swarthmore.cs.cs71.shelved.spark.junit.tests;
import edu.swarthmore.cs.cs71.shelved.model.server.HibUser;
import org.junit.Assert;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

public class TestHibUser {
    @Test
    public void TestNewUser(){
        String email = "leah@swat.edu";
        String name = "Leah";
        String password = "aaaa123456";
        HibUser newUser = new HibUser();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        String hashedPassword = BCrypt.hashpw(password, newUser.getSalt());

        Assert.assertEquals(email, newUser.getEmail().getEmail());
        Assert.assertEquals(name, newUser.getName());
        Assert.assertEquals(hashedPassword, newUser.getPassword());
    }
}
