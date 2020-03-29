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

package de.md5lukas.i18n.core.bungee.config;

import de.md5lukas.i18n.core.config.ConfigAdapter;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class BungeeConfigAdapter implements ConfigAdapter {

    private static final char SEPARATOR = '.';

    private final Configuration configuration, defaults;

    public BungeeConfigAdapter(@NotNull Configuration configuration, @Nullable Configuration defaults) {
        this.configuration = configuration;
        this.defaults = defaults;
    }

    @Override
    public String getString(String path) {
        return configuration.getString(path);
    }

    @Override
    public Collection<String> listAllKeys() {
        Set<String> keys = new HashSet<>();
        if (defaults != null)
            getKeysDeep(defaults, "", keys);
        getKeysDeep(configuration, "", keys);
        return keys;
    }

    private void getKeysDeep(Configuration section, String path, Set<String> keys) {
        for (String key : section.getKeys()) {
            String currentPath = (path.isEmpty() ? "" : path + SEPARATOR) + key;
            Object value = section.get(key);
            if (value instanceof String) {
                keys.add(currentPath);
            } else if (value instanceof Configuration) {
                getKeysDeep((Configuration) value, currentPath, keys);
            }
        }
    }
}