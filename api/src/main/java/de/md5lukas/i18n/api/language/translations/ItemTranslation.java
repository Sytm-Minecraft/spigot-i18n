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
import de.md5lukas.i18n.api.language.Language;
import de.md5lukas.i18n.api.language.LanguageStore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

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
     * <br><br>
     * A {@link NullPointerException} is thrown if any of the parameters are null
     *
     * @param languageStore    The language store to sue to retrieve the language data from
     * @param materialSupplier The material supplier that will provide that material in the case a new item stack should get created
     * @param displayNameKey   The key of the display name in the configs
     * @param descriptionKey   The key of the description in the configs
     */
    public ItemTranslation(LanguageStore languageStore, Supplier<Material> materialSupplier, String displayNameKey, String descriptionKey) {
        this.languageStore = checkNotNull(languageStore, "The language store cannot be null");
        this.materialSupplier = checkNotNull(materialSupplier, "The material supplier cannot be null");
        this.displayNameKey = checkNotNull(displayNameKey, "The display name key cannot be null");
        this.descriptionKey = checkNotNull(descriptionKey, "The description key cannot be null");
    }

    /**
     * Creates an item stack using both translation keys provided at creation and the language of the player
     *
     * @param player The player to use the language from
     * @return A newly created item stack
     */
    public ItemStack getStack(Player player) {
        return getStack(player, null);
    }

    /**
     * Creates an item stack using both translation keys provided at creation and the language of the player
     *
     * @param player                 The player to use the language from
     * @param targetsAndReplacements The targets and replacements object containing both display name and description substitutions
     * @return A newly created item stack
     * @throws NullPointerException If player is null
     */
    public ItemStack getStack(Player player, ItemTranslationTAR targetsAndReplacements) {
        checkNotNull(player, "The player to get the stack for cannot be null");

        Language language = languageStore.getLanguage(player);

        ItemStack stack = new ItemStack(materialSupplier.get());
        ItemMeta meta = stack.getItemMeta();

        String displayName = language.getTranslation(displayNameKey), description = language.getTranslation(descriptionKey);

        String[] displayNameTAR, descriptionTAR;
        if (targetsAndReplacements == null) {
            displayNameTAR = descriptionTAR = new String[] {};
        } else {
            displayNameTAR = targetsAndReplacements.getDisplayName();
            descriptionTAR = targetsAndReplacements.getDescription();
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes(altColorChar,
                StringHelper.multiReplace(displayName, displayNameTAR)));

        meta.setLore(Arrays.asList(
                ChatColor.translateAlternateColorCodes(altColorChar,
                        StringHelper.multiReplace(description, descriptionTAR))
                        .split("\\r?\\n", -1)
        ));

        stack.setItemMeta(meta);
        return stack;
    }
}
