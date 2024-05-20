package com.ssafy.enjoytrip.global;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    boolean success;
    int status;
    T data;
}
