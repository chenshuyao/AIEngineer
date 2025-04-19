package com.aiengineer.student.service;

import com.aiengineer.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
    Student saveStudent(Student student);
    Student getStudentById(Long id);
    Page<Student> getAllStudents(Pageable pageable);
    Student updateStudent(Student student);
    void deleteStudent(Long id);
    Page<Student> searchByKeyword(String keyword, Pageable pageable);
    Page<Student> searchByName(String name, Pageable pageable);
    Page<Student> searchByPhone(String phone, Pageable pageable);
}
