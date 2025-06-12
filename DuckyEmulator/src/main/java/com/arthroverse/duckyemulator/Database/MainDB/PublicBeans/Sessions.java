package com.arthroverse.duckyemulator.Database.MainDB.PublicBeans;

import com.arthroverse.duckyemulator.Database.DBService.MySQLService;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Sessions {
    private StringProperty sessionId;
    private ObjectProperty<Integer> totalQuestions;
    private ObjectProperty<Boolean> status;
    private ObjectProperty<Integer> totalCorrectQuestions;
    private StringProperty timeTaken;
    private StringProperty timeLimit;

    private String candidateEmail;
    private TreeMap<Integer, Questions> selectedQuestions = new TreeMap<>();
    private TreeMap<Integer, Integer> questionNumAndIdPair = new TreeMap<>();

    //keep track of current Session running
    private static Sessions currentSession;

    public StringProperty getSessionIdProperty(){
        return this.sessionId;
    }

    public ObjectProperty<Integer> getTotalQuestionsProperty(){
        return this.totalQuestions;
    }

    public ObjectProperty<Boolean> getStatusProperty(){
        return this.status;
    }

    public ObjectProperty<Integer> getTotalCorrectQuestionsProperty(){
        return this.totalCorrectQuestions;
    }

    public StringProperty getTimeTakenProperty(){
        return this.timeTaken;
    }

    public StringProperty getTimeLimitProperty(){
        return this.timeLimit;
    }

    public String getSessionId(){
        return this.sessionId.get();
    }

    public Integer getTotalQuestions(){
        return this.totalQuestions.get();
    }

    public Boolean getStatus(){
        return this.status.get();
    }

    public Integer getTotalCorrectQuestions(){
        return this.totalCorrectQuestions.get();
    }

    public String getTimeTaken(){
        return this.timeTaken.get();
    }

    public String getTimeLimit(){
        return this.timeLimit.get();
    }

    public String getCandidateEmail(){
        return this.candidateEmail;
    }

    public TreeMap<Integer, Questions> getSelectedQuestions(){
        return this.selectedQuestions;
    }

    public TreeMap<Integer, Integer> getQuestionNumAndIdPair(){
        return this.questionNumAndIdPair;
    }

    public static Sessions getCurrentSession(){
        return currentSession;
    }

    public void setSessionIdProperty(StringProperty sessionId){
        this.sessionId = sessionId;
    }

    public void setTotalQuestionsProperty(ObjectProperty<Integer> totalQuestions){
        this.totalQuestions = totalQuestions;
    }

    public void setStatusProperty(ObjectProperty<Boolean> status){
        this.status = status;
    }

    public void setTotalCorrectQuestionsProperty(ObjectProperty<Integer> totalCorrectQuestions){
        this.totalCorrectQuestions = totalCorrectQuestions;
    }

    public void setTimeTakenProperty(StringProperty timeTaken){
        this.timeTaken = timeTaken;
    }

    public void setTimeLimitProperty(StringProperty timeLimit){
        this.timeLimit = timeLimit;
    }

    public void setSessionId(String sessionId){
        this.sessionId.set(sessionId);
    }

    public void setTotalQuestions(Integer totalQuestions){
        this.totalQuestions.set(totalQuestions);
    }

    public void setStatus(Boolean status){
        this.status.set(status);
    }

    public void setTotalCorrectQuestions(Integer totalCorrectQuestions){
        this.totalCorrectQuestions.set(totalCorrectQuestions);
    }

    public void setTimeTaken(String timeTaken){
        this.timeTaken.set(timeTaken);
    }

    public void setTimeLimit(String timeLimit){
        this.timeLimit.set(timeLimit);
    }

    public void setCandidateEmail(String candidateEmail){
        this.candidateEmail = candidateEmail;
    }

    public static void setCurrentSession(Sessions currentSession){
        Sessions.currentSession = currentSession;
    }

    public Sessions(){
        sessionId = new SimpleStringProperty();
        totalQuestions = new SimpleObjectProperty<>();
        status = new SimpleObjectProperty<>();
        totalCorrectQuestions = new SimpleObjectProperty<>();
        timeTaken = new SimpleStringProperty();
        timeLimit = new SimpleStringProperty();
    }

    public static void insert(Sessions s){
        s.setCandidateEmail(Users.getCurrentUserEmailInActiveSession());
        int totalQuestionsInSession = (int)(Math.random() * 11 + 10); //generate around 10-20 questions at once, inclusive
        s.setTotalQuestions(totalQuestionsInSession);
        ArrayList<Integer> selectedQuestionIds = new ArrayList<>();
        for(int i = 0; i < totalQuestionsInSession; i++){
            int index = (int)(Math.random() * Questions.getAllQuestionIds().size());
            selectedQuestionIds.add(Questions.getAllQuestionIds().get(index));
        }

        String selectedQuestionIdsList = selectedQuestionIds.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        Questions.querySelectedQuestionIds(s.selectedQuestions, selectedQuestionIdsList);

        s.totalQuestions.set(s.selectedQuestions.size());

        //process queried data for frontend (setup question number associated with each questionId)
        int questionNum = 1;
        for(Integer i: s.selectedQuestions.keySet()){
            s.questionNumAndIdPair.put(questionNum, i);
            questionNum++;
        }

        String sqlQuery = "INSERT INTO Sessions(SessionId, UserEmail) " +
                "VALUES(?, ?);";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                ){
            stmt.setString(1, s.getSessionId());
            stmt.setString(2, s.getCandidateEmail());
            stmt.executeUpdate();
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_SESSION_INSERT_NEW_FAILED.toString());
        }
    }
}
