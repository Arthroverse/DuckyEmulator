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
 */
CREATE DATABASE IF NOT EXISTS DuckyEmulator_QuestionDB;
USE DuckyEmulator_QuestionDB;
CREATE TABLE IF NOT EXISTS Classifications(
    ClassificationId INT NOT NULL AUTO_INCREMENT,
    Classification VARCHAR(50),
    Description VARCHAR(512),
    PRIMARY KEY(ClassificationId),
    Deleted BOOLEAN DEFAULT 0,
    DeletedAt VARCHAR(100),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Topics(
    TopicId INT NOT NULL AUTO_INCREMENT,
    TopicName VARCHAR(50) NOT NULL,
    Description VARCHAR(512),
    PRIMARY KEY(TopicId),
    Deleted BOOLEAN DEFAULT 0,
    DeletedAt VARCHAR(100),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Questions(
    QuestionId INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(QuestionId),
    ClassificationId INT NOT NULL,
    FOREIGN KEY (ClassificationId) REFERENCES Classifications(ClassificationId),
    QuestionStatement VARCHAR(512) NOT NULL,
    CorrectAnswer VARCHAR(512) NOT NULL,
    Choice1 VARCHAR(512) NOT NULL,
    Choice2 VARCHAR(512) NOT NULL,
    Choice3 VARCHAR(512) NOT NULL,
    Choice4 VARCHAR(512) NOT NULL,
    ImagePath VARCHAR(512),
    Deleted BOOLEAN DEFAULT 0,
    DeletedAt VARCHAR(100),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS QTRelationship(
    QuestionId INT NOT NULL,
    FOREIGN KEY (QuestionId) REFERENCES Questions(QuestionId),
    TopicId INT NOT NULL,
    FOREIGN KEY (TopicId) REFERENCES Topics(TopicId),
    Deleted BOOLEAN DEFAULT 0,
    DeletedAt VARCHAR(100) DEFAULT '',
    DeletedBy VARCHAR(50) DEFAULT '',
    PRIMARY KEY(QuestionId, TopicId, Deleted, DeletedAt, DeletedBy)
);

CREATE TABLE IF NOT EXISTS Users(
    UserEmail VARCHAR(50) NOT NULL,
    PRIMARY KEY(UserEmail),
    UserName VARCHAR(50) NOT NULL,
    UserPassword VARCHAR(150) NOT NULL,
    IsAdminPrivilged BOOLEAN DEFAULT 0
);

CREATE TABLE IF NOT EXISTS Sessions(
    SessionId TIMESTAMP PRIMARY KEY NOT NULL,
    UserEmail VARCHAR(50) NOT NULL,
    FOREIGN KEY (UserEmail) REFERENCES Users(UserEmail),
    StartTime TIMESTAMP,
    EndTime TIMESTAMP,
    TotalQuestions INT,
    TotalCorrectQuestions INT,
    Deleted BOOLEAN DEFAULT 0,
    DeletedAt VARCHAR(100),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Session_has_question(
    SessionId TIMESTAMP NOT NULL,
    QuestionId INT NOT NULL,
    FOREIGN KEY (SessionId) REFERENCES Sessions(SessionId),
    FOREIGN KEY (QuestionId) REFERENCES Questions(QuestionId),
    UserAnswer VARCHAR(512),
    Deleted BOOLEAN DEFAULT 0,
    DeletedAt VARCHAR(100) DEFAULT '',
    DeletedBy VARCHAR(50) DEFAULT '',
    PRIMARY KEY(SessionId, QuestionId, Deleted, DeletedAt, DeletedBy)
);
INSERT INTO Users(UserEmail, UserName, UserPassword, IsAdminPrivilged)
VALUES('admin@example.com', 'vinh', 'a', True),
('user@example.com', 'hoang', 'a', False);
-- FOR TESTING AND DEBUGGING PURPOSES
-- DROP DATABASE DuckyEmulator_QuestionDB;

SELECT * FROM Sessions WHERE Deleted = 0
ORDER BY SessionId LIMIT 10 OFFSET 0;

SELECT * FROM Sessions WHERE Deleted = 0 AND UserEmail = 'admin@example.com'ORDER BY SessionId LIMIT 10 OFFSET 0;