package com.example.questionbanknew.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Ders ismi (Matematik, Fen, Türkçe vs.)
}