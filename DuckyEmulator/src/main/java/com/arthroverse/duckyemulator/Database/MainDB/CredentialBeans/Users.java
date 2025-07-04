/*
 * Copyright (c) 2025 Arthroverse Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Organization: Arthroverse Laboratory
 * Author: Vinh Dinh Mai
 * Contact: business@arthroverse.com
 *
 *
 * @author ducksabervn
 */
package com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans;

import com.arthroverse.duckyemulator.Database.DBService.MySQLService;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.Constant.FailedOperationType;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Users {
    private String userEmail;
    private static String currentUserNameInActiveSession; //STATIC DE PHUC VU CHO SESSION DANG LOGIN HIEN TAI
    private static String currentUserEmailInActiveSession;
    private String userPassword;
    private boolean isAdminPrivileged;
    private String userName;

    private static final String EMAIL_REGEX_PATTERN =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX_PATTERN);

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public static String getUserName() {
        return currentUserNameInActiveSession;
    }

    public static void setUserName(String userNameForFrontEnd) {
        Users.currentUserNameInActiveSession = userNameForFrontEnd;
    }

    public static String getCurrentUserEmailInActiveSession(){
        return currentUserEmailInActiveSession;
    }

    public static void setCurrentUserEmailInActiveSession(String email){
        currentUserEmailInActiveSession = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean getAdminPrivileged() {
        return isAdminPrivileged;
    }

    public void setAdminPrivileged(boolean adminPrivileged) {
        this.isAdminPrivileged = adminPrivileged;
    }

    public String getUserObjectName(){
        return this.userName;
    }

    public void setUserObjectName(String name){
        this.userName = name;
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
        String sqlQuery = "SELECT COUNT(UserEmail), IsAdminPrivilged FROM Users WHERE " +
                "UserEmail = ? AND UserPassword = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery)
                ){
            ResultSet rs = null;
            stmt.setString(1, user.getUserEmail());
            stmt.setString(2, user.getUserPassword());
            rs = stmt.executeQuery();
            rs.next();
            if(rs.getInt(1) != 1){
                return false;
            }
            user.setAdminPrivileged(rs.getBoolean(2));
            setUserName(fetchUserName(user));
            setCurrentUserEmailInActiveSession(user.getUserEmail());
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

    public static boolean insert(Users u){
        String sqlFindDuplicateEmail = "SELECT COUNT(UserEmail) FROM Users WHERE UserEmail = ?;";
        String sqlQuery = "INSERT INTO Users(UserEmail, UserName, UserPassword, IsAdminPrivilged) " +
                "VALUES(?, ?, ?, ?);";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(sqlFindDuplicateEmail);
                PreparedStatement stmt2 = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
                ){
            stmt1.setString(1,u.userEmail);
            ResultSet rs = stmt1.executeQuery();
            rs.next();
            if(rs.getInt(1) == 0){
                stmt2.setString(1, u.getUserEmail());
                stmt2.setString(2, u.userName);
                stmt2.setString(3, u.userPassword);
                stmt2.setBoolean(4, u.isAdminPrivileged);
                if (stmt2.executeUpdate() == 1) {
                    return true;
                }
            }else{
                AlertUtil.generateErrorWindow("DuckyEmulator",
                        FailedOperationType.SQL_CREATE_NEW_ACCOUNT_FAILED.toString(), ErrorMessage.CREATE_ACCOUNT_DUPLICATE_EMAIL.toString());
                return false;
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "Duckyemulator");
        }
        return false;
    }
}
