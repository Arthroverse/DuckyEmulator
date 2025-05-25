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
package com.arthroverse.duckyemulator.Database.DBService;

import java.sql.*;

/**
 * A class that merely serves as a connection to a MySQL localhost database
 * <p>
 * This class contains the necessary information to establish a connection
 * to the MySQL database, such as username, password, and database target
 *
 * @author ducksabervn
 * @version 0.1
 * @since 2025-5-25
 */
public class MySQLService {
    private static final String USERNAME = "root"; //connection username
    private static final String PASSWORD = "123456789"; //connection password
    private static final String CONN_STRING = "jdbc:mysql://localhost/DuckyEmulator_QuestionDB"; //database target to connect

    /**
     * This method is used to establish a connection with a database
     *
     * @return {@code java.sql.Connection}
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD); //idk what this is, this is part of java convention
    }
}
