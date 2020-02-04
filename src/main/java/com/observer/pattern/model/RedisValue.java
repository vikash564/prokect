package com.observer.pattern.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisValue {
    private Long key;
    private RedisObject value;
}