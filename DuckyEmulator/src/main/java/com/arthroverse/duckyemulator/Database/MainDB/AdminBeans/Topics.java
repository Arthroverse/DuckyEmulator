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
import com.arthroverse.duckyemulator.UIControllers.AdminUIsControllers.TopicsClassIndexUIController;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorMessage;
import com.arthroverse.duckyemulator.Utilities.Constant.ErrorTitle;
import com.arthroverse.duckyemulator.Utilities.Constant.FailedOperationType;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;
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
import java.time.LocalDateTime;
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

    public static Map<String, Topics> topicsNameAsKey;

    private static Map<Integer, Topics> deletedTopics = new HashMap<>();

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

    public static Map<Integer, Topics> getDeletedTopics(){
        return deletedTopics;
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

    public static void resetDeletedTopics(){
        deletedTopics = new HashMap<>();
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
        topicsNameAsKey = new HashMap<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Topics WHERE Deleted = 0;");
        ){
            while(rs.next()){
                if(!deletedTopics.containsKey(rs.getInt("TopicId"))){
                    int tId = rs.getInt("TopicId");
                    final String tName = rs.getString("TopicName");
                    String tDescription = rs.getString("Description");
                    Topics top = makeTopic(tId, tName, tDescription);
                    topicsQuestionView.put(tId, top);
                    topicNames.put(tId, tName);
                    topicsNameAsKey.put(tName, top);
                }
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_TOPIC_INIT_QUERY_FAILED.toString());
        }
    }

    public static ObservableList<Topics> select(int offset) {
        ObservableList<Topics> topics = FXCollections.observableArrayList();
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM Topics WHERE Deleted = 0 LIMIT 10 OFFSET " + offset + ";"
                )
        ) {
            while (rs.next()) {
                if(!deletedTopics.containsKey(rs.getInt("TopicId"))){
                    int tId = rs.getInt("TopicId");
                    String tName = rs.getString("TopicName");
                    String tDescription = rs.getString("Description");
                    Topics top = makeTopic(tId, tName, tDescription);
                    topics.add(top);
                }
            }
        } catch (Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_TOPIC_PARTIAL_SELECT_FAILED.toString());
        }
        return topics;
    }

    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(TopicId) FROM Topics WHERE Deleted = 0");
        ) {
            rs.next();
            int maxNumPage = rs.getInt(1);
            if(rs.getInt(1) == 0) maxNumPage = 1;
            TopicsClassIndexUIController.setTopicsMaxPageNum((int)(Math.ceil(maxNumPage/10.0)));
        } catch (Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_TOPIC_PAGINATION_SET_PAGE_FAILED.toString());
        }
    }

    public static void delete(Topics t){
        int totalRelatedQuestions = 0;
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(QuestionId) FROM QTRelationship WHERE TopicId = " +
                        t.getTopicId() + ";");
        ){
            rs.next();
            totalRelatedQuestions = rs.getInt(1);
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_TOPIC_FIND_TOTAL_RELATED_QUEST_FAILED.toString());
        }

        boolean isRelated = totalRelatedQuestions == 0 ? false : true;
        if(isRelated){
            AlertUtil.generateErrorWindow(ErrorTitle.SQL_TOPIC_DELETE_FAILED.toString(),
                    FailedOperationType.SQL_TOPIC_DELETE_TOPIC_FAILED.toString(),
                    String.format(ErrorMessage.SQL_TOPIC_NUM_OF_QUEST_RELATED.toString(), totalRelatedQuestions));
        }else{
            String deletedDate = LocalDateTime.now().toString();
            String deletedBy = Users.getCurrentUserEmailInActiveSession();

            String sqlTopicUpdateDeleteStatus = "UPDATE Topics SET Deleted = 1, DeletedBy = ?, DeletedAt = ? " +
                    "WHERE TopicId = ?;";
            try(
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt1 = conn.prepareStatement(sqlTopicUpdateDeleteStatus);
                    ){
                stmt1.setString(1, deletedBy);
                stmt1.setString(2, deletedDate);
                stmt1.setInt(3, t.getTopicId());
                stmt1.executeUpdate();
                topicsQuestionView.remove(t.getTopicId());
                topicNames.remove(t.getTopicId());
                final String tName = t.getTopicName();
                topicsNameAsKey.remove(tName);
            }catch(Exception e){
                AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                        ErrorTitle.SQL_TOPIC_DELETE_FAILED.toString());
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
                final String tName = newTopic.getTopicName();
                topicsNameAsKey.put(tName, newTopic);
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_TOPIC_INSERT_FAILED.toString());
        }
    }

    public static void update(Topics t, final String oldTopicName){
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
            topicsNameAsKey.remove(oldTopicName);
            final String tName = t.getTopicName();
            topicsNameAsKey.put(tName, t);
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_TOPIC_UPDATE_FAILED.toString());
        }
    }

    public static ArrayList<Integer> findingTopicIds(ArrayList<String> allTopicNames){
        ArrayList<Integer> returnTopicIds = new ArrayList<>();
        for(String str: allTopicNames){
            for(Topics t: topicsQuestionView.values()){
                if(str.equals(t.getTopicName())) returnTopicIds.add(t.getTopicId()); //do this to maintain the original input inputted from user side
            }
        }
        return returnTopicIds;
    }

    public static Topics findingTopics(String topicName){
        if(topicsNameAsKey.containsKey(topicName)) return topicsNameAsKey.get(topicName);
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