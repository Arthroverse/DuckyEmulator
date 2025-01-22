CREATE DATABASE DuckyEmulator_QuestionDB;

USE DuckyEmulator_QuestionDB;

CREATE TABLE Classifications(
    ClassificationID INT PRIMARY KEY NOT NULL,
    Classification VARCHAR(50) NOT NULL,
    Description VARCHAR(512)
);

CREATE TABLE Topics(
    TopicID INT PRIMARY KEY NOT NULL,
    TopicName VARCHAR(50),
    Description VARCHAR(512)
);

CREATE TABLE Questions(
    QuestionID INT PRIMARY KEY NOT NULL,
    ClassificationID INT NOT NULL,
    FOREIGN KEY (ClassificationID) REFERENCES Classifications(ClassificationID),
    CorrectAnswer VARCHAR(512),
    Choice1 VARCHAR(512),
    Choice2 VARCHAR(512),
    Choice3 VARCHAR(512),
    Choice4 VARCHAR(512),
    ImagePath VARCHAR(512)
);

CREATE TABLE QTRelationship(
    QuestionID INT NOT NULL,
    FOREIGN KEY (QuestionID) REFERENCES Questions(QuestionID),
    TopicID INT NOT NULL,
    FOREIGN KEY (TopicID) REFERENCES Topics(TopicID),
    PRIMARY KEY(QuestionID, TopicID)
);