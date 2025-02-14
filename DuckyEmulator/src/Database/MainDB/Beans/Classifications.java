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

public class Classifications {
    private ObjectProperty<Integer> classificationId;
    private StringProperty classification;
    private StringProperty classificationDescription;

    //for question table view
    public static Map<Integer, Classifications> classQuestionView;

    public static Map<Integer, String> classificationNames;

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
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Classifications;");
                ){
            while(rs.next()){
                int cId = rs.getInt("ClassificationId");
                String classifications = rs.getString("Classification");
                String cDescription = rs.getString("Description");
                Classifications clazz = makeClassifications(cId, classifications, cDescription);
                classificationNames.put(cId, classifications);
                classQuestionView.put(cId, clazz);
            }
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification initial query failed");
        }
    }

    public static ObservableList<Classifications> select(int offset) {
        ObservableList<Classifications> clazzs = FXCollections.observableArrayList();
        try (
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "SELECT * FROM Classifications LIMIT 10 OFFSET " + offset + ";"
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
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification partial query failed");
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
            TopicsClassIndexUIController.setClassessMaxPageNum((int)(Math.ceil(maxPageNum/10.0)));
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification pagination setPage operation failed");
        }
    }

    public static boolean delete(Classifications clazz){
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
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification delete operation failed");
        }
        boolean isRelated = totalRelatedQuestions == 0 ? false : true;
        if(isRelated){
            AlertUtil.generateErrorWindow("Delete topic failed", "Delete topic",
                    "There are " + totalRelatedQuestions +
                            " questions associated with this topic, please delete them first !");
            return false;
        }else{
            String sqlDel = "DELETE FROM Classifications WHERE ClassificationId = ?;";
            try(
                    Connection conn = MySQLService.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sqlDel);
                    ){
                stmt.setInt(1, clazz.getClassificationId());

                boolean isDeleted = stmt.executeUpdate() == 1 ? true : false;

                if(isDeleted){
                    classQuestionView.remove(clazz.getClassificationId());
                    classificationNames.remove(clazz.getClassificationId());
                    return true;
                }
                else return false;
            }catch(Exception e){
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTraceAsString = sw.toString();
                AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification delete operation failed");
                return false;
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
            }
        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification insert operation failed");
        }
    }
    public static void update(Classifications c){
        String sqlUpdateQuestionsTable = "UPDATE Classifications SET Classification = ?, Description = ? " +
                "WHERE ClassificationId = ?;";

        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlUpdateQuestionsTable);
                ){
            int cId = c.getClassificationId();
            String classifications = c.getClassification();
            String cDescription = c.getClassificationDescription();
            Classifications clazz = makeClassifications(cId, classifications, cDescription);
            stmt.setString(1, c.getClassification());
            stmt.setString(2, c.getClassificationDescription());
            stmt.setInt(3, c.getClassificationId());
            stmt.executeUpdate();
            classQuestionView.replace(c.getClassificationId(), clazz);
            classificationNames.replace(c.getClassificationId(), clazz.getClassification());

        }catch(Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String stackTraceAsString = sw.toString();
            AlertUtil.generateExceptionViewer(stackTraceAsString, "Classification update operation failed");
        }
    }

    public static Integer searchClassification(String name){
        for(Classifications c: classQuestionView.values()){
            if(c.getClassification().equals(name)) return c.getClassificationId();
        }
        return null;
    }

    public static String searchClassification(int id){
        for(Classifications c: classQuestionView.values()){
            if(c.getClassificationId() == id) return c.getClassification();
        }
        return null;
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