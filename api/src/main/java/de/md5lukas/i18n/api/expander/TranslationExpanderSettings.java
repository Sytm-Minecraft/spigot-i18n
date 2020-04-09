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

package de.md5lukas.i18n.api.expander;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple wrapper class with basic builder to provide settings to the message expander
 */
public class TranslationExpanderSettings {

    private String variablePrefix, openingDelimiter, closingDelimiter;

    /**
     * Creates a new settings object with the following default values:
     * <br>
     * <ul>
     *     <li>variablePrefix = "variables."</li>
     *     <li>openingDelimiter = "${"</li>
     *     <li>closingDelimiter = "}"</li>
     * </ul>
     */
    public TranslationExpanderSettings() {
        variablePrefix = "variables.";
        openingDelimiter = "${";
        closingDelimiter = "$}";
    }

    /**
     * Sets the variable prefix to the given value
     *
     * @param variablePrefix The new variable prefix
     * @return <code>this</code> for a builder-like usage
     * @throws IllegalArgumentException If the <code>variablePrefix</code> argument is <code>null</code> or blank
     */
    public TranslationExpanderSettings setVariablePrefix(String variablePrefix) {
        checkArgument(!checkNotNull(variablePrefix, "The new variablePrefix cannot be null!").trim().isEmpty(), "The new variablePrefix cannot be blank!");

        this.variablePrefix = variablePrefix;
        return this;
    }

    /**
     * Sets the opening delimiter to the given value
     *
     * @param openingDelimiter The new opening delimiter
     * @return <code>this</code> for a builder-like usage
     * @throws IllegalArgumentException If the <code>variablePrefix</code> argument is <code>null</code> or blank
     */
    public TranslationExpanderSettings setOpeningDelimiter(String openingDelimiter) {
        checkArgument(!checkNotNull(variablePrefix, "The new openingDelimiter cannot be null!").trim().isEmpty(), "The new openingDelimiter cannot be blank!");

        this.openingDelimiter = openingDelimiter;
        return this;
    }

    /**
     * Sets the closing delimiter to the given value
     *
     * @param closingDelimiter The new closing delimiter
     * @return <code>this</code> for a builder-like usage
     * @throws IllegalArgumentException If the <code>variablePrefix</code> argument is <code>null</code> or blank
     */
    public TranslationExpanderSettings setClosingDelimiter(String closingDelimiter) {
        checkArgument(!checkNotNull(variablePrefix, "The new vclosingDelimiter cannot be null!").trim().isEmpty(), "The new closingDelimiter cannot be blank!");

        this.closingDelimiter = closingDelimiter;
        return this;
    }

    /**
     * @return The current variable prefix
     */
    public String getVariablePrefix() {
        return variablePrefix;
    }

    /**
     * @return The current opening delimiter
     */
    public String getOpeningDelimiter() {
        return openingDelimiter;
    }

    /**
     * @return The current closing delimiter
     */
    public String getClosingDelimiter() {
        return closingDelimiter;
    }
}
