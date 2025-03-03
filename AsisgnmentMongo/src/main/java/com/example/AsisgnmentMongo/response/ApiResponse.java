package com.example.AsisgnmentMongo.response;

import com.example.AsisgnmentMongo.Entities.Course;
import com.example.AsisgnmentMongo.Entities.Student;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
@Data
public class ApiResponse<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
