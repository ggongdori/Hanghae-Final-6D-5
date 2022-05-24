package com.hanghae.justpotluck.global.audit;

public interface Auditable {
    TimeEntity getTimeEntity();
    void setTimeEntity(TimeEntity timeEntity);
}