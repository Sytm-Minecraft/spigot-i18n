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

import de.md5lukas.i18n.api.service.LanguageSettings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class LanguageStore {

    private final LanguageSettings languageSettings;
    private final Map<String, Language> languages;

    /**
     * Creates a new language store and tries to load the {@link LanguageSettings} service.
     * If it can't find one, {@link NullPointerException} is thrown.
     */
    public LanguageStore() {
        RegisteredServiceProvider<LanguageSettings> rsp = checkNotNull(Bukkit.getServer().getServicesManager().getRegistration(LanguageSettings.class),
                "A service provider for language settings could not be found");
        languageSettings = checkNotNull(rsp.getProvider(), "A service provider for language settings could not be found");

        this.languages = new HashMap<>();
    }

    /**
     * Overrides the old languages (if present) with the new languages in this language store
     *
     * @param newLanguages A list of the new languages that should be used
     */
    public void setLanguages(List<Language> newLanguages) {
        languages.clear();
        newLanguages.forEach(language -> languages.put(language.getLanguageKey(), language));
    }

    /**
     * Gets a language from the languages set via {@link #setLanguages(List)} based on the key provided
     *
     * @param key The key of the language
     * @return The language or <code>null</code> if not present
     */
    public Language getLanguage(String key) {
        return languages.get(key);
    }

    /**
     * Gets a language from the languages set via {@link #setLanguages(List)} based on the language of the command sender
     *
     * @param commandSender The command sender from where the language to use should be retrieved from
     * @return The language of the command sender or <code>null</code> if not present
     */
    public Language getLanguage(CommandSender commandSender) {
        return getLanguage(languageSettings.getLanguage(commandSender));
    }
}
