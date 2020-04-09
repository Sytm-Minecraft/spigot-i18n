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

package de.md5lukas.i18n.internal.language;

import de.md5lukas.i18n.api.language.Language;
import de.md5lukas.i18n.api.language.LanguageStore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageLoader {

    private final Plugin plugin;
    private final LanguageStore languageStore;

    public LanguageLoader(Plugin plugin, LanguageStore languageStore) {
        this.plugin = plugin;
        this.languageStore = languageStore;
    }

    public void load() {
        extractLanguageFiles();
        File languageFolder = new File(plugin.getDataFolder(), "lang");
        List<Language> languages = new ArrayList<>();
        for (File file : languageFolder.listFiles()) {
            FileConfiguration lang = YamlConfiguration.loadConfiguration(file);

            Map<String, String> translations = new HashMap<>();

            lang.getKeys(true).stream().filter(key -> !key.startsWith("meta")).filter(lang::isString)
                    .forEach(key -> translations.put(key, lang.getString(key)));

            Language language = new Language(lang.getString("meta.key"), translations, lang.getString("meta.nameInEnglish"),
                    lang.getString("meta.nameInLanguage"));

            languages.add(language);
        }
        languageStore.setLanguages(languages);
    }

    private void extractLanguageFiles() {
        plugin.saveResource("lang/en.yml", false);
    }
}
