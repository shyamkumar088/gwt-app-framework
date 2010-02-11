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

import org.gwtaf.security.cache.UserCache;
import org.gwtaf.security.cache.UserCacheImpl;
import org.gwtaf.security.gin.annotation.SecurityLogin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;

/**
 * A {@link AbstractGinModule} that does some default binding for
 * gwt-af-security.
 * 
 * @author Arthur Kalmenson
 */
public class SecurityGinModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(UserCache.class).to(UserCacheImpl.class).asEagerSingleton();
	}
	
	@Provides @SecurityLogin
	public Integer getDefaultCacheLenght() {
		return 1800;
	}
}
