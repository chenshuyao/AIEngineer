package com.aiengineer.student.repository;

import com.aiengineer.student.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.isDelete = 0 AND " +
           "(LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Student> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.isDelete = 0 AND LOWER(s.name) = LOWER(:name)")
    Page<Student> findByName(@Param("name") String name, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.isDelete = 0 AND s.phone = :phone")
    Page<Student> findByPhone(@Param("phone") String phone, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.isDelete = 0")
    Page<Student> findAllActive(Pageable pageable);
}
