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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LanguageCommand implements CommandExecutor {

    private final Main main;

    public LanguageCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {

        } else {
            switch (args[0].toLowerCase()) {
                case "help":
                    // TODO: show help + LICENSE and source
                case "setdefault":
                    // TODO: set default
                    break;
                default:
                    // TODO: set language
                    break;
            }
        }
        return true;
    }
}
