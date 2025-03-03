package com.example.Asisgnmentpostgre.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties({"instructor", "studentsEnrollments"})
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long fee;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudentCourseEnrollment> studentsEnrollments;

    @JsonIgnore
    @OneToOne(mappedBy = "course")
    private Instructor instructor;
}
