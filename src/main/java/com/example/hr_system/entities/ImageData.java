package com.example.hr_system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.*;

@Entity
@Table(name = "ImageData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
//    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    @OneToOne(mappedBy = "image")
    private JobSeeker jobSeeker;
    @OneToOne(mappedBy = "image")
    private Employer employer;
    @OneToOne(mappedBy = "image")
    private Vacancy vacancy;
}
