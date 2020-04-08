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

package de.md5lukas.i18n.api.service;

import org.bukkit.plugin.Plugin;

import java.util.Set;

/**
 * Simple service to register languages that are supported on the server
 */
public interface LanguageSupport {

    /**
     * Registers the provided languages as supported for the given plugin.
     * <br><br>
     * If languages have already been registered, they will be overridden.
     *
     * @param plugin    The plugin that should be the languages registered to
     * @param languages All loaded languages that have been detected
     */
    void registerLanguages(Plugin plugin, Set<String> languages);

    /**
     * All languages that are supported by every registered plugin are listed.
     * <br><br>
     * The returned set is not modifiable
     *
     * @return All fully supported languages
     * @see java.util.Collections#unmodifiableSet(Set)
     */
    Set<String> fullySupportedLanguages();

    /**
     * All languages that are supported at least once are listed.
     * <br><br>
     * The returned set is not modifiable
     *
     * @return All partially supported languages
     * @see java.util.Collections#unmodifiableSet(Set)
     */
    Set<String> partiallySupportedLanguages();
}
