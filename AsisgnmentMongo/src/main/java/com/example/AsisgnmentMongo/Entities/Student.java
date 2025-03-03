package com.example.AsisgnmentMongo.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "students")
public class Student implements Serializable {

    @Id
    private String studentId;
    private String studentName;
    private Date dob;
    private List<String> enrolledCourseIds;
    private List<CourseStatus> courseStatuses;

    public void updateCourseStatus(String courseId, CourseStatus status) {
        for (int i = 0; i < enrolledCourseIds.size(); i++) {
            if (enrolledCourseIds.get(i).equals(courseId)) {
                courseStatuses.set(i, status);
                return;
            }
        }
        enrolledCourseIds.add(courseId);
        courseStatuses.add(status);
    }
}
