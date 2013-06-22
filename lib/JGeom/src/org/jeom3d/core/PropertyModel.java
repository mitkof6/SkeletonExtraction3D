package org.jeom3d.core;

import java.beans.PropertyChangeListener;

/**
 * An object with named properties that can be monitored for changes.
 * @author Siddharth
 * @version 06/10/09
 */
public interface PropertyModel {
    /**
     *  Adds a listener for changes to any property.
     * @param listener the listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) ;

    /**
     * Adds a listener for changes to the specified property.
     * @param propertyName the properrty name.
     * @param listener the listener.
     */
    public void addPropertyChangeListener(String propertyName,
                                          PropertyChangeListener listener) ;
    /**
     * Removes a listener for changes to any property.
     * @param listener the listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) ;

    /**
     * Removes a listener for changes to the specified property.
     * @param propertyName the property name.
     * @param listener the listener.
     */
    public void removePropertyChangeListener(String propertyName,
                                             PropertyChangeListener listener) ;

    /**
     * Determines whether this object has the specified property.
     * @param propertyName the property name.
     * @return true if the property exists, false otherwise.
     */
    public boolean hasProperty(String propertyName) ;

    /**
     * Gets the names of the properties for this object.
     * @return array of property names.
     */
    public String[] getPropertyNames() ;

    /**
     * Gets the value of the specified property.
     * @param PropertyName the property name.
     * @return the property value.
     */
    public Object getPropertyValue(String PropertyName) ;
}
