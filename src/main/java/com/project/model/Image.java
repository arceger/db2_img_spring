package com.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;


import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "image_table")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private Blob image;

    private Date date = new Date();


}
