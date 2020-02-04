package com.observer.pattern.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.observer.pattern.constant.Constant.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Locker {

    private OrderState state;

    @JsonAlias({"created_at" , "createdAt"})
    private Long createdAt;

    private Long doorId;

    private Long lockerId;

    private String status;


}
