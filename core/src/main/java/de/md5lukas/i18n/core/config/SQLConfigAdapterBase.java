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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class SQLConfigAdapterBase implements ConfigAdapter {


    protected final Logger logger;
    protected final String host, username, password, database, table, language;
    protected final int port;
    protected final boolean useSSL;
    protected Connection connection;

    protected Map<String, String> cache;

    public SQLConfigAdapterBase(String host, int port, String username, String password, String database, boolean useSSL,
            String table, String language, Logger logger) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.table = table;
        this.useSSL = useSSL;
        this.language = language;
        this.logger = logger;
    }

    public abstract boolean openConnection();

    public final void closeConnection() {
        try {
            if (this.connection == null || this.connection.isClosed())
                return;
            synchronized (this) {
                if (this.connection == null || this.connection.isClosed())
                    return;
                this.connection.close();
                this.connection = null;
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Couldn't properly close sql connection");
        }
    }

    @Override
    public final String getString(String path) {
        if (cache == null)
            this.listAllKeys();
        return cache.get(path);
    }

    @Override
    public final Collection<String> listAllKeys() {
        if (cache == null) {
            this.cache = new HashMap<>();
            if (connection != null) {
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT path, value FROM " + this.table + " WHERE language=?");
                    statement.setString(1, language);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        cache.put(resultSet.getString("path"), resultSet.getString("value"));
                    }
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Could not retrieve data from sql server", e);
                }
            }
        }
        return cache.keySet();
    }
}
