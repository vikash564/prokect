package com.observer.pattern.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.observer.pattern.constant.Constant.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KafkaPojo {

    private OrderState state;

    @JsonAlias({"created_at" , "createdAt"})
    private long createdAt;

    private String metadata;

    @JsonAlias({"cancelReason", "cancel_reason"})
    private String cancelReason;


}
