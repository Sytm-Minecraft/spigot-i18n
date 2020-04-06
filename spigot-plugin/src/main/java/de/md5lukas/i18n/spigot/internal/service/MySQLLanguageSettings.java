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

package de.md5lukas.i18n.spigot.internal.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.md5lukas.i18n.spigot.internal.config.MySQLSettings;
import de.md5lukas.i18n.spigot.service.LanguageSettings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class MySQLLanguageSettings implements LanguageSettings {

    private final Plugin plugin;
    private final String host, username, password, database, table;
    private final int port;
    private Connection connection;

    private String defaultLanguage, consoleLanguage;
    private LoadingCache<UUID, String> playerSettingsCache;

    public MySQLLanguageSettings(Plugin plugin, MySQLSettings mySQLSettings) {
        this.plugin = plugin;
        this.host = mySQLSettings.getHost();
        this.port = mySQLSettings.getPort();
        this.username = mySQLSettings.getUsername();
        this.password = mySQLSettings.getPassword();
        this.database = mySQLSettings.getDatabase();
        this.table = mySQLSettings.getTable();

        playerSettingsCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize((long) (Bukkit.getMaxPlayers() * 1.25))
                .build(new CacheLoader<UUID, String>() {
                    @Override
                    public String load(UUID key) throws Exception {
                        String result = get(key.toString());
                        if (result == null)
                            return getDefaultLanguage();
                        return result;
                    }
                });
    }

    public boolean openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "A suitable database driver for MySQL could not be found!");
            return false;
        }
        synchronized (this) {
            try {
                if (connection != null && !connection.isClosed())
                    return true;
                this.connection = DriverManager
                        .getConnection(String.format("jdbc:mysql://%s:%d/%s", this.host, this.port, this.database), this.username, this.password);
                return true;
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "A connection to the MySQL database could not be established!", e);
            }
        }
        return false;
    }

    public void closeConnection() {
        synchronized (this) {
            try {
                if (this.connection == null || this.connection.isClosed())
                    return;
                this.connection.close();
                this.connection = null;
            } catch (SQLException e) {
                plugin.getLogger().log(Level.WARNING, "Couldn't properly close MySQL connection", e);
            }
        }
    }

    @Override
    public String getLanguage(UUID uuid) {
        try {
            return playerSettingsCache.get(uuid);
        } catch (ExecutionException e) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't retrieve player language settings", e);
        }
        return defaultLanguage;
    }

    @Override
    public void setLanguage(UUID uuid, String language) {
        playerSettingsCache.put(uuid, language == null ? getDefaultLanguage() : language);
        if (language == null) {
            delete(uuid.toString());
        } else {
            update(uuid.toString(), language);
        }
    }

    @Override
    public String getConsoleLanguage() {
        if (consoleLanguage == null) {
            try {
                consoleLanguage = get("console");
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not load console language", e);
            }
            if (consoleLanguage == null)
                consoleLanguage = getDefaultLanguage();
        }
        return consoleLanguage;
    }

    @Override
    public void setConsoleLanguage(String language) {
        consoleLanguage = language;
        if (language == null) {
            delete("console");
        } else {
            update("console", language);
        }
    }

    @Override
    public String getDefaultLanguage() {
        if (defaultLanguage == null) {
            try {
                defaultLanguage = get("default");
                if (defaultLanguage == null) {
                    update("default", "en");
                }
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not load default language", e);
            }
            if (defaultLanguage == null)
                defaultLanguage = "en";
        }
        return defaultLanguage;
    }

    @Override
    public void setDefaultLanguage(String language) {
        defaultLanguage = language;
        update("default", language);
    }

    private String get(String uuid) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT language FROM " + table + " WHERE id=?");
        statement.setString(1, uuid);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getString("language");
        }
        return null;
    }

    private void update(String uuid, String language) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO " + table + " (id, language) VALUES(?, ?) ON DUPLICATE KEY UPDATE language=VALUES(language)");
                statement.setString(1, uuid);
                statement.setString(2, language);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void delete(String uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement statement = connection.prepareStatement("DELETE FROM " + table + " WHERE id=?");
                statement.setString(1, uuid);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
