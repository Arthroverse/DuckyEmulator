package com.arthroverse.duckyemulator.Database.MainDB.PublicBeans;

import com.arthroverse.duckyemulator.Database.DBService.MySQLService;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Questions;
import com.arthroverse.duckyemulator.Database.MainDB.AdminBeans.Topics;
import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.UIControllers.PublicUIController.UserSessionHistoriesPageUIController;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Sessions {
    private StringProperty sessionId;
    private ObjectProperty<Integer> totalQuestions;
    private ObjectProperty<Integer> totalCorrectQuestions;
    private StringProperty timeTaken;
    private StringProperty percentage;
    private StringProperty startTime;
    private StringProperty endTime;
    private ArrayList<SessionInput> isAnsweredStatForNav = new ArrayList<>();

    private String candidateEmail;
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

    public static void setCurrentSession(Sessions currentSession){
        Sessions.currentSession = currentSession;
    }

    public Sessions(){
        this.sessionId = new SimpleStringProperty();
        this.totalQuestions = new SimpleObjectProperty<>();
        this.totalCorrectQuestions = new SimpleObjectProperty<>();
        this.timeTaken = new SimpleStringProperty();
        this.percentage = new SimpleStringProperty();
        this.startTime = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();
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
                "JOIN Questions AS Q ON JSession.QuestionId = Q.QuestionId WHERE SessionId = ? AND JSession.Deleted = 0;";
        String sqlUpdateSession = "UPDATE Sessions SET TotalQuestions = ?, StartTime = ?," +
                " EndTime = ?, TotalCorrectQuestions = ? WHERE SessionId = ?;";
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

            stmt2.setLong(1, s.totalQuestions.get());
            stmt2.setString(2, s.getStartTime());
            stmt2.setString(3, s.getEndTime());
            stmt2.setDouble(4, correctQuestionsCount);
            stmt2.setString(5, s.getSessionId());
            stmt2.executeUpdate();
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "DuckyEmulator");
        }
    }

    public static void saveUserAnswerToDb(Sessions s){
        String sqlQueryAddInputToJoinTable = "UPDATE Session_has_question SET UserAnswer = ? WHERE SessionId = ? AND QuestionId = ?;";
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

    /**
     * This method is used to set up the number of total pages available in the emulator window
     * <p>
     * Each time a question is added to or deleted from the database, the emulator window will be
     * re-invoked invoking this method => Ensuring a real-time update to the data for better UX
     */
    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                //This query will count the total questions available in the database, not deleted yet.
                ResultSet rs = stmt.executeQuery("SELECT COUNT(SessionId) FROM Sessions WHERE Deleted = 0");
        ){
            /*
             * The current cursor is pointing to nothing, and the table only has 1 column and 1 row =>
             * A call to rs.next() will make it point to the row => retrieving the data from there*/
            rs.next();
            int maxNumPage = rs.getInt(1);

            //extreme case: if there are no questions in the database => we still initialize the table to a single page table
            if(rs.getInt(1) == 0) maxNumPage = 1;

            /*
             * Here is how the algorithm works:
             *
             * Let's say we have 48 questions, each page is capable of holding 10 questions => how
             * many pages are needed to hold all 48 questions?
             *
             * The answer is 5 => The number of pages will depend on the number of questions available in
             * the database. If the number of questions is DIVISIBLE by 10 => the number of pages is
             *
             * TOTAL QUESTIONS / 10
             *
             * If it is not divisible by 10 => increment the number of pages by 1
             *
             * This algorithm works like the ceiling function in Mathematics
             * Wikipedia: https://en.wikipedia.org/wiki/Floor_and_ceiling_functions*/
            UserSessionHistoriesPageUIController.setMaxPageNum((int)Math.ceil(maxNumPage/10.0));

        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_SET_PAGE_FAILED.toString());
        }
    }

    public static ArrayList<Sessions> selectSessions(int offset){
        ArrayList<Sessions> someSessions = new ArrayList<>();
        String sqlSelectAllSessions = "SELECT * FROM Sessions WHERE Deleted = 0 " +
                "ORDER BY SessionId LIMIT 10 OFFSET ? ";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(sqlSelectAllSessions);
                ){
            stmt1.setInt(1, offset);
            ResultSet rs1 = stmt1.executeQuery();
            while(rs1.next()){
                Sessions sess = new Sessions();
                sess.setSessionId(rs1.getString(1));
                sess.setCandidateEmail(rs1.getString(2));
                sess.setStartTime(rs1.getString(3));
                sess.setEndTime(rs1.getString(4));
                DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                Duration duration = Duration.between(LocalDateTime.parse(sess.startTime.get(), dtf1),
                        LocalDateTime.parse(sess.endTime.get(), dtf1));
                LocalTime lt = LocalTime.of(duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                sess.setTimeTaken(lt.format(dtf2));
                double totalQuestions = rs1.getInt(5);
                double totalCorrectQuestions = rs1.getInt(6);
                sess.setTotalQuestions((int)totalQuestions);
                sess.setTotalCorrectQuestions((int)totalCorrectQuestions);
                sess.setPercentage(Double.toString(totalCorrectQuestions/totalQuestions * 100));
                someSessions.add(sess);
            }

        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "DuckyEmulator");
        }
        return someSessions;
    }

    //Since all sessions object in the table view in the history page is mostly incomplete (missing SessionInput TreeMap and SessionResult ArrayList), we have to
    //query the data by hand
    public static void selectASession(Sessions session){
        String sqlSelectSessionData = "SELECT * FROM Sessions AS JSessions " +
                "JOIN Session_has_question AS J ON J.SessionId = JSessions.SessionId " +
                "JOIN Questions AS Q ON Q.QuestionId = J.QuestionId " +
                "WHERE JSessions.SessionId = ? AND JSessions.Deleted = 0";

        String sqlRemarkTest = "SELECT * FROM Session_has_question AS JSession " +
                "JOIN Questions AS Q ON JSession.QuestionId = Q.QuestionId WHERE SessionId = ? AND JSession.Deleted = 0;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(sqlSelectSessionData);
                PreparedStatement stmt2 = conn.prepareStatement(sqlRemarkTest)
                ){
            stmt1.setString(1, session.sessionId.get());
            ResultSet rs1 = stmt1.executeQuery();
            ArrayList<Integer> allAssociatedQuestionIds = new ArrayList<>();
            while(rs1.next()){
                allAssociatedQuestionIds.add(rs1.getInt(11));
            }
            ArrayList<Questions> selectedQuestions = Questions.selectSomeQuestions(allAssociatedQuestionIds);
            stmt2.setString(1, session.sessionId.get());
            ResultSet rs2 = stmt2.executeQuery();
            int questionNum = 1;
            for(Questions q: selectedQuestions){
                session.questionIdAndNumPair.put(q.getQuestionId(), questionNum);
                session.questionNumAndIdPair.put(questionNum, q.getQuestionId());
                questionNum++;
            }

            int questionIndex = 0;
            while(rs2.next()){
                String isAnsweredAsString = "❗";
                boolean isAnswered = false;
                if(rs2.getString(3) != null){
                    isAnsweredAsString = "✅";
                    isAnswered = true;
                }
                SessionInput si = new SessionInput(session.questionIdAndNumPair.get(rs2.getInt(2)), isAnsweredAsString);
                si.setAnswered(isAnswered);
                si.setUserAnswer(rs2.getString(3));

                boolean isCorrect = false;
                String isCorrectAsString = "❌";
                if(rs2.getString(3) != null &&
                    rs2.getString(3).equals(rs2.getString(10))){
                    isCorrect = true;
                    isCorrectAsString = "✅";
                }

                ArrayList<String> associatedTopicNames = new ArrayList<>() ;
                for(Topics t: Topics.findingTopics(selectedQuestions.get(questionIndex).getForeignKeyTopicId())){
                    associatedTopicNames.add(t.getTopicName());
                }
                String associatedTopicsAsString = associatedTopicNames.toString();
                associatedTopicsAsString = associatedTopicsAsString.substring(1, associatedTopicsAsString.length() - 1);

                SessionResult sr = new SessionResult(si,
                        selectedQuestions.get(questionIndex), isCorrect, associatedTopicsAsString, isCorrectAsString);
                session.testResult.add(sr);
                questionIndex++;
            }
            Sessions.setCurrentSession(session);
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "DuckyEmulator");
        }
    }

    public static void delete(Sessions session){
        String sqlDeleteSession = "UPDATE Sessions SET Deleted = 1, DeletedBy = ?, DeletedAt = ? WHERE SessionId = ?;";
        String sqlDeleteSessionRelationship = "UPDATE Session_has_question SET Deleted = 1, DeletedBy = ?, DeletedAt = ? WHERE SessionId = ?;";
        try(
              Connection conn = MySQLService.getConnection();
              PreparedStatement stmt1 = conn.prepareStatement(sqlDeleteSession);
              PreparedStatement stmt2 = conn.prepareStatement(sqlDeleteSessionRelationship);
                ){
            stmt1.setString(1, Users.getCurrentUserEmailInActiveSession());
            stmt1.setString(2, LocalDateTime.now().toString());
            stmt1.setString(3, session.sessionId.get());
            stmt1.executeUpdate();

            stmt2.setString(1, Users.getCurrentUserEmailInActiveSession());
            stmt2.setString(2, LocalDateTime.now().toString());
            stmt2.setString(3, session.sessionId.get());
            stmt2.executeUpdate();
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    "DuckyEmulator");
        }
    }
}
