package com.example.demojunittesting.repository;

import com.example.demojunittesting.domain.Student;
import com.example.demojunittesting.domain.enumaration.EGender;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AllArgsConstructor
public class StudentRepositoryTest {

    private final StudentRepository studentRepository;
    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void canSelectExistsEmail() {
        //given
        Student student = Student.builder()
                .name("Tuan")
                .email("tuan@gmail.com")
                .gender(EGender.MALE)
                .build();
        studentRepository.save(student);

        //when
        Boolean expected = studentRepository.selectExistsEmail(student.getEmail());

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void cannotSelectEmailDoesNotExist() {
        //given
//        Student student = Student.builder()
//                .name("Tuan")
//                .email("tuan@gmail.com")
//                .gender(EGender.MALE)
//                .build();
        //when
        Boolean expected = studentRepository.selectExistsEmail("tuan@gmail.com");

        //then
        assertThat(expected).isFalse();
    }
}