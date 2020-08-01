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

package de.md5lukas.i18n.language;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class is storage for languages that helps retrieving them based on players or the language keys
 *
 * @author Lukas Planz
 * @since 1.0.0
 */
public final class LanguageStorage {

    private String defaultLanguage;
    private final Map<String, Language> languages;

    /**
     * Creates a new language store with the given default language.
     *
     * @param defaultLanguage The default language to use
     * @throws NullPointerException If the defaultLanguage is null
     * @since 1.0.0
     */
    public LanguageStorage(String defaultLanguage) {
        setDefaultLanguage(defaultLanguage);

        this.languages = new HashMap<>();
    }

    /**
     * Overrides the old languages (if present) with the new languages in this language store
     *
     * @param newLanguages A list of the new languages that should be used
     * @throws NullPointerException     If the newLanguages are null
     * @throws IllegalArgumentException If the newLanguages do not contain the default language
     * @since 1.0.0
     */
    public void setLanguages(List<Language> newLanguages) {
        checkNotNull(newLanguages, "The new languages to set cannot be null");
        // Check if any of the languages is null and if the default language is provided
        boolean containsDefaultLanguage = false;
        for (int index = 0; index < newLanguages.size(); index++) {
            Language language = newLanguages.get(index);
            checkNotNull(language, "The language at index %d is null", index);
            if (defaultLanguage.equalsIgnoreCase(language.getLanguageKey()))
                containsDefaultLanguage = true;
        }
        checkArgument(containsDefaultLanguage, "The newLanguages do not contain the default language");

        languages.clear();
        newLanguages.forEach(language -> languages.put(language.getLanguageKey(), language));
    }

    /**
     * Retrieves the default language that is used, if a player has selected an unknown language
     *
     * @return The default language
     * @since 1.0.0
     */
    public Language getDefaultLanguage() {
        return languages.get(defaultLanguage);
    }

    /**
     * Updates the default language to the new value
     *
     * @param defaultLanguage The new default language
     * @throws NullPointerException If the new default language is null
     */
    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = checkNotNull(defaultLanguage, "The default language cannot be null");
    }

    /**
     * Gets a language from the languages set via {@link #setLanguages(List)} based on the key provided
     * <br><br>
     * If the provided language is not available or the key null, the default language is returned
     *
     * @param key The key of the language
     * @return The language based on the input key
     * @since 1.0.0
     */
    public Language getLanguage(String key) {
        if (key == null)
            return getDefaultLanguage();
        Language language = languages.get(trimLanguageKey(key));
        if (language == null)
            return languages.get(defaultLanguage);
        return language;
    }

    /**
     * Gets a language from the languages set via {@link #setLanguages(List)} based on the command sender.
     * <br><br>
     * If the command sender is a {@link Player}, then {@link Player#getLocale()} is used to retrieve the locale of the player.
     * <br>
     * Otherwise the default language is returned
     *
     * @param commandSender The command sender to get the language for
     * @return The language of the command sender or <code>null</code> if not present
     * @since 1.0.0
     */
    public Language getLanguage(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            return getLanguage(((Player) commandSender).getLocale());
        } else {
            return getDefaultLanguage();
        }
    }

    private String trimLanguageKey(String key) {
        key = key.toLowerCase();
        int index = key.indexOf('_');
        if (index == -1)
            return key;
        return key.substring(0, index);
    }
}
