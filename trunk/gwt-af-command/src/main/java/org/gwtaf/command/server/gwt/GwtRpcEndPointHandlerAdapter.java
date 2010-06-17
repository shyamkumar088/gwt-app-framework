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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Spring HandlerAdapter to dispatch GWT-RPC requests. Relies on handlers
 * registered by {@link GwtAnnotationHandlerMapping}.
 * <p />
 * This is almost exact copy from the <a
 * href="http://blog.digitalascent.com/2007/11/gwt-rpc-with-spring-2x_12.html"
 * >blog post</a> explaining how to integrate GWT with Spring MVC. This just
 * includes an implement of {@link ServletContextAware} which was required to
 * integrate with Spring.
 * 
 * @author Arthur Kalmenson
 * @author Chris Lee
 */
@SuppressWarnings("serial")
public class GwtRpcEndPointHandlerAdapter extends RemoteServiceServlet
		implements HandlerAdapter, ServletContextAware {

	@SuppressWarnings("unchecked")
	protected static ThreadLocal handlerHolder = new ThreadLocal();

	private ServletContext servletContext;

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public long getLastModified(HttpServletRequest request, Object handler) {
		return -1;
	}

	@SuppressWarnings("unchecked")
	public ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		try {
			// store the handler for retrieval in processCall()
			handlerHolder.set(handler);
			doPost(request, response);
		} finally {
			// clear out thread local to avoid resource leak
			handlerHolder.set(null);
		}

		return null;
	}

	protected Object getCurrentHandler() {
		return handlerHolder.get();
	}

	public boolean supports(Object handler) {
		return handler instanceof RemoteService
				&& handler.getClass().isAnnotationPresent(GwtRpcEndPoint.class);
	}

	@Override
	public String processCall(String payload) throws SerializationException {
		/*
		 * The code below is borrowed from RemoteServiceServet.processCall, with
		 * the following changes:
		 * 
		 * 1) Changed object for decoding and invocation to be the handler
		 * (versus the original 'this')
		 */

		try {
			RPCRequest rpcRequest =
					RPC.decodeRequest(payload, getCurrentHandler().getClass(),
							this);
			onAfterRequestDeserialized(rpcRequest);
			return RPC.invokeAndEncodeResponse(getCurrentHandler(), rpcRequest
					.getMethod(), rpcRequest.getParameters(), rpcRequest
					.getSerializationPolicy());

		} catch (Throwable t) {
			return RPC.encodeResponseForFailure(null, t);
		}
	}
}
