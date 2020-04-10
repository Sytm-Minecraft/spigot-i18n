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

import java.util.Collections;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple class to hold data about a language
 */
public class Language {

    private final String languageKey;
    private final Map<String, String> translations;
    private final String nameInEnglish;
    private final String nameInLanguage;

    /**
     * Creates a new language instance with the provided values
     * <br><br>
     * Throws {@link NullPointerException} if any of the parameters are null
     *
     * @param languageKey    The language key, e.g. <code>en</code> or <code>de</code>
     * @param translations   The actual messages in key / value format
     * @param nameInEnglish  The name of the language in english, e.g. <code>German</code>
     * @param nameInLanguage The name of the language in itself, e.g. <code>Deutsch</code>
     */
    public Language(String languageKey, Map<String, String> translations, String nameInEnglish, String nameInLanguage) {
        this.languageKey = checkNotNull(languageKey, "The language key cannot be null").toLowerCase();
        this.translations = Collections.unmodifiableMap(checkNotNull(translations, "The translation map cannot be null"));
        this.nameInEnglish = checkNotNull(nameInEnglish, "The language name in english cannot be null");
        this.nameInLanguage = checkNotNull(nameInLanguage, "The language name in itself cannot be null");
    }

    /**
     * @return The language key
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
     */
    public String getTranslation(String key) {
        return translations.get(checkNotNull(key, "The key to get the translation from cannot be null"));
    }

    /**
     * The returned map of the key / value translations is a read-only view of the stored translations of this language
     *
     * @return The translation map
     * @see Collections#unmodifiableMap(Map)
     */
    public Map<String, String> getTranslations() {
        return translations;
    }

    /**
     * @return The name of the language in english
     */
    public String getNameInEnglish() {
        return nameInEnglish;
    }

    /**
     * @return The name of the language in itself
     */
    public String getNameInLanguage() {
        return nameInLanguage;
    }
}
