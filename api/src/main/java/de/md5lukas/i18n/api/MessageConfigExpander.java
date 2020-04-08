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

package de.md5lukas.i18n.api;

import de.md5lukas.i18n.api.config.ConfigAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class MessageConfigExpander {

    private static final String VARIABLE_PREFIX = "variable.";

    /**
     * Expand all variables in a config using this method. Variables must start with <code>variable.</code> and the prefix will then be excluded from the final result
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

        List<String[]> variables = all.entrySet().stream().filter(entry -> entry.getKey().startsWith(VARIABLE_PREFIX)).map(entry -> {
            all.remove(entry.getKey());
            return new String[]{ entry.getKey().substring(VARIABLE_PREFIX.length()).toLowerCase(), entry.getValue() };
        }).collect(Collectors.toList());

        for (String[] variable : variables) {
            String key = openingDelimiter + variable[0] + closingDelimiter;
            for (Map.Entry<String, String> entry : all.entrySet()) {
                entry.setValue(entry.getValue().replace(key, variable[1]));
            }
        }
        return all;
    }
}
