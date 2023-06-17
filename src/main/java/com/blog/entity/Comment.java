package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data                 //gives us setter and getter
@AllArgsConstructor   //gives us a Constructor with argument
@NoArgsConstructor    //gives us a No argument Constructor
@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)   //FetchType.LAZY=It will load only those tables which will required when that module of your running
    @JoinColumn(name="post_id")          //FetchType.EAGER=It will load all database tables into the temporary memory even without their requirement
    private Post post;          //@JoinColumn =annotation is used to specify the foreign key column name (post_id) in the
                               // Comment table, which establishes the relationship between Post and Comment

}
