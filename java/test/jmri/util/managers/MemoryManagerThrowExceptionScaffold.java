// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.util.managers;

import javax.annotation.Nonnull;
import javax.annotation.CheckForNull;
import jmri.InstanceManager;
import jmri.Memory;
import jmri.jmrix.internal.InternalSystemConnectionMemo;
import jmri.managers.DefaultMemoryManager;

/**
 * This manager always throws an IllegalArgumentException from provide and get methods.
 * It is used for testing exception handling in tests.
 * 
 * The class name ends with 'Scaffold' to exclude it from the coverage statistics,
 * since it is part of the testing infrastructure.
 */
public class MemoryManagerThrowExceptionScaffold extends DefaultMemoryManager {

    public MemoryManagerThrowExceptionScaffold() {
        super(InstanceManager.getDefault(InternalSystemConnectionMemo.class));
    }
    
    /** {@inheritDoc} */
    @Override
    @Nonnull
    protected Memory createNewMemory(@Nonnull String systemName, @CheckForNull String userName) {
        throw new IllegalArgumentException("Illegal argument");
    }
    
    /** {@inheritDoc} */
    @Override
    @Nonnull
    public Memory provideMemory(@Nonnull String name) {
        throw new IllegalArgumentException("Illegal argument");
    }
    
    /** {@inheritDoc} */
    @Override
    public Memory getMemory(@Nonnull String name) {
        throw new IllegalArgumentException("Illegal argument");
    }
    
    /** {@inheritDoc} */
    @Override
    public Memory getBySystemName(@Nonnull String name) {
        throw new IllegalArgumentException("Illegal argument");
    }
    
    /** {@inheritDoc} */
    @Override
    public Memory getByUserName(@Nonnull String key) {
        throw new IllegalArgumentException("Illegal argument");
    }
    
    /** {@inheritDoc} */
    @Override
    @Nonnull
    public Memory newMemory(@Nonnull String systemName, @CheckForNull String userName) {
        throw new IllegalArgumentException("Illegal argument");
    }
    
}
