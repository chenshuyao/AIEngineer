package com.aiengineer.student.service.impl;

import com.aiengineer.student.model.Student;
import com.aiengineer.student.repository.StudentRepository;
import com.aiengineer.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
    }

    @Override
    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAllActive(pageable);
    }

    @Override
    public Student updateStudent(Student student) {
        if (!studentRepository.existsById(student.getId())) {
            throw new EntityNotFoundException("Student not found with id: " + student.getId());
        }
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        student.setIsDelete(1);
        studentRepository.save(student);
    }

    @Override
    public Page<Student> searchByKeyword(String keyword, Pageable pageable) {
        return studentRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<Student> searchByName(String name, Pageable pageable) {
        return studentRepository.findByName(name, pageable);
    }

    @Override
    public Page<Student> searchByPhone(String phone, Pageable pageable) {
        return studentRepository.findByPhone(phone, pageable);
    }
}
