package com.denis.thymeleaf.thymeleaf.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본 키 생성을 데이터베이스에 위임
    //즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다.
    //Ex) MySQL, PostgreSQL, SQL Server DB2 등

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
