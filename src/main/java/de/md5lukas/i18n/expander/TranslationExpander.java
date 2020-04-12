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

package de.md5lukas.i18n.expander;

import com.google.common.base.Preconditions;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TranslationExpander {

    /**
     * Expands all variables in the input map and then filters them out. The passed map will be modified.
     *
     * @param input    The map containing key / value pairs from the configuration
     * @param settings The settings object to use its settings from
     * @return The modified <code>input</code> map
     * @throws NullPointerException If either <code>input</code> and/or <code>settings</code> are null
     */
    public static Map<String, String> expandVariables(Map<String, String> input, TranslationExpanderSettings settings) {
        Preconditions.checkNotNull(input, "The input map to process cannot be null");
        Preconditions.checkNotNull(settings, "The settings to use cannot be null");

        List<String[]> variables = input.entrySet().stream().filter(entry -> entry.getKey().startsWith(settings.getVariablePrefix())).map(entry -> {
            input.remove(entry.getKey());
            return new String[] { entry.getKey().substring(settings.getVariablePrefix().length()).toLowerCase(), entry.getValue() };
        }).collect(Collectors.toList());

        for (String[] variable : variables) {
            String key = settings.getOpeningDelimiter() + variable[0] + settings.getClosingDelimiter();
            for (Map.Entry<String, String> entry : input.entrySet()) {
                entry.setValue(entry.getValue().replace(key, variable[1]));
            }
        }

        return input;
    }

    /**
     * Expands all variables in the input map and then filters them out. The passed map will be modified.
     * <br><br>
     * Shorthand for <code>expandVariables(input, new TranslationExpanderSettings())</code> effectively just using the default values.
     * To see the default values take a look at {@link TranslationExpanderSettings#TranslationExpanderSettings()}
     *
     * @param input The map containing key / value pairs from the configuration
     * @return The modified <code>input</code> map
     * @throws NullPointerException If <code>input</code> is null
     * @see #expandVariables(Map, TranslationExpanderSettings)
     */
    public static Map<String, String> expandVariables(Map<String, String> input) {
        return expandVariables(input, new TranslationExpanderSettings());
    }
}
