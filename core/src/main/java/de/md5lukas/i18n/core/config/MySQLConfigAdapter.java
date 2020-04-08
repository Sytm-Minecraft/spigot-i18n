/*
 *     A simple utility designed for spigot to make multi-language support easy
 *     Copyright (C) 2020 Lukas Planz
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.md5lukas.i18n.core.config;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A ConfigAdapter targeted at MySQL / MariaDB databases
 */
public class MySQLConfigAdapter extends SQLConfigAdapterBase {

    /**
     * Create a new MySQLConfigAdapter instance using the following parameters
     *
     * @param host     The host of the MySQL database
     * @param port     The port of the MySQL database
     * @param username The username
     * @param password The password of the user
     * @param database The database to use
     * @param table    The table to use in the database
     * @param language The initial language to use for the queries
     * @param logger   The logger to use for error reporting
     */
    public MySQLConfigAdapter(String host, int port, String username, String password, String database,
            String table, String language, Logger logger) {
        super(host, port, username, password, database, table, language, logger);
    }

    @Override
    public boolean openConnection() {
        try {
            if (connection != null && !connection.isClosed())
                return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Couldn't check if the MySQL connection is closed or not", e);
        }
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "A suitable database driver for MySQL could not be found!");
            return false;
        }
        synchronized (this) {
            try {
                if (connection != null && !connection.isClosed())
                    return true;
                this.connection = DriverManager
                        .getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
                return true;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "A connection to the mysql database could not be established!", e);
            }
        }
        return false;
    }
}
