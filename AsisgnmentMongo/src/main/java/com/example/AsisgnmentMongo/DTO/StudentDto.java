package com.example.AsisgnmentMongo.DTO;



import com.example.AsisgnmentMongo.Entities.CourseStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class StudentDto {

    private String studentId;
    private String studentName;
    private Date dob;
    private List<String> enrolledCourseIds;
    private List<CourseStatus> courseStatuses;

}
