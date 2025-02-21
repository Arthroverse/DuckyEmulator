package Database.MainDB.CredentialBeans;

import Database.DBService.MySQLService;
import Utilities.Constant.ErrorTitle.ErrorTitle;
import Utilities.PromptAlert.AlertUtil;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import java.util.regex.Pattern;

public class Users {
    private String userEmail;
    private String userName;
    private String userPassword;
    private String userType;
    private String userEmailUuid;

    private static final String EMAIL_REGEX_PATTERN =
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX_PATTERN);

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserEmailUuid() {
        return userEmailUuid;
    }

    public void setUserEmailUuid(String userEmailUuid) {
        this.userEmailUuid = userEmailUuid;
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
        String sqlQuery = "SELECT COUNT(UserEmailUuid) FROM Users WHERE UserEmailUuid = ? AND " +
                "UserEmail = ? AND UserName = ? AND UserPassword = ? AND UserType = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery)
                ){
            ResultSet rs = null;
            stmt.setString(1, user.getUserEmailUuid());
            stmt.setString(2, user.getUserEmail());
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getUserPassword());
            stmt.setString(5, user.getUserType());
            rs = stmt.executeQuery();
            rs.next();
            if(rs.getInt(1) != 1){
                return false;
            }
            return true;
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_USER_CHECK_CREDENTIAL_FAILED.toString());
            return false;
        }
    }

    private static void checkInputData(Users user){

    }

    public static String emailToUuid(String input){
        return UUID.nameUUIDFromBytes(input.getBytes(StandardCharsets.UTF_8)).toString();
    }

}
