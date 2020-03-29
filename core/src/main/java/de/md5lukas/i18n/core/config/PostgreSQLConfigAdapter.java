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
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLConfigAdapter extends SQLConfigAdapterBase {

    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";

    @Nullable
    private final File postgreSQLFile;

    public PostgreSQLConfigAdapter(@NotNull String host, int port, @NotNull String username, @NotNull String password, @NotNull String database, boolean useSSL,
            @NotNull String table, @NotNull String language, @NotNull Logger logger, @Nullable File postgreSQLFile) {
        super(host, port, username, password, database, useSSL, table, language, logger);
        this.postgreSQLFile = postgreSQLFile;
    }

    @Override
    public boolean openConnection() {
        try {
            if (connection != null && !connection.isClosed())
                return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Couldn't check if the PostgreSQL connection is closed or not", e);
        }
        try {
            Class.forName(POSTGRESQL_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "A suitable database driver for PostgreSQL could not be found!");
            try {
                loadPostgreSQLJDBCDriver();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Could not load PostgreSQL driver", ex);
                return false;
            }
        }
        synchronized (this) {
            try {
                if (connection != null && !connection.isClosed())
                    return true;
                this.connection = DriverManager
                        .getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?ssl=" + this.useSSL, this.username,
                                this.password);
                return true;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "A connection to the mysql database could not be established!", e);
            }
        }
        return false;
    }

    private void loadPostgreSQLJDBCDriver() throws FileNotFoundException, MalformedURLException, ClassNotFoundException {
        if (postgreSQLFile == null)
            throw new FileNotFoundException("A folder or file to load the PostgreSQL driver has not been set");
        String notFound = "A folder or file to load the PostgreSQL driver has not been found";
        if (postgreSQLFile.exists())
            throw new FileNotFoundException(notFound);
        File jarFile = postgreSQLFile;
        if (postgreSQLFile.isDirectory()) {
            File[] result = postgreSQLFile.listFiles((dir, name) -> {
                name = name.toLowerCase();
                return name.startsWith("postgresql") && name.endsWith(".jar");
            });
            if (result == null || result.length == 0)
                throw new FileNotFoundException(notFound);
            Arrays.sort(result, (file1, file2) -> file1.getName().compareToIgnoreCase(file2.getName()));
            jarFile = result[0];
        }
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{ jarFile.toURI().toURL() }, this.getClass().getClassLoader());
        Class.forName(POSTGRESQL_DRIVER, true, urlClassLoader);
    }
}
