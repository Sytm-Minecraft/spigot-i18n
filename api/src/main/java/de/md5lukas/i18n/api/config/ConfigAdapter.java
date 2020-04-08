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

package de.md5lukas.i18n.api.config;

import java.util.Collection;

/**
 * A simple interface to abstract the actual config parser away
 */
public interface ConfigAdapter {

    /**
     * Get a string from the config
     *
     * @param path the path where the string can be found
     * @return the string or <code>null</code> if not present
     */
    String getString(String path);

    /**
     * List all keys in the config that have a string as a value
     *
     * @return all keys
     */
    Collection<String> listAllKeys();
}
