 <a name="readme-top"></a>

<div align="center">
  <img src="https://raw.githubusercontent.com/Arthroverse/DuckyEmulator/refs/heads/main/DuckyEmulator/src/main/resources/com/arthroverse/duckyemulator/images/icons/duckyemulator.png" alt="DuckyEmulator Logo" width="200">
  <h1 align="center">DuckyEmulator: High-Performance Test Emulator</h1>
  <p align="center">A modern, efficient test emulation platform built for Oracle certification preparation</p>
</div>

## 🚀 Overview

**DuckyEmulator** is a high-performance test emulation platform specifically designed for Oracle certification preparation. Built as a superior alternative to existing solutions like Enthuware Test Studio, DuckyEmulator offers significant performance improvements and a modern, intuitive user interface.

### Key Highlights
- **🏃‍♂️ Performance First**: Dramatically reduced memory usage (from 7GB+ to minimal footprint)
- **🎨 Modern UI**: Built with MaterialFX and JavaFX for a beautiful, responsive interface
- **📊 Comprehensive Management**: Full admin panel for questions, topics, and classifications
- **⚡ Fast Navigation**: Efficient question browsing with pagination and search
- **📈 Detailed Analytics**: Comprehensive test results and session history
- **🔒 Secure Authentication**: Robust user management with role-based access

![DuckyEmulator Screenshot](https://raw.githubusercontent.com/Arthroverse/DuckyEmulator/refs/heads/2025-9-28/DevelopmentDocs/v0.1/README/Screenshot%202025-09-28%20at%2018.14.55.png)
![DuckyEmulator Screenshot](https://raw.githubusercontent.com/Arthroverse/DuckyEmulator/refs/heads/2025-9-28/DevelopmentDocs/v0.1/README/Screenshot%202025-09-28%20at%2018.15.23.png)

## ✨ Features

### 👑 Admin Panel
- **Question Management**: Create, read, update, and delete questions with multiple choice options
- **Topic Organization**: Manage test topics with hierarchical structure
- **Classification System**: Easy, Medium, Hard difficulty levels
- **Image Support**: Upload and manage question images with relative path storage
- **Bulk Operations**: Efficient pagination for handling large question banks

### 👨‍💻 User Interface
- **Test Sessions**: Create and manage timed test sessions with 10-20 randomized questions
- **Smart Navigation**: Jump to specific questions, mark answers, track progress
- **Real-time Timer**: Live elapsed time tracking during tests
- **Comprehensive Results**: Detailed score breakdown with question-by-question analysis
- **Session History**: View and manage past test attempts

### 🛠️ Technical Features
- **High Performance**: Optimized database queries and efficient memory management
- **Cross-platform**: Java-based solution running on Windows, macOS, and Linux
- **Responsive Design**: MaterialFX components for modern UI/UX
- **Robust Database**: MySQL integration with JDBC connectivity
- **Modular Architecture**: Well-structured codebase with clear separation of concerns

## 🚀 Quick Start

### Prerequisites
- **Java 17** or higher
- **MySQL 8.0** or higher
- **Maven 3.8** or higher
- **4GB RAM** minimum (8GB recommended)

### Installation

1. **Install JDK 17.0.14 or higher**: Download from [this link](https://www.oracle.com/java/technologies/javase/jdk17-0-13-later-archive-downloads.html)

2. **Install MySQL 8.0 or higher**: Download from [this link](https://dev.mysql.com/downloads/mysql/)

3. **Download DuckyEmulator**: Get the JAR file from [this link](https://github.com/Arthroverse/DuckyEmulator/releases/tag/V1.0.0)

4. **Run the application**: Double-click the JAR file or execute:
   ```bash
   java -jar DuckyEmulator.jar
   ```
### First Launch
1. Launch DuckyEmulator
2. Create admin account on first startup
3. Login to admin panel to add questions and topics
4. Switch to user mode to take practice tests

## 📖 Documentation

### Project Structure
```
DuckyEmulator/
├── src/main/java/com/arthroverse/duckyemulator/
│   ├── Main/                    # Application entry point
│   ├── Database/                # Database models and services
│   │   ├── AdminBeans/         # Admin data models
│   │   ├── PublicBeans/        # User session models  
│   │   └── CredentialBeans/    # Authentication models
│   ├── UIControllers/          # JavaFX controllers
│   │   ├── AdminUIControllers/ # Admin panel controllers
│   │   └── PublicUIController/ # User interface controllers
│   ├── UIs/                    # Navigation and scene management
│   └── Utilities/              # Helper classes and utilities
├── src/main/resources/
│   ├── fxmls/                  # JavaFX FXML layouts
│   ├── css/                    # Stylesheets
│   └── images/                 # Application assets
└── docs/                       # Documentation and guides
```

### System Requirements

#### Admin Functions
- ✅ Admin login/logout with secure authentication
- ✅ Topic management (CRUD operations)
- ✅ Classification management (Easy/Medium/Hard)
- ✅ Question management with image support
- ✅ Bulk operations with pagination

#### User Functions
- ✅ User registration and authentication
- ✅ Test session creation with randomized questions
- ✅ Question navigation and answer selection
- ✅ Real-time timer and progress tracking
- ✅ Comprehensive result analysis
- ✅ Session history and management

### Technology Stack
- **Frontend**: JavaFX 22 + MaterialFX
- **Backend**: Java 17 + JDBC
- **Database**: MySQL 8.0
- **Build Tool**: Maven 3.8+
- **IDE**: IntelliJ IDEA Ultimate (recommended)

## 🛠️ Development

### Setting up Development Environment
```bash
# Clone and enter project directory
git clone https://github.com/arthroverse/DuckyEmulator.git
cd DuckyEmulator

# Install dependencies
mvn clean install

# Run in development mode
mvn javafx:run

#To debug, runs
mvn javafx:run -P debug
```

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### Ways to Contribute
- 🐛 **Bug Reports**: Found a bug? [Open an issue](https://github.com/arthroverse/DuckyEmulator/issues)
- 📝 **Documentation**: Improve our docs and guides
- 🔧 **Code**: Submit pull requests for bug fixes and features

### Code Review Process
- All PRs require review from maintainers
- Automated tests must pass
- Follow existing code style and patterns
- Include appropriate documentation updates

## 📊 Performance

DuckyEmulator delivers significant performance improvements over existing solutions:

| Metric | Enthuware Test Studio | DuckyEmulator | Improvement |
|--------|----------------------|---------------|-------------|
| Memory Usage | ~7GB | <1GB | **85% reduction** |
| Startup Time | 45-60s | 8-12s | **75% faster** |
| Question Load | 3-5s | <1s | **80% faster** |
| UI Responsiveness | Poor | Excellent | **Dramatically improved** |

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

```
Copyright (c) 2025 Arthroverse Laboratory

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```

## 🙏 Acknowledgments

DuckyEmulator is built with gratitude to the open-source community:

- **[OpenJFX](https://openjfx.io/)** - Modern Java UI toolkit
- **[MaterialFX](https://github.com/palexdev/MaterialFX)** - Material Design components for JavaFX
- **[MySQL](https://www.mysql.com/)** - Reliable database engine
- **[Maven](https://maven.apache.org/)** - Dependency management and build automation

Special thanks to all contributors and testers who helped shape DuckyEmulator into a robust testing platform.

## 📞 Support

### Getting Help
- 📧 **Email**: [vinh.md@arthroverse.com](mailto:vinh.md@arthroverse.com)
- 🐛 **Bug Reports**: [GitHub Issues](https://github.com/arthroverse/DuckyEmulator/issues)
