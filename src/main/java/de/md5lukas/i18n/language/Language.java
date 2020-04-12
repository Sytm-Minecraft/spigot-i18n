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

import java.util.Collections;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple class to hold data about a language
 *
 * @author Lukas Planz
 * @since 1.0.0
 */
public final class Language {

    private final String languageKey;
    private final Map<String, String> translations;

    /**
     * Creates a new language instance with the provided values
     *
     * @param languageKey  The language key, e.g. <code>en</code> or <code>de</code>
     * @param translations The actual messages in key / value format
     * @throws NullPointerException If either the language key or the translations are null
     * @since 1.0.0
     */
    public Language(String languageKey, Map<String, String> translations) {
        this.languageKey = checkNotNull(languageKey, "The language key cannot be null").toLowerCase();
        this.translations = Collections.unmodifiableMap(checkNotNull(translations, "The translation map cannot be null"));
    }

    /**
     * @return The language key
     * @since 1.0.0
     */
    public String getLanguageKey() {
        return languageKey;
    }

    /**
     * Get a translation from the map based on its key
     *
     * @param key The key of the translation in the map
     * @return The translation, or <code>null</code> if not present
     * @throws NullPointerException If the key is null
     * @see Map#get(Object)
     * @since 1.0.0
     */
    public String getTranslation(String key) {
        return translations.get(checkNotNull(key, "The key to get the translation from cannot be null"));
    }

    /**
     * The returned map of the key / value translations is a read-only view of the stored translations of this language
     *
     * @return The translation map
     * @see Collections#unmodifiableMap(Map)
     * @since 1.0.0
     */
    public Map<String, String> getTranslations() {
        return translations;
    }
}
