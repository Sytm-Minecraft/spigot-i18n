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

package de.md5lukas.i18n.spigot.internal;

import de.md5lukas.i18n.api.service.LanguageSettings;
import de.md5lukas.i18n.api.service.LanguageSupport;
import de.md5lukas.i18n.spigot.internal.config.Config;
import de.md5lukas.i18n.spigot.internal.language.LanguageSupportProvider;
import de.md5lukas.i18n.spigot.internal.service.MySQLLanguageSettings;
import de.md5lukas.i18n.spigot.internal.service.YamlLanguageSettings;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        loadConfig();
        ServicesManager sm = getServer().getServicesManager();
        switch (config.getFormat()) {
            case YML:
                sm.register(LanguageSettings.class, new YamlLanguageSettings(this), this, ServicePriority.Lowest);
                break;
            case MYSQL:
                sm.register(LanguageSettings.class, new MySQLLanguageSettings(this, config.getMySQLSettings()), this, ServicePriority.Lowest);
                break;
            default:
                break;
        }
        sm.register(LanguageSupport.class, new LanguageSupportProvider(), this, ServicePriority.Lowest);

        getCommand("language").setExecutor(new LanguageCommand(this));
    }

    private void loadConfig() {
        config = new Config();
        saveDefaultConfig();
        config.load(getConfig());
    }

    private void _reloadConfig() {
        reloadConfig();
        config.load(getConfig());
    }
}
