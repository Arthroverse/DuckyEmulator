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
    PRIMARY KEY(ClassificationId)
);

CREATE TABLE IF NOT EXISTS Topics(
    TopicId INT NOT NULL AUTO_INCREMENT,
    TopicName VARCHAR(50) NOT NULL,
    Description VARCHAR(512),
    PRIMARY KEY(TopicId)
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
    ImagePath VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS QTRelationship(
    QuestionId INT NOT NULL,
    FOREIGN KEY (QuestionId) REFERENCES Questions(QuestionId),
    TopicId INT NOT NULL,
    FOREIGN KEY (TopicId) REFERENCES Topics(TopicId),
    PRIMARY KEY(QuestionId, TopicId)
);

CREATE TABLE IF NOT EXISTS Users(
    UserEmail VARCHAR(50) NOT NULL,
    PRIMARY KEY(UserEmail),
    UserName VARCHAR(50) NOT NULL,
    UserPassword VARCHAR(150) NOT NULL,
    UserType VARCHAR(10) NOT NULL
);

INSERT INTO Users(UserEmail, UserName, UserPassword, UserType)
VALUES('a@example.com', 'vinh', '123456789', 'Admin');

CREATE TABLE IF NOT EXISTS ArchivedQuestions(
    QuestionId INT NOT NULL,
    PRIMARY KEY(QuestionId),
    ClassificationId INT NOT NULL,
    QuestionStatement VARCHAR(512) NOT NULL,
    CorrectAnswer VARCHAR(512) NOT NULL,
    Choice1 VARCHAR(512) NOT NULL,
    Choice2 VARCHAR(512) NOT NULL,
    Choice3 VARCHAR(512) NOT NULL,
    Choice4 VARCHAR(512) NOT NULL,
    ImagePath VARCHAR(512),
    DeletedAt VARCHAR(150),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ArchivedClassifications(
    ClassificationId INT NOT NULL,
    Classification VARCHAR(50),
    Description VARCHAR(512),
    PRIMARY KEY(ClassificationId),
    DeletedAt VARCHAR(150),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ArchivedTopics(
    TopicId INT NOT NULL,
    TopicName VARCHAR(50) NOT NULL,
    Description VARCHAR(512),
    PRIMARY KEY(TopicId),
    DeletedAt VARCHAR(150),
    DeletedBy VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS ArchivedQTRelationship(
    QuestionId INT NOT NULL,
    TopicId INT NOT NULL,
    PRIMARY KEY(QuestionId, TopicId)
);

-- FOR TESTING AND DEBUGGING PURPOSES
-- DROP DATABASE DuckyEmulator_QuestionDB;