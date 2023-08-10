package com.example.hr_system.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;
}
