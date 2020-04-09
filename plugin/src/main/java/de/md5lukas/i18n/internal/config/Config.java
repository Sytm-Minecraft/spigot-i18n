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

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;

public class Config {

    private ProviderFormat format;
    private MySQLSettings mySQLSettings;

    public void load(FileConfiguration configuration) {
        format = Arrays.stream(ProviderFormat.values()).filter(value -> value.name().equalsIgnoreCase(configuration.getString("settingsProvider.format")))
                .findFirst().orElse(ProviderFormat.YML);
        if (ProviderFormat.MYSQL.equals(format)) {
            mySQLSettings = new MySQLSettings(configuration.getConfigurationSection("settingsProvider.mysql"));
        } else {
            mySQLSettings = null;
        }
    }

    public ProviderFormat getFormat() {
        return format;
    }

    public MySQLSettings getMySQLSettings() {
        return mySQLSettings;
    }
}