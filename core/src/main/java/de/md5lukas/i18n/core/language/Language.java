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

import java.util.Map;

public class Language {

    private String languageKey;
    private Map<String, String> messages;
    private String nameInEnglish;
    private String nameInLanguage;

    public Language(String languageKey, Map<String, String> messages, String nameInEnglish, String nameInLanguage) {
        this.languageKey = languageKey.toLowerCase();
        this.messages = messages;
        this.nameInEnglish = nameInEnglish;
        this.nameInLanguage = nameInLanguage;
    }


    public String getLanguageKey() {
        return languageKey;
    }


    public String getMessage(String key) {
        return messages.get(key);
    }


    public String getNameInEnglish() {
        return nameInEnglish;
    }


    public String getNameInLanguage() {
        return nameInLanguage;
    }
}
