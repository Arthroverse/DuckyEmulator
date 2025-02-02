package Database.MainDB.Beans;

import Database.DBService.MySQLService;
import UIControllers.AdminUIsControllers.QBankIndexUIController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

<<<<<<< Updated upstream
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
=======
import java.sql.*;
import java.util.ArrayList;
>>>>>>> Stashed changes

import static Database.MainDB.Beans.Topics.topicsQuestionView;

public class Questions {
    private ObjectProperty<Integer> foreignKeyClassificationId;
<<<<<<< Updated upstream
    private ObjectProperty<Integer> foreignKeyTopicId;
=======
    private ArrayList<Integer> foreignKeyTopicId;
    private ArrayList<Integer> oldForeignKeyTopicId;
    private StringProperty foreignKeyTopicIdForDisplay;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        foreignKeyTopicId = new SimpleObjectProperty<Integer>(null);
=======
        foreignKeyTopicId = new ArrayList<>();
        foreignKeyTopicIdForDisplay = new SimpleStringProperty();
        oldForeignKeyTopicId = new ArrayList<>();
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public Integer getForeignKeyTopicId() {
        return this.foreignKeyTopicId.get();
=======
    public ArrayList<Integer> getForeignKeyTopicId() {
        return this.foreignKeyTopicId;
    }

    public String getForeignKeyTopicIdForDisplay(){
        return this.foreignKeyTopicIdForDisplay.get();
    }

    public ArrayList<Integer> getOldForeignKeyTopicId(){
        return this.oldForeignKeyTopicId;
>>>>>>> Stashed changes
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

    public StringProperty getForeignKeyTopicIdForDisplayProperty(){
        return this.foreignKeyTopicIdForDisplay;
    }

    public ObjectProperty<Integer> getForeignKeyClassificationIdProperty() {
        return this.foreignKeyClassificationId;
    }

    public ObjectProperty<Integer> getForeignKeyTopicIdProperty() {
        return this.foreignKeyTopicId;
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

<<<<<<< Updated upstream
    public void setForeignKeyTopicId(int id) {
        this.foreignKeyTopicId.set(id);
=======
    public void setForeignKeyTopicIdForDisplay(String fk){
        this.foreignKeyTopicIdForDisplay.set(fk);
    }

    public void setForeignKeyTopicId(ArrayList<Integer> foreignKeyTopicId) {
        this.foreignKeyTopicId = new ArrayList<>(foreignKeyTopicId);
    }

    public void setOldForeignKeyTopicId(ArrayList<Integer> foreignKeyTopicId){
        this.oldForeignKeyTopicId = new ArrayList<>(foreignKeyTopicId);
>>>>>>> Stashed changes
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

    public static ObservableList<Questions> select(int offset) {
        ObservableList<Questions> questions = FXCollections.observableArrayList();
<<<<<<< Updated upstream
=======
        ArrayList<Integer[]> gatheredQtTable = new ArrayList<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM qtrelationship;");
                ){
            while(rs.next()){
                Integer[] array = new Integer[2];
                array[0] = rs.getInt(1);
                array[1] = rs.getInt(2);
                gatheredQtTable.add(array);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
>>>>>>> Stashed changes
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM Questions LIMIT 10 OFFSET " + offset + ";"
                )
        ) {
            while (rs.next()) {
                Questions quest = new Questions();
<<<<<<< Updated upstream
                quest.setForeignKeyClassificationId(rs.getInt("ClassificationId"));
                quest.setForeignKeyTopicId(rs.getInt("TopicId"));
                quest.setQuestionId(rs.getInt("QuestionId"));
=======
                ArrayList<Integer> topicIds = new ArrayList<>();
                ArrayList<String> topicNames = new ArrayList<>();
                topicNames.add(null);
                for(Topics t: topicsQuestionView){
                    topicNames.add(t.getTopicName());
                }
                quest.setQuestionId(rs.getInt("QuestionId"));
                quest.setForeignKeyClassificationId(rs.getInt("ClassificationId"));
                for(Integer[] arrInt: gatheredQtTable) if(arrInt[0].equals(quest.getQuestionId())) topicIds.add(arrInt[1]);
                quest.setForeignKeyTopicId(topicIds);
                quest.setOldForeignKeyTopicId(topicIds);
>>>>>>> Stashed changes
                quest.setQuestionStatement(rs.getString("QuestionStatement"));
                quest.setChoice1(rs.getString("Choice1"));
                quest.setChoice2(rs.getString("Choice2"));
                quest.setChoice3(rs.getString("Choice3"));
                quest.setChoice4(rs.getString("Choice4"));
                quest.setCorrectAnswer(rs.getString("CorrectAnswer"));
                quest.setImagePath(rs.getString("ImagePath"));
                questions.add(quest);
                ArrayList<String> questionTopics = new ArrayList<>();
                for(Integer i: topicIds){
                    questionTopics.add(topicNames.get(i));
                }
                String questionTopicsDisplay = questionTopics.toString();
                quest.setForeignKeyTopicIdForDisplay(questionTopicsDisplay.substring(1, questionTopicsDisplay.length() - 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT ClassificationId, COUNT(QuestionId) " +
                                "FROM Questions " +
                                "GROUP BY ClassificationId;"
                );
        ) {
            int maxNumPage = 0;
            while (rs.next()) {
                maxNumPage += rs.getInt(2);
            }
            if (maxNumPage % 10 != 0) {
                QBankIndexUIController.setMaxPageNum(
                        maxNumPage / 10 + 1
                );
            } else {
                QBankIndexUIController.setMaxPageNum(
                        maxNumPage / 10
                );
            }
            QBankIndexUIController.setOffset(10);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    public static void insert(Questions quest) {
        String sqlInsertQuestions = "INSERT INTO Questions(ClassificationId, TopicId, " +
                "QuestionStatement, CorrectAnswer, Choice1, Choice2, Choice3, Choice4) " +
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
            if (stmt.executeUpdate() == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int questId = key.getInt(1);
                quest.setQuestionId(questId);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
    public static void update(Questions q) {
        for(Integer i: QBankIndexUIController.getOldFKCache()){
            if(!(q.getForeignKeyTopicId().contains(i))){
                String sqlQtRelationship = "DELETE FROM qtrelationship WHERE (QuestionId, TopicId) = (?, ?);";
                try (
                        Connection conn = MySQLService.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sqlQtRelationship);
                ) {
                    stmt.setInt(1, i);
                    stmt.setInt(2, q.getQuestionId());
                    stmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for(Integer i: q.getForeignKeyTopicId()){
            if(!(QBankIndexUIController.getOldFKCache().contains(i))){
                String sqlQtRelationship = "INSERT INTO qtrelationship (QuestionId, TopicId) VALUES(?, ?);";
                try (
                        Connection conn = MySQLService.getConnection();
                        PreparedStatement stmt = conn.prepareStatement(sqlQtRelationship);
                ) {
                    stmt.setInt(1, q.getQuestionId());
                    stmt.setInt(2, i);
                    stmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String sqlQuestionUpdate = "UPDATE Questions SET "
                + "ClassificationId = ?, QuestionStatement = ?, CorrectAnswer = ?, "
                + "Choice1 = ?, Choice2 = ?, Choice3 = ?, Choice4 = ? "
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
            stmt.setInt(8, q.getQuestionId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}