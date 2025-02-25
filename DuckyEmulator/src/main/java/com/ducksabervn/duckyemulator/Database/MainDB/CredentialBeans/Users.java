package com.ducksabervn.duckyemulator.Database.MainDB.CredentialBeans;

import com.ducksabervn.duckyemulator.Database.DBService.MySQLService;
import com.ducksabervn.duckyemulator.Utilities.Constant.ErrorTitle;
import com.ducksabervn.duckyemulator.Utilities.PromptAlert.AlertUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Users {
    private String userEmail;
    private static String userNameForFrontEnd;
    private String userPassword;
    private String userType;

    private static final String EMAIL_REGEX_PATTERN =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX_PATTERN);

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public static String getUserNameForFrontEnd() {
        return userNameForFrontEnd;
    }

    public static void setUserNameForFrontEnd(String userNameForFrontEnd) {
        Users.userNameForFrontEnd = userNameForFrontEnd;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public static boolean checkValidEmail(String email){
        return email == null ? false : EMAIL_PATTERN.matcher(email).matches();
    }

    public static void selectAll(){
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Users");
                ){

        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_USER_LOAD_DB_FAILED.toString());
        }
    }

    public static boolean checkCredential(Users user){
        String sqlQuery = "SELECT COUNT(UserEmail) FROM Users WHERE " +
                "UserEmail = ? AND UserPassword = ? AND UserType = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery)
                ){
            ResultSet rs = null;
            stmt.setString(1, user.getUserEmail());
            stmt.setString(2, user.getUserPassword());
            stmt.setString(3, user.getUserType());
            rs = stmt.executeQuery();
            rs.next();
            if(rs.getInt(1) != 1){
                return false;
            }
            setUserNameForFrontEnd(fetchUserName(user));
            return true;
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_USER_CHECK_CREDENTIAL_FAILED.toString());
            return false;
        }
    }

    private static String fetchUserName(Users user){
        String sqlQuery = "SELECT UserName FROM Users WHERE UserEmail = ?";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                ){
            stmt.setString(1, user.getUserEmail());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getString(1);
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_USER_LOAD_USERNAME_FAILED.toString());
            return null;
        }
    }

}
