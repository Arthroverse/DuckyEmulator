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

public class Classifications {
    private ObjectProperty<Integer> classificationId;
    private StringProperty classification;
    private StringProperty classificationDescription;

    //for question table view
    public static ArrayList<Classifications> classQuestionView;

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

    public static ObservableList<Classifications> selectAll(){
        classQuestionView = new ArrayList<>();
        ObservableList<Classifications> classifications = FXCollections.observableArrayList();
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Classifications;");
                ){
            while(rs.next()){
                Classifications clazz = new Classifications();
                clazz.setClassificationId(rs.getInt("ClassificationId"));
                clazz.setClassification(rs.getString("Classification"));
                clazz.setClassificationDescription(rs.getString("Description"));
                classifications.add(clazz);
                classQuestionView.add(clazz);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return classifications;
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
                Classifications clazz = new Classifications();
                clazz.setClassificationId(rs.getInt("ClassificationId"));
                clazz.setClassification(rs.getString("Classification"));
                clazz.setClassificationDescription(rs.getString("Description"));
                clazzs.add(clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            TopicsClassIndexUIController.setClassessOffset(10);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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

                if(isDeleted) return true;
                else return false;
            }catch(Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void insert(Classifications clazz){
        String sqlInsert = "INSERT INTO Classifications(Classification, Description) "
                + "VALUES(?, ?);";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlInsert);
                ){
            stmt.setString(1, clazz.getClassification());
            stmt.setString(2, clazz.getClassificationDescription());
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void update(Classifications c){
        String sqlUpdateQuestionsTable = "UPDATE Classifications SET Classification = ?, Description = ? " +
                "WHERE ClassificationId = ?;";

        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlUpdateQuestionsTable);
                ){
            stmt.setString(1, c.getClassification());
            stmt.setString(2, c.getClassificationDescription());
            stmt.setInt(3, c.getClassificationId());
            stmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Integer searchClassificationByName(String name){
        String sqlSelect = "SELECT ClassificationId FROM Classifications WHERE Classification = ?;";
        try(
                Connection conn = MySQLService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sqlSelect);
                ){
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String searchClassificationById(int id){
        try(
                Connection conn = MySQLService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Classification FROM Classifications WHERE ClassificationId = " + id + ";");
                ){
            rs.next();
            return rs.getString(1);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}