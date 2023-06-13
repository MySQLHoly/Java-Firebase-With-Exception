package com.sandro.postmanagement.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
public class ErrorDTO {
    private final String code;
    private final String debugMessage;
    private final List<String> details;
}
