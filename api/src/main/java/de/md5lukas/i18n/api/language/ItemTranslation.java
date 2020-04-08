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

package de.md5lukas.i18n.api.language;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * A simple helper for translations as item stacks
 */
public class ItemTranslation {

    private final LanguageStore languageStore;
    private final Supplier<Material> materialSupplier;
    private final String displayNameKey;
    private final String descriptionKey;

    private char altColorChar = '&'; // TODO make customizable

    /**
     * Creates a new translation helper for item stacks using the language store, the material supplier and the keys for the configuration
     *
     * @param languageStore    The language store to sue to retrieve the language data from
     * @param materialSupplier The material supplier that will provide that material in the case a new item stack should get created
     * @param displayNameKey   The key of the display name in the configs
     * @param descriptionKey   The key of the description in the configs
     */
    public ItemTranslation(LanguageStore languageStore, Supplier<Material> materialSupplier, String displayNameKey, String descriptionKey) {
        this.languageStore = languageStore;
        this.materialSupplier = materialSupplier;
        this.displayNameKey = displayNameKey;
        this.descriptionKey = descriptionKey;
    }

    /**
     * Creates an item stack using both translation keys provided at creation and the language of the player
     *
     * @param player                 The player to use the language from
     * @param targetsAndReplacements The targets and replacements object containing both display name and description substitutions
     * @return A newly created item stack
     */
    public ItemStack getStack(Player player, ItemTranslationTAR targetsAndReplacements) {
        Language language = languageStore.getLanguage(player);

        ItemStack stack = new ItemStack(materialSupplier.get());
        ItemMeta meta = stack.getItemMeta();

        String displayName = language.getTranslation(displayNameKey), description = language.getTranslation(descriptionKey);

        meta.setDisplayName(ChatColor.translateAlternateColorCodes(altColorChar,
                StringHelper.multiReplace(displayName, targetsAndReplacements.displayName)));

        meta.setLore(Arrays.asList(
                ChatColor.translateAlternateColorCodes(altColorChar,
                        StringHelper.multiReplace(description, targetsAndReplacements.description))
                        .split("\\r?\\n", -1)
        ));

        stack.setItemMeta(meta);
        return stack;
    }

    /**
     * Simple wrapper class to allow specifying targets and replacements for both display name and description in a easy manner
     */
    public static class ItemTranslationTAR {

        private String[] displayName, description;

        /**
         * Create a new instance that is empty
         */
        public ItemTranslationTAR() {
            displayName = description = new String[]{};
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
    }
}
