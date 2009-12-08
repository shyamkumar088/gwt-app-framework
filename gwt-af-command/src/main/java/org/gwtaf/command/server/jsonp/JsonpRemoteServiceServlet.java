package org.gwtaf.command.server.jsonp;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gwtaf.command.rpc.JsonpServiceProxy;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * An implementation of {@link RemoteServiceServlet} that handles a GWT-RPC sent
 * over a JSONP GET request
 * 
 * Adapted from:
 * http://timepedia.blogspot.com/2009/04/gwt-rpc-over-arbitrary-transports
 * -uber.html
 * 
 * @author Jason Kong
 * 
 */
public class JsonpRemoteServiceServlet extends RemoteServiceServlet {

	/**
	 * The actual callback function name that will be retrieved from the
	 * {@link HttpServletRequest}
	 */
	private String callbackFunction;

	@Override
	protected void doGet(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		doPost(httpServletRequest, httpServletResponse);
	}

	@Override
	protected String readContent(HttpServletRequest httpServletRequest)
			throws ServletException, IOException {

		// grab the GWT-RPC payload string from the request
		String str = httpServletRequest.getMethod().equals("POST") ? RPCServletUtils
				.readContentAsUtf8(httpServletRequest, false)
				: httpServletRequest
						.getParameter(JsonpServiceProxy.RPC_PAYLOAD_PARAM);

		// decode it
		String ustr = URLDecoder.decode(str, "UTF-8");

		// retrieve the callback parameters from the request as well. This will
		// be used when padding the JSON response
		callbackFunction = httpServletRequest
				.getParameter(JsonpServiceProxy.CALLBACK_PARAM);
		return ustr;
	}

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			RPCRequest rpcRequest = RPC.decodeRequest(payload, this.getClass(),
					this);
			onAfterRequestDeserialized(rpcRequest);
			String encodedResponse = RPC.invokeAndEncodeResponse(this,
					rpcRequest.getMethod(), rpcRequest.getParameters(),
					rpcRequest.getSerializationPolicy(), rpcRequest.getFlags());

			// the responseStr by default is a GWT-RPC string. We'll need to
			// turn it into a JSON object, and pad it with the
			// callback function.
			System.out.println(encodedResponse);
			String responseStr = callbackFunction + "({\"entry\":\""
					+ encodedResponse.replaceAll("\"", "\\\\\"") + "\"});";
			return responseStr;

		} catch (IncompatibleRemoteServiceException ex) {
			log(
					"An IncompatibleRemoteServiceException was thrown while processing this call.",
					ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}
}
