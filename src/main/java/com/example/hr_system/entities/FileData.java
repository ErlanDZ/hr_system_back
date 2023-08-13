package com.example.hr_system.entities;

import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;

@Entity
@Table(name = "file_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    //    @Lob
    @Column(name = "image_data")
    private byte[] fileData;

    @OneToOne(mappedBy = "resume")
    private JobSeeker jobSeeker;

    @OneToOne(mappedBy = "resume")
    private Employer employer;
    @OneToOne(mappedBy = "resume")
    private Vacancy vacancy;

    @Column(name = "path")
    private String path;

}
