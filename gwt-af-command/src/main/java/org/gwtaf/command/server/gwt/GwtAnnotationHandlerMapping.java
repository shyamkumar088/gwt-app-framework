/*
 * Copyright 2008. Mount Sinai Hospital, Toronto, Canada.
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

package org.gwtaf.command.server.gwt;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.AbstractDetectingUrlHandlerMapping;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Spring HandlerMapping that detects beans annotated with @GwtRpcEndPoint and
 * registers their URLs.
 * <p />
 * This is almost exact copy from the <a
 * href="http://blog.digitalascent.com/2007/11/gwt-rpc-with-spring-2x_12.html"
 * >blog post</a> explaining how to integrate GWT with Spring MVC. The only
 * difference is we don't replace '.' with '/' so URLs use the regular package
 * name.
 * 
 * @author Arthur Kalmenson
 * @author Chris Lee
 */
public class GwtAnnotationHandlerMapping extends
		AbstractDetectingUrlHandlerMapping {

	private String prefix = "";

	private String suffix = "";

	protected String[] buildUrls(Class<?> handlerType, String beanName) {

		String remoteServiceName = null;
		for (Class<?> itf : handlerType.getInterfaces()) {
			// find the interface that extends RemoteService
			if (RemoteService.class.isAssignableFrom(itf)) {
				remoteServiceName = itf.getName();
			}
		}

		if (remoteServiceName == null) {
			throw new IllegalArgumentException(
					"Unable to generate name for "
							+ handlerType.getName()
							+ "; cannot locate interface that is a subclass of RemoteService");
		}

		String classPath = remoteServiceName;

		StringBuilder sb = new StringBuilder();

		sb.append(prefix);

		sb.append(classPath);

		sb.append(suffix);
		return new String[] { sb.toString() };
	}

	@Override
	protected final String[] determineUrlsForHandler(String beanName) {
		String[] urls = new String[0];
		Class<?> handlerType = getApplicationContext().getType(beanName);
		if (handlerType.isAnnotationPresent(GwtRpcEndPoint.class)) {
			GwtRpcEndPoint endPointAnnotation = handlerType
					.getAnnotation(GwtRpcEndPoint.class);
			if (StringUtils.hasText(endPointAnnotation.value())) {
				urls = new String[] { endPointAnnotation.value() };
			} else {
				urls = buildUrls(handlerType, beanName);
			}
		}

		return urls;
	}

	public final void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public final void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
