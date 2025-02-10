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
CREATE DATABASE DuckyEmulator_QuestionDB;

USE DuckyEmulator_QuestionDB;

CREATE TABLE Classifications(
    ClassificationId INT NOT NULL AUTO_INCREMENT,
    Classification VARCHAR(50),
    Description VARCHAR(512),
    PRIMARY KEY(ClassificationId)
);

CREATE TABLE Topics(
    TopicId INT NOT NULL AUTO_INCREMENT,
    TopicName VARCHAR(50) NOT NULL,
    Description VARCHAR(512),
    PRIMARY KEY(TopicId)
);

CREATE TABLE Questions(
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

CREATE TABLE QTRelationship(
    QuestionId INT NOT NULL,
    FOREIGN KEY (QuestionId) REFERENCES Questions(QuestionId),
    TopicId INT NOT NULL,
    FOREIGN KEY (TopicId) REFERENCES Topics(TopicId),
    PRIMARY KEY(QuestionId, TopicId)
);

SELECT RESULT2.*, C.Classification, C.Description AS Classification_Descriptions
FROM Classifications AS C
JOIN (SELECT RESULT.*, T.TopicName, T.Description AS Topic_Descriptions
      FROM Topics AS T
               JOIN (SELECT Q.*, QT.TopicId
                     FROM Questions AS Q
                              JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId)
          AS RESULT ON RESULT.TopicId = T.TopicId) AS RESULT2 ON RESULT2.ClassificationId = C.ClassificationId;

    SELECT Q.*, T.TopicId, T.TopicName, T.Description AS Topic_Description, C.Classification, C.Description AS Classification_Description
    FROM Questions AS Q
    JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId
    JOIN Topics AS T ON T.TopicId = QT.TopicId
    JOIN Classifications AS C ON C.ClassificationId = Q.ClassificationId
    ORDER BY Q.QuestionId;


SELECT Q.*, T.TopicId, T.TopicName, T.Description AS Topic_Description,
       C.Classification, C.Description AS Classification_Description
FROM Questions AS Q
         JOIN (
             SELECT QuestionId
             FROM Questions
             ORDER BY QuestionId
             LIMIT 10 OFFSET 0
) AS RQ ON Q.QuestionId = RQ.QuestionId
         JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId
         JOIN Topics AS T ON T.TopicId = QT.TopicId
         JOIN Classifications AS C ON C.ClassificationId = Q.ClassificationId
ORDER BY Q.QuestionId;


SELECT QuestionId
FROM Questions
ORDER BY QuestionId
LIMIT 10 OFFSET 0;

SELECT Q.*, T.TopicId, T.TopicName, T.Description AS Topic_Description,
       C.Classification, C.Description AS Classification_Description
FROM Questions AS Q
         JOIN QTRelationship AS QT ON Q.QuestionId = QT.QuestionId
         JOIN Topics AS T ON T.TopicId = QT.TopicId
         JOIN Classifications AS C ON C.ClassificationId = Q.ClassificationId
WHERE Q.QuestionId IN (
    SELECT *
    FROM(
            SELECT QuestionId
            FROM Questions
            ORDER BY QuestionId

        ) AS Limited_Table
    )
ORDER BY Q.QuestionId;


SELECT COUNT(QuestionId)
FROM Questions

-- FOR TESTING AND DEBUGGING PURPOSES
-- DROP DATABASE DuckyEmulator_QuestionDB;