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

import org.gwt.beansbinding.core.client.BeanProperty;
import org.gwt.beansbinding.core.client.Binding;
import org.gwt.beansbinding.core.client.BindingListener;
import org.gwt.beansbinding.core.client.Bindings;
import org.gwt.beansbinding.core.client.Converter;
import org.gwt.beansbinding.core.client.PropertyStateEvent;
import org.gwt.beansbinding.core.client.AutoBinding.UpdateStrategy;
import org.gwt.beansbinding.core.client.Binding.SyncFailure;
import org.gwt.beansbinding.core.client.ext.BeanAdapterFactory;
import org.gwt.beansbinding.core.client.ext.BeanAdapterProvider;

/**
 * A class for creating new {@link Binding}s.
 * 
 * @author Jason Kong
 */
public class BindingBuilder {

	/**
	 * Default constructor
	 */
	public BindingBuilder() {

	}

	/**
	 * Creates a binding of the specified inferred types
	 * 
	 * @param source
	 *            the source object
	 * @param sourcePropertyName
	 *            the name of the property in the source object
	 * @param target
	 *            the target object
	 * @param targetPropertyName
	 *            the name of hte property in the target object
	 * @param adapterProviders
	 *            the {@link BeanAdapterProvider}s needed by this binding
	 * @return
	 */
	public <SS, SV, TS, TV> Binding<SS, SV, TS, TV> createBinding(SS source,
			String sourcePropertyName, Class<SV> svClass, TS target,
			String targetPropertyName, Class<TV> tvClass,
			BeanAdapterProvider... adapterProviders) {
		return createBindingWithConverter(source, sourcePropertyName, svClass,
				target, targetPropertyName, tvClass, null, adapterProviders);
	}

	public <SS, SV, TS, TV> Binding<SS, SV, TS, TV> createBindingWithConverter(
			SS source, String sourcePropertyName, Class<SV> svClass, TS target,
			String targetPropertyName, Class<TV> tvClass,
			Converter<SV, TV> converter,
			BeanAdapterProvider... adapterProviders) {
		return createFullBinding(source, sourcePropertyName, svClass, target,
				targetPropertyName, tvClass, converter, null, adapterProviders);
	}

	/**
	 * Creates a binding provided a listener. The listener is attached before
	 * bind() is called, giving the listener a chance to react at initial
	 * binding.
	 * 
	 * @return a binding with a listener.
	 */
	public <SS, SV, TS, TV> Binding<SS, SV, TS, TV> createBindingWithListener(
			SS source, String sourcePropertyName, Class<SV> svClass, TS target,
			String targetPropertyName, Class<TV> tvClass,
			BindingListener listener, BeanAdapterProvider... adapterProviders) {
		return createFullBinding(source, sourcePropertyName, svClass, target,
				targetPropertyName, tvClass, null, listener, adapterProviders);
	}

	public <SS, SV, TS, TV> Binding<SS, SV, TS, TV> createFullBinding(
			SS source, String sourcePropertyName, Class<SV> svClass, TS target,
			String targetPropertyName, Class<TV> tvClass,
			Converter<SV, TV> converter, BindingListener listener,
			BeanAdapterProvider... adapterProviders) {
		// loop through the providers

		for (BeanAdapterProvider provider : adapterProviders) {
			BeanAdapterFactory.addProvider(provider);
		}

		// set up the properties
		BeanProperty<SS, SV> sourceProp = BeanProperty
				.create(sourcePropertyName);
		BeanProperty<TS, TV> targetProp = BeanProperty
				.create(targetPropertyName);

		// Type safety check
		if (sourceProp.getValue(source) != null) {
			if (!sourceProp.getValue(source).getClass().equals(svClass)) {
				throw new IllegalArgumentException(
						"Source Value Class type does not match svClass type"
								+ svClass + " doesn't match "
								+ sourceProp.getValue(source).getClass() + ")");
			}
		}

		if (targetProp.getValue(target) != null) {
			if (!targetProp.getValue(target).getClass().equals(tvClass)) {
				throw new IllegalArgumentException(
						"Target Value Class type does not match tvClass type ("
								+ tvClass + " doesn't match "
								+ targetProp.getValue(target).getClass() + ")");
			}
		}

		// Build a binding
		Binding<SS, SV, TS, TV> binding = Bindings.createAutoBinding(
				UpdateStrategy.READ_WRITE, source, sourceProp, target,
				targetProp);

		// assign the converter if not null
		if (converter != null) {
			binding.setConverter(converter);
		}

		// add the listener if not null
		if (listener != null) {
			binding.addBindingListener(listener);
		}

		binding.bind();

		return binding;
	}

}
