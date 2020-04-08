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
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Translation {

    private final String message;
    private final List<String> asList;

    public Translation(String message) {
        this.message = message;
        this.asList = Collections.unmodifiableList(Arrays.asList(this.message.split("\\r?\\n", -1)));
    }

    public String getMessage(String... targetsAndReplacements) {
        return multiReplace(message, targetsAndReplacements);
    }

    public List<String> asList(String... targetsAndReplacements) {
        if (targetsAndReplacements.length == 0)
            return asList;
        return asList.stream().map(string -> multiReplace(string, targetsAndReplacements)).collect(Collectors.toList());
    }

    public void send(CommandSender sender, String... targetsAndReplacements) {
        sender.sendMessage(multiReplace(message, targetsAndReplacements));
    }

    private String multiReplace(String string, String... targetsAndReplacements) {
        if (targetsAndReplacements.length == 0)
            return string;
        Preconditions.checkArgument(targetsAndReplacements.length % 2 == 0, "Every target sequence needs a replacement");

        for (int index = 0; index < targetsAndReplacements.length; index += 2) {
            string = string.replace(targetsAndReplacements[index], targetsAndReplacements[index + 1]);
        }

        return string;
    }
}
