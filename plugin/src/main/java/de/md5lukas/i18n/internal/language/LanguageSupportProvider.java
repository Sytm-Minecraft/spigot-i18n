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

import de.md5lukas.i18n.api.service.LanguageSupport;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class LanguageSupportProvider implements LanguageSupport {

    private Map<Plugin, Set<String>> languagesSupported = new HashMap<>();
    private boolean shouldCalculate = true;
    private Set<String> fullySupported = new HashSet<>(), fullySupportedUnmodifiable = Collections.unmodifiableSet(fullySupported);
    private Set<String> partiallySupported = new HashSet<>(), partiallySupportedUnmodifiable = Collections.unmodifiableSet(partiallySupported);

    @Override
    public void registerLanguages(Plugin plugin, Set<String> languages) {
        languagesSupported.put(plugin, new HashSet<>(languages));
    }

    @Override
    public Set<String> fullySupportedLanguages() {
        calculateSupportedLanguages();
        return fullySupportedUnmodifiable;
    }

    @Override
    public Set<String> partiallySupportedLanguages() {
        calculateSupportedLanguages();
        return partiallySupportedUnmodifiable;
    }

    @Override
    public boolean isLanguageRegistered(String key) {
        return fullySupported.contains(key) || partiallySupported.contains(key);
    }

    public void calculateSupportedLanguages() {
        if (shouldCalculate) {
            shouldCalculate = false;
            fullySupported.clear();
            partiallySupported.clear();
            for (Set<String> languages : languagesSupported.values()) {
                partiallySupported.addAll(languages);
                if (fullySupported.isEmpty()) {
                    fullySupported.addAll(languages);
                } else {
                    fullySupported.removeIf(language -> !languages.contains(language));
                }
            }
        }
    }
}
