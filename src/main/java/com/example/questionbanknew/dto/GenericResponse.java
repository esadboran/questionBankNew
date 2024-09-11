package com.example.questionbanknew.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private String message;
    private T data;
}