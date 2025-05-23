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
import java.util.HashMap;
import java.util.Map;

public class Classifications {
    private ObjectProperty<Integer> classificationId;
    private StringProperty classification;
    private StringProperty classificationDescription;

    //for question table view
    public static Map<Integer, Classifications> classQuestionView;

    public static Map<Integer, String> classificationNames;

    public static Map<String, Classifications> classificationNameAsKey;

    public Classifications(){
        classificationId = new SimpleObjectProperty<Integer>(null);
        classification = new SimpleStringProperty();
        classificationDescription = new SimpleStringProperty();
    }

    public Integer getClassificationId(){
        return this.classificationId.get();
    }

    public String getClassification(){
        return this.classification.get();
    }

    public String getClassificationDescription(){
        return this.classificationDescription.get();
    }

    public ObjectProperty<Integer> getClassificationIdProperty(){
        return this.classificationId;
    }

    public StringProperty getClassificationProperty(){
        return this.classification;
    }

    public StringProperty getClassificationDescriptionProperty(){
        return this.classificationDescription;
    }

    public void setClassificationId(int id){
        this.classificationId.set(id);
    }

    public void setClassification(String classification){
        this.classification.set(classification);
    }

    public void setClassificationDescription(String classificationDescription){
        this.classificationDescription.set(classificationDescription);
    }

    public static void selectAll(){
        classQuestionView = new HashMap<>();
        classificationNames = new HashMap<>();
        classificationNameAsKey = new HashMap<>();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Classifications WHERE Deleted = 0;");
        ){
            while(rs.next()){
                int cId = rs.getInt("ClassificationId");
                final String classifications = rs.getString("Classification");
                String cDescription = rs.getString("Description");
                Classifications clazz = makeClassifications(cId, classifications, cDescription);
                classificationNames.put(cId, classifications);
                classQuestionView.put(cId, clazz);
                classificationNameAsKey.put(classifications, clazz);
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_CLASS_INIT_QUERY_FAILED.toString());
        }
    }

    public static ObservableList<Classifications> select(int offset) {
        ObservableList<Classifications> clazzs = FXCollections.observableArrayList();
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM Classifications WHERE Deleted = 0 LIMIT 10 OFFSET " + offset + ";"
                )
        ) {
            while (rs.next()) {
                int cId = rs.getInt("ClassificationId");
                String classifications = rs.getString("Classification");
                String cDescription = rs.getString("Description");
                Classifications clazz = makeClassifications(cId, classifications, cDescription);
                clazzs.add(clazz);
            }
        }catch(Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_CLASS_PARTIAL_QUERY_FAILED.toString());
        }
        return clazzs;
    }

    public static void setPage() {
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(ClassificationId) FROM Classifications");
        ) {
            rs.next();
            int maxPageNum = rs.getInt(1);
            if(rs.getInt(1) == 0) maxPageNum = 1;
            TopicsClassIndexUIController.setClassessMaxPageNum((int)(Math.ceil(maxPageNum/10.0)));
        } catch (Exception e) {
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_CLASS_PAGINATION_SET_PAGE_FAILED.toString());
        }
    }

    public static void delete(Classifications clazz){
        int totalRelatedQuestions = 0;
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT QuestionId FROM Questions WHERE ClassificationId = "
                        + clazz.getClassificationId() + ";");
        ){
            while(rs.next()){
                totalRelatedQuestions++;
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_CLASS_DELETION_FAILED.toString());
        }
        boolean isRelated = totalRelatedQuestions == 0 ? false : true;
        if(isRelated){
            AlertUtil.generateErrorWindow(ErrorTitle.SQL_CLASS_DELETION_FAILED.toString(),
                    FailedOperationType.SQL_CLASS_DELETE_CLASS_FAILED.toString(),
                    String.format(ErrorMessage.SQL_CLASS_NUM_OF_RELATED_QUESTION.toString(), totalRelatedQuestions));
        }else{
            String deletedDate = LocalDateTime.now().toString();
            String deletedBy = Users.getCurrentUserEmailInActiveSession();

            String sqlClassificationUpdateDeleteStatus = "UPDATE Classifications SET Deleted = 1, DeletedBy = ?, DeletedAt = ? " +
                    "WHERE ClassificationId = ?;";
            try(
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt1 = conn.prepareStatement(sqlClassificationUpdateDeleteStatus);
                    ){
                stmt1.setString(1, deletedBy);
                stmt1.setString(2, deletedDate);
                stmt1.setInt(3, clazz.getClassificationId());
                stmt1.executeUpdate();
            }catch(Exception e){
                AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                        ErrorTitle.SQL_CLASS_DELETION_FAILED.toString());
            }
        }
    }

    public static void insert(Classifications clazz){
        String sqlInsert = "INSERT INTO Classifications(Classification, Description) "
                + "VALUES(?, ?);";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
        ){
            ResultSet key = null;
            stmt.setString(1, clazz.getClassification());
            stmt.setString(2, clazz.getClassificationDescription());
            int rowUpdated = stmt.executeUpdate();
            if(rowUpdated == 1){
                key = stmt.getGeneratedKeys();
                key.next();
                int returnedKey = key.getInt(1);
                clazz.setClassificationId(returnedKey);
                classQuestionView.put(returnedKey, clazz);
                classificationNames.put(returnedKey, clazz.getClassification());
                final String clazzName = clazz.getClassification();
                classificationNameAsKey.put(clazzName, clazz);
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_CLASS_INSERTION_FAILED.toString());
        }
    }
    public static void update(Classifications c, final String oldClassificationName){
        String sqlUpdateQuestionsTable = "UPDATE Classifications SET Classification = ?, Description = ? " +
                "WHERE ClassificationId = ?;";

        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlUpdateQuestionsTable);
        ){
            final String classifications = c.getClassification();
            stmt.setString(1, c.getClassification());
            stmt.setString(2, c.getClassificationDescription());
            stmt.setInt(3, c.getClassificationId());
            stmt.executeUpdate();
            classQuestionView.replace(c.getClassificationId(), c);
            classificationNames.replace(c.getClassificationId(), c.getClassification());
            classificationNameAsKey.remove(oldClassificationName);
            classificationNameAsKey.put(classifications, c);

        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e),
                    ErrorTitle.SQL_CLASS_UPDATION_FAILED.toString());
        }
    }

    public static Integer searchClassification(String name){
        if(classificationNameAsKey.containsKey(name))
            return classificationNameAsKey.get(name).getClassificationId();
        return null;
    }

    public static String searchClassification(int id){
        if(!classQuestionView.containsKey(id)) return null;
        return classQuestionView.get(id).getClassification();
    }

    public static Classifications makeClassifications(int cId,
                                                      String classsifications,
                                                      String cDescription){
        Classifications clazz = new Classifications();
        clazz.setClassificationId(cId);
        clazz.setClassification(classsifications);
        clazz.setClassificationDescription(cDescription);
        return clazz;
    }
}