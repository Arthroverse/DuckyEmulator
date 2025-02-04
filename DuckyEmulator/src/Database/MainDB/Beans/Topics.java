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
import UIControllers.AdminUIsControllers.TopicsClassIndexUIController;
import Utilities.PromptAlert.AlertUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Topics {
    private ObjectProperty<Integer> topicId;
    private StringProperty topicName;
    private StringProperty topicDescription;

    //for question table view
    public static ArrayList<Topics> topicsQuestionView;

    public Topics(){
        topicId = new SimpleObjectProperty<Integer>(null);
        topicName = new SimpleStringProperty();
        topicDescription = new SimpleStringProperty();
    }

    public Integer getTopicId(){
        return this.topicId.get();
    }

    public String getTopicName(){
        return this.topicName.get();
    }

    public String getTopicDescription(){
        return this.topicDescription.get();
    }

    public ObjectProperty<Integer> getTopicIdProperty(){
        return this.topicId;
    }

    public StringProperty getTopicNameProperty(){
        return this.topicName;
    }

    public StringProperty getTopicDescriptionProperty(){
        return this.topicDescription;
    }

    public void setTopicId(int id){
        this.topicId.set(id);
    }

    public void setTopicName(String name) {
        this.topicName.set(name);
    }

    public void setTopicDescription(String description){
        this.topicDescription.set(description);
    }

    public static ObservableList<Topics> selectAll(){
        topicsQuestionView = new ArrayList<>();
        ObservableList<Topics> topics = FXCollections.observableArrayList();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Topics;");
        ){
            while(rs.next()){
                Topics top = new Topics();
                top.setTopicId(rs.getInt("TopicId"));
                top.setTopicName(rs.getString("TopicName"));
                top.setTopicDescription(rs.getString("Description"));
                topics.add(top);
                topicsQuestionView.add(top);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return topics;
    }

    public static ObservableList<Topics> select(int offset) {
        ObservableList<Topics> topics = FXCollections.observableArrayList();
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM Topics LIMIT 10 OFFSET " + offset + ";"
                )
        ) {
            while (rs.next()) {
                Topics t = new Topics();
                t.setTopicId(rs.getInt("TopicId"));
                t.setTopicName(rs.getString("TopicName"));
                t.setTopicDescription(rs.getString("Description"));
                topics.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topics;
    }

    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT TopicId, COUNT(TopicId) " +
                                "FROM Topics " +
                                "GROUP BY TopicId;"
                );
        ) {
            int maxNumPage = 0;
            while (rs.next()) {
                maxNumPage += rs.getInt(2);
            }
            if (maxNumPage % 10 != 0) {
                TopicsClassIndexUIController.setTopicsMaxPageNum(
                        maxNumPage / 10 + 1
                );
            } else {
                TopicsClassIndexUIController.setTopicsMaxPageNum(
                        maxNumPage / 10
                );
            }
            TopicsClassIndexUIController.setTopicsOffset(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean delete(Topics top){
        ArrayList<Integer> questionIds = new ArrayList<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM QTRelationship WHERE TopicId = " +
                        top.getTopicId() + ";");
        ){
            while(rs.next()){
                questionIds.add(rs.getInt(1));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        boolean isSingle = false;
        for(Integer i: questionIds){
            try(
                    Connection conn = MySQLService.getConnection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM QTRelationship WHERE QuestionId = " + i + ";");
            ){
                int index = 0;
                while(rs.next()) index++;
                if(index == 1) isSingle = true;
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(isSingle){
            AlertUtil.generateErrorWindow("Delete topic failed", "Delete topic",
                    "There are questions associated with this topic, please delete them first !");
            return false;
        }else{
            String sqlCurrentTopicInQuestionDelete = "DELETE FROM qtrelationship WHERE TopicId = ?;";
            try(
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sqlCurrentTopicInQuestionDelete);
            ){
                stmt.setInt(1, top.getTopicId());
                stmt.executeUpdate();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        String sqlTopicDelete = "DELETE FROM Topics WHERE TopicId = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlTopicDelete);
        ){
            stmt.setInt(1, top.getTopicId());
            boolean isDeleted = stmt.executeUpdate() == 1 ? true : false;
            if(isDeleted) return true;
            else return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static void insert(Topics newTopic){
        String sqlInsert = "INSERT INTO Topics(TopicName, Description) "
                + "VALUES(?, ?);";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlInsert);
        ){
            stmt.setString(1, newTopic.getTopicName());
            stmt.setString(2, newTopic.getTopicDescription());
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void update(Topics t){
        String sqlTopicsUpdate = "UPDATE Topics SET TopicName = ?, Description = ? " +
                "WHERE TopicId = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlTopicsUpdate);
        ){
            stmt.setString(1, t.getTopicName());
            stmt.setString(2, t.getTopicDescription());
            stmt.setInt(3, t.getTopicId());
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> findingTopicIds(ArrayList<String> allTopicNames){
        String sqlTopicId = "SELECT TopicId FROM Topics WHERE TopicName = ?;";
        ArrayList<Integer> topicIds = new ArrayList<>();
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlTopicId, Statement.RETURN_GENERATED_KEYS);
        ){
            for(String str: allTopicNames){
                stmt.setString(1, str);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                topicIds.add(rs.getInt(1));
            }
            return topicIds;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> findingTopicNames(ArrayList<Integer> topicIds){
        ArrayList<String> topicNames = new ArrayList<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
        ){
            for(Integer i: topicIds){
                ResultSet rs = stmt.executeQuery("SELECT * FROM Topics WHERE TopicId = " + i + ";");
                rs.next();
                topicNames.add(rs.getString(2));
            }
            return topicNames;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Topics> findingTopics(ArrayList<Integer> topicIds){
        ArrayList<Topics> topics = new ArrayList<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
        ){
            for(Integer i: topicIds){
                ResultSet rs = stmt.executeQuery("SELECT * FROM Topics WHERE TopicId = " + i + ";");
                while(rs.next()){
                    Topics t = new Topics();
                    t.setTopicId(i);
                    t.setTopicName(rs.getString("TopicName"));
                    t.setTopicDescription(rs.getString("Description"));
                    topics.add(t);
                }
            }
            return topics;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}