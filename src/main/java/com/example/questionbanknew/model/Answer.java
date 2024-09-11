package com.example.questionbanknew.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;  // Cevap içeriği

    // Cevap verilen soru
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    // Cevap veren öğretmen
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}