package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Data                 //gives us setter and getter
@AllArgsConstructor   //gives us a Constructor with argument
@NoArgsConstructor    //gives us a No argument Constructor
@Entity
@Table(
        name="posts",uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="content",nullable=false)
    private String content;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "post")  //CascadeType.ALL= means whenever i make any changes in post that should reflect in comments
    private List<Comment> comments;                          //make changes in parent table that should be changes in child table also



}
