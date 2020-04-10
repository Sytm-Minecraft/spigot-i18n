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

import de.md5lukas.i18n.api.language.LanguageStore;
import de.md5lukas.i18n.api.language.translations.Translation;

public class TranslationHolder {

    private final LanguageStore store;

    public TranslationHolder(LanguageStore store) {
        this.store = store;

        COMMAND_NOT_FOUND = t("command.notFound");
        COMMAND_NO_PERMISSION = t("command.noPermission");
        COMMAND_INFO_LICENSE = t("command.info.license");
        COMMAND_LIST_HEADER = t("command.list.header");
        COMMAND_LIST_FULLY_SUPPORTED_HEADER = t("command.list.fullySupported.header");
        COMMAND_LIST_FULLY_SUPPORTED_LANGUAGE_COLOR = t("command.list.fullySupported.languageColor");
        COMMAND_LIST_FULLY_SUPPORTED_COMMA_COLOR = t("command.list.fullySupported.commaColor");
        COMMAND_LIST_PARTIALLY_SUPPORTED_HEADER = t("command.list.partiallySupported.header");
        COMMAND_LIST_PARTIALLY_SUPPORTED_LANGUAGE_COLOR = t("command.list.partiallySupported.languageColor");
        COMMAND_LIST_PARTIALLY_SUPPORTED_COMMA_COLOR = t("command.list.partiallySupported.commaColor");
        COMMAND_CHANGE_SUCCESS = t("command.change.success");
        COMMAND_CHANGE_NOT_FOUND = t("command.change.notFound");
        COMMAND_SET_DEFAULT_SUCCESS = t("command.setDefault.success");
        COMMAND_SET_DEFAULT_NOT_FOUND = t("command.setDefault.notFound");
        COMMAND_SET_DEFAULT_MISSING_ARGUMENT = t("command.setDefault.missingArgument");
        COMMAND_HELP_HEADER = t("command.help.header");
        COMMAND_HELP_LIST_LANGUAGES = t("command.help.listLanguages");
        COMMAND_HELP_CHANGE_LANGUAGE = t("command.help.changeLanguage");
        COMMAND_HELP_SET_DEFAULT_LANGUAGE = t("command.help.setDefaultLanguage");
        COMMAND_HELP_INFO = t("command.help.info");
        COMMAND_HELP_HELP = t("command.help.help");
    }

    public final Translation
            COMMAND_NOT_FOUND,
            COMMAND_NO_PERMISSION,
            COMMAND_INFO_LICENSE,
            COMMAND_LIST_HEADER,
            COMMAND_LIST_FULLY_SUPPORTED_HEADER,
            COMMAND_LIST_FULLY_SUPPORTED_LANGUAGE_COLOR,
            COMMAND_LIST_FULLY_SUPPORTED_COMMA_COLOR,
            COMMAND_LIST_PARTIALLY_SUPPORTED_HEADER,
            COMMAND_LIST_PARTIALLY_SUPPORTED_LANGUAGE_COLOR,
            COMMAND_LIST_PARTIALLY_SUPPORTED_COMMA_COLOR,
            COMMAND_CHANGE_SUCCESS,
            COMMAND_CHANGE_NOT_FOUND,
            COMMAND_SET_DEFAULT_SUCCESS,
            COMMAND_SET_DEFAULT_NOT_FOUND,
            COMMAND_SET_DEFAULT_MISSING_ARGUMENT,
            COMMAND_HELP_HEADER,
            COMMAND_HELP_LIST_LANGUAGES,
            COMMAND_HELP_CHANGE_LANGUAGE,
            COMMAND_HELP_SET_DEFAULT_LANGUAGE,
            COMMAND_HELP_INFO,
            COMMAND_HELP_HELP;

    private Translation t(String key) {
        return new Translation(store, key);
    }
}
