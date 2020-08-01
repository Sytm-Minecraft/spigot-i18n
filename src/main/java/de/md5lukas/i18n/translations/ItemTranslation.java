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

import de.md5lukas.i18n.language.Language;
import de.md5lukas.i18n.language.LanguageStorage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple helper for translations as item stacks
 *
 * @author Lukas Planz
 * @since 1.0.0
 */
public final class ItemTranslation {

    private final LanguageStorage languageStorage;
    private final Supplier<Material> materialSupplier;
    private final String displayNameKey;
    private final String descriptionKey;

    private final ColorCodeSettings colorCodeSettings;

    /**
     * Creates a new translation helper for item stacks using the language store, the material supplier and the keys for the configuration
     *
     * @param languageStorage   The language store to sue to retrieve the language data from
     * @param materialSupplier  The material supplier that will provide that material in the case a new item stack should get created
     * @param displayNameKey    The key of the display name in the configs
     * @param descriptionKey    The key of the description in the configs
     * @param colorCodeSettings The color code settings to use for this translation
     * @throws NullPointerException If any of the parameters are null
     * @since 1.0.0
     */
    public ItemTranslation(LanguageStorage languageStorage, Supplier<Material> materialSupplier, String displayNameKey, String descriptionKey, ColorCodeSettings
            colorCodeSettings) {
        this.languageStorage = checkNotNull(languageStorage, "The language store cannot be null");
        this.materialSupplier = checkNotNull(materialSupplier, "The material supplier cannot be null");
        this.displayNameKey = checkNotNull(displayNameKey, "The display name key cannot be null");
        this.descriptionKey = checkNotNull(descriptionKey, "The description key cannot be null");
        this.colorCodeSettings = checkNotNull(colorCodeSettings, "The color code settings cannot be null");
    }

    /**
     * Creates a new translation helper for item stacks using the language store, the material supplier and the keys for the configuration
     *
     * @param languageStorage  The language store to sue to retrieve the language data from
     * @param materialSupplier The material supplier that will provide that material in the case a new item stack should get created
     * @param displayNameKey   The key of the display name in the configs
     * @param descriptionKey   The key of the description in the configs
     * @throws NullPointerException If any of the parameters are null
     * @since 1.0.0
     */
    public ItemTranslation(LanguageStorage languageStorage, Supplier<Material> materialSupplier, String displayNameKey, String descriptionKey) {
        this(languageStorage, materialSupplier, displayNameKey, descriptionKey, ColorCodeSettings.DEFAULT);
    }

    /**
     * Creates a new translation helper for item stacks using the language store, the material supplier and the key for the configuration.
     * <br>
     * To get the keys for the display name and description, <code>.displayName</code> and <code>.description</code> are appended to the key
     * respectively to get the keys that are actually used.
     *
     * @param languageStorage   The language store to sue to retrieve the language data from
     * @param materialSupplier  The material supplier that will provide that material in the case a new item stack should get created
     * @param key               The common key in the configs
     * @param colorCodeSettings The color code settings to use for this translation
     * @throws NullPointerException If any of the parameters are null
     * @since 1.0.0
     */
    public ItemTranslation(LanguageStorage languageStorage, Supplier<Material> materialSupplier, String key, ColorCodeSettings colorCodeSettings) {
        this(languageStorage, materialSupplier, checkNotNull(key, "The common key cannot be null") + ".displayName", key + ".description", colorCodeSettings);
    }

    /**
     * Creates a new translation helper for item stacks using the language store, the material supplier and the key for the configuration.
     * <br>
     * To get the keys for the display name and description, <code>.displayName</code> and <code>.description</code> are appended to the key
     * respectively to get the keys that are actually used.
     *
     * @param languageStorage  The language store to sue to retrieve the language data from
     * @param materialSupplier The material supplier that will provide that material in the case a new item stack should get created
     * @param key              The common key in the configs
     * @throws NullPointerException If any of the parameters are null
     * @since 1.0.0
     */
    public ItemTranslation(LanguageStorage languageStorage, Supplier<Material> materialSupplier, String key) {
        this(languageStorage, materialSupplier, key, ColorCodeSettings.DEFAULT);
    }

    /**
     * Creates an item stack using both translation keys provided at creation and the language of the player
     *
     * @param player The player to use the language from
     * @return A newly created item stack
     * @since 1.0.0
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
     * @since 1.0.0
     */
    public ItemStack getStack(Player player, ItemTranslationTAR targetsAndReplacements) {
        checkNotNull(player, "The player to get the stack for cannot be null");

        Language language = languageStorage.getLanguage(player);

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

        meta.setDisplayName(colorCodeSettings.apply(StringHelper.multiReplace(displayName, displayNameTAR)));

        meta.setLore(Arrays.asList(colorCodeSettings.apply(StringHelper.multiReplace(description, descriptionTAR)).split("\\r?\\n", 0)));

        stack.setItemMeta(meta);
        return stack;
    }
}
