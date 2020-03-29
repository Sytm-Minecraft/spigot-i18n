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

package de.md5lukas.i18n.core.spigot.config;

import de.md5lukas.i18n.core.config.ConfigAdapter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class SpigotConfigAdapter implements ConfigAdapter {

    private final FileConfiguration configuration;

    public SpigotConfigAdapter(FileConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getString(@NotNull String path) {
        return this.configuration.getString(path);
    }

    @NotNull
    @Override
    public Collection<String> listAllKeys() {
        return configuration.getKeys(true).stream().filter(configuration::isString).collect(Collectors.toList());
    }
}
