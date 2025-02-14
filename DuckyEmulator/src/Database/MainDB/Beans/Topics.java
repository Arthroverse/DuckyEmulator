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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Topics {
    private ObjectProperty<Integer> topicId;
    private StringProperty topicName;
    private StringProperty topicDescription;

    //for question table view
    public static Map<Integer, Topics> topicsQuestionView;

    public static Map<Integer, String> topicNames;

    public Topics(){
        topicId = new SimpleObjectProperty<>(null);
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

    public static void selectAll(){
        topicsQuestionView = new HashMap<>();
        topicNames = new HashMap<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Topics;");
        ){
            while(rs.next()){
                int tId = rs.getInt("TopicId");
                String tName = rs.getString("TopicName");
                String tDescription = rs.getString("Description");
                Topics top = makeTopic(tId, tName, tDescription);
                topicsQuestionView.put(tId, top);
                topicNames.put(tId, tName);
            }
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics initial query operation failed");
        }
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
                int tId = rs.getInt("TopicId");
                String tName = rs.getString("TopicName");
                String tDescription = rs.getString("Description");
                Topics top = makeTopic(tId, tName, tDescription);
                topics.add(top);
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics partial select operation failed");
        }
        return topics;
    }

    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(TopicId) FROM Topics");
        ) {
            rs.next();
            int maxNumPage = rs.getInt(1);
            TopicsClassIndexUIController.setTopicsMaxPageNum((int)(Math.ceil(maxNumPage/10.0)));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics pagination setPage operation failed");
        }
    }

    public static boolean delete(Topics top){
        int totalRelatedQuestions = 0;
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(QuestionId) FROM QTRelationship WHERE TopicId = " +
                        top.getTopicId() + ";");
        ){
            rs.next();
            totalRelatedQuestions = rs.getInt(1);
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics find total related question operation failed");
        }

        boolean isRelated = totalRelatedQuestions == 0 ? false : true;
        if(isRelated){
            AlertUtil.generateErrorWindow("Delete topic failed", "Delete topic",
                    "There are " + totalRelatedQuestions +
                            " questions associated with this topic, please delete them first !");
            return false;
        }else{
            String sqlTopicDelete = "DELETE FROM Topics WHERE TopicId = ?;";
            try(
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sqlTopicDelete);
            ){
                stmt.setInt(1, top.getTopicId());
                boolean isDeleted = stmt.executeUpdate() == 1 ? true : false;
                if(isDeleted){
                    topicsQuestionView.remove(top.getTopicId());
                    topicNames.remove(top.getTopicId());
                    return true;
                }
                else return false;
            }catch(Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTraceAsString = sw.toString();
                AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics delete operation failed");
                return false;
            }
        }
    }

    public static void insert(Topics newTopic){
        String sqlInsert = "INSERT INTO Topics(TopicName, Description) "
                + "VALUES(?, ?);";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ){
            stmt.setString(1, newTopic.getTopicName());
            stmt.setString(2, newTopic.getTopicDescription());
            ResultSet key = null;
            int rowUpdated = stmt.executeUpdate();
            if(rowUpdated == 1){
                key = stmt.getGeneratedKeys();
                key.next();
                int returnedKey = key.getInt(1);
                newTopic.setTopicId(returnedKey);
                topicsQuestionView.put(returnedKey, newTopic);
                topicNames.put(returnedKey, newTopic.getTopicName());
            }
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics insert operation failed");
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
            topicsQuestionView.replace(t.getTopicId(), t);
            topicNames.replace(t.getTopicId(), t.getTopicName());
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Topics update operation failed");
        }
    }

    public static ArrayList<Integer> findingTopicIds(ArrayList<String> allTopicNames){
        ArrayList<Integer> returnTopicIds = new ArrayList<>();
        int index = 0;
        for(Topics t: topicsQuestionView.values()){
            if(allTopicNames.contains(t.getTopicName())) returnTopicIds.add(t.getTopicId());
        }
        return returnTopicIds;
    }

    public static Topics findingTopics(String topicName){
        for(Topics t: topicsQuestionView.values()){
            if(t.getTopicName().equals(topicName)) return t;
        }
        return null;
    }

    public static ArrayList<Topics> findingTopics(ArrayList<Integer> topicIds){
        ArrayList<Topics> returnTopics = new ArrayList<>();
        for(Integer i: topicIds){
            if(topicsQuestionView.containsKey(i)) returnTopics.add(topicsQuestionView.get(i));
        }
        return returnTopics;
    }

    public static Topics makeTopic(int tId,
                                   String tName,
                                   String tDescription){
        Topics top = new Topics();
        top.setTopicId(tId);
        top.setTopicName(tName);
        top.setTopicDescription(tDescription);
        return top;
    }
}