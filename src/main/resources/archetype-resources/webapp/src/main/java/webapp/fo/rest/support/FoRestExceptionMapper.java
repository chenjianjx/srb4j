package ${groupId}.${rootArtifactId}.webapp.fo.rest.support;

import java.util.Date;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoConstants;
import ${groupId}.${rootArtifactId}.intf.fo.basic.FoResponse;

/**
 * catch all un-catched exceptions and turns it into error response
 * 
 * @author chenjianjx@gmail.com
 *
 */
@Component
@Provider
public class FoRestExceptionMapper implements ExceptionMapper<Throwable> {

	private static final Logger logger = LoggerFactory
			.getLogger(FoRestExceptionMapper.class);

	@Context
	private javax.inject.Provider<ContainerRequestContext> containerRequestContextProvider;

	public Response toResponse(Throwable ex) {

		if (ex instanceof javax.ws.rs.NotFoundException) {// do not log for 404
			return Response.status(Status.NOT_FOUND).build();
		}

		String exceptionId = Thread.currentThread().getName() + "-"
				+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmsSSS") + "-"
				+ RandomStringUtils.randomAlphanumeric(5);
		// first log it
		logger.error(FoConstants.FEC_UNKNOWN_SERVER_ERROR + ". Exception Id = "
				+ exceptionId, ex);
		// then turn into to restful json
		FoResponse<Void> foResponse = FoResponse.devErrResponse(
				FoConstants.FEC_UNKNOWN_SERVER_ERROR, ex.getClass().getName()
						+ " -" + StringUtils.defaultString(ex.getMessage()),
				exceptionId);
		return FoRestUtils.fromFoResponse(foResponse,
				containerRequestContextProvider.get());
	}

}
