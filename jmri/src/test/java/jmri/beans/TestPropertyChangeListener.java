// SPDX-License-Identifier: GPL-2.0 WITH Classpath-exception-2.0
package jmri.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class TestPropertyChangeListener implements PropertyChangeListener {

    private final List<PropertyChangeEvent> events = new ArrayList<>();
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        events.add(evt);
    }

    public PropertyChangeEvent getLastEvent() {
        return events.isEmpty() ? null : events.get(events.size() - 1);
    }

    public List<PropertyChangeEvent> getEvents() {
        return new ArrayList<>(events);
    }

    public void clear() {
        events.clear();
    }
}
