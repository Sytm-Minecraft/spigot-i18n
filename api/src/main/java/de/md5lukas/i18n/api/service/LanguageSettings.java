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
     * Get the language of a player and if none is set fall back to default language
     *
     * @param player The player
     * @return The language of the player
     */
    default String getLanguage(Player player) {
        return this.getLanguage(player.getUniqueId());
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
     * Sets the language for a player
     * <br><br>
     * Use <code>null</code> to use the default value again
     *
     * @param player   The player
     * @param language The new language of the player
     */
    default void setLanguage(Player player, String language) {
        this.setLanguage(player.getUniqueId(), language);
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
