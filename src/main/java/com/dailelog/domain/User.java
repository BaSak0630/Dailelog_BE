package com.dailelog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
/*@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")*/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String loginId; // 아이디

    private String password;

    private LocalDateTime createdAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Session> sessions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    public Session addSession(){
        Session session = Session.builder()
                .user(this)
                .build();

        sessions.add(session);

        return session;
    }

    @Builder
    public User(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }
}
