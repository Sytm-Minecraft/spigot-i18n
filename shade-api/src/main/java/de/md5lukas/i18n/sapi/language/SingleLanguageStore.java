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

import com.google.common.base.Preconditions;
import org.bukkit.command.CommandSender;

/**
 * Simple language store that only has one language that cannot be exchanged
 */
public class SingleLanguageStore implements LanguageStore {

    private Language language;

    /**
     * Creates a new instance that will always return the provided language
     *
     * @param language The language to use
     * @throws NullPointerException If the language is null
     */
    public SingleLanguageStore(Language language) {
        this.language = Preconditions.checkNotNull(language, "The provided language cannot be null");
    }

    /**
     * Updates the language referenced in this store
     *
     * @param language The new language to use
     * @throws NullPointerException If the language is null
     */
    public void setLanguage(Language language) {
        this.language = Preconditions.checkNotNull(language, "The provided language cannot be null");
    }

    /**
     * This will always return the language provided at creation
     *
     * @param key The key of the language
     * @return The language provided at creation
     */
    @Override
    public Language getLanguage(String key) {
        return language;
    }

    /**
     * This will always return the language provided at creation
     *
     * @param commandSender The command sender
     * @return The language provided at creation
     */
    @Override
    public Language getLanguage(CommandSender commandSender) {
        return language;
    }
}
