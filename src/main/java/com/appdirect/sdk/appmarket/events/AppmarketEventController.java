package com.appdirect.sdk.appmarket.events;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appdirect.sdk.web.oauth.OAuthKeyExtractor;

@Slf4j
@RestController
class AppmarketEventController {

	private final AppmarketEventService appmarketEventService;
	private final OAuthKeyExtractor keyExtractor;

	AppmarketEventController(AppmarketEventService appmarketEventService, OAuthKeyExtractor keyExtractor) {
		this.appmarketEventService = appmarketEventService;
		this.keyExtractor = keyExtractor;
	}

	@RequestMapping(method = GET, value = "/api/v1/integration/processEvent", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<APIResult> processEvent(HttpServletRequest request, @RequestParam("eventUrl") String eventUrl) {
		String keyUsedToSignRequest = keyExtractor.extractFrom(request);
		log.info("eventUrl={} signed with consumerKey={}", eventUrl, keyUsedToSignRequest);

		APIResult result = appmarketEventService.processEvent(eventUrl, eventExecutionContext(request, keyUsedToSignRequest));

		log.info("apiResult={}", result);
		return new ResponseEntity<>(result, httpStatusOf(result));
	}

	private EventHandlingContext eventExecutionContext(HttpServletRequest request, String keyUsedToSignRequest) {
		HashMap<String, String[]> queryParamsNotTiedToRequestReference = new HashMap<>(request.getParameterMap());
		return new EventHandlingContext(keyUsedToSignRequest, queryParamsNotTiedToRequestReference);
	}

	private HttpStatus httpStatusOf(APIResult result) {
		return HttpStatus.valueOf(result.getStatusCodeReturnedToAppmarket());
	}
}
