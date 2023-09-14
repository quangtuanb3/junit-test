package com.example.demojunittesting.repository;

import com.example.demojunittesting.domain.Student;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "SELECT EXISTS (SELECT 1 FROM Student s WHERE s.email = :email)")
    Boolean selectExistsEmail(String email);
}
