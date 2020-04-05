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

package de.md5lukas.i18n.core.language;

import java.util.HashMap;
import java.util.Map;

public class LanguageStore {

    private static String defaultLanguage;
    private static Map<String, Language> loadedLanguages = new HashMap<>();

    public static void setDefaultLanguage(String language) {
        if (!isLanguageRegistered(language))
            throw new IllegalStateException("The default language needs to be registered to be able to set it");
        defaultLanguage = language.toLowerCase();
    }

    public static boolean isLanguageRegistered(String language) {
        return loadedLanguages.containsKey(language.toLowerCase());
    }


    public static Language getLanguage(String language) {
        return loadedLanguages.get(language == null ? defaultLanguage : language);
    }

    public static void registerLanguage(Language language) {
        loadedLanguages.put(language.getLanguageKey(), language);
    }
}
