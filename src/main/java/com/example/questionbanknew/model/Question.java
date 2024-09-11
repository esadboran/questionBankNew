package com.example.questionbanknew.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {    // TODO : Questionslara Favori,Beğenme,OluşturulmaTarihi gibi şeyler koyabiliriz.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;   // Soru başlığı
    private String content; // Soru açıklaması

    @ManyToOne
    @JoinColumn(name = "user_id")  // Soruyu yükleyen öğrenci
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")  // Soru kategorisi (ilkokul, lise vs.)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subject_id")  // Soruya ait ders
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "exam_type_id")  // Soruya ait sınav türü
    private ExamType examType;

    // Bir soruya ait birden fazla cevap olabilir
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers;
}