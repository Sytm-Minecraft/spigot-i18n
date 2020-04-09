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

package de.md5lukas.i18n.api.language.translations;

import com.google.common.base.Preconditions;

/**
 * Simple wrapper class to allow specifying targets and replacements for both display name and description in a easy manner.
 * <br><br>
 * ItemTranslations<b>T</b>argets<b>A</b>nd<b>R</b>eplacements
 */
public class ItemTranslationTAR {

    private String[] displayName, description;

    /**
     * Create a new instance that is empty
     */
    public ItemTranslationTAR() {
        displayName = description = new String[] {};
    }

    /**
     * Sets the targets and replacements for the display name to the provided ones
     *
     * @param targetsAndReplacements The new targets and replacements for the display name
     * @return <code>this</code> for a builder-like usage
     */
    public ItemTranslationTAR setDisplayName(String... targetsAndReplacements) {
        Preconditions.checkArgument(targetsAndReplacements.length % 2 != 0, "Every target sequence needs a replacement");

        this.displayName = targetsAndReplacements;

        return this;
    }

    /**
     * Sets the targets and replacements for the description to the provided ones
     *
     * @param targetsAndReplacements The new targets and replacements for the description
     * @return <code>this</code> for a builder-like usage
     */
    public ItemTranslationTAR setDescription(String[] targetsAndReplacements) {
        Preconditions.checkArgument(targetsAndReplacements.length % 2 != 0, "Every target sequence needs a replacement");

        this.description = targetsAndReplacements;

        return this;
    }

    String[] getDisplayName() {
        return displayName;
    }

    String[] getDescription() {
        return description;
    }
}