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

package de.md5lukas.i18n.sapi.language;

import org.bukkit.command.CommandSender;

/**
 * Simple interface defining common methods for a language storage
 */
public interface LanguageStore {

    /**
     * Gets a language from the languages in this store based on the key of the language.
     * <br><br>
     * This may vary based on the implementation.
     *
     * @param key The key of the language
     * @return An appropriate language that is in this store
     */
    Language getLanguage(String key);

    /**
     * Gets a language from the languages in this store based on the preferences of the command sender.
     * <br><br>
     * This may vary based on the implementation.
     *
     * @param commandSender
     * @return
     */
    Language getLanguage(CommandSender commandSender);
}
