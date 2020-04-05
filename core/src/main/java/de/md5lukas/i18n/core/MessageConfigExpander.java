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

package de.md5lukas.i18n.core;

import de.md5lukas.i18n.core.config.ConfigAdapter;

import java.util.HashMap;
import java.util.Map;

public final class MessageConfigExpander {

    private static final char ESCAPE = '\\';

    /**
     * Expand all variables in a config using this method
     *
     * @param config           The configuration where all the strings are located
     * @param openingDelimiter The opening part of the enclosure that marks a string as a variable
     * @param closingDelimiter The closing part of the enclosure that marks a string as a variable
     * @return all string entries with the variables expanded
     */

    public static Map<String, String> expandVariables(ConfigAdapter config, String openingDelimiter, String closingDelimiter) {
        Map<String, String> all = new HashMap<>();
        for (String key : config.listAllKeys()) {
            String value = config.getString(key);
            all.put(key, value);
        }
        for (String key : all.keySet()) {
            String variable = openingDelimiter + key + closingDelimiter;
            String replacement = all.get(key);
            for (Map.Entry<String, String> entry : all.entrySet()) {
                if (key.equals(entry.getKey())) {
                    continue;
                }
                entry.setValue(entry.getValue().replace(variable, replacement));
            }
        }
        return all;
    }
}
