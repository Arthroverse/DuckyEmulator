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
    TopicId INT NOT NULL,
    FOREIGN KEY (TopicID) REFERENCES Topics(TopicId),
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

-- Insert Classifications
INSERT INTO Classifications (Classification, Description) VALUES
('Easy', 'Basic concept questions suitable for beginners'),
('Medium', 'Intermediate questions requiring good understanding of topics'),
('Hard', 'Advanced questions requiring in-depth knowledge and analysis');

-- Insert Topics
INSERT INTO Topics (TopicName, Description) VALUES
('Data Types', 'Fundamental data types in programming'),
('Control Flow', 'Loops, conditions, and program flow control'),
('Functions', 'Function declaration, parameters, and return values'),
('Arrays', 'Array operations, manipulation, and algorithms'),
('Object-Oriented Programming', 'Classes, objects, inheritance, and polymorphism'),
('Database Design', 'Database schemas, relationships, and normalization'),
('Web Development', 'HTML, CSS, and basic web concepts'),
('Algorithms', 'Basic algorithmic concepts and problem-solving'),
('Network Basics', 'Fundamental networking concepts and protocols'),
('Security', 'Basic security concepts and best practices'),
('Version Control', 'Git basics and version control concepts'),
('Software Testing', 'Basic testing methodologies and practices');

-- Insert Questions
INSERT INTO Questions (ClassificationId, TopicId, QuestionStatement, CorrectAnswer, Choice1, Choice2, Choice3, Choice4, ImagePath) VALUES
-- Data Types (TopicID: 1)
(1, 1, 'Which of the following is NOT a primitive data type in Java?', 'String', 'int', 'boolean', 'char', 'String', NULL),
(2, 1, 'What is the size of a long data type in Java?', '64 bits', '32 bits', '16 bits', '64 bits', '128 bits', NULL),
(3, 1, 'In Python, what is the result of type(1/2)?', 'float', 'int', 'float', 'double', 'decimal', NULL),

-- Control Flow (TopicID: 2)
(1, 2, 'What is the correct syntax for an infinite loop in C++?', 'while(true)', 'while(1)', 'while(true)', 'loop(infinite)', 'for(;;)', NULL),
(2, 2, 'Which statement is used to exit a loop in Python?', 'break', 'exit', 'break', 'continue', 'return', NULL),
(3, 2, 'What is the difference between break and continue?', 'break exits the loop, continue skips to next iteration', 'They are the same', 'break skips iteration, continue exits loop', 'break exits the loop, continue skips to next iteration', 'Neither affects loop flow', NULL),

-- Functions (TopicID: 3)
(1, 3, 'What is a function parameter?', 'A variable passed to a function', 'A return value', 'A variable passed to a function', 'A function name', 'A loop counter', NULL),
(2, 3, 'What is function overloading?', 'Multiple functions with same name but different parameters', 'Recursive function', 'Multiple functions with same name but different parameters', 'Function with too many parameters', 'Anonymous function', NULL),
(3, 3, 'What is a pure function?', 'A function that always returns same output for same input', 'Any function that returns a value', 'A function that always returns same output for same input', 'A function without parameters', 'A void function', NULL),

-- Arrays (TopicID: 4)
(1, 4, 'What is the index of the first element in an array?', '0', '1', '0', '-1', 'NULL', NULL),
(2, 4, 'What is array bounds checking?', 'Verifying array access is within valid range', 'Sorting array elements', 'Verifying array access is within valid range', 'Checking array size', 'Finding array length', NULL),
(3, 4, 'What is a multidimensional array?', 'An array of arrays', 'A sorted array', 'An array of arrays', 'A large array', 'A dynamic array', NULL),

-- Object-Oriented Programming (TopicID: 5)
(1, 5, 'What is encapsulation?', 'Bundling data and methods that operate on that data within a single unit', 'Creating objects', 'Bundling data and methods that operate on that data within a single unit', 'Inheritance', 'Method overriding', NULL),
(2, 5, 'What is inheritance?', 'A mechanism that allows a class to inherit properties from another class', 'Creating objects', 'Method overriding', 'A mechanism that allows a class to inherit properties from another class', 'Data hiding', NULL),
(3, 5, 'What is polymorphism?', 'The ability of different classes to be treated as instances of the same class', 'Multiple inheritance', 'The ability of different classes to be treated as instances of the same class', 'Data encapsulation', 'Method overloading', NULL),

-- Database Design (TopicID: 6)
(1, 6, 'What is a primary key?', 'A unique identifier for a record in a table', 'A foreign key', 'A unique identifier for a record in a table', 'An index', 'A constraint', NULL),
(2, 6, 'What is normalization?', 'Process of organizing data to minimize redundancy', 'Adding indexes', 'Process of organizing data to minimize redundancy', 'Creating tables', 'Adding constraints', NULL),
(3, 6, 'What is a foreign key?', 'A field that links to primary key in another table', 'A unique identifier', 'A field that links to primary key in another table', 'An index', 'A constraint', NULL),

-- Web Development (TopicID: 7)
(1, 7, 'What does HTML stand for?', 'HyperText Markup Language', 'High Text Markup Language', 'HyperText Markup Language', 'High Text Making Language', 'HyperText Making Language', NULL),
(2, 7, 'What is CSS used for?', 'Styling web pages', 'Creating web pages', 'Styling web pages', 'Programming web pages', 'Database management', NULL),
(3, 7, 'What is responsive design?', 'Design that adapts to different screen sizes', 'Fast loading design', 'Design that adapts to different screen sizes', 'Colorful design', 'Static design', NULL),

-- Algorithms (TopicID: 8)
(1, 8, 'What is the time complexity of binary search?', 'O(log n)', 'O(n)', 'O(log n)', 'O(n^2)', 'O(1)', NULL),
(2, 8, 'What is a recursive algorithm?', 'An algorithm that calls itself', 'A sorting algorithm', 'An algorithm that calls itself', 'A search algorithm', 'A linear algorithm', NULL),
(3, 8, 'What is the bubble sort algorithm?', 'A simple sorting algorithm that repeatedly steps through the list', 'A search algorithm', 'A simple sorting algorithm that repeatedly steps through the list', 'A recursive algorithm', 'A constant time algorithm', NULL),

-- Network Basics (TopicID: 9)
(1, 9, 'What is IP?', 'Internet Protocol', 'Internet Program', 'Internet Protocol', 'Internet Provider', 'Internet Processing', NULL),
(2, 9, 'What is DNS?', 'Domain Name System', 'Data Network Service', 'Domain Name System', 'Digital Network System', 'Data Name Service', NULL),
(3, 9, 'What is HTTP?', 'HyperText Transfer Protocol', 'High Text Transfer Protocol', 'HyperText Transfer Protocol', 'High Transfer Text Protocol', 'HyperText Transfer Program', NULL),

-- Security (TopicID: 10)
(1, 10, 'What is encryption?', 'Process of converting data into a code to prevent unauthorized access', 'Password protection', 'Process of converting data into a code to prevent unauthorized access', 'Firewall', 'Antivirus', NULL),
(2, 10, 'What is a firewall?', 'A network security system that monitors traffic', 'An antivirus program', 'A network security system that monitors traffic', 'An encryption tool', 'A password manager', NULL),
(3, 10, 'What is authentication?', 'Process of verifying identity', 'Encryption', 'Process of verifying identity', 'Authorization', 'Access control', NULL),

-- Version Control (TopicID: 11)
(1, 11, 'What is Git?', 'A distributed version control system', 'A programming language', 'A distributed version control system', 'An IDE', 'A compiler', NULL),
(2, 11, 'What is a commit?', 'A snapshot of changes in the repository', 'A branch', 'A snapshot of changes in the repository', 'A merge', 'A clone', NULL),
(3, 11, 'What is a branch in Git?', 'A parallel version of the repository', 'A commit', 'A parallel version of the repository', 'A merge', 'A clone', NULL),

-- Software Testing (TopicID: 12)
(1, 12, 'What is unit testing?', 'Testing individual components of software', 'Testing entire system', 'Testing individual components of software', 'Integration testing', 'System testing', NULL),
(2, 12, 'What is regression testing?', 'Testing to ensure new code hasn''t broken existing functionality', 'Unit testing', 'Testing to ensure new code hasn''t broken existing functionality', 'Integration testing', 'System testing', NULL),
(3, 12, 'What is integration testing?', 'Testing combined components of software', 'Unit testing', 'Testing combined components of software', 'Regression testing', 'System testing', NULL),

-- Additional Questions for various topics to reach 50
(1, 1, 'What is type casting in programming?', 'Converting one data type to another', 'Creating new types', 'Converting one data type to another', 'Deleting types', 'Comparing types', NULL),
(2, 2, 'What is a switch statement?', 'A multi-way branch statement', 'A loop statement', 'A multi-way branch statement', 'An if statement', 'A goto statement', NULL),
(3, 3, 'What is recursion?', 'A function calling itself', 'A loop type', 'A function calling itself', 'A data structure', 'An algorithm', NULL),
(1, 4, 'What is array concatenation?', 'Combining two arrays into one', 'Splitting arrays', 'Combining two arrays into one', 'Sorting arrays', 'Searching arrays', NULL),
(2, 5, 'What is method overriding?', 'Redefining a method in a derived class', 'Creating new methods', 'Redefining a method in a derived class', 'Deleting methods', 'Hiding methods', NULL),
(3, 6, 'What is an index in databases?', 'A data structure improving the speed of data retrieval', 'A primary key', 'A data structure improving the speed of data retrieval', 'A foreign key', 'A table', NULL),
(1, 7, 'What is JavaScript?', 'A programming language for web pages', 'A markup language', 'A programming language for web pages', 'A styling language', 'A database language', NULL),
(2, 8, 'What is linear search?', 'Sequentially checking each element in a list', 'Binary search', 'Sequentially checking each element in a list', 'Quick sort', 'Merge sort', NULL),
(3, 9, 'What is TCP/IP?', 'A suite of communication protocols', 'A programming language', 'A suite of communication protocols', 'A web browser', 'An operating system', NULL),
(1, 10, 'What is a virus?', 'Malicious software that can replicate itself', 'A computer program', 'Malicious software that can replicate itself', 'An antivirus', 'A firewall', NULL),
(2, 11, 'What is merging in Git?', 'Combining changes from different branches', 'Creating branches', 'Combining changes from different branches', 'Deleting branches', 'Copying branches', NULL),
(3, 12, 'What is black box testing?', 'Testing without knowledge of internal code', 'White box testing', 'Testing without knowledge of internal code', 'Unit testing', 'Integration testing', NULL),
(1, 1, 'What is a boolean?', 'A data type with true/false values', 'A number type', 'A data type with true/false values', 'A string type', 'An object type', NULL),
(2, 2, 'What is a do-while loop?', 'A loop that executes at least once', 'A while loop', 'A loop that executes at least once', 'A for loop', 'An infinite loop', NULL),
(3, 3, 'What is a callback function?', 'A function passed as an argument to another function', 'A main function', 'A function passed as an argument to another function', 'A recursive function', 'A void function', NULL);

-- Insert QTRelationship entries
INSERT INTO QTRelationship (QuestionId, TopicId) VALUES
-- Data Types questions (Topic 1)
(1, 1), (2, 1), (3, 1), (37, 1), (46, 1),

-- Control Flow questions (Topic 2)
(4, 2), (5, 2), (6, 2), (38, 2), (47, 2),

-- Functions questions (Topic 3)
(7, 3), (8, 3), (9, 3), (39, 3), (48, 3),

-- Arrays questions (Topic 4)
(10, 4), (11, 4), (12, 4), (40, 4),

-- OOP questions (Topic 5)
(13, 5), (14, 5), (15, 5), (41, 5),

-- Database Design questions (Topic 6)
(16, 6), (17, 6), (18, 6), (42, 6),

-- Web Development questions (Topic 7)
(19, 7), (20, 7), (21, 7), (43, 7),

-- Algorithms questions (Topic 8)
(22, 8), (23, 8), (24, 8), (44, 8),

-- Network Basics questions (Topic 9)
(25, 9), (26, 9), (27, 9), (45, 9),

-- Security questions (Topic 10)
(28, 10), (29, 10), (30, 10), (49, 10),

-- Version Control questions (Topic 11)
(31, 11), (32, 11), (33, 11), (50, 11),

-- Software Testing questions (Topic 12)
(34, 12), (35, 12), (36, 12);

-- FOR TESTING AND DEBUGGING PURPOSES
-- DROP DATABASE DuckyEmulator_QuestionDB;