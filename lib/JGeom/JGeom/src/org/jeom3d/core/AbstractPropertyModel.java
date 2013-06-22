package org.jeom3d.core;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Implementation of PropertyModel.
 * @author Siddharth
 * @version 06/10/09
 */
public abstract class AbstractPropertyModel implements PropertyModel {

    private PropertyChangeSupport _support = null;

    /**
     * Gets the {@code PropertyChangeSupport} object.
     * @return the propery change support object.
     */
    private PropertyChangeSupport getSupport() {
        if (_support == null) {
            _support = createPropertyChangeSupport();
        }
        return _support;
    }

    /**
     * Creates a property change support object. Override this method if you want
     * to change the source object in the property events fired by this model.
     * @return the {@code properyChangeSupport} object.
     */
    protected PropertyChangeSupport createPropertyChangeSupport() {
        return new PropertyChangeSupport(this);
    }

    /**
     * {@inheritDoc}
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getSupport().addPropertyChangeListener(propertyName, listener);
    }

    /**
     * {@inheritDoc}
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (_support != null) {
            _support.removePropertyChangeListener(listener);
            if (_support.getPropertyChangeListeners().length == 0) {
                _support = null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        if (_support != null) {
            _support.removePropertyChangeListener(propertyName, listener);
            if (_support.getPropertyChangeListeners().length == 0) {
                _support = null;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasProperty(String propertyName) {
        String[] names = getPropertyNames();
        if (names == null) {
            return false;
        }
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(propertyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Notifies listeners that the specified propety has been changed.
     * @param propertyName the property name.
     * @param oldValue the old value.
     * @param newValue the new value.
     */
    protected void firePropertyChange(String propertyName,
            Object oldValue, Object newValue) {
        if (_support != null) {
            _support.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /**
     * Notifies listeners that the specified propety has been changed.
     * @param propertyName the property name.
     * @param oldValue the old value.
     * @param newValue the new value.
     */
    protected void firePropertyChange(String propertyName,
            boolean oldValue, boolean newValue) {
        if (_support != null) {
            _support.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /**
     * Notifies listeners that the specified propety has been changed.
     * @param propertyName the property name.
     * @param oldValue the old value.
     * @param newValue the new value.
     */
    protected void firePropertyChange(String propertyName,
            int oldValue, int newValue) {
        if (_support != null) {
            _support.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /**
     * Notifies listeners that the specified propety has been changed.
     * @param propertyName the property name.
     * @param oldValue the old value.
     * @param newValue the new value.
     */
    protected void firePropertyChange(String propertyName,
            float oldValue, float newValue) {
        if(_support != null) {
            _support.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /**
     * Notifies listeners that the specified propety has been changed.
     * @param evt the {@code PropertyChangeEvent} object.
     */
    protected void firePropertyChange(PropertyChangeEvent evt) {
        if(_support != null) {
            _support.firePropertyChange(evt);
        }
    }

    /**
     * Fires events based on differences between a new model and an old model.
     * Fires an event for
     * 1) every property tha is in one model but not in the other.
     * 2) for every property common to both models but with different values.
     * In the property change events, the old values for properties unique to
     * new model and new values for properties uniquq to old model will be null.
     *
     * @param oldModel the old model.
     * @param newModel the new model.
     */
    protected void firePropertyDifference(PropertyModel oldModel) {
        firePropertyDifferences(oldModel, this);
    }

    /**
     * Fires events based on differences between a new model and an old model.
     * Fires an event for
     * 1) every property tha is in one model but not in the other.
     * 2) for every property common to both models but with different values.
     * In the property change events, the old values for properties unique to
     * new model and new values for properties uniquq to old model will be null.
     *
     * @param oldModel the old model.
     * @param newModel the new model.
     */
    protected void firePropertyDifferences(PropertyModel oldModel,
            PropertyModel newModel) {

        //Consolidate names from both models into one set of unique names.
        String[][] both = new String[][] {
            oldModel == null ? new String[0] : oldModel.getPropertyNames() ,
            newModel == null ? new String[0] : newModel.getPropertyNames()
        } ;
        HashSet<String> names = new HashSet<String>() ;
        for (int i = 0; i < both.length; i++) {
            for (int j = 0; j < both[i].length; j++) {
                names.add(both[i][j]);
            }
        }

        //Fire an event for all properties that have different values.
        Iterator<String> i = names.iterator() ;
        while(i.hasNext()) {
            String name = i.next() ;
            Object oldValue = oldModel == null ? null: oldModel.getPropertyValue(name) ;
            Object newValue = newModel == null ? null : newModel.getPropertyValue(name) ;
            if (oldValue == null ? newValue != null : !oldValue.equals(newValue)) {
                firePropertyChange(name, oldValue, newValue);
            }
        }
    }

    /**
     * Makes a property name prefixed with the name of the specified class.
     * @param prefixClass the class with which to construct the prefix.
     * @param name the class name.
     * @return the property name.
     */
    protected static String makePropertyName(@SuppressWarnings("rawtypes") Class prefixClass, String name) {
        return prefixClass.getName()+"#"+name ;
    }
}
