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

package de.md5lukas.i18n.spigot.language;

import de.md5lukas.i18n.spigot.service.LanguageSupport;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Collections;
import java.util.Set;

/**
 * A simple wrapper for {@link LanguageSupport} that fails silently if a service provider is not registered
 */
public class LanguageSupportHelper {

    private final LanguageSupport languageSupport;

    public LanguageSupportHelper() {
        RegisteredServiceProvider<LanguageSupport> rsp = Bukkit.getServer().getServicesManager().getRegistration(LanguageSupport.class);
        if (rsp == null) {
            languageSupport = null;
        } else {
            languageSupport = rsp.getProvider();
        }
    }

    /**
     * Same as {@link LanguageSupport#registerLanguages(Plugin, Set)}, but if no service provider is present it does nothing
     */
    public void registerLanguages(Plugin plugin, Set<String> languages) {
        if (languageSupport != null) {
            languageSupport.registerLanguages(plugin, languages);
        }
    }

    /**
     * Same as {@link LanguageSupport#fullySupportedLanguages()}, but if no service provider is present it returns an empty set
     */
    public Set<String> fullySupportedLanguages() {
        if (languageSupport != null)
            return languageSupport.fullySupportedLanguages();
        return Collections.emptySet();
    }

    /**
     * Same as {@link LanguageSupport#partiallySupportedLanguages()}, but if no service provider is present it returns an empty set
     */
    public Set<String> partiallySupportedLanguages() {
        if (languageSupport != null)
            return languageSupport.partiallySupportedLanguages();
        return Collections.emptySet();
    }

    public boolean isServiceProviderPresent() {
        return languageSupport != null;
    }
}
