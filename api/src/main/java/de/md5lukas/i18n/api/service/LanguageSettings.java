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

package de.md5lukas.i18n.api.service;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A simple interface to manage language settings for players
 */
public interface LanguageSettings {

    /**
     * Get the language of a player based on their UUID and if none is set fall back to default language
     *
     * @param uuid The uuid of the player
     * @return The language of the player
     */
    String getLanguage(UUID uuid);

    /**
     * Get the language of a command sender. If it is a player it will use {@link #getLanguage(UUID)} and if it is the console
     * {@link #getConsoleLanguage()}. Other variations are not supported
     *
     * @param commandSender The command sender to get the language for
     * @return The language of the player
     */
    default String getLanguage(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            return this.getLanguage(((Player) commandSender).getUniqueId());
        } else if (commandSender instanceof ConsoleCommandSender) {
            return this.getConsoleLanguage();
        } else {
            throw new IllegalArgumentException("#getLanguage(CommandSender) only accepts 'Player' and 'ConsoleCommandSender'");
        }
    }

    /**
     * Sets the language for a player based on their uuid
     * <br><br>
     * Use <code>null</code> to use the default value again
     *
     * @param uuid     The uuid of the player
     * @param language The new language of the player
     */
    void setLanguage(UUID uuid, String language);

    /**
     * Sets the language of a command sender
     * <br><br>
     * Use <code>null</code> to use the default value again
     *
     * @param commandSender The command sender to set the language of
     * @param language      The new language of the command sender
     */
    default void setLanguage(CommandSender commandSender, String language) {
        if (commandSender instanceof Player) {
            this.setLanguage(((Player) commandSender).getUniqueId(), language);
        } else if (commandSender instanceof ConsoleCommandSender) {
            this.setConsoleLanguage(language);
        } else {
            throw new IllegalArgumentException("#setLanguage(CommandSender, String) only accepts 'Player' and 'ConsoleCommandSender'");
        }
    }

    /**
     * Returns the language set for the console output, and if none is set the default language
     *
     * @return The console language
     */
    String getConsoleLanguage();

    /**
     * Sets the language for the console output
     * <br><br>
     * Use <code>null</code> to use the default value again
     *
     * @param language The new language to be used in the console
     */
    void setConsoleLanguage(String language);

    /**
     * @return The default language
     */
    String getDefaultLanguage();

    /**
     * Sets a new default language that should be used if a player hasn't set his own language
     *
     * @param language The new default language
     */
    void setDefaultLanguage(String language);
}
