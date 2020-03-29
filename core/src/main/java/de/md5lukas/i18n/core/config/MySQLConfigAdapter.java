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

import org.jetbrains.annotations.NotNull;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConfigAdapter extends SQLConfigAdapterBase {

    public MySQLConfigAdapter(@NotNull String host, int port, @NotNull String username, @NotNull String password, @NotNull String database, boolean useSSL,
            @NotNull String table, @NotNull String language, @NotNull Logger logger) {
        super(host, port, username, password, database, useSSL, table, language, logger);
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
                        .getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=" + this.useSSL, this.username,
                                this.password);
                return true;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "A connection to the mysql database could not be established!", e);
            }
        }
        return false;
    }
}
