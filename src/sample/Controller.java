package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import javax.swing.*;
import java.sql.*;

public class Controller {
    @FXML
    JFXTextField nom;
    @FXML
    JFXTextField prenom;
    @FXML
    JFXTextField email;
    @FXML
    JFXTextField logemail;
    @FXML
    JFXTextField pwd;
    @FXML
    JFXTextField pwdconfirm;
    @FXML
    JFXTextField logpwd;
    public void insertomydb(String sql){
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/Cilimata3hayt", "root", "root");
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

          //  String sql = "INSERT INTO Users (Nom,Prenom,email,Password) VALUES('Adam','Bendabiza','Bendabiza@gmail.com','testpwd')";
            String sql1 ="Select email,Password from Users where email='"+email.getText()+"'";
            ResultSet rs = stmt.executeQuery(sql1);
            int rows=0;
            while (rs.next()) {
                rows++;
            }
            if(rows >=1)JOptionPane.showMessageDialog(null,"You're already with us ! did u forget ur password ?");
           else{ stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null,"Signed !");
            }

            System.out.println("Created table in given database...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

    }
    public ResultSet getfromydb(String sql){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://localhost/Cilimata3hayt", "root", "root");
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("geting from table in given database...");
            stmt = conn.createStatement();

            //  String sql = "INSERT INTO Users (Nom,Prenom,email,Password) VALUES('Adam','Bendabiza','Bendabiza@gmail.com','testpwd')";
             rs = stmt.executeQuery(sql);

            System.out.println("received table in given database...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return rs;
    }

    public void signup(){
        if(!pwd.getText().equals("")&&!nom.getText().equals("")&&!prenom.getText().equals("")) {
            if(pwd.getText().equals(pwdconfirm.getText()))
            insertomydb("INSERT INTO Users (Nom,Prenom,email,Password) VALUES('" + nom.getText() + "','" + prenom.getText() + "','" + email.getText() + "','" + pwd.getText() + "')");
            else JOptionPane.showMessageDialog(null,"check password again");
        }else JOptionPane.showMessageDialog(null,"Fill empty blanks");

    }
    public void login(){
        if(!logemail.getText().equals("")&&!logpwd.getText().equals("")) {

            String sql ="Select email,Password from Users where email='"+logemail.getText()+"'";
        System.out.println(sql);
            ResultSet rs = getfromydb(sql);
            int rows=0;
            try {
                    String password="CodeRedForDb";
                    while (rs.next()) {
                    rows++;

                        password = rs.getString("Password");
                    }
                    if(rows >=1)
                if(password.equals(this.logpwd.getText()))JOptionPane.showMessageDialog(null,"Welcome .. Sir");
                else JOptionPane.showMessageDialog(null,"U've got the wrong password check again");
                else{
                    JOptionPane.showMessageDialog(null,"sorry but we couldn't find ur account .. did u sign up already ?");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }}else JOptionPane.showMessageDialog(null , "Fill empty blanks please");
    }
}
