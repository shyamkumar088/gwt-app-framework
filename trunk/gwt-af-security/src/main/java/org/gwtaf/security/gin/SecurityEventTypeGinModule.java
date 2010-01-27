/*
 * Copyright 2010. Mount Sinai Hospital, Toronto, Canada.
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
package org.gwtaf.security.gin;

import org.gwtaf.security.event.AuthorizationErrorEventHandler;
import org.gwtaf.security.event.LoginFailedEventHandler;
import org.gwtaf.security.event.LoginSuccessfulEventHandler;
import org.gwtaf.security.event.RequestCurrentUserEventHandler;
import org.gwtaf.security.event.LoginFailedEvent;
import org.gwtaf.security.event.LoginSuccessfulEvent;
import org.gwtaf.security.event.ReturnedCurrentUserEvent;
import org.gwtaf.security.event.ReturnedCurrentUserEventHandler;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

/**
 * An {@link AbstractGinModule} that binds all the security events as
 * {@link Singleton}s.
 * 
 * @author Arthur Kalmenson
 */
public class SecurityEventTypeGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<Type<AuthorizationErrorEventHandler>>() {
		}).in(Singleton.class);
		bind(new TypeLiteral<Type<RequestCurrentUserEventHandler>>() {
		}).in(Singleton.class);
		bind(new TypeLiteral<Type<LoginFailedEventHandler>>() {
		}).in(Singleton.class);
		bind(new TypeLiteral<Type<LoginSuccessfulEventHandler>>() {
		}).in(Singleton.class);
		bind(new TypeLiteral<Type<ReturnedCurrentUserEventHandler>>() {
		}).in(Singleton.class);
	}
}
