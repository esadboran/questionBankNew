package com.example.questionbanknew.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category { //TODO: Category ile Subject arasında bağ kurulabilir ilkokul matematik gibi

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Kategori ismi (ilkokul, ortaokul, lise, üniversite)
}