/*
 * Copyright 2009. Mount Sinai Hospital, Toronto, Canada.
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
package com.google.gwt.user.rebind.rpc;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class JsonpServiceInterfaceProxyGenerator extends Generator {

	@Override
	public String generate(TreeLogger logger, GeneratorContext ctx,
			String requestedClass) throws UnableToCompleteException {

		logger.log(TreeLogger.WARN, "Running JsonpProxyCreator", null);

		TypeOracle typeOracle = ctx.getTypeOracle();
		assert (typeOracle != null);

		JClassType remoteService = typeOracle.findType(requestedClass);
		if (remoteService == null) {
			logger.log(TreeLogger.ERROR, "Unable to find metadata for type '"
					+ requestedClass + "'", null);
			throw new UnableToCompleteException();
		}

		if (remoteService.isInterface() == null) {
			logger.log(TreeLogger.ERROR, remoteService.getQualifiedSourceName()
					+ " is not an interface", null);
			throw new UnableToCompleteException();
		}

		JsonpProxyCreator proxyCreator = new JsonpProxyCreator(remoteService);

		TreeLogger proxyLogger = logger.branch(TreeLogger.DEBUG,
				"Generating client proxy for remote service interface '"
						+ remoteService.getQualifiedSourceName() + "'", null);

		return proxyCreator.create(proxyLogger, ctx);
	}
}
