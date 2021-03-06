// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.startup;

import java.util.Locale;

/**
 * Abstract implementation of {@link StartupActionFactory} that covers some
 * boilerplate code for most implementations.
 *
 * @author Randall Wood (C) 2016, 2020
 */
abstract public class AbstractStartupActionFactory implements StartupActionFactory {

    /**
     * {@inheritDoc}
     * <p>
     * This implementation calls
     * {@link #getTitle(java.lang.Class, java.util.Locale)} with the default
     * locale.
     *
     * @param clazz the class
     * @return the title
     * @throws IllegalArgumentException if the class is not supported by this
     *                                  factory
     */
    @Override
    public String getTitle(Class<?> clazz) throws IllegalArgumentException {
        return this.getTitle(clazz, Locale.getDefault());
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns an empty array.
     *
     * @param clazz the class
     * @return an empty array
     * @throws IllegalArgumentException if the class is not supported by this
     *                                  factory
     */
    @Override
    public String[] getOverriddenClasses(Class<?> clazz) throws IllegalArgumentException {
        return new String[0];
    }

}
