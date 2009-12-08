package org.gwtaf.command.rpc;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter;
import com.google.gwt.user.client.rpc.impl.Serializer;

/**
 * An extension of {@link RemoteServiceProxy} that uses JSONP as a transport
 * mechanism. Makes use of {@link JsonpRequestBuilder} to make JSONP requests
 * 
 * @author Jason Kong
 * 
 */
public class JsonpServiceProxy extends RemoteServiceProxy {

	/**
	 * The request parameter for the gwt-rpc payload
	 */
	public static final String RPC_PAYLOAD_PARAM = "rpcpayload";

	/**
	 * The callback parameter for the JSONP callback
	 */
	public static final String CALLBACK_PARAM = "callback";

	/**
	 * Protected constructor of {@code JsonpServiceProxy}
	 * 
	 * @param moduleBaseURL
	 *            the base URL of the module
	 * @param remoteServiceRelativePath
	 *            the relative path to this remote service
	 * @param serializationPolicyName
	 *            the serialization policy name
	 * @param serializer
	 *            the serializer
	 */
	protected JsonpServiceProxy(String moduleBaseURL,
			String remoteServiceRelativePath, String serializationPolicyName,
			Serializer serializer) {
		super(moduleBaseURL, remoteServiceRelativePath,
				serializationPolicyName, serializer);
	}

	static boolean isReturnValue(String encodedResponse) {
		return encodedResponse.startsWith("//OK");
	}

	/**
	 * Return <code>true</code> if the encoded response contains a checked
	 * exception that was thrown by the method invocation.
	 * 
	 * @param encodedResponse
	 * @return <code>true</code> if the encoded response contains a checked
	 *         exception that was thrown by the method invocation
	 */
	static boolean isThrownException(String encodedResponse) {
		return encodedResponse.startsWith("//EX");
	}

	@Override
	protected <T> Request doInvoke(
			final RequestCallbackAdapter.ResponseReader responseReader,
			String methodName, int invocationCount, String requestData,
			final AsyncCallback<T> tAsyncCallback) {

		try {

			// create a JsonpRequestBuilder to make hte call with
			JsonpRequestBuilder jrb = new JsonpRequestBuilder();
			jrb.setCallbackParam(CALLBACK_PARAM);

			// request an Object using the JsonpRequestBuilder using the GWT RPC
			// payload
			jrb.requestObject(getServiceEntryPoint() + "?" + RPC_PAYLOAD_PARAM
					+ "=" + URL.encode(requestData),
					new AsyncCallback<JsonpResponseJso>() {

						public void onFailure(Throwable throwable) {
							tAsyncCallback
									.onFailure(new InvocationException(
											"Unable to initiate the asynchronous service invocation -- check the network connection"));
							throwable.printStackTrace();
						}

						/**
						 * On success, the JSONP callback will send back a
						 * StringJso object, which contains one field: a GWT-RPC
						 * encoded string. We'll decode the GWT-RPC response and
						 * return control back to the onSuccess/onFailure of the
						 * {@link AsyncCallback} of the initial GWT-RPC call
						 * 
						 * @param encodedResponse
						 */
						@SuppressWarnings("unchecked")
						public void onSuccess(JsonpResponseJso encodedResponse) {
							try {

								// the encoded response is returned by calling
								// 'getEntry()' in the StringJso. We'll clean up
								// the escaping of quotes though
								String rpcString = encodedResponse.getEntry()
										.replaceAll("\\\\\"", "\"");

								if (isReturnValue(rpcString)) {
									tAsyncCallback
											.onSuccess((T) responseReader
													.read(createStreamReader(rpcString)));
								} else if (isThrownException(encodedResponse
										.getEntry())) {
									
									//using createStreamReader(..).readObject();
									tAsyncCallback
											.onFailure((Throwable) createStreamReader(
													rpcString).readObject());

								} else {
									tAsyncCallback
											.onFailure(new InvocationException(
													"Unknown return value type"));
								}
							} catch (SerializationException e) {
								tAsyncCallback
										.onFailure(new InvocationException(
												"Failure deserializing object "
														+ e));
							}
						}
					});
		} catch (Exception ex) {
			InvocationException iex = new InvocationException(
					"Unable to initiate the asynchronous service invocation -- check the network connection",
					ex);
			tAsyncCallback.onFailure(iex);
		} finally {
			if (RemoteServiceProxy.isStatsAvailable()
					&& RemoteServiceProxy.stats(RemoteServiceProxy.bytesStat(
							methodName, invocationCount, requestData.length(),
							"requestSent"))) {
			}
		}
		return null;
	}

	public static class JsonpResponseJso extends JavaScriptObject {
		protected JsonpResponseJso() {
		}

		public final native String getEntry() /*-{
												return this.entry;
												}-*/;
	}

}
