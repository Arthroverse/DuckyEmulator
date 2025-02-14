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
package Database.MainDB.Beans;

import Database.DBService.MySQLService;
import UIControllers.AdminUIsControllers.QBankIndexUIController;
import Utilities.PromptAlert.AlertUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Questions {
    private ObjectProperty<Integer> foreignKeyClassificationId;
    private StringProperty foreignKeyClassificationIdForDisplay;
    private ArrayList<Integer> foreignKeyTopicId;
    private ArrayList<Integer> oldForeignKeyTopicId;
    private ObjectProperty<Integer> questionId;
    private StringProperty questionStatement;
    private StringProperty choice1;
    private StringProperty choice2;
    private StringProperty choice3;
    private StringProperty choice4;
    private StringProperty correctAnswer;
    private StringProperty imagePath;

    public Questions() {
        foreignKeyClassificationId = new SimpleObjectProperty<Integer>(null);
        foreignKeyClassificationIdForDisplay = new SimpleStringProperty();
        foreignKeyTopicId = new ArrayList<>();
        oldForeignKeyTopicId = new ArrayList<>();
        questionId = new SimpleObjectProperty<Integer>(null);
        questionStatement = new SimpleStringProperty();
        choice1 = new SimpleStringProperty();
        choice2 = new SimpleStringProperty();
        choice3 = new SimpleStringProperty();
        choice4 = new SimpleStringProperty();
        correctAnswer = new SimpleStringProperty();
        imagePath = new SimpleStringProperty();
    }

    public Integer getForeignKeyClassificationId() {
        return this.foreignKeyClassificationId.get();
    }

    public String getForeignKeyClassificationIdForDisplay(){
        return this.foreignKeyClassificationIdForDisplay.get();
    }
  
    public ArrayList<Integer> getForeignKeyTopicId() {
        return this.foreignKeyTopicId;
    }

    public ArrayList<Integer> getOldForeignKeyTopicId(){
        return this.oldForeignKeyTopicId;
    }

    public Integer getQuestionId() {
        return this.questionId.get();
    }

    public String getQuestionStatement() {
        return this.questionStatement.get();
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

    public String getImagePath() {
        return this.imagePath.get();
    }

    public StringProperty getForeignKeyClassificationIdForDisplayProperty(){
        return this.foreignKeyClassificationIdForDisplay;
    }

    public ObjectProperty<Integer> getForeignKeyClassificationIdProperty() {
        return this.foreignKeyClassificationId;
    }

    public ObjectProperty<Integer> getQuestionIdProperty() {
        return this.questionId;
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

    public void setForeignKeyClassificationId(int id) {
        this.foreignKeyClassificationId.set(id);
    }

    public void setForeignKeyClassificationIdForDisplay(String fk){
        this.foreignKeyClassificationIdForDisplay.set(fk);
    }

    public void setForeignKeyTopicId(ArrayList<Integer> foreignKeyTopicId) {
        this.foreignKeyTopicId = new ArrayList<>(foreignKeyTopicId);
    }

    public void addIndividualForeignKeyTopicId(Integer i){
        this.foreignKeyTopicId.add(i);
        this.setIndividualOldForeignKeyTopicId(i);
    }

    public void setIndividualOldForeignKeyTopicId(Integer i){
        this.oldForeignKeyTopicId.add(i);
    }

    public void setOldForeignKeyTopicId(ArrayList<Integer> arr){
        this.oldForeignKeyTopicId = new ArrayList<>(arr);
    }
  
    public void setQuestionId(int id) {
        this.questionId.set(id);
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

    /**
     * This method will select 10 questions each time it get called by
     * using <pre>
     *     {@code LIMIT <int> OFFSET <int>}</pre>
     *     in MySQL. The old version
     * of this code use old heavily-based logical way (which use {@code boolean},
     * {@code for} loop, {@code int} variable to control the data loading. However,
     * that method is super inefficient and unnecessary complex, therefore, we
     * decided to use a BRAND NEW type of storage called {@code HashMap<K, V>}.
     * It introduces the key-value mechanism, which makes thing become extremely easier
     * to handle the data load control without depending on traditional variables.
     * <p>
     * If a new QuestionId doesn't present in the {@code HashMap<K, V>}, it will be added in
     * the {@code else} clause. We also fix the problem when the {@code ResultSet} reaches
     * the final line => which will cause {@code rs.next()} to return {@code false} =>
     * We fix this by just constantly changing the {@code oldForeignKeyTopicId} each time
     * a new Id is added. This is a reasonable fix because each time when a question is
     * either updated or added, this method will always be called => it will become both new
     * and old data*/
    public static ObservableList<Questions> select(int offset) {
        String qIdOffset = "SELECT QuestionId " +
                "FROM Questions " +
                "ORDER BY QuestionId " +
                "LIMIT 10 OFFSET ? ";
        ArrayList<Integer> qIdList = new ArrayList<>();
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(qIdOffset);
                ){
            stmt.setInt(1, offset);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                qIdList.add(rs.getInt(1));
            }
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question select qId operation failed");
        }

        String qIdListString = qIdList.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        ObservableMap<Integer, Questions> questions = FXCollections.observableMap(new HashMap<>());
        String sqlQuery = "SELECT Q.*, T.TopicId, T.TopicName, T.Description AS Topic_Description, " +
        "C.Classification, C.Description AS Classification_Description " +
        "FROM Questions AS Q " +
        "JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId " +
        "JOIN Topics AS T ON T.TopicId = QT.TopicId " +
        "JOIN Classifications AS C ON C.ClassificationId = Q.ClassificationId " +
        "WHERE Q.QuestionId IN ( " + qIdListString + ") " +
        "ORDER BY Q.QuestionId; ";
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQuery);
        ) {
            Questions quest;
            while(rs.next()){
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
                if(questions.containsKey(rs.getInt(1))){
                    quest = questions.get(rs.getInt(1));
                    quest.addIndividualForeignKeyTopicId(rs.getInt("TopicId"));
                }else{
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
                    questions.put(rs.getInt(1), quest);
                }
            }
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question partial select operation failed");
        }
        return FXCollections.observableArrayList(questions.values());
    }

    public static Questions makeQuestion(int qId,
                                         int cId,
                                         int tId,
                                         String qStmt,
                                         String ch1,
                                         String ch2,
                                         String ch3,
                                         String ch4,
                                         String ans,
                                         String imgPath){
        Questions quest = new Questions();
        quest.setQuestionId(qId);
        quest.setForeignKeyClassificationId(cId);
        quest.setForeignKeyClassificationIdForDisplay(
                Classifications.searchClassification(cId)
        );
        quest.addIndividualForeignKeyTopicId(tId);
        quest.setQuestionStatement(qStmt);
        quest.setChoice1(ch1);
        quest.setChoice2(ch2);
        quest.setChoice3(ch3);
        quest.setChoice4(ch4);
        quest.setCorrectAnswer(ans);
        quest.setImagePath(imgPath);
        return quest;
    }

    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(QuestionId) FROM Questions");
        ) {
            rs.next();
            int maxNumPage = rs.getInt(1);
            QBankIndexUIController.setMaxPageNum((int)Math.ceil(maxNumPage/10.0));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question pagination setPage operation failed");
        }
    }

    public static boolean delete(Questions quest) {
        String sqlQtRelationship = "DELETE FROM QTRelationship WHERE QuestionId = ?";
        try (
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQtRelationship);
        ) {
            stmt.setInt(1, quest.getQuestionId());
            stmt.executeUpdate();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question qtRelationship delete operation failed");
        }
        String sqlDel = "DELETE FROM Questions WHERE QuestionId = ?;";
        try (
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlDel);
        ) {
            stmt.setInt(1, quest.getQuestionId());
            boolean isDeleted = stmt.executeUpdate() == 1 ? true : false;
            if (isDeleted) return true;
            else return false;
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question delete operation failed");
            return false;
        }
    }

    public static void insert(Questions quest) {
        String sqlInsertQuestions = "INSERT INTO Questions(ClassificationId, " +
                "QuestionStatement, CorrectAnswer, Choice1, Choice2, Choice3, Choice4, ImagePath) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        ResultSet key = null;
        try (
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlInsertQuestions, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setInt(1, quest.getForeignKeyClassificationId());
            stmt.setString(2, quest.getQuestionStatement());
            stmt.setString(3, quest.getCorrectAnswer());
            stmt.setString(4, quest.getChoice1());
            stmt.setString(5, quest.getChoice2());
            stmt.setString(6, quest.getChoice3());
            stmt.setString(7, quest.getChoice4());
            stmt.setString(8, quest.getImagePath());
            if (stmt.executeUpdate() == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int questId = key.getInt(1);
                quest.setQuestionId(questId);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question insert operation failed");
        }
        String sqlInsertQtRelationship = "INSERT INTO QtRelationship(QuestionId, TopicId) "
                + "VALUES(?, ?);";
        for(Integer i: quest.getForeignKeyTopicId()){
            try (
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sqlInsertQtRelationship);
            ) {
                stmt.setInt(1, quest.getQuestionId());
                stmt.setInt(2, i);
                stmt.executeUpdate();
            } catch (Exception e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTraceAsString = sw.toString();
                AlertUtil.generateExceptionViewer(stackTraceAsString, "Question qtRelationship insert operation failed");
            }
        }
    }
    public static void update(Questions q) {
        for(Integer i: q.getOldForeignKeyTopicId()){
            if(!(q.getForeignKeyTopicId().contains(i))){
                String sqlQtRelationship = "DELETE FROM qtrelationship WHERE (QuestionId, TopicId) = (?, ?);";
                try (
                        Connection conn = MySQLService.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sqlQtRelationship);
                ) {
                    stmt.setInt(1, q.getQuestionId());
                    stmt.setInt(2, i);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String stackTraceAsString = sw.toString();
                    AlertUtil.generateExceptionViewer(stackTraceAsString, "Question qtRelationship delete operation failed");
                }
            }
        }
        for(Integer i: q.getForeignKeyTopicId()){
            if(!(q.getOldForeignKeyTopicId().contains(i))){
                String sqlQtRelationship = "INSERT INTO qtrelationship (QuestionId, TopicId) VALUES(?, ?);";
                try (
                        Connection conn = MySQLService.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sqlQtRelationship);
                ) {
                    stmt.setInt(1, q.getQuestionId());
                    stmt.setInt(2, i);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    e.printStackTrace(pw);
                    String stackTraceAsString = sw.toString();
                    AlertUtil.generateExceptionViewer(stackTraceAsString, "Question qtRelationship insert operation failed");
                }
            }
        }
        String sqlQuestionUpdate = "UPDATE Questions SET "
                + "ClassificationId = ?, QuestionStatement = ?, CorrectAnswer = ?, "
                + "Choice1 = ?, Choice2 = ?, Choice3 = ?, Choice4 = ?, ImagePath = ? "
                + "WHERE QuestionId = ?";
        try (
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlQuestionUpdate);
        ) {
            stmt.setInt(1, q.getForeignKeyClassificationId());
            stmt.setString(2, q.getQuestionStatement());
            stmt.setString(3, q.getCorrectAnswer());
            stmt.setString(4, q.getChoice1());
            stmt.setString(5, q.getChoice2());
            stmt.setString(6, q.getChoice3());
            stmt.setString(7, q.getChoice4());
            stmt.setString(8, q.getImagePath());
            stmt.setInt(9, q.getQuestionId());
            stmt.executeUpdate();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Question update operation failed");
        }
    }
}