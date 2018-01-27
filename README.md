# Shelved
This is an Android app project for CS71. Software Engineering (Prof. Zachary Palmer)

View the Wiki page for our original vision statement, architectural information, an overview of the final status of the project (for now), and screenshots of the interfaces.

This project is made with a maven project - **nonAndroid**, and an Android project - **android**.

1. To test on a MySQL database, you need to change your credentials in 
   **shelved/nonAndroid/server/src/main/resources/hibernate.cfg.xml**
   ```java
    <property name="hibernate.connection.username">[UserName]</property>
    <property name="hibernate.connection.password">[Password]</property>
   ```
2. Depending on whether you are using an emulator, or an actual device connected to the computer, you would need to change in the file 
**shelved/android/app/src/main/res/values/string_local.xml** following the example file in **shelved/android/app/src/main/res/string_local.xml.example** by changing the IP address:
* Using emulator: 10.0.2.2
* Device: Current computer's IP address

  ```java
  <resources>
    <string name="server_url">[IP address]</string>
  </resources>
  ```
3. In order to start using the app (for now), you will need either a connected device, or choose an emulator. The steps to run are:
* Run Server in **nonAndroid**
* Run app in **android**
