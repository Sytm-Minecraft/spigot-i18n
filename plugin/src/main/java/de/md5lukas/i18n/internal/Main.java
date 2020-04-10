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

package de.md5lukas.i18n.internal;

import de.md5lukas.i18n.api.language.MultiLanguageStore;
import de.md5lukas.i18n.api.service.LanguageSettings;
import de.md5lukas.i18n.api.service.LanguageSupport;
import de.md5lukas.i18n.internal.config.Config;
import de.md5lukas.i18n.internal.language.LanguageLoader;
import de.md5lukas.i18n.internal.language.LanguageSupportProvider;
import de.md5lukas.i18n.internal.language.TranslationHolder;
import de.md5lukas.i18n.internal.service.MySQLLanguageSettings;
import de.md5lukas.i18n.internal.service.YamlLanguageSettings;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    private Config config;
    private TranslationHolder translationHolder;
    private LanguageSupport languageSupport;
    private LanguageSettings languageSettings = null;

    @Override
    public void onEnable() {
        loadConfig();
        registerServices();
        loadLanguages();

        getCommand("language").setExecutor(new LanguageCommand(this));
    }

    @Override
    public void onDisable() {
        if (languageSettings instanceof YamlLanguageSettings) {
            ((YamlLanguageSettings) languageSettings).save();
        }
    }

    public TranslationHolder th() {
        return translationHolder;
    }

    public LanguageSettings getLanguageSettings() {
        return languageSettings;
    }

    public LanguageSupport getLanguageSupport() {
        return languageSupport;
    }

    private void registerServices() {
        ServicesManager sm = getServer().getServicesManager();
        switch (config.getFormat()) {
            case YML:
                languageSettings = new YamlLanguageSettings(this);
                sm.register(LanguageSettings.class, languageSettings, this, ServicePriority.Lowest);
                break;
            case MYSQL:
                languageSettings = new MySQLLanguageSettings(this, config.getMySQLSettings());
                sm.register(LanguageSettings.class, languageSettings, this, ServicePriority.Lowest);
                break;
            default:
                getLogger().log(Level.SEVERE, "Could not determine format for service provider (%s), disabling", config.getFormat());
                disableThis();
                return;
        }

        languageSupport = new LanguageSupportProvider();

        sm.register(LanguageSupport.class, languageSupport, this, ServicePriority.Lowest);
    }

    private void loadConfig() {
        config = new Config();
        saveDefaultConfig();
        config.load(getConfig());
    }

    private void loadLanguages() {
        MultiLanguageStore store = new MultiLanguageStore();
        translationHolder = new TranslationHolder(store);
        new LanguageLoader(this, store).load();
    }

    private void disableThis() {
        getServer().getPluginManager().disablePlugin(this);
    }
}
