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
package com.ducksabervn.duckyemulator.Database.DBService;

import java.sql.*;

public class MySQLService {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";
    private static final String CONN_STRING = "jdbc:mysql://localhost/DuckyEmulator_QuestionDB";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
    }
}
