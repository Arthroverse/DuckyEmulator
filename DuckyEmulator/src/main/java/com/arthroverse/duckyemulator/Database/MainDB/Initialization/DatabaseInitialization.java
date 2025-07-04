package com.arthroverse.duckyemulator.Database.MainDB.Initialization;

import com.arthroverse.duckyemulator.Database.DBService.MySQLService;
import com.arthroverse.duckyemulator.Utilities.PromptAlert.AlertUtil;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;

public final class DatabaseInitialization{
    public static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS DuckyEmulator_QuestionDB;";

    public static final String USE_DATABASE = "USE DuckyEmulator_QuestionDB;";

    public static final String CREATE_CLASSIFICATIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS Classifications(" +
                    "ClassificationId INT NOT NULL AUTO_INCREMENT," +
                    "Classification VARCHAR(50)," +
                    "Description VARCHAR(512)," +
                    "PRIMARY KEY(ClassificationId)," +
                    "Deleted BOOLEAN DEFAULT 0," +
                    "DeletedAt VARCHAR(100)," +
                    "DeletedBy VARCHAR(50)" +
                    ");";

    public static final String CREATE_TOPICS_TABLE =
            "CREATE TABLE IF NOT EXISTS Topics(" +
                    "TopicId INT NOT NULL AUTO_INCREMENT," +
                    "TopicName VARCHAR(50) NOT NULL," +
                    "Description VARCHAR(512)," +
                    "PRIMARY KEY(TopicId)," +
                    "Deleted BOOLEAN DEFAULT 0," +
                    "DeletedAt VARCHAR(100)," +
                    "DeletedBy VARCHAR(50)" +
                    ");";

    public static final String CREATE_QUESTIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS Questions(" +
                    "QuestionId INT NOT NULL AUTO_INCREMENT," +
                    "PRIMARY KEY(QuestionId)," +
                    "ClassificationId INT NOT NULL," +
                    "FOREIGN KEY (ClassificationId) REFERENCES Classifications(ClassificationId)," +
                    "QuestionStatement VARCHAR(512) NOT NULL," +
                    "CorrectAnswer VARCHAR(512) NOT NULL," +
                    "Choice1 VARCHAR(512) NOT NULL," +
                    "Choice2 VARCHAR(512) NOT NULL," +
                    "Choice3 VARCHAR(512) NOT NULL," +
                    "Choice4 VARCHAR(512) NOT NULL," +
                    "ImagePath VARCHAR(512)," +
                    "Deleted BOOLEAN DEFAULT 0," +
                    "DeletedAt VARCHAR(100)," +
                    "DeletedBy VARCHAR(50)" +
                    ");";

    public static final String CREATE_QT_RELATIONSHIP_TABLE =
            "CREATE TABLE IF NOT EXISTS QTRelationship(" +
                    "QuestionId INT NOT NULL," +
                    "FOREIGN KEY (QuestionId) REFERENCES Questions(QuestionId)," +
                    "TopicId INT NOT NULL," +
                    "FOREIGN KEY (TopicId) REFERENCES Topics(TopicId)," +
                    "Deleted BOOLEAN DEFAULT 0," +
                    "DeletedAt VARCHAR(100) DEFAULT ''," +
                    "DeletedBy VARCHAR(50) DEFAULT ''," +
                    "PRIMARY KEY(QuestionId, TopicId, Deleted, DeletedAt, DeletedBy)" +
                    ");";

    public static final String CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS Users(" +
                    "UserEmail VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY(UserEmail)," +
                    "UserName VARCHAR(50) NOT NULL," +
                    "UserPassword VARCHAR(150) NOT NULL," +
                    "IsAdminPrivilged BOOLEAN DEFAULT 0" +
                    ");";

    public static final String CREATE_SESSIONS_TABLE =
            "CREATE TABLE IF NOT EXISTS Sessions(" +
                    "SessionId TIMESTAMP PRIMARY KEY NOT NULL," +
                    "UserEmail VARCHAR(50) NOT NULL," +
                    "FOREIGN KEY (UserEmail) REFERENCES Users(UserEmail)," +
                    "StartTime TIMESTAMP," +
                    "EndTime TIMESTAMP," +
                    "TotalQuestions INT," +
                    "TotalCorrectQuestions INT," +
                    "Deleted BOOLEAN DEFAULT 0," +
                    "DeletedAt VARCHAR(100)," +
                    "DeletedBy VARCHAR(50)" +
                    ");";

    public static final String CREATE_SESSION_HAS_QUESTION_TABLE =
            "CREATE TABLE IF NOT EXISTS Session_has_question(" +
                    "SessionId TIMESTAMP NOT NULL," +
                    "QuestionId INT NOT NULL," +
                    "FOREIGN KEY (SessionId) REFERENCES Sessions(SessionId)," +
                    "FOREIGN KEY (QuestionId) REFERENCES Questions(QuestionId)," +
                    "UserAnswer VARCHAR(512)," +
                    "Deleted BOOLEAN DEFAULT 0," +
                    "DeletedAt VARCHAR(100) DEFAULT ''," +
                    "DeletedBy VARCHAR(50) DEFAULT ''," +
                    "PRIMARY KEY(SessionId, QuestionId, Deleted, DeletedAt, DeletedBy)" +
                    ");";

    // Data holder classes
    public static class ClassificationData {
        public String classification;
        public String description;

        public ClassificationData(String classification, String description) {
            this.classification = classification;
            this.description = description;
        }

        public String getClassification() {
            return classification;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class TopicData {
        public String topicName;
        public String description;

        public TopicData(String topicName, String description) {
            this.topicName = topicName;
            this.description = description;
        }

        public String getTopicName() {
            return topicName;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class QuestionData {
        public int classificationId;
        public String questionStatement;
        public String correctAnswer;
        public String choice1;
        public String choice2;
        public String choice3;
        public String choice4;

        public QuestionData(int classificationId, String questionStatement, String correctAnswer,
                            String choice1, String choice2, String choice3, String choice4) {
            this.classificationId = classificationId;
            this.questionStatement = questionStatement;
            this.correctAnswer = correctAnswer;
            this.choice1 = choice1;
            this.choice2 = choice2;
            this.choice3 = choice3;
            this.choice4 = choice4;
        }

        public int getClassificationId() {
            return classificationId;
        }

        public String getQuestionStatement() {
            return questionStatement;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String getChoice1() {
            return choice1;
        }

        public String getChoice2() {
            return choice2;
        }

        public String getChoice3() {
            return choice3;
        }

        public String getChoice4() {
            return choice4;
        }
    }

    public static class QTRelationshipData {
        public int questionId;
        public int topicId;

        public QTRelationshipData(int questionId, int topicId) {
            this.questionId = questionId;
            this.topicId = topicId;
        }

        public int getQuestionId() {
            return questionId;
        }

        public int getTopicId() {
            return topicId;
        }
    }

    // Data ArrayLists
    public static ArrayList<ClassificationData> classificationsData = new ArrayList<>();
    public static ArrayList<TopicData> topicsData = new ArrayList<>();
    public static ArrayList<QuestionData> questionsData = new ArrayList<>();
    public static ArrayList<QTRelationshipData> qtRelationshipData = new ArrayList<>();

    static {
        // Initialize Classifications Data
        classificationsData.add(new ClassificationData("Beginner", "Basic concepts and fundamentals suitable for newcomers"));
        classificationsData.add(new ClassificationData("Intermediate", "Topics requiring some prior knowledge and experience"));
        classificationsData.add(new ClassificationData("Advanced", "Complex concepts for experienced learners"));
        classificationsData.add(new ClassificationData("Expert", "Specialized knowledge and advanced techniques"));
        classificationsData.add(new ClassificationData("Novice", "Entry-level concepts with detailed explanations"));
        classificationsData.add(new ClassificationData("Upper Beginner", "Building blocks and basic problem-solving"));
        classificationsData.add(new ClassificationData("Lower Intermediate", "Transitioning from basics to more complex topics"));
        classificationsData.add(new ClassificationData("Upper Intermediate", "Advanced concepts with practical applications"));
        classificationsData.add(new ClassificationData("Pre-Advanced", "Bridge between intermediate and advanced topics"));
        classificationsData.add(new ClassificationData("Lower Expert", "Specialized knowledge with industry relevance"));
        classificationsData.add(new ClassificationData("Upper Expert", "Cutting-edge techniques and best practices"));
        classificationsData.add(new ClassificationData("Professional", "Industry-standard practices and patterns"));
        classificationsData.add(new ClassificationData("Senior Professional", "Architecture-level knowledge and decision making"));
        classificationsData.add(new ClassificationData("Master", "Deep expertise in specific domains"));
        classificationsData.add(new ClassificationData("Specialist", "Focused expertise in niche areas"));
        classificationsData.add(new ClassificationData("Domain Expert", "Comprehensive knowledge of specific domains"));
        classificationsData.add(new ClassificationData("Research Level", "Academic and research-oriented concepts"));
        classificationsData.add(new ClassificationData("Industry Expert", "Real-world implementation expertise"));
        classificationsData.add(new ClassificationData("Solution Architect", "System-wide design and integration knowledge"));
        classificationsData.add(new ClassificationData("Technical Lead", "Leadership-level technical expertise"));
        classificationsData.add(new ClassificationData("Principal Engineer", "High-level system design expertise"));
        classificationsData.add(new ClassificationData("Distinguished", "Industry-leading expertise"));
        classificationsData.add(new ClassificationData("Fellow", "Thought leadership and innovation"));
        classificationsData.add(new ClassificationData("Emeritus", "Lifetime achievement level expertise"));

        // Initialize Topics Data
        topicsData.add(new TopicData("Python Basics", "Fundamental concepts of Python programming"));
        topicsData.add(new TopicData("Java Core", "Core Java programming concepts"));
        topicsData.add(new TopicData("JavaScript Essentials", "Essential JavaScript programming concepts"));
        topicsData.add(new TopicData("C++ Programming", "C++ language fundamentals"));
        topicsData.add(new TopicData("SQL Fundamentals", "Basic database query language concepts"));
        topicsData.add(new TopicData("HTML/CSS", "Web markup and styling basics"));
        topicsData.add(new TopicData("React Framework", "React.js development framework"));
        topicsData.add(new TopicData("Node.js", "Server-side JavaScript runtime"));
        topicsData.add(new TopicData("Angular", "Angular framework fundamentals"));
        topicsData.add(new TopicData("Vue.js", "Vue.js framework basics"));
        topicsData.add(new TopicData("Django", "Python web framework"));
        topicsData.add(new TopicData("Spring Boot", "Java Spring Boot framework"));
        topicsData.add(new TopicData("Express.js", "Node.js web application framework"));
        topicsData.add(new TopicData("MongoDB", "NoSQL database concepts"));
        topicsData.add(new TopicData("PostgreSQL", "Advanced relational database"));
        topicsData.add(new TopicData("Redis", "In-memory data structure store"));
        topicsData.add(new TopicData("Git Basics", "Version control fundamentals"));
        topicsData.add(new TopicData("Docker Containers", "Container platform basics"));
        topicsData.add(new TopicData("Kubernetes", "Container orchestration"));
        topicsData.add(new TopicData("AWS Services", "Amazon Web Services platform"));
        topicsData.add(new TopicData("Azure Basics", "Microsoft Azure cloud platform"));
        topicsData.add(new TopicData("Google Cloud", "Google Cloud Platform services"));
        topicsData.add(new TopicData("Linux Administration", "Linux system administration"));
        topicsData.add(new TopicData("Windows Server", "Windows server management"));
        topicsData.add(new TopicData("Network Protocols", "Communication protocols"));
        topicsData.add(new TopicData("Cybersecurity Basics", "Basic security concepts"));
        topicsData.add(new TopicData("Encryption", "Data encryption methods"));
        topicsData.add(new TopicData("Authentication", "User authentication systems"));
        topicsData.add(new TopicData("RESTful APIs", "REST architecture style"));
        topicsData.add(new TopicData("GraphQL", "API query language"));
        topicsData.add(new TopicData("Unit Testing", "Code testing fundamentals"));
        topicsData.add(new TopicData("CI/CD Pipeline", "Continuous integration/deployment"));
        topicsData.add(new TopicData("Agile Methods", "Agile development practices"));
        topicsData.add(new TopicData("Scrum", "Scrum framework implementation"));
        topicsData.add(new TopicData("Data Structures", "Basic data structure concepts"));
        topicsData.add(new TopicData("Algorithms", "Algorithm implementation"));
        topicsData.add(new TopicData("Machine Learning", "ML fundamentals"));
        topicsData.add(new TopicData("Deep Learning", "Neural network concepts"));
        topicsData.add(new TopicData("Big Data", "Large-scale data processing"));
        topicsData.add(new TopicData("Data Analytics", "Data analysis methods"));
        topicsData.add(new TopicData("UI Design", "User interface design"));
        topicsData.add(new TopicData("UX Principles", "User experience concepts"));
        topicsData.add(new TopicData("Mobile UI", "Mobile interface design"));
        topicsData.add(new TopicData("iOS Development", "Apple iOS development"));
        topicsData.add(new TopicData("Android Dev", "Android app development"));
        topicsData.add(new TopicData("Flutter", "Cross-platform development"));
        topicsData.add(new TopicData("React Native", "Mobile app framework"));
        topicsData.add(new TopicData("WebAssembly", "Low-level web programming"));
        topicsData.add(new TopicData("Microservices", "Distributed system design"));
        topicsData.add(new TopicData("System Design", "Architecture principles"));
        topicsData.add(new TopicData("Code Quality", "Code maintenance and quality"));
        topicsData.add(new TopicData("Performance Optimization", "System optimization"));
        topicsData.add(new TopicData("Security Testing", "Security assessment methods"));
        topicsData.add(new TopicData("Cloud Security", "Cloud platform security"));
        topicsData.add(new TopicData("Blockchain Dev", "Blockchain development"));
        topicsData.add(new TopicData("IoT Programming", "IoT device programming"));
        topicsData.add(new TopicData("Shell Scripting", "Command-line automation"));

        // Initialize Questions Data
        questionsData.add(new QuestionData(1, "What is the correct way to declare a variable in Python?", "variable_name = value", "variable_name = value", "var variable_name = value", "dim variable_name = value", "let variable_name = value"));
        questionsData.add(new QuestionData(2, "Which of the following is NOT a valid HTTP request method?", "INJECT", "GET", "POST", "INJECT", "DELETE"));
        questionsData.add(new QuestionData(3, "In React.js, what hook is used to perform side effects?", "useEffect", "useState", "useMemo", "useEffect", "useReducer"));
        questionsData.add(new QuestionData(4, "What is the time complexity of binary search?", "O(log n)", "O(n)", "O(log n)", "O(n²)", "O(1)"));
        questionsData.add(new QuestionData(5, "Which command creates a new branch and switches to it in Git?", "git checkout -b branch_name", "git branch new branch_name", "git create branch_name", "git checkout -b branch_name", "git switch branch_name"));
        questionsData.add(new QuestionData(15, "What is the primary purpose of containerization in Docker?", "Application isolation and portability", "Application isolation and portability", "Data encryption", "Network optimization", "User authentication"));
        questionsData.add(new QuestionData(3, "What is the purpose of the \"useCallback\" hook in React?", "To memoize functions", "To manage state", "To memoize functions", "To handle side effects", "To control routing"));
        questionsData.add(new QuestionData(2, "Which CSS property is used to create a grid layout?", "display: grid", "display: flex", "display: block", "display: grid", "display: inline-grid"));
        questionsData.add(new QuestionData(4, "In GraphQL, what is the purpose of mutations?", "To modify server-side data", "To query data", "To authenticate users", "To validate schemas", "To modify server-side data"));
        questionsData.add(new QuestionData(3, "What is the purpose of an index in PostgreSQL?", "To improve query performance", "To store data", "To improve query performance", "To validate data", "To backup data"));
        questionsData.add(new QuestionData(4, "Which of these is NOT a valid MongoDB aggregation stage?", "SELECT", "$match", "SELECT", "$group", "$project"));
        questionsData.add(new QuestionData(2, "What is the primary purpose of Redis?", "In-memory data caching", "Full-text search", "In-memory data caching", "Document storage", "Graph database"));
        questionsData.add(new QuestionData(4, "Which AWS service is used for container orchestration?", "ECS/EKS", "EC2", "S3", "ECS/EKS", "RDS"));
        questionsData.add(new QuestionData(3, "What is the purpose of a Dockerfile ENTRYPOINT?", "Specify the main executable", "Copy files", "Set environment variables", "Specify the main executable", "Install packages"));
        questionsData.add(new QuestionData(2, "Which command deploys a stack in Azure CLI?", "az deployment group create", "az group deploy", "az deployment group create", "az stack deploy", "az resource create"));
        questionsData.add(new QuestionData(2, "What is the purpose of JWT in authentication?", "Stateless authentication", "Database encryption", "Password hashing", "Stateless authentication", "Session management"));
        questionsData.add(new QuestionData(3, "Which design pattern is most suitable for handling different payment methods in an e-commerce system?", "Strategy Pattern", "Singleton Pattern", "Observer Pattern", "Strategy Pattern", "Factory Pattern"));
        questionsData.add(new QuestionData(4, "What is the role of a service mesh in microservices architecture?", "Handle service-to-service communication", "Database management", "User interface", "File storage", "Handle service-to-service communication"));
        questionsData.add(new QuestionData(3, "What is the purpose of CORS in web security?", "Control resource access across domains", "Encrypt passwords", "Control resource access across domains", "Manage user sessions", "Handle authentication"));
        questionsData.add(new QuestionData(4, "Which encryption method is asymmetric?", "RSA", "AES", "DES", "Blowfish", "RSA"));
        questionsData.add(new QuestionData(2, "What is the main purpose of penetration testing?", "Identify security vulnerabilities", "Optimize performance", "Identify security vulnerabilities", "Debug code", "Monitor systems"));
        questionsData.add(new QuestionData(4, "Which algorithm is NOT suitable for classification problems?", "K-means", "Random Forest", "SVM", "Logistic Regression", "K-means"));
        questionsData.add(new QuestionData(3, "What is the purpose of dropout layers in neural networks?", "Prevent overfitting", "Increase accuracy", "Prevent overfitting", "Speed up training", "Reduce memory usage"));
        questionsData.add(new QuestionData(2, "Which evaluation metric is most suitable for imbalanced classification problems?", "F1 Score", "Accuracy", "F1 Score", "Error Rate", "Mean Squared Error"));
        questionsData.add(new QuestionData(3, "What is the primary benefit of using TypeScript over JavaScript?", "Static type checking", "Better performance", "Static type checking", "Smaller bundle size", "Built-in security"));
        questionsData.add(new QuestionData(4, "Which Shell command is used for finding text patterns in files?", "grep", "find", "sed", "awk", "grep"));
        questionsData.add(new QuestionData(2, "What is the purpose of the virtual keyword in C++?", "Enable polymorphism", "Improve performance", "Enable polymorphism", "Memory management", "Template creation"));
        questionsData.add(new QuestionData(3, "In Spring Boot, what is the purpose of @Autowired?", "Dependency injection", "Request mapping", "Dependency injection", "Exception handling", "Data validation"));
        questionsData.add(new QuestionData(4, "Which NoSQL database type is most suitable for hierarchical data?", "Document store", "Key-value store", "Column store", "Time series", "Document store"));
        questionsData.add(new QuestionData(2, "What is the main purpose of CI/CD pipelines?", "Automate build and deployment", "Monitor performance", "Automate build and deployment", "Store source code", "Manage databases"));
        questionsData.add(new QuestionData(3, "Which Flutter widget is used for responsive layouts?", "LayoutBuilder", "Container", "LayoutBuilder", "Column", "Row"));
        questionsData.add(new QuestionData(4, "What is the role of a Kubernetes Service?", "Network abstraction for pods", "Container runtime", "Storage management", "User interface", "Network abstraction for pods"));
        questionsData.add(new QuestionData(2, "Which HTTP status code indicates a successful creation?", "201", "200", "201", "204", "301"));
        questionsData.add(new QuestionData(3, "What is the purpose of lazy loading in web development?", "Improve initial load time", "Enhance security", "Improve initial load time", "Reduce server load", "Manage state"));
        questionsData.add(new QuestionData(4, "Which design principle suggests \"Program to an interface, not an implementation\"?", "Dependency Inversion", "Single Responsibility", "Open/Closed", "Liskov Substitution", "Dependency Inversion"));
        questionsData.add(new QuestionData(2, "What is the main purpose of WebSocket protocol?", "Real-time bidirectional communication", "Data encryption", "Real-time bidirectional communication", "File transfer", "Authentication"));
        questionsData.add(new QuestionData(3, "Which React Native component is equivalent to div in HTML?", "View", "Container", "View", "Box", "Section"));
        questionsData.add(new QuestionData(4, "What is the purpose of blockchain sharding?", "Improve scalability", "Enhance security", "Reduce costs", "Increase decentralization", "Improve scalability"));
        questionsData.add(new QuestionData(2, "Which Git command shows commit history?", "git log", "git status", "git log", "git show", "git history"));
        questionsData.add(new QuestionData(3, "What is the primary use of Redis pub/sub?", "Message broadcasting", "Data caching", "Message broadcasting", "Session management", "Queue processing"));
        questionsData.add(new QuestionData(4, "Which AWS service is best for serverless computing?", "Lambda", "EC2", "ECS", "Fargate", "Lambda"));
        questionsData.add(new QuestionData(2, "What is the purpose of npm in Node.js?", "Package management", "Server runtime", "Package management", "Database management", "Testing framework"));
        questionsData.add(new QuestionData(3, "Which testing type focuses on component integration?", "Integration testing", "Unit testing", "Integration testing", "E2E testing", "Stress testing"));

        // Initialize QT Relationship Data
        qtRelationshipData.add(new QTRelationshipData(1, 1));
        qtRelationshipData.add(new QTRelationshipData(1, 35));
        qtRelationshipData.add(new QTRelationshipData(2, 25));
        qtRelationshipData.add(new QTRelationshipData(2, 29));
        qtRelationshipData.add(new QTRelationshipData(3, 7));
        qtRelationshipData.add(new QTRelationshipData(3, 3));
        qtRelationshipData.add(new QTRelationshipData(3, 33));
        qtRelationshipData.add(new QTRelationshipData(4, 35));
        qtRelationshipData.add(new QTRelationshipData(4, 36));
        qtRelationshipData.add(new QTRelationshipData(5, 17));
        qtRelationshipData.add(new QTRelationshipData(5, 32));
        qtRelationshipData.add(new QTRelationshipData(6, 7));
        qtRelationshipData.add(new QTRelationshipData(6, 3));
        qtRelationshipData.add(new QTRelationshipData(6, 49));
        qtRelationshipData.add(new QTRelationshipData(7, 6));
        qtRelationshipData.add(new QTRelationshipData(7, 41));
        qtRelationshipData.add(new QTRelationshipData(8, 30));
        qtRelationshipData.add(new QTRelationshipData(8, 29));
        qtRelationshipData.add(new QTRelationshipData(8, 48));
        qtRelationshipData.add(new QTRelationshipData(9, 15));
        qtRelationshipData.add(new QTRelationshipData(9, 5));
        qtRelationshipData.add(new QTRelationshipData(9, 49));
        qtRelationshipData.add(new QTRelationshipData(10, 14));
        qtRelationshipData.add(new QTRelationshipData(10, 39));
        qtRelationshipData.add(new QTRelationshipData(10, 40));
        qtRelationshipData.add(new QTRelationshipData(11, 16));
        qtRelationshipData.add(new QTRelationshipData(11, 48));
        qtRelationshipData.add(new QTRelationshipData(11, 50));
        qtRelationshipData.add(new QTRelationshipData(12, 20));
        qtRelationshipData.add(new QTRelationshipData(12, 19));
        qtRelationshipData.add(new QTRelationshipData(12, 18));
        qtRelationshipData.add(new QTRelationshipData(13, 18));
        qtRelationshipData.add(new QTRelationshipData(13, 32));
        qtRelationshipData.add(new QTRelationshipData(13, 48));
        qtRelationshipData.add(new QTRelationshipData(14, 21));
        qtRelationshipData.add(new QTRelationshipData(14, 20));
        qtRelationshipData.add(new QTRelationshipData(14, 32));
        qtRelationshipData.add(new QTRelationshipData(15, 28));
        qtRelationshipData.add(new QTRelationshipData(15, 26));
        qtRelationshipData.add(new QTRelationshipData(15, 51));
        qtRelationshipData.add(new QTRelationshipData(16, 48));
        qtRelationshipData.add(new QTRelationshipData(16, 49));
        qtRelationshipData.add(new QTRelationshipData(16, 45));
        qtRelationshipData.add(new QTRelationshipData(17, 48));
        qtRelationshipData.add(new QTRelationshipData(17, 45));
        qtRelationshipData.add(new QTRelationshipData(17, 32));
        qtRelationshipData.add(new QTRelationshipData(18, 26));
        qtRelationshipData.add(new QTRelationshipData(18, 28));
        qtRelationshipData.add(new QTRelationshipData(18, 29));
        qtRelationshipData.add(new QTRelationshipData(19, 27));
        qtRelationshipData.add(new QTRelationshipData(19, 26));
        qtRelationshipData.add(new QTRelationshipData(19, 52));
        qtRelationshipData.add(new QTRelationshipData(20, 51));
        qtRelationshipData.add(new QTRelationshipData(20, 26));
        qtRelationshipData.add(new QTRelationshipData(20, 52));
        qtRelationshipData.add(new QTRelationshipData(21, 37));
        qtRelationshipData.add(new QTRelationshipData(21, 38));
        qtRelationshipData.add(new QTRelationshipData(21, 40));
        qtRelationshipData.add(new QTRelationshipData(22, 38));
        qtRelationshipData.add(new QTRelationshipData(22, 37));
        qtRelationshipData.add(new QTRelationshipData(22, 39));
        qtRelationshipData.add(new QTRelationshipData(23, 37));
        qtRelationshipData.add(new QTRelationshipData(23, 40));
        qtRelationshipData.add(new QTRelationshipData(23, 39));
        qtRelationshipData.add(new QTRelationshipData(24, 3));
        qtRelationshipData.add(new QTRelationshipData(24, 49));
        qtRelationshipData.add(new QTRelationshipData(24, 33));
        qtRelationshipData.add(new QTRelationshipData(25, 56));
        qtRelationshipData.add(new QTRelationshipData(25, 23));
        qtRelationshipData.add(new QTRelationshipData(25, 55));
        qtRelationshipData.add(new QTRelationshipData(26, 4));
        qtRelationshipData.add(new QTRelationshipData(26, 35));
        qtRelationshipData.add(new QTRelationshipData(26, 49));
        qtRelationshipData.add(new QTRelationshipData(27, 12));
        qtRelationshipData.add(new QTRelationshipData(27, 48));
        qtRelationshipData.add(new QTRelationshipData(27, 45));
        qtRelationshipData.add(new QTRelationshipData(28, 14));
        qtRelationshipData.add(new QTRelationshipData(28, 39));
        qtRelationshipData.add(new QTRelationshipData(28, 48));
        qtRelationshipData.add(new QTRelationshipData(29, 32));
        qtRelationshipData.add(new QTRelationshipData(29, 48));
        qtRelationshipData.add(new QTRelationshipData(29, 49));
        qtRelationshipData.add(new QTRelationshipData(30, 46));
        qtRelationshipData.add(new QTRelationshipData(30, 41));
        qtRelationshipData.add(new QTRelationshipData(30, 42));
        qtRelationshipData.add(new QTRelationshipData(31, 19));
        qtRelationshipData.add(new QTRelationshipData(31, 18));
        qtRelationshipData.add(new QTRelationshipData(31, 48));
        qtRelationshipData.add(new QTRelationshipData(32, 25));
        qtRelationshipData.add(new QTRelationshipData(32, 29));
        qtRelationshipData.add(new QTRelationshipData(32, 28));
        qtRelationshipData.add(new QTRelationshipData(33, 7));
        qtRelationshipData.add(new QTRelationshipData(33, 49));
        qtRelationshipData.add(new QTRelationshipData(33, 50));
        qtRelationshipData.add(new QTRelationshipData(34, 48));
        qtRelationshipData.add(new QTRelationshipData(34, 49));
        qtRelationshipData.add(new QTRelationshipData(34, 45));
        qtRelationshipData.add(new QTRelationshipData(35, 25));
        qtRelationshipData.add(new QTRelationshipData(35, 29));
        qtRelationshipData.add(new QTRelationshipData(35, 28));
        qtRelationshipData.add(new QTRelationshipData(36, 47));
        qtRelationshipData.add(new QTRelationshipData(36, 44));
        qtRelationshipData.add(new QTRelationshipData(36, 41));
        qtRelationshipData.add(new QTRelationshipData(37, 54));
        qtRelationshipData.add(new QTRelationshipData(37, 48));
        qtRelationshipData.add(new QTRelationshipData(37, 39));
        qtRelationshipData.add(new QTRelationshipData(38, 17));
        qtRelationshipData.add(new QTRelationshipData(38, 32));
        qtRelationshipData.add(new QTRelationshipData(38, 49));
        qtRelationshipData.add(new QTRelationshipData(39, 16));
        qtRelationshipData.add(new QTRelationshipData(39, 48));
        qtRelationshipData.add(new QTRelationshipData(39, 50));
        qtRelationshipData.add(new QTRelationshipData(40, 20));
        qtRelationshipData.add(new QTRelationshipData(40, 45));
        qtRelationshipData.add(new QTRelationshipData(40, 48));
        qtRelationshipData.add(new QTRelationshipData(41, 8));
        qtRelationshipData.add(new QTRelationshipData(41, 29));
        qtRelationshipData.add(new QTRelationshipData(41, 32));
        qtRelationshipData.add(new QTRelationshipData(42, 31));
        qtRelationshipData.add(new QTRelationshipData(42, 33));
        qtRelationshipData.add(new QTRelationshipData(42, 49));
        qtRelationshipData.add(new QTRelationshipData(43, 30));
        qtRelationshipData.add(new QTRelationshipData(43, 29));
        qtRelationshipData.add(new QTRelationshipData(43, 48));
    }


    public static void initialize(){
        try(
                Connection conn = MySQLService.getFirstConnection();
                Statement stmt = conn.createStatement();
                ){
            stmt.executeUpdate(CREATE_DATABASE);
            stmt.executeUpdate(USE_DATABASE);
            stmt.executeUpdate(CREATE_CLASSIFICATIONS_TABLE);
            stmt.executeUpdate(CREATE_TOPICS_TABLE);
            stmt.executeUpdate(CREATE_QUESTIONS_TABLE);
            stmt.executeUpdate(CREATE_QT_RELATIONSHIP_TABLE);
            stmt.executeUpdate(CREATE_USERS_TABLE);
            stmt.executeUpdate(CREATE_SESSIONS_TABLE);
            stmt.executeUpdate(CREATE_SESSION_HAS_QUESTION_TABLE);
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e), "DuckyEmulator");
        }
    }

    public static void insertSampleData(){
        String sqlCheckIfDataImported = "SELECT COUNT(QuestionId) FROM Questions;";
        try(
                Connection conn1 = MySQLService.getConnection();
                Statement stmt = conn1.createStatement();
                ){
            ResultSet rs = stmt.executeQuery(sqlCheckIfDataImported);
            rs.next();
            if(rs.getInt(1) != 0) return;
            else{
                try(Connection conn = MySQLService.getFirstConnection()){
                    conn.createStatement().executeUpdate(USE_DATABASE);

                    // Insert Classifications
                    String insertClassificationSQL = "INSERT INTO Classifications (Classification, Description) VALUES (?, ?)";
                    try(java.sql.PreparedStatement pstmt = conn.prepareStatement(insertClassificationSQL)){
                        for(ClassificationData classification : classificationsData){
                            pstmt.setString(1, classification.getClassification());
                            pstmt.setString(2, classification.getDescription());
                            pstmt.addBatch();
                        }
                        pstmt.executeBatch();
                    }

                    // Insert Topics
                    String insertTopicSQL = "INSERT INTO Topics (TopicName, Description) VALUES (?, ?)";
                    try(java.sql.PreparedStatement pstmt = conn.prepareStatement(insertTopicSQL)){
                        for(TopicData topic : topicsData){
                            pstmt.setString(1, topic.getTopicName());
                            pstmt.setString(2, topic.getDescription());
                            pstmt.addBatch();
                        }
                        pstmt.executeBatch();
                    }

                    // Insert Questions
                    String insertQuestionSQL = "INSERT INTO Questions (ClassificationId, QuestionStatement, CorrectAnswer, Choice1, Choice2, Choice3, Choice4) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try(java.sql.PreparedStatement pstmt = conn.prepareStatement(insertQuestionSQL)){
                        for(QuestionData question : questionsData){
                            pstmt.setInt(1, question.getClassificationId());
                            pstmt.setString(2, question.getQuestionStatement());
                            pstmt.setString(3, question.getCorrectAnswer());
                            pstmt.setString(4, question.getChoice1());
                            pstmt.setString(5, question.getChoice2());
                            pstmt.setString(6, question.getChoice3());
                            pstmt.setString(7, question.getChoice4());
                            pstmt.addBatch();
                        }
                        pstmt.executeBatch();
                    }

                    // Insert QT Relationships
                    String insertQTRelationshipSQL = "INSERT INTO QTRelationship (QuestionId, TopicId) VALUES (?, ?)";
                    try(java.sql.PreparedStatement pstmt = conn.prepareStatement(insertQTRelationshipSQL)){
                        for(QTRelationshipData qtRelationship : qtRelationshipData){
                            pstmt.setInt(1, qtRelationship.getQuestionId());
                            pstmt.setInt(2, qtRelationship.getTopicId());
                            pstmt.addBatch();
                        }
                        pstmt.executeBatch();
                    }

                }catch(Exception e){
                    AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e), "DuckyEmulator");
                }
            }
        }catch(Exception e){
            AlertUtil.generateExceptionViewer(AlertUtil.generateExceptionString(e), "DuckyEmulator");
        }
    }
}
