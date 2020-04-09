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

package de.md5lukas.i18n.internal.config;

import org.bukkit.configuration.ConfigurationSection;

public class MySQLSettings {

    private final String host, password, username, database, table;
    private final int port;

    public MySQLSettings(ConfigurationSection section) {
        host = section.getString("host");
        port = section.getInt("port");
        password = section.getString("password");
        username = section.getString("username");
        database = section.getString("database");
        table = section.getString("table");
    }

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }

    public int getPort() {
        return port;
    }
}
