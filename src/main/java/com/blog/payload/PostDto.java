package com.blog.payload;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min = 2,message = "Title Should be more than 2 character")
    private String title;

    @NotEmpty
    @Size(min = 5,message = "description Should be min 5 characters")
    private String description;
    private String content;
}
