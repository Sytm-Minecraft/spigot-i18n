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

package de.md5lukas.i18n.core.bungee;

import de.md5lukas.i18n.core.bungee.config.BungeeConfigAdapter;
import de.md5lukas.i18n.core.MessageConfigExpander;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class BungeeMessageConfigExpander {

    /**
     * Expand all variables in a config using this method
     *
     * @param config           The configuration where all the strings are located
     * @param defaults         The defaults configuration if present
     * @param openingDelimiter The opening part of the enclosure that marks a string as a variable
     * @param closingDelimiter The closing part of the enclosure that marks a string as a variable
     * @return all string entries with the variables expanded
     */
    @NotNull
    public static Map<String, String> expandVariables(@NotNull Configuration config, @Nullable Configuration defaults, @NotNull String openingDelimiter,
            @NotNull String closingDelimiter) {
        return MessageConfigExpander.expandVariables(new BungeeConfigAdapter(config, defaults), openingDelimiter, closingDelimiter);
    }
}
