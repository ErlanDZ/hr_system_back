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
    @JoinColumn(name = "job_seeker")
    private JobSeeker userId;

    @Column(name = "content")
    private String content;
}
