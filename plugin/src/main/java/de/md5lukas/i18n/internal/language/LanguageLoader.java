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

import de.md5lukas.i18n.api.language.MultiLanguageStore;
import de.md5lukas.i18n.sapi.language.Language;
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
    private final MultiLanguageStore languageStore;

    public LanguageLoader(Plugin plugin, MultiLanguageStore languageStore) {
        this.plugin = plugin;
        this.languageStore = languageStore;
    }

    public void load() {
        extractLanguageFiles();
        File languageFolder = new File(plugin.getDataFolder(), "lang");
        List<Language> languages = new ArrayList<>();
        for (File file : languageFolder.listFiles()) {
            FileConfiguration lang = YamlConfiguration.loadConfiguration(file);

            if (!lang.getString("command.info.license").toLowerCase().contains("gnu lesser general public license")) {
                lang.set("command.info.license",
                        "&7This plugin is licensed under the &eGNU Lesser General Public License&7 and its source code is freely available at GitHub.\n"
                                + "&7If you want to get a copy of it yourself, just click this message");
            }

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
