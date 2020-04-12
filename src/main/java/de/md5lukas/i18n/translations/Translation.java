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

import de.md5lukas.i18n.language.LanguageStorage;
import org.bukkit.command.CommandSender;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple helper for translations as messages
 *
 * @author Lukas Planz
 * @since 1.0.0
 */
public final class Translation {

    private final LanguageStorage languageStorage;
    private final String key;

    private final ColorCodeSettings colorCodeSettings;

    /**
     * Creates a new translation helper using the language store, the key in the configuration and color code settings
     *
     * @param languageStorage   The language store to use to retrieve the language data from
     * @param key               The key of this translation in the configs
     * @param colorCodeSettings The color code settings to use for this translation
     * @since 1.0.0
     */
    public Translation(LanguageStorage languageStorage, String key, ColorCodeSettings colorCodeSettings) {
        this.languageStorage = checkNotNull(languageStorage, "The language store cannot be null");
        this.key = checkNotNull(key, "The translation key cannot be null");
        this.colorCodeSettings = checkNotNull(colorCodeSettings, "The color code settings cannot be null");
    }

    /**
     * Creates a new translation helper using the language store and the key in the configuration
     * <br><br>
     * {@link ColorCodeSettings#ColorCodeSettings()} for default color code settings
     *
     * @param languageStorage The language store to use to retrieve the language data from
     * @param key             The key of this translation in the configs
     * @since 1.0.0
     */
    public Translation(LanguageStorage languageStorage, String key) {
        this(languageStorage, key, ColorCodeSettings.DEFAULT);
    }

    /**
     * Gets the string from the current translation in the language of the command sender
     *
     * @param commandSender          The command sender of which the language should be used
     * @param targetsAndReplacements The targets and replacements to use to substitute strings in this translation
     * @return The translated message with substitutions for the targets in place
     * @throws NullPointerException If commandSender is null
     * @since 1.0.0
     */
    public String getAsString(CommandSender commandSender, String... targetsAndReplacements) {
        checkNotNull(commandSender, "The command sender cannot be null");
        return colorCodeSettings.apply(StringHelper.multiReplace(languageStorage.getLanguage(commandSender).getTranslation(key)));
    }

    /**
     * Gets the message using {@link #getAsString(CommandSender, String...)} and directly sends it to the command sender
     *
     * @param commandSender          The command sender of which the language should be used and the message should be sent to
     * @param targetsAndReplacements The targets and replacements to use to substitute strings in this translation
     * @throws NullPointerException If commandSender is null
     * @since 1.0.0
     */
    public void send(CommandSender commandSender, String... targetsAndReplacements) {
        checkNotNull(commandSender, "The command sender cannot be null")
                .sendMessage(colorCodeSettings.apply(getAsString(commandSender, targetsAndReplacements)));
    }
}
