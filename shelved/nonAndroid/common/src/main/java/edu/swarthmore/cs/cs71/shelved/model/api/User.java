package edu.swarthmore.cs.cs71.shelved.model.api;

public interface User {
    void setToken(String token);
    void setEmail(String email);
    void setPassword(String password);
    void setName(String name);
    public void setBio(String bio);
    public void setLocation(String location);
    void setShelves(); // initialize list of all Shelf
    void setSalt();
    void changePassword(String oldPassword, String newPassword);

    // getters
    String getPassword();
    String getName();
    String getBio();
    String getLocation();
    String getSalt();



}
