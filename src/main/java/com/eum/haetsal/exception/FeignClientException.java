package com.eum.haetsal.exception;

import com.eum.haetsal.common.DTO.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class FeignClientException extends RuntimeException {
    private final int status;

    private final String errorMessage;

    private final Map<String, Collection<String>> headers;

    private final ErrorResponse errorForm;
}