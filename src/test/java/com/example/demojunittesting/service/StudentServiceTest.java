package com.example.demojunittesting.service;

import com.example.demojunittesting.domain.Student;
import com.example.demojunittesting.domain.enumaration.EGender;
import com.example.demojunittesting.exception.BadRequestException;
import com.example.demojunittesting.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }
    @Test
    void canAddStudent() {
        //given
        Student student = Student.builder()
                .name("Tuan")
                .email("tuan@gmail.com")
                .gender(EGender.MALE)
                .build();

        //when
        underTest.addStudent(student);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student expected = studentArgumentCaptor.getValue();
        assertThat(expected).isEqualTo(student);

    }
    @Test
    void cannotAddStudentWithBlankName() {
        // Given
        Student studentWithBlankName = Student.builder()
                .name("") // Blank name
                .email("tuan@gmail.com")
                .gender(EGender.MALE)
                .build();

        // When
        // Attempt to add a student with a blank name
        Throwable exception = catchThrowable(() -> underTest.addStudent(studentWithBlankName));

        // Then
        // Verify that an exception was thrown
        assertThat(exception).isInstanceOf(BadRequestException.class);
        assertThat(exception).hasMessageContaining("Name cannot be blank");

        // Verify that studentRepository.save() was never called
        verify(studentRepository, never()).save(any());
    }
    @Test
    void willThrowWhenEmailExisted() {
        // Given
        String email = "tuan@gmail.com";
        Student student = Student.builder()
                .name("Tuan")
                .email(email)
                .gender(EGender.MALE)
                .build();

        // Mock the behavior of studentRepository.selectExistsEmail(anyString())
        given(studentRepository.selectExistsEmail(email)).willReturn(true);

        // When and Then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + email + " taken");

        // Verify that studentRepository.save(any()) was never called
        verify(studentRepository, never()).save(any());
    }

    @Test
    @Disabled
    void deleteStudent() {
    }
}