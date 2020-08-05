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

import com.google.common.base.Preconditions;

final class StringHelper {

    // TODO use method from commons library and update JavaDoc to explain it better
    static String multiReplace(String string, String... targetsAndReplacements) {
        if (targetsAndReplacements.length == 0)
            return string;
        Preconditions.checkArgument(targetsAndReplacements.length % 2 == 0, "Every target sequence needs a replacement");

        for (int index = 0; index < targetsAndReplacements.length; index += 2) {
            string = string.replace(targetsAndReplacements[index], targetsAndReplacements[index + 1]);
        }

        return string;
    }
}
