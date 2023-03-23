package com.onurhizar.gamepass.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCategoryRequest {
    private String name;
    private String parentName;
}
