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

package de.md5lukas.i18n.internal;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class LanguageCommand implements CommandExecutor {

    private final Main main;

    public LanguageCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendLanguageList(sender);
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "info":
                    sendInfo(sender);
                    break;
                case "help":
                    sendHelp(sender);
                    break;
                case "setdefault":
                    if (sender.hasPermission("spigot-i18n.set-default")) {
                        main.th().COMMAND_SET_DEFAULT_MISSING_ARGUMENT.send(sender);
                    } else {
                        main.th().COMMAND_NO_PERMISSION.send(sender);
                    }
                    break;
                default:
                    changeLanguage(sender, args[0]);
                    break;
            }
        } else {
            switch (args[0].toLowerCase()) {
                case "setdefault":
                    setDefaultLanguage(sender, args[1]);
                    break;
                default:
                    main.th().COMMAND_NOT_FOUND.send(sender);
                    break;
            }
        }
        return true;
    }

    private void sendLanguageList(CommandSender sender) {
        main.th().COMMAND_LIST_HEADER.send(sender);
        Set<String> fullySupported = main.getLanguageSupport().fullySupportedLanguages(),
                partiallySupported = main.getLanguageSupport().partiallySupportedLanguages();
        if (!fullySupported.isEmpty()) {
            main.th().COMMAND_LIST_FULLY_SUPPORTED_HEADER.send(sender);
            String languageColor = main.th().COMMAND_LIST_FULLY_SUPPORTED_LANGUAGE_COLOR.getAsString(sender),
                    commaColor = main.th().COMMAND_LIST_FULLY_SUPPORTED_COMMA_COLOR.getAsString(sender);
            sender.sendMessage(getLanguageSupportString(fullySupported, languageColor, commaColor));
        }
        if (!partiallySupported.isEmpty()) {
            main.th().COMMAND_LIST_PARTIALLY_SUPPORTED_HEADER.send(sender);
            String languageColor = main.th().COMMAND_LIST_PARTIALLY_SUPPORTED_LANGUAGE_COLOR.getAsString(sender),
                    commaColor = main.th().COMMAND_LIST_PARTIALLY_SUPPORTED_COMMA_COLOR.getAsString(sender);
            sender.sendMessage(getLanguageSupportString(partiallySupported, languageColor, commaColor));
        }
    }

    private String getLanguageSupportString(Set<String> languages, String languageColor, String commaColor) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String key : languages) {
            if (first) {
                first = false;
            } else {
                sb.append(commaColor).append(" ");
            }
            sb.append(languageColor).append(key);
        }

        return sb.toString();
    }

    private void setDefaultLanguage(CommandSender sender, String languageKey) {
        if (sender.hasPermission("spigot-i18n.set-default")) {
            if (main.getLanguageSupport().isLanguageRegistered(languageKey)) {
                main.getLanguageSettings().setDefaultLanguage(languageKey);
                main.th().COMMAND_SET_DEFAULT_SUCCESS.send(sender, "%key%", languageKey);
            } else {
                main.th().COMMAND_SET_DEFAULT_NOT_FOUND.send(sender);
            }
        } else {
            main.th().COMMAND_NO_PERMISSION.send(sender);
        }
    }

    private void changeLanguage(CommandSender sender, String languageKey) {
        if ("default".equalsIgnoreCase(languageKey)) {
            main.getLanguageSettings().setLanguage(sender, null);
        } else if (main.getLanguageSupport().isLanguageRegistered(languageKey)) {
            main.getLanguageSettings().setLanguage(sender, languageKey);
        } else {
            main.th().COMMAND_CHANGE_NOT_FOUND.send(sender, "%key%", languageKey);
            return;
        }
        main.th().COMMAND_CHANGE_SUCCESS.send(sender);
    }

    private void sendHelp(CommandSender sender) {
        main.th().COMMAND_HELP_HELP.send(sender);
        main.th().COMMAND_HELP_LIST_LANGUAGES.send(sender);
        main.th().COMMAND_HELP_CHANGE_LANGUAGE.send(sender);
        if (sender.hasPermission("spigot-i18n.set-default")) {
            main.th().COMMAND_HELP_SET_DEFAULT_LANGUAGE.send(sender);
        }
        main.th().COMMAND_HELP_INFO.send(sender);
        main.th().COMMAND_HELP_HELP.send(sender);
    }

    private void sendInfo(CommandSender sender) {
        String message = main.th().COMMAND_INFO_LICENSE.getAsString(sender);
        if (sender instanceof Player) {
            BaseComponent[] components = TextComponent.fromLegacyText(message);
            ClickEvent event = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Sytm/spigot-i18n");
            for (BaseComponent component : components) {
                component.setClickEvent(event);
            }
            ((Player) sender).spigot().sendMessage(components);
        } else {
            sender.sendMessage(message);
            sender.sendMessage("https://github.com/Sytm/spigot-i18n");
        }
    }
}
