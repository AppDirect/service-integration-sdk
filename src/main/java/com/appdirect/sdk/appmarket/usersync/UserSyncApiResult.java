package com.appdirect.sdk.appmarket.usersync;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Payload returned to the ISV in response to a UserSync request
 */
@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserSyncApiResult {
	private HttpStatus status;
	private String code;
	private String message;
}
