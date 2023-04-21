package com.geulkkoli.domain.post;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ConfigDate {

    private String createdAt;
    private String updateddAt;
}
