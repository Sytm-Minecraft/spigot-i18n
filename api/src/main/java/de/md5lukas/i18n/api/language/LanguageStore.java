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

package de.md5lukas.i18n.api.language;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LanguageStore {

    private Supplier<String> defaultLanguage;
    private Map<String, Language> loadedLanguages;

    public LanguageStore(Supplier<String> defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
        loadedLanguages = new HashMap<>();
    }

    public boolean isLanguageRegistered(String language) {
        return loadedLanguages.containsKey(language.toLowerCase());
    }

    public Language getLanguage(String language) {
        return loadedLanguages.get(language == null ? defaultLanguage.get() : language);
    }

    public void registerLanguage(Language language) {
        loadedLanguages.put(language.getLanguageKey(), language);
    }
}
