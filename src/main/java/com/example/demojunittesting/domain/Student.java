package com.example.demojunittesting.domain;

import com.example.demojunittesting.domain.enumaration.EGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "students")
public class Student {
    @Id
//    @SequenceGenerator(
//            name = "student_sequence",
//            sequenceName = "student_sequence",
//            allocationSize = 1
//    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EGender gender;

    public Student(String name, String email, @NotNull EGender gender) {
        this.name = name;
        this.email = email;
        this.gender = gender;
    }
}
