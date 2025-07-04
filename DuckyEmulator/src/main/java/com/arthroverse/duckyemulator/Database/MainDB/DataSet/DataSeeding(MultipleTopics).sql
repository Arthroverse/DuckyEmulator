USE DuckyEmulator_QuestionDB;
INSERT INTO Classifications (Classification, Description) VALUES
                                                                       ('Beginner', 'Basic concepts and fundamentals suitable for newcomers'),
                                                                       ('Intermediate', 'Topics requiring some prior knowledge and experience'),
                                                                       ('Advanced', 'Complex concepts for experienced learners'),
                                                                       ('Expert', 'Specialized knowledge and advanced techniques'),
                                                                       ('Novice', 'Entry-level concepts with detailed explanations'),
                                                                       ('Upper Beginner', 'Building blocks and basic problem-solving'),
                                                                       ('Lower Intermediate', 'Transitioning from basics to more complex topics'),
                                                                       ('Upper Intermediate', 'Advanced concepts with practical applications'),
                                                                       ('Pre-Advanced', 'Bridge between intermediate and advanced topics'),
                                                                       ('Lower Expert', 'Specialized knowledge with industry relevance'),
                                                                       ('Upper Expert', 'Cutting-edge techniques and best practices'),
                                                                       ('Professional', 'Industry-standard practices and patterns'),
                                                                       ('Senior Professional', 'Architecture-level knowledge and decision making'),
                                                                       ('Master', 'Deep expertise in specific domains'),
                                                                       ('Specialist', 'Focused expertise in niche areas'),
                                                                       ('Domain Expert', 'Comprehensive knowledge of specific domains'),
                                                                       ('Research Level', 'Academic and research-oriented concepts'),
                                                                       ('Industry Expert', 'Real-world implementation expertise'),
                                                                       ('Solution Architect', 'System-wide design and integration knowledge'),
                                                                       ('Technical Lead', 'Leadership-level technical expertise'),
                                                                       ('Principal Engineer', 'High-level system design expertise'),
                                                                       ('Distinguished', 'Industry-leading expertise'),
                                                                       ('Fellow', 'Thought leadership and innovation'),
                                                                       ('Emeritus', 'Lifetime achievement level expertise');
-- Topics Insert Statements
INSERT INTO Topics (TopicName, Description) VALUES
                                                         ('Python Basics', 'Fundamental concepts of Python programming'),
                                                         ('Java Core', 'Core Java programming concepts'),
                                                         ('JavaScript Essentials', 'Essential JavaScript programming concepts'),
                                                         ('C++ Programming', 'C++ language fundamentals'),
                                                         ('SQL Fundamentals', 'Basic database query language concepts'),
                                                         ('HTML/CSS', 'Web markup and styling basics'),
                                                         ('React Framework', 'React.js development framework'),
                                                         ('Node.js', 'Server-side JavaScript runtime'),
                                                         ('Angular', 'Angular framework fundamentals'),
                                                         ('Vue.js', 'Vue.js framework basics'),
                                                         ('Django', 'Python web framework'),
                                                         ('Spring Boot', 'Java Spring Boot framework'),
                                                         ('Express.js', 'Node.js web application framework'),
                                                         ('MongoDB', 'NoSQL database concepts'),
                                                         ('PostgreSQL', 'Advanced relational database'),
                                                         ('Redis', 'In-memory data structure store'),
                                                         ('Git Basics', 'Version control fundamentals'),
                                                         ('Docker Containers', 'Container platform basics'),
                                                         ('Kubernetes', 'Container orchestration'),
                                                         ('AWS Services', 'Amazon Web Services platform'),
                                                         ('Azure Basics', 'Microsoft Azure cloud platform'),
                                                         ('Google Cloud', 'Google Cloud Platform services'),
                                                         ('Linux Administration', 'Linux system administration'),
                                                         ('Windows Server', 'Windows server management'),
                                                         ('Network Protocols', 'Communication protocols'),
                                                         ('Cybersecurity Basics', 'Basic security concepts'),
                                                         ('Encryption', 'Data encryption methods'),
                                                         ('Authentication', 'User authentication systems'),
                                                         ('RESTful APIs', 'REST architecture style'),
                                                         ('GraphQL', 'API query language'),
                                                         ('Unit Testing', 'Code testing fundamentals'),
                                                         ('CI/CD Pipeline', 'Continuous integration/deployment'),
                                                         ('Agile Methods', 'Agile development practices'),
                                                         ('Scrum', 'Scrum framework implementation'),
                                                         ('Data Structures', 'Basic data structure concepts'),
                                                         ('Algorithms', 'Algorithm implementation'),
                                                         ('Machine Learning', 'ML fundamentals'),
                                                         ('Deep Learning', 'Neural network concepts'),
                                                         ('Big Data', 'Large-scale data processing'),
                                                         ('Data Analytics', 'Data analysis methods'),
                                                         ('UI Design', 'User interface design'),
                                                         ('UX Principles', 'User experience concepts'),
                                                         ('Mobile UI', 'Mobile interface design'),
                                                         ('iOS Development', 'Apple iOS development'),
                                                         ('Android Dev', 'Android app development'),
                                                         ('Flutter', 'Cross-platform development'),
                                                         ('React Native', 'Mobile app framework'),
                                                         ('WebAssembly', 'Low-level web programming'),
                                                         ('Microservices', 'Distributed system design'),
                                                         ('System Design', 'Architecture principles'),
                                                         ('Code Quality', 'Code maintenance and quality'),
                                                         ('Performance Optimization', 'System optimization'),
                                                         ('Security Testing', 'Security assessment methods'),
                                                         ('Cloud Security', 'Cloud platform security'),
                                                         ('Blockchain Dev', 'Blockchain development'),
                                                         ('IoT Programming', 'IoT device programming'),
                                                         ('Shell Scripting', 'Command-line automation');
USE DuckyEmulator_QuestionDB;

INSERT INTO Questions (ClassificationId, QuestionStatement, CorrectAnswer, Choice1, Choice2, Choice3, Choice4) VALUES
                                                                                                                   (1, 'What is the correct way to declare a variable in Python?', 'variable_name = value', 'variable_name = value', 'var variable_name = value', 'dim variable_name = value', 'let variable_name = value'),
                                                                                                                   (2, 'Which of the following is NOT a valid HTTP request method?', 'INJECT', 'GET', 'POST', 'INJECT', 'DELETE'),
                                                                                                                   (3, 'In React.js, what hook is used to perform side effects?', 'useEffect', 'useState', 'useMemo', 'useEffect', 'useReducer'),
                                                                                                                   (4, 'What is the time complexity of binary search?', 'O(log n)', 'O(n)', 'O(log n)', 'O(n²)', 'O(1)'),
                                                                                                                   (5, 'Which command creates a new branch and switches to it in Git?', 'git checkout -b branch_name', 'git branch new branch_name', 'git create branch_name', 'git checkout -b branch_name', 'git switch branch_name'),

                                                                                                                   (15, 'What is the primary purpose of containerization in Docker?', 'Application isolation and portability', 'Application isolation and portability', 'Data encryption', 'Network optimization', 'User authentication'),
                                                                                                                   (3, 'What is the purpose of the "useCallback" hook in React?', 'To memoize functions', 'To manage state', 'To memoize functions', 'To handle side effects', 'To control routing'),
                                                                                                                   (2, 'Which CSS property is used to create a grid layout?', 'display: grid', 'display: flex', 'display: block', 'display: grid', 'display: inline-grid'),
                                                                                                                   (4, 'In GraphQL, what is the purpose of mutations?', 'To modify server-side data', 'To query data', 'To authenticate users', 'To validate schemas', 'To modify server-side data'),
                                                                                                                   (3, 'What is the purpose of an index in PostgreSQL?', 'To improve query performance', 'To store data', 'To improve query performance', 'To validate data', 'To backup data'),

                                                                                                                   (4, 'Which of these is NOT a valid MongoDB aggregation stage?', 'SELECT', '$match', 'SELECT', '$group', '$project'),
                                                                                                                   (2, 'What is the primary purpose of Redis?', 'In-memory data caching', 'Full-text search', 'In-memory data caching', 'Document storage', 'Graph database'),
                                                                                                                   (4, 'Which AWS service is used for container orchestration?', 'ECS/EKS', 'EC2', 'S3', 'ECS/EKS', 'RDS'),
                                                                                                                   (3, 'What is the purpose of a Dockerfile ENTRYPOINT?', 'Specify the main executable', 'Copy files', 'Set environment variables', 'Specify the main executable', 'Install packages'),
                                                                                                                   (2, 'Which command deploys a stack in Azure CLI?', 'az deployment group create', 'az group deploy', 'az deployment group create', 'az stack deploy', 'az resource create'),

                                                                                                                   (2, 'What is the purpose of JWT in authentication?', 'Stateless authentication', 'Database encryption', 'Password hashing', 'Stateless authentication', 'Session management'),
                                                                                                                   (3, 'Which design pattern is most suitable for handling different payment methods in an e-commerce system?', 'Strategy Pattern', 'Singleton Pattern', 'Observer Pattern', 'Strategy Pattern', 'Factory Pattern'),
                                                                                                                   (4, 'What is the role of a service mesh in microservices architecture?', 'Handle service-to-service communication', 'Database management', 'User interface', 'File storage', 'Handle service-to-service communication'),
                                                                                                                   (3, 'What is the purpose of CORS in web security?', 'Control resource access across domains', 'Encrypt passwords', 'Control resource access across domains', 'Manage user sessions', 'Handle authentication'),
                                                                                                                   (4, 'Which encryption method is asymmetric?', 'RSA', 'AES', 'DES', 'Blowfish', 'RSA'),

                                                                                                                   (2, 'What is the main purpose of penetration testing?', 'Identify security vulnerabilities', 'Optimize performance', 'Identify security vulnerabilities', 'Debug code', 'Monitor systems'),
                                                                                                                   (4, 'Which algorithm is NOT suitable for classification problems?', 'K-means', 'Random Forest', 'SVM', 'Logistic Regression', 'K-means'),
                                                                                                                   (3, 'What is the purpose of dropout layers in neural networks?', 'Prevent overfitting', 'Increase accuracy', 'Prevent overfitting', 'Speed up training', 'Reduce memory usage'),
                                                                                                                   (2, 'Which evaluation metric is most suitable for imbalanced classification problems?', 'F1 Score', 'Accuracy', 'F1 Score', 'Error Rate', 'Mean Squared Error'),
                                                                                                                   (3, 'What is the primary benefit of using TypeScript over JavaScript?', 'Static type checking', 'Better performance', 'Static type checking', 'Smaller bundle size', 'Built-in security'),

                                                                                                                   (4, 'Which Shell command is used for finding text patterns in files?', 'grep', 'find', 'sed', 'awk', 'grep'),
                                                                                                                   (2, 'What is the purpose of the virtual keyword in C++?', 'Enable polymorphism', 'Improve performance', 'Enable polymorphism', 'Memory management', 'Template creation'),
                                                                                                                   (3, 'In Spring Boot, what is the purpose of @Autowired?', 'Dependency injection', 'Request mapping', 'Dependency injection', 'Exception handling', 'Data validation'),
                                                                                                                   (4, 'Which NoSQL database type is most suitable for hierarchical data?', 'Document store', 'Key-value store', 'Column store', 'Time series', 'Document store'),
                                                                                                                   (2, 'What is the main purpose of CI/CD pipelines?', 'Automate build and deployment', 'Monitor performance', 'Automate build and deployment', 'Store source code', 'Manage databases'),

                                                                                                                   (3, 'Which Flutter widget is used for responsive layouts?', 'LayoutBuilder', 'Container', 'LayoutBuilder', 'Column', 'Row'),
                                                                                                                   (4, 'What is the role of a Kubernetes Service?', 'Network abstraction for pods', 'Container runtime', 'Storage management', 'User interface', 'Network abstraction for pods'),
                                                                                                                   (2, 'Which HTTP status code indicates a successful creation?', '201', '200', '201', '204', '301'),
                                                                                                                   (3, 'What is the purpose of lazy loading in web development?', 'Improve initial load time', 'Enhance security', 'Improve initial load time', 'Reduce server load', 'Manage state'),
                                                                                                                   (4, 'Which design principle suggests "Program to an interface, not an implementation"?', 'Dependency Inversion', 'Single Responsibility', 'Open/Closed', 'Liskov Substitution', 'Dependency Inversion'),

                                                                                                                   (2, 'What is the main purpose of WebSocket protocol?', 'Real-time bidirectional communication', 'Data encryption', 'Real-time bidirectional communication', 'File transfer', 'Authentication'),
                                                                                                                   (3, 'Which React Native component is equivalent to div in HTML?', 'View', 'Container', 'View', 'Box', 'Section'),
                                                                                                                   (4, 'What is the purpose of blockchain sharding?', 'Improve scalability', 'Enhance security', 'Reduce costs', 'Increase decentralization', 'Improve scalability'),
                                                                                                                   (2, 'Which Git command shows commit history?', 'git log', 'git status', 'git log', 'git show', 'git history'),
                                                                                                                   (3, 'What is the primary use of Redis pub/sub?', 'Message broadcasting', 'Data caching', 'Message broadcasting', 'Session management', 'Queue processing'),

                                                                                                                   (4, 'Which AWS service is best for serverless computing?', 'Lambda', 'EC2', 'ECS', 'Fargate', 'Lambda'),
                                                                                                                   (2, 'What is the purpose of npm in Node.js?', 'Package management', 'Server runtime', 'Package management', 'Database management', 'Testing framework'),
                                                                                                                   (3, 'Which testing type focuses on component integration?', 'Integration testing', 'Unit testing', 'Integration testing', 'E2E testing', 'Stress testing');

INSERT INTO QTRelationship (QuestionId, TopicId) VALUES
                                                     (1, 1), (1, 35),
                                                     (2, 25), (2, 29),
                                                     (3, 7), (3, 3), (3, 33),
                                                     (4, 35), (4, 36),
                                                     (5, 17), (5, 32),
                                                     (6, 7), (6, 3), (6, 49),
                                                     (7, 6), (7, 41),
                                                     (8, 30), (8, 29), (8, 48),
                                                     (9, 15), (9, 5), (9, 49),
                                                     (10, 14), (10, 39), (10, 40),
                                                     (11, 16), (11, 48), (11, 50),
                                                     (12, 20), (12, 19), (12, 18),
                                                     (13, 18), (13, 32), (13, 48),
                                                     (14, 21), (14, 20), (14, 32),
                                                     (15, 28), (15, 26), (15, 51),
                                                     (16, 48), (16, 49), (16, 45),
                                                     (17, 48), (17, 45), (17, 32),
                                                     (18, 26), (18, 28), (18, 29),
                                                     (19, 27), (19, 26), (19, 52),
                                                     (20, 51), (20, 26), (20, 52),
                                                     (21, 37), (21, 38), (21, 40),
                                                     (22, 38), (22, 37), (22, 39),
                                                     (23, 37), (23, 40), (23, 39),
                                                     (24, 3), (24, 49), (24, 33),
                                                     (25, 56), (25, 23), (25, 55),
                                                     (26, 4), (26, 35), (26, 49),
                                                     (27, 12), (27, 48), (27, 45),
                                                     (28, 14), (28, 39), (28, 48),
                                                     (29, 32), (29, 48), (29, 49),
                                                     (30, 46), (30, 41), (30, 42),
                                                     (31, 19), (31, 18), (31, 48),
                                                     (32, 25), (32, 29), (32, 28),
                                                     (33, 7), (33, 49), (33, 50),
                                                     (34, 48), (34, 49), (34, 45),
                                                     (35, 25), (35, 29), (35, 28),
                                                     (36, 47), (36, 44), (36, 41),
                                                     (37, 54), (37, 48), (37, 39),
                                                     (38, 17), (38, 32), (38, 49),
                                                     (39, 16), (39, 48), (39, 50),
                                                     (40, 20), (40, 45), (40, 48),
                                                     (41, 8), (41, 29), (41, 32),
                                                     (42, 31), (42, 33), (42, 49),
                                                     (43, 30), (43, 29), (43, 48);