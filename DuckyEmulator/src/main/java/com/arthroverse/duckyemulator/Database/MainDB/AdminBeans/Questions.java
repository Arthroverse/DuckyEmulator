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
package com.arthroverse.duckyemulator.Database.MainDB.AdminBeans;

import com.arthroverse.duckyemulator.Database.DBService.MySQLService;
import com.arthroverse.duckyemulator.Database.MainDB.CredentialBeans.Users;
import com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.QBankIndexUIController;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.FileHandler.FileHandler;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A class serves as a "Bean", meaning results returned from a table from a MySQL Database
 * <p>
 * This class contains all the necessary fields and methods to store the returned data
 * from a single Question table in the MySQL database (QuestionId, Choices, Correct answer,...)
 * and process the returned data (CRUD), meaning CREATE - READ - UPDATE - DELETE
 * @author ducksabervn
 * @since 2025-5-25
 * @version 0.1
 */
public class Questions{

    //============================================================================
    // FIELD DECLARATION SECTION
    //============================================================================


    //This field is used to store a Foreign Key that links to a classificationId in the Classification table
    private Integer foreignKeyClassificationId;

    /**
     * This is used to store all IDs of all topics associated with a question
     * <p>
     * This {@code ArrayList} will always be updated after each call to
     * the {@code select()} method, where all data associated with a question
     * will be refreshed
     */
    private ArrayList<Integer> foreignKeyTopicId;

    /**
     * This is used to compare with the newly updated {@code foreignKeyTopicId}
     * above, so that the system will detect any changes to the topic associated
     * with the question => Use it to remove and add a new pair of QuestionId - TopicId
     * to the QTRelationship table (n-n relationship)
     *
     * <p>
     * The initial value of this {@code ArrayList} is the same as the {@code foreignKeyTopicId}.
     * This is because after an update of a question, the newly updated data will be stored and
     * considered as a new original data => will be further used as a reference for a new update
     */
    private ArrayList<Integer> oldForeignKeyTopicId;

    //Each question is associated with a specific ID
    private Integer questionId;

    //The arrayList containing all existing questionIds
    private static ArrayList<Integer> allQuestionIds = new ArrayList<>();

    //These are used for displaying and storing the question data
    private StringProperty questionClassification;
    private StringProperty questionStatement;
    private StringProperty choice1;
    private StringProperty choice2;
    private StringProperty choice3;
    private StringProperty choice4;
    private StringProperty correctAnswer;
    private StringProperty imagePath;




    //============================================================================
    // CONSTRUCTOR
    //============================================================================

    public Questions() {
        foreignKeyClassificationId = 0;
        foreignKeyTopicId = new ArrayList<>();
        oldForeignKeyTopicId = new ArrayList<>();
        questionId = 0;
        questionClassification = new SimpleStringProperty();
        questionStatement = new SimpleStringProperty();
        choice1 = new SimpleStringProperty();
        choice2 = new SimpleStringProperty();
        choice3 = new SimpleStringProperty();
        choice4 = new SimpleStringProperty();
        correctAnswer = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
    }




    //============================================================================
    // GETTER AND ACCESSOR
    //============================================================================


    //----------------------------------------------------------------------------
    // GETTER
    //----------------------------------------------------------------------------
    public Integer getForeignKeyClassificationId() {
        return this.foreignKeyClassificationId;
    }

    public Integer getQuestionId() {
        return this.questionId;
    }

    public ArrayList<Integer> getForeignKeyTopicId() {
        return this.foreignKeyTopicId;
    }

    public ArrayList<Integer> getOldForeignKeyTopicId(){
        return this.oldForeignKeyTopicId;
    }

    public StringProperty getQuestionClassificationProperty(){
        return this.questionClassification;
    }

    public StringProperty getQuestionStatementProperty() {
        return this.questionStatement;
    }

    public StringProperty getChoice1Property() {
        return this.choice1;
    }

    public StringProperty getChoice2Property() {
        return this.choice2;
    }

    public StringProperty getChoice3Property() {
        return this.choice3;
    }

    public StringProperty getChoice4Property() {
        return this.choice4;
    }

    public StringProperty getCorrectAnswerProperty() {
        return this.correctAnswer;
    }

    public StringProperty getImagePathProperty() {
        return this.imagePath;
    }

    public String getChoice1() {
        return this.choice1.get();
    }

    public String getChoice2() {
        return this.choice2.get();
    }

    public String getChoice3() {
        return this.choice3.get();
    }

    public String getChoice4() {
        return this.choice4.get();
    }

    public String getCorrectAnswer() {
        return this.correctAnswer.get();
    }

    public String getQuestionStatement() {
        return this.questionStatement.get();
    }

    public String getImagePath() {
        return this.imagePath.get();
    }

    public static ArrayList<Integer> getAllQuestionIds(){
        return allQuestionIds;
    }




    //----------------------------------------------------------------------------
    // SETTER
    //----------------------------------------------------------------------------
    public void setForeignKeyClassificationId(int id) {
        this.foreignKeyClassificationId = id;
    }

    public void setQuestionClassification(String clazz){
        this.questionClassification.set(clazz);
    }

    public void setForeignKeyTopicId(ArrayList<Integer> foreignKeyTopicId) {
        this.foreignKeyTopicId = new ArrayList<>(foreignKeyTopicId);
    }

    public void addIndividualForeignKeyTopicId(Integer i){
        this.foreignKeyTopicId.add(i);
        /*
        * Each time a question gets updated, its updated data will be stored
        * and considered as "old". This makes sure that any further updates,
        * the question will be compared to its old data
        * */
        this.setIndividualOldForeignKeyTopicId(i);
    }

    public void setIndividualOldForeignKeyTopicId(Integer i){
        this.oldForeignKeyTopicId.add(i);
    }

    public void setQuestionId(int id) {
        this.questionId = id;
    }

    public void setQuestionStatement(String stmt) {
        this.questionStatement.set(stmt);
    }

    public void setChoice1(String c) {
        this.choice1.set(c);
    }

    public void setChoice2(String c) {
        this.choice2.set(c);
    }

    public void setChoice3(String c) {
        this.choice3.set(c);
    }

    public void setChoice4(String c) {
        this.choice4.set(c);
    }

    public void setCorrectAnswer(String correctAns) {
        this.correctAnswer.set(correctAns);
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public static void setAllQuestionIds(ArrayList<Integer> allQuestionIds){
        allQuestionIds = allQuestionIds;
    }




    //============================================================================
    // CRUD SECTION
    //============================================================================




    //----------------------------------------------------------------------------
    // SELECT
    //----------------------------------------------------------------------------

    /**
     * This method acts merely as a {@code SELECT} query in MySQL database
     * <p>
     * This method will select 10 questions in a row to display in the emulator. This
     * makes sure that the user doesn't have to wait for the database to load entirely
     * the Question table at once, as it will be very time-consuming. (Imagine wtf will
     * happen if we load a table with >= 1 billion rows at once?
     *
     * @param offset
     * @return {@code ObservableList<Questions>}
     */
    public static ObservableList<Questions> select(int offset) {
        /*
        * This is a simple yet powerful MySQL query. This will select 10 question IDs
        * in the Question table at once (which you can see in the LIMIT = 10 value),
        * where each question isn't "soft" deleted yet,
        * by the "Question bank admin" marked by a BOOLEAN variable setting at false (0) as
        * default value.
        *
        * The OFFSET value acts as a limit of the query. For example, I want to select 10 questions
        * from ID 1 -> ID 10 => The OFFSET will be 0, if I want to select 10 questions from ID 11 -> ID 20,
        * OFFSET will be 10*/
        String qIdOffset = "SELECT QuestionId " +
                "FROM Questions " +
                "WHERE Deleted = 0 " +
                "ORDER BY QuestionId " +
                "LIMIT 10 OFFSET ? ";
        ArrayList<Integer> qIdList = new ArrayList<>(); //An arraylist to store all returned question IDs
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(qIdOffset);
                ){
            stmt.setInt(1, offset);
            ResultSet rs = stmt.executeQuery();
            /*
            * ResultSet after any execution will return a loopable table => rs.next() will
            * do its job, which verifies whether the table still has any rows left. If it does,
            * then we add returned questionId to the arraylist
            * */
            while(rs.next()){
                qIdList.add(rs.getInt(1)); //add the data in the first column into the arraylist
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_SELECT_QID_FAILED.toString());
        }

        /*
        * To insert the returned arraylist as a parameter to a prepared statement in MySQL, we have
        * to process it properly by removing the first and last brackets from the returned string
        * from the toString() method in ArrayList class
        *
        * I actually don't have a clue what this is since I copied this directly from
        * StackOverFlow (but it gets the job done, so idgaf)
        *
        * ts pmo icl */
        String qIdListString = qIdList.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        /*
        * This ObservableMap object is used to store all returned "Questions" objects and displays them to the emulator
        * window. The core of this ObservableMap is a TreeMap, we use TreeMap because our system will
        * query each question through its ID*/
        ObservableMap<Integer, Questions> questions = FXCollections.observableMap(new TreeMap<>());

        if(!qIdListString.isEmpty()){ /*
                                        If the questionId list string is empty => no available questions,
                                        otherwise, continue*/

            /*
            * To retrieve all necessary data for a single question (statement, choices, answer, topics),
            * I have to join all tables into a single table and begin extracting their content based on each
            * column name.
            *
            * In this case, to prevent NULL data from happening, I have implemented a full JOIN query to make
            * sure that data from both tables exists.
            *
            * The WHERE and IN queries are used for further filtration of data, where the ID lies in the range
            * inside the bracket (passed in a question id list string processed above)*/
            String sqlQuery = "SELECT Q.*, T.TopicId, T.TopicName, T.Description AS Topic_Description, " +
                    "C.Classification, C.Description AS Classification_Description " +
                    "FROM Questions AS Q " +
                    "JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId " +
                    "JOIN Topics AS T ON T.TopicId = QT.TopicId " +
                    "JOIN Classifications AS C ON C.ClassificationId = Q.ClassificationId " +
                    "WHERE Q.QuestionId IN ( " + qIdListString + " )" +
                    "ORDER BY Q.QuestionId; ";

            try(
                    Connection conn = MySQLService.getConnection();
                    Statement stmt = conn.createStatement();
                    //The result of the execution will be a table that will then be looped for further processing
                    ResultSet rs = stmt.executeQuery(sqlQuery);
            ) {
                Questions quest;
                while(rs.next()){
                    //all of the code down here is merely collecting data from the table based on the column name
                    int qId = rs.getInt("QuestionId");
                    int cId = rs.getInt("ClassificationId");
                    int tId = rs.getInt("TopicId");
                    String qStmt = rs.getString("QuestionStatement");
                    String ch1 = rs.getString("Choice1");
                    String ch2 = rs.getString("Choice2");
                    String ch3 = rs.getString("Choice3");
                    String ch4 = rs.getString("Choice4");
                    String ans = rs.getString("CorrectAnswer");
                    String imgPath = rs.getString("ImagePath");

                    /*
                    * If the question has already been presented in the main Questions' TreeMap, continue to
                    * add the question's topicId to its topicId arraylist field until the condition becomes
                    * false.*/
                    if(questions.containsKey(rs.getInt(1))){
                        quest = questions.get(rs.getInt(1));
                        quest.addIndividualForeignKeyTopicId(rs.getInt("TopicId"));
                    }
                    /*
                    * If the condition becomes false, we have to add a new question, as this means that the question
                    * is not present in the TreeMap. Therefore, calling the method to make a new Question object.
                    * It also has a TopicId parameter because the first row of a new question in the returned
                    * ResultSet will contain all data in a question object (including the first topicId)*/
                    else{
                        quest = makeQuestion(qId,
                                cId,
                                tId,
                                qStmt,
                                ch1,
                                ch2,
                                ch3,
                                ch4,
                                ans,
                                imgPath);
                        questions.put(rs.getInt(1), quest); //then add the newly created question object to the TreeMap
                    }
                }
            }catch(Exception e){
                AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                        ErrorTitle.SQL_QUEST_PARTIAL_SELECT_FAILED.toString());
            }
        }
        return FXCollections.observableArrayList(questions.values());
    }

    /**
     * This method merely creates and returns a "Questions" object
     *
     * @param qId
     * @param cId
     * @param tId
     * @param qStmt
     * @param ch1
     * @param ch2
     * @param ch3
     * @param ch4
     * @param ans
     * @param imgPath
     * @return {@code Questions}
     */
    private static Questions makeQuestion(int qId,
                                         int cId,
                                         int tId,
                                         String qStmt,
                                         String ch1,
                                         String ch2,
                                         String ch3,
                                         String ch4,
                                         String ans,
                                         String imgPath) {
        try {
            Questions quest = new Questions();
            quest.setQuestionId(qId);
            quest.setForeignKeyClassificationId(cId);
            quest.setQuestionClassification(
                    Classifications.searchClassification(cId)
            );
            quest.addIndividualForeignKeyTopicId(tId);
            quest.setQuestionStatement(qStmt);
            quest.setChoice1(ch1);
            quest.setChoice2(ch2);
            quest.setChoice3(ch3);
            quest.setChoice4(ch4);
            quest.setCorrectAnswer(ans);
            imgPath = imgPath == null ? "" : imgPath;
            quest.setImagePath(FileHandler.addNewImage(imgPath, qId));
            return quest;
        } catch (Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_ADD_QUESTION_FAILED.toString());
            return null;
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
                ResultSet rs = stmt.executeQuery("SELECT COUNT(QuestionId) FROM Questions WHERE Deleted = 0");
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
            QBankIndexUIController.setMaxPageNum((int)Math.ceil(maxNumPage/10.0));

        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_SET_PAGE_FAILED.toString());
        }
    }

    public static void selectAll(){
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT QuestionId FROM Questions WHERE Deleted = 0");
                ){
            while(rs.next()){
                allQuestionIds.add(rs.getInt(1));
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_INIT_FAILED.toString());
        }
    }

    public static void querySelectedQuestionIds(TreeMap<Integer, Questions> qMap, String selectedIds){
        String sqlQuery = "SELECT Q.*, T.TopicId, T.TopicName, T.Description AS Topic_Description, " +
                "C.Classification, C.Description AS Classification_Description " +
                "FROM Questions AS Q " +
                "JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId " +
                "JOIN Topics AS T ON T.TopicId = QT.TopicId " +
                "JOIN Classifications AS C ON C.ClassificationId = Q.ClassificationId " +
                "WHERE Q.QuestionId IN ( " + selectedIds + " )" +
                "ORDER BY Q.QuestionId; ";

        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                //The result of the execution will be a table that will then be looped for further processing
                ResultSet rs = stmt.executeQuery(sqlQuery);
        ) {
            Questions quest;
            while(rs.next()){
                //all of the code down here is merely collecting data from the table based on the column name
                int qId = rs.getInt("QuestionId");
                int cId = rs.getInt("ClassificationId");
                int tId = rs.getInt("TopicId");
                String qStmt = rs.getString("QuestionStatement");
                String ch1 = rs.getString("Choice1");
                String ch2 = rs.getString("Choice2");
                String ch3 = rs.getString("Choice3");
                String ch4 = rs.getString("Choice4");
                String ans = rs.getString("CorrectAnswer");
                String imgPath = rs.getString("ImagePath");

                /*
                 * If the question has already been presented in the main Questions' TreeMap, continue to
                 * add the question's topicId to its topicId arraylist field until the condition becomes
                 * false.*/
                if(qMap.containsKey(rs.getInt(1))){
                    quest = qMap.get(rs.getInt(1));
                    quest.addIndividualForeignKeyTopicId(rs.getInt("TopicId"));
                }
                /*
                 * If the condition becomes false, we have to add a new question, as this means that the question
                 * is not present in the TreeMap. Therefore, calling the method to make a new Question object.
                 * It also has a TopicId parameter because the first row of a new question in the returned
                 * ResultSet will contain all data in a question object (including the first topicId)*/
                else{
                    quest = makeQuestion(qId,
                            cId,
                            tId,
                            qStmt,
                            ch1,
                            ch2,
                            ch3,
                            ch4,
                            ans,
                            imgPath);
                    qMap.put(rs.getInt(1), quest); //then add the newly created question object to the TreeMap
                }
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_PARTIAL_SELECT_FAILED.toString());
        }
    }




    //----------------------------------------------------------------------------
    // DELETE
    //----------------------------------------------------------------------------

    /**
     * This method acts as the {@code DELETE} query in MySQL
     *
     * This method will delete a question from the database
     * @param quest
     */
    public static void delete(Questions quest){
        String deletedDate = LocalDateTime.now().toString(); //Get deletion date for audit and data compliance
        String deletedBy = Users.getCurrentUserEmailInActiveSession(); //Get actor for audit and data compliance

        String questionUpdateDeleteStatus = "UPDATE Questions SET Deleted = 1, DeletedBy = ?, DeletedAt = ? " +
                "WHERE QuestionId = ?;"; //Soft delete
        String qtRelationshipUpdateDeleteStatus = "UPDATE QTRelationship SET Deleted = 1, DeletedBy = ?, DeletedAt = ? " +
                "WHERE QuestionId = ?;"; //Soft delete
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt1 = conn.prepareStatement(questionUpdateDeleteStatus);
                PreparedStatement stmt2 = conn.prepareStatement(qtRelationshipUpdateDeleteStatus);
                ){
            stmt1.setString(1, deletedBy);
            stmt1.setString(2, deletedDate);
            stmt1.setInt(3, quest.getQuestionId());
            stmt1.executeUpdate();

            stmt2.setString(1, deletedBy);
            stmt2.setString(2, deletedDate);
            stmt2.setInt(3, quest.getQuestionId());
            stmt2.executeUpdate();

            /*
            * If there is no image path present in the question, do nothing; otherwise, move the
            * image from the main source folder to the disposal folder and wait for further
            * processing*/
            if(!quest.getImagePath().equals("")){
                FileHandler.moveDeletedImageToDisposal(quest.getImagePath());
                FileHandler.removeImage(quest.getImagePath());
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_DELETE_FAILED.toString());
        }
    }




    //----------------------------------------------------------------------------
    // INSERT
    //----------------------------------------------------------------------------

    /**
     * This method acts as the {@code INSERT} query in MySQL
     *
     * This method will add a brand new question to the database
     * @param quest
     */
    public static void insert(Questions quest) {
        String sqlInsertQuestions = "INSERT INTO Questions(ClassificationId, " +
                "QuestionStatement, CorrectAnswer, Choice1, Choice2, Choice3, Choice4, ImagePath, Deleted) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, 0);";
        String sqlImgUpdateQuery = "UPDATE Questions SET ImagePath = ? WHERE QuestionId = ?";
        ResultSet key = null;
        try (
                Connection conn = MySQLService.getConnection();
                /*
                * The part Statement.RETURN_GENERATED_KEY means that each time executed, the query will return
                * a key acting as the new questionId of the newly added question. Without this, the new question
                * won't have an ID considering this as a bug*/
                PreparedStatement stmt1 = conn.prepareStatement(sqlInsertQuestions, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmt2 = conn.prepareStatement(sqlImgUpdateQuery);
        ) {
            stmt1.setInt(1, quest.getForeignKeyClassificationId());
            stmt1.setString(2, quest.getQuestionStatement());
            stmt1.setString(3, quest.getCorrectAnswer());
            stmt1.setString(4, quest.getChoice1());
            stmt1.setString(5, quest.getChoice2());
            stmt1.setString(6, quest.getChoice3());
            stmt1.setString(7, quest.getChoice4());
            stmt1.setString(8, quest.getImagePath());
            if (stmt1.executeUpdate() == 1) {
                key = stmt1.getGeneratedKeys(); //The generated questionid will be returned as a table in ResultSet
                key.next(); //move the pointer to the row containing the key
                int questId = key.getInt(1);
                quest.setQuestionId(questId);
            }
            String newImagePath = FileHandler.
                    addNewImage(quest.getImagePath(), quest.getQuestionId());
            quest.setImagePath(newImagePath);
            stmt2.setString(1,newImagePath);
            stmt2.setInt(2, quest.getQuestionId());
            stmt2.executeUpdate();
        } catch (Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_INSERTION_FAILED.toString());
        }

        /*
        * A question is associated with multiple topics => these topics are managed in a join table
        * called QTRelationship => to properly add the question to the database, this join table
        * must be properly updated with new data for this question => a loop will be used to
        * continuously add a pair of values to the join table*/
        String sqlInsertQtRelationship = "INSERT INTO QTRelationship(QuestionId, TopicId, Deleted) "
                + "VALUES(?, ?, 0);";
        for(Integer i: quest.getForeignKeyTopicId()){
            try(
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sqlInsertQtRelationship);
            ){
                stmt.setInt(1, quest.getQuestionId());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            }catch (Exception e){
                AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                        ErrorTitle.SQL_QUEST_QTRELATIONSHIP_INSERT_FAILED.toString());
            }
        }
    }




    //----------------------------------------------------------------------------
    // UPDATE
    //----------------------------------------------------------------------------

    /**
     * This method acts as the {@code UPDATE} query in MySQL
     *
     * This method will update the information contained in the selected question
     * @param q
     */
    public static void update(Questions q) {
        /*
        * Idgaf about touching grass, so I decided to implement soft delete in my code*/
        String deletedBy = Users.getCurrentUserEmailInActiveSession(); //Get actor email for audit and data compliance

        /*
        * This loop will compare the old foreign key of topicid and the new foreign key topic id.
        * If there are values in the old list that aren't present in the new list, meaning the topic
        * deleted => remove the pair of values out of the join table*/
        for(Integer i: q.getOldForeignKeyTopicId()){
            if(!(q.getForeignKeyTopicId().contains(i))){
                String deletedDate = LocalDateTime.now().toString(); //Put this in the loop since the deletion time is different
                String sqlQTRelationship = "UPDATE QTRelationship SET Deleted = 1, DeletedBy = ?, DeletedAt = ? " +
                        "WHERE QuestionId = ? AND TopicId = ?;";
                try (
                        Connection conn = MySQLService.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sqlQTRelationship);
                ) {
                    stmt.setString(1, deletedBy);
                    stmt.setString(2, deletedDate);
                    stmt.setInt(3, q.getQuestionId());
                    stmt.setInt(4, i);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                            ErrorTitle.SQL_QUEST_QTRELATIONSHIP_DELETE_FAILED.toString());
                }
            }
        }

        /*
        * This loop will compare the new foreign key topic id list with the old foreign key
        * topic id. If there are values in the new list that aren't present in the old list, meaning
        * that the question has new topics => add new pairs of values into the join table*/
        for(Integer i: q.getForeignKeyTopicId()){
            if(!(q.getOldForeignKeyTopicId().contains(i))){
                String sqlQtRelationship = "INSERT INTO QTRelationship (QuestionId, TopicId) VALUES(?, ?);";
                try(
                        Connection conn = MySQLService.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sqlQtRelationship);
                ){
                    stmt.setInt(1, q.getQuestionId());
                    stmt.setInt(2, i);
                    stmt.executeUpdate();
                }catch (Exception e){
                    AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                            ErrorTitle.SQL_QUEST_QTRELATIONSHIP_INSERT_FAILED.toString());
                }
            }
        }

        //update question information
        String sqlQuestionUpdate = "UPDATE Questions SET "
                + "ClassificationId = ?, QuestionStatement = ?, CorrectAnswer = ?, "
                + "Choice1 = ?, Choice2 = ?, Choice3 = ?, Choice4 = ?, ImagePath = ? "
                + "WHERE QuestionId = ?";
        try (
                Connection conn1 = MySQLService.getConnection();
                PreparedStatement stmt1 = conn1.prepareStatement(sqlQuestionUpdate);
        ) {
            stmt1.setInt(1, q.getForeignKeyClassificationId());
            stmt1.setString(2, q.getQuestionStatement());
            stmt1.setString(3, q.getCorrectAnswer());
            stmt1.setString(4, q.getChoice1());
            stmt1.setString(5, q.getChoice2());
            stmt1.setString(6, q.getChoice3());
            stmt1.setString(7, q.getChoice4());
            String newImagePath = FileHandler.addNewImage(q.getImagePath(), q.getQuestionId());
            q.setImagePath(newImagePath);
            stmt1.setString(8, q.getImagePath());
            stmt1.setInt(9, q.getQuestionId());
            stmt1.executeUpdate();
        } catch (Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_QUEST_UPDATE_FAILED.toString());
        }
    }
}