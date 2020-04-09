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

package de.md5lukas.i18n.internal.service;

import com.google.common.base.Preconditions;
import de.md5lukas.i18n.api.service.LanguageSettings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class YamlLanguageSettings implements LanguageSettings {

    private final Plugin plugin;
    private final File languageFile;
    private FileConfiguration cfg;

    public YamlLanguageSettings(Plugin plugin) {
        this.plugin = plugin;
        languageFile = new File(plugin.getDataFolder(), "language-settings.yml");
        cfg = YamlConfiguration.loadConfiguration(languageFile);
        if (!cfg.contains("defaultLanguage")) {
            cfg.set("defaultLanguage", "en");
            save();
        }
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::save, 20L * 60L * 5L, 20L * 60L * 5L);
    }

    @Override
    public String getLanguage(UUID uuid) {
        return cfg.getString(uuid.toString(), getDefaultLanguage());
    }

    @Override
    public void setLanguage(UUID uuid, String language) {
        cfg.set(uuid.toString(), language);
    }

    @Override
    public String getConsoleLanguage() {
        return cfg.getString("consoleLanguage", getDefaultLanguage());
    }

    @Override
    public void setConsoleLanguage(String language) {
        cfg.set("consoleLanguage", language);
    }

    @Override
    public String getDefaultLanguage() {
        return cfg.getString("defaultLanguage", "en");
    }

    @Override
    public void setDefaultLanguage(String language) {
        cfg.set("defaultLanguage", Preconditions.checkNotNull(language, "The provided language to set as a default cannot be null"));
    }

    public void save() {
        try {
            cfg.save(languageFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save yaml language settings", e);
        }
    }
}
