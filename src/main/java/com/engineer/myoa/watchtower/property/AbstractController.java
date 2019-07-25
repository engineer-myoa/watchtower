package com.engineer.myoa.watchtower.property;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AbstractController {

	public <E> ResponseEntity<CommonResponse<E>> makeResponse(HttpServletRequest request, CommonResponse<E> response) {
		return makeResponse(request, response, HttpStatus.OK);
	}

	public <E> ResponseEntity<CommonResponse<E>> makeResponse(HttpServletRequest request, CommonResponse<E> response
		, HttpStatus httpStatus) {
		return new ResponseEntity<>(response, getAllHeaders(request), httpStatus);
	}

	public HttpHeaders getAllHeaders(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		Collections.list(request.getHeaderNames()).stream().peek(e -> headers.set(e, request.getHeader(e)));

		return headers;
	}
}
