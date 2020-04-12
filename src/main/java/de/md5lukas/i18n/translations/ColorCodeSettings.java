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

package de.md5lukas.i18n.translations;

import net.md_5.bungee.api.ChatColor;

/**
 * Set settings for the color codes in translations
 */
public final class ColorCodeSettings {

    static final ColorCodeSettings DEFAULT = new ColorCodeSettings();

    private boolean enabled;
    private char altColorChar;

    /**
     * Creates a new settings object with the following default values:
     * <ul>
     *     <li>enabled = true</li>
     *     <li>altColorChar = {@literal '&'}</li>
     * </ul>
     */
    public ColorCodeSettings() {
        enabled = true;
        altColorChar = '&';
    }

    /**
     * Sets the enabled status to the new value
     *
     * @param enabled The new value
     * @return <code>this</code> for a builder like usage
     */
    public ColorCodeSettings setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Sets the new alternate color char to use
     *
     * @param altColorChar The new value
     * @return <code>this</code> for a builder like usage
     * @see ChatColor#translateAlternateColorCodes(char, String)
     */
    public ColorCodeSettings setAltColorChar(char altColorChar) {
        this.altColorChar = altColorChar;
        return this;
    }

    String apply(String input) {
        if (enabled) {
            return ChatColor.translateAlternateColorCodes(altColorChar, input);
        } else {
            return input;
        }
    }
}
