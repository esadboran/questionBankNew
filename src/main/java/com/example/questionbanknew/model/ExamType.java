package com.example.questionbanknew.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExamType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Sınav türü (YKS, LGS, ALES)
}