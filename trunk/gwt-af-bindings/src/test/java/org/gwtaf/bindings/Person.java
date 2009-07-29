/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
 * 
 * Licensed under the Apache License, Version 2.0. You
 * can find a copy of the license at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * IN NO EVENT SHALL MOUNT SINAI HOSPITAL BE LIABLE TO ANY PARTY FOR DIRECT, 
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST 
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, 
 * EVEN IF MOUNT SINAI HOSPITAL HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * 
 * MOUNT SINAI HOSPITAL SPECIFICALLY DISCLAIMS ANY IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND 
 * ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". 
 * MOUNT SINAI HOSPITAL HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, 
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS. 
 */
package org.gwtaf.bindings;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

import org.gwt.beansbinding.core.client.util.HasPropertyChangeSupport;

/**
 * A class for testing the GWT-AF project
 * 
 * @author Jason Kong
 * 
 */
public class Person implements HasPropertyChangeSupport {

	/**
	 * The date of birth
	 */
	private Date birthday;

	/**
	 * The person's full name
	 */
	private String fullname;

	/**
	 * Whether or not they are alive
	 */
	private Boolean isAlive;

	/**
	 * The {@link PropertyChangeSupport} that this class must support
	 */
	protected PropertyChangeSupport props = new PropertyChangeSupport(this);

	/**
	 * Default empty constructor
	 */
	public Person() {

	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		Date oldDate = this.birthday;
		this.birthday = birthday;
		props.firePropertyChange("birthday", oldDate, birthday);
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		String oldName = this.fullname;
		this.fullname = fullname;
		props.firePropertyChange("fullname", oldName, fullname);
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		props.addPropertyChangeListener(l);
	}

	public void addPropertyChangeListener(String propName,
			PropertyChangeListener l) {
		props.addPropertyChangeListener(propName, l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		props.removePropertyChangeListener(l);
	}

	public void removePropertyChangeListener(String propName,
			PropertyChangeListener l) {
		props.removePropertyChangeListener(propName, l);
	}

}