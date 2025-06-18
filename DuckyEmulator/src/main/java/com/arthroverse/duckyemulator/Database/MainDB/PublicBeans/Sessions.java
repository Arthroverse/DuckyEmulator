package com.arthroverse.duckyemulator.Database.MainDB.PublicBeans;

import com.arthroverse.duckyemulator.Database.DBService.MySQLService;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Sessions {
    private StringProperty sessionId;
    private ObjectProperty<Integer> totalQuestions;
    private ObjectProperty<Boolean> status;
    private ObjectProperty<Integer> totalCorrectQuestions;
    private StringProperty timeTaken;
    private StringProperty percentage;
    private StringProperty startTime;
    private StringProperty endTime;
    private ArrayList<SessionInput> isAnsweredStatForNav = new ArrayList<>();

    private String candidateEmail;
    private long timeTakenInSecond;
    private TreeMap<Integer, Questions> selectedQuestions = new TreeMap<>();
    private TreeMap<Integer, Integer> questionNumAndIdPair = new TreeMap<>();
    private TreeMap<Integer, Integer> questionIdAndNumPair = new TreeMap<>();
    private TreeMap<Integer, SessionInput> userInput = new TreeMap<>();
    private ArrayList<SessionResult> testResult = new ArrayList<>();

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

    public StringProperty getPercentageProperty(){
        return this.percentage;
    }

    public StringProperty getStartTimeProperty(){
        return this.startTime;
    }

    public StringProperty getEndTimeProperty(){
        return this.endTime;
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

    public String getPercentage(){
        return this.percentage.get();
    }

    public String getStartTime(){
        return this.startTime.get();
    }

    public String getEndTime(){
        return this.endTime.get();
    }

    public String getCandidateEmail(){
        return this.candidateEmail;
    }

    public long getTimeTakenInSecond(){
        return this.timeTakenInSecond;
    }

    public ArrayList<SessionInput> getIsAnsweredStatForNav(){
        return this.isAnsweredStatForNav;
    }

    public TreeMap<Integer, Questions> getSelectedQuestions(){
        return this.selectedQuestions;
    }

    public TreeMap<Integer, Integer> getQuestionNumAndIdPair(){
        return this.questionNumAndIdPair;
    }

    public TreeMap<Integer, SessionInput> getUserInput(){
        return this.userInput;
    }

    public TreeMap<Integer, Integer> getQuestionIdAndNumPair(){
        return this.questionIdAndNumPair;
    }

    public ArrayList<SessionResult> getTestResult(){
        return this.testResult;
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
        this.percentage = timeLimit;
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

    public void setPercentage(String percentage){
        this.percentage.set(percentage);
    }

    public void setStartTime(String startTime){
        this.startTime.set(startTime);
    }

    public void setEndTime(String endTime){
        this.endTime.set(endTime);
    }

    public void setCandidateEmail(String candidateEmail){
        this.candidateEmail = candidateEmail;
    }

    public void setTimeTakenInSecond(long timeTakenInSecond){
        this.timeTakenInSecond = timeTakenInSecond;
    }

    public static void setCurrentSession(Sessions currentSession){
        Sessions.currentSession = currentSession;
    }

    public Sessions(){
        this.sessionId = new SimpleStringProperty();
        this.totalQuestions = new SimpleObjectProperty<>();
        this.status = new SimpleObjectProperty<>();
        this.totalCorrectQuestions = new SimpleObjectProperty<>();
        this.timeTaken = new SimpleStringProperty();
        this.percentage = new SimpleStringProperty();
        this.startTime = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();

        this.timeTakenInSecond = 0;
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
            s.questionIdAndNumPair.put(i, questionNum);
            questionNum++;
        }

        //Automatically set all question status as unanswered by default
        String unansweredForNav = "❗";
        for(Integer i: s.questionNumAndIdPair.keySet()){
            SessionInput si = new SessionInput(i, unansweredForNav);
            s.isAnsweredStatForNav.add(si);
            s.userInput.put(i, si);
        }

        String sqlQuery = "INSERT INTO Sessions(SessionId, UserEmail) " +
                "VALUES(?, ?);";
        String sqlQueryInsertSessionQIdPair = "INSERT INTO Session_has_question(SessionId, QuestionId) " +
                "VALUES(?, ?)";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(sqlQuery);
                PreparedStatement stmt2 = conn.prepareStatement(sqlQueryInsertSessionQIdPair);
                ){
            stmt1.setString(1, s.getSessionId());
            stmt1.setString(2, s.getCandidateEmail());
            stmt1.executeUpdate();

            for(Integer i: s.selectedQuestions.keySet()) {
                stmt2.setString(1, s.getSessionId());
                stmt2.setInt(2, i);
                stmt2.executeUpdate();
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_SESSION_INSERT_NEW_FAILED.toString());
        }
    }

    public static void markTest(Sessions s){
        String sqlMarkTest = "SELECT * FROM Session_has_question AS JSession " +
                "JOIN Questions AS Q ON JSession.QuestionId = Q.QuestionId WHERE SessionId = ?;";
        String sqlUpdateSession = "UPDATE Sessions SET ElapsedTime = ? WHERE SessionId = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(sqlMarkTest);
                PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateSession)
                ){
            s.totalQuestions.set(s.selectedQuestions.size());
            double correctQuestionsCount = 0;
            stmt1.setString(1, s.getSessionId());
            ResultSet rs = stmt1.executeQuery();
            while(rs.next()){
                Questions selectedQuestion = s.selectedQuestions.get(rs.getInt(2));
                int questionNum = s.questionIdAndNumPair.get(rs.getInt(2));
                SessionInput si = s.userInput.get(questionNum);
                boolean isCorrect = false;
                String isCorrectAsString = "❌";
                if(rs.getString("UserAnswer") != null
                    && rs.getString("UserAnswer").equals(rs.getString("CorrectAnswer"))){
                   isCorrect = true;
                   isCorrectAsString = "✅";
                   correctQuestionsCount++;
                }

                ArrayList<String> associatedTopicNames = new ArrayList<>() ;
                for(Topics t: Topics.findingTopics(selectedQuestion.getForeignKeyTopicId())){
                    associatedTopicNames.add(t.getTopicName());
                }
                String associatedTopicsAsString = associatedTopicNames.toString();
                associatedTopicsAsString = associatedTopicsAsString.substring(1, associatedTopicsAsString.length() - 1);
                s.testResult.add(new SessionResult(
                        si, selectedQuestion, isCorrect, associatedTopicsAsString, isCorrectAsString
                ));
            }
            s.totalCorrectQuestions.set((int)correctQuestionsCount);
            s.percentage.set(Double.toString(correctQuestionsCount / s.selectedQuestions.size() * 100));

            stmt2.setLong(1, s.timeTakenInSecond);
            stmt2.setString(2, s.getSessionId());
            stmt2.executeUpdate();
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "DuckyEmulator");
        }
    }

    public static void saveUserAnswerToDb(Sessions s){
        String sqlQueryAddInputToJoinTable = "UPDATE Session_has_question SET UserAnswer = ? WHERE SessionId = ? AND QuestionId = ?";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQueryAddInputToJoinTable);
        ){
            for(int i = 1; i <= s.getQuestionNumAndIdPair().size(); i++){
                stmt.setString(1, s.getUserInput().get(i).getUserAnswer());
                stmt.setString(2, s.getSessionId());
                stmt.setInt(3, s.getQuestionNumAndIdPair().get(i));
                stmt.executeUpdate();
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "DuckyEmulator");
        }
    }


}
