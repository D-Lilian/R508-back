/* (C)2025 */
package com.course.backend.repository;

import com.course.backend.model.Grade;
import com.course.backend.model.StudentGrade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The interface Grade repository.
 * This interface is used to interact with the database.
 */
@RepositoryRestResource(exported = false)
public interface GradeRepository extends JpaRepository<Grade, Long> {

    /**
     * Find by student id list.
     * Find all grades of a student by student id.
     *
     * @param id the id
     * @return the list
     */
    List<Grade> findByStudentId(Long id);

    /**
     * Find by student id and course id list.
     * Find a grade of a student by student id and course id.
     *
     * @param studentId the student id
     * @param courseId  the course id
     * @return the list
     */
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    /**
     * Exists by course id boolean.
     * Check if a course exists by course id.
     *
     * @param courseId the course id
     * @return the boolean
     */
    boolean existsByCourseId(Long courseId);

    /**
     * Find by student id and academic year grade.
     * Find all grades of a student by student id and academic year.
     *
     * @param studentId    the student id
     * @param academicYear the academic year
     * @return the list
     */
    @Query(
            "SELECT new com.course.backend.model.StudentGrade(c.code, c.name, c.credits, g.grade,"
                + " g.semester) FROM Grade g JOIN g.course c WHERE g.student.id = :studentId AND"
                + " g.academicYear = :academicYear")
    List<StudentGrade> findByStudentIdAndAcademicYear(Long studentId, String academicYear);
}
