/* (C)2025 */
package com.course.backend.repository;

import com.course.backend.dto.CourseStatistics;
import com.course.backend.dto.CourseStatisticsImpl;
import com.course.backend.dto.GlobalStatistics;
import com.course.backend.dto.GlobalStatisticsImpl;
import com.course.backend.dto.StudentSemesterStatistics;
import com.course.backend.dto.StudentSemesterStatisticsImpl;
import com.course.backend.model.Grade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface StatRepository extends JpaRepository<Grade, Long> {

    /**
     * Get global statistics.
     * This method is used to find statistics.
     *
     * @param academicYear the academic year
     * @return the course
     */
    @Query(
            "SELECT new com.course.backend.dto.GlobalStatisticsImpl(AVG(g.grade), COUNT(DISTINCT"
                + " g.student), COUNT(DISTINCT g.course), AVG(CASE WHEN g.grade >= 10 THEN 100.0 ELSE"
                + " 0.0 END)) FROM Grade g WHERE g.academicYear = :academicYear")
    GlobalStatisticsImpl getGlobalStatistics(@Param("academicYear") String academicYear);

    /**
     * Get course statistics.
     * This method is used to find statistics for a specific course.
     *
     * @param courseId     the course id
     * @param academicYear the academic year
     * @return the course
     */
    @Query(
            value =
                    "SELECT new com.course.backend.dto.CourseStatisticsImpl(c.code, c.name,"
                        + " CAST(COALESCE(AVG(g.grade), 0) AS double), CAST(COALESCE(MIN(g.grade),"
                        + " 0) AS double), CAST(COALESCE(MAX(g.grade), 0) AS double), COUNT(g.id),"
                        + " CAST(COALESCE(CAST(COUNT(CASE WHEN g.grade >= 10 THEN 1 END) AS double)"
                        + " / CAST(NULLIF(COUNT(*), 0) AS double) * 100, 0) AS double)) FROM Course"
                        + " c LEFT JOIN Grade g ON g.course.id = c.id AND g.academicYear ="
                        + " :academicYear WHERE c.id = :courseId GROUP BY c.id, c.code, c.name")
    CourseStatisticsImpl getCourseStatistics(
            @Param("courseId") Long courseId, @Param("academicYear") String academicYear);

    /**
     * Get student semester statistics.
     * This method is used to find statistics for a specific student.
     *
     * @param studentId    the student id
     * @param academicYear the academic year
     * @return the course
     */
    @Query(
            "SELECT new com.course.backend.dto.StudentSemesterStatisticsImpl(g.semester,"
                + " AVG(g.grade), SUM(g.course.credits), SUM(CASE WHEN g.grade >= 10 THEN"
                + " g.course.credits ELSE 0 END), COUNT(g.course)) FROM Grade g WHERE g.student.id"
                + " = :studentId AND g.academicYear = :academicYear GROUP BY g.semester")
    List<StudentSemesterStatisticsImpl> getStudentSemesterStatistics(
            @Param("studentId") Long studentId, @Param("academicYear") String academicYear);

    /**
     * Exists by academic year boolean.
     * This method is used to check if a grade exists for an academic year.
     *
     * @param academicYear the academic year
     * @return the boolean
     */
    boolean existsByAcademicYear(String academicYear);

    /**
     * Exists by course id and academic year boolean.
     * This method is used to check if a grade exists for a course and an academic year.
     *
     * @param courseId     the course id
     * @param academicYear the academic year
     * @return the boolean
     */
    boolean existsByCourseIdAndAcademicYear(Long courseId, String academicYear);

    // Add a methode to know if a student as a grade for a specific academic year
    /**
     * Exists by student id and academic year boolean.
     * This method is used to check if a grade exists for a student and an academic year.
     *
     * @param studentId    the student id
     * @param academicYear the academic year
     * @return the boolean
     */
    boolean existsByStudentIdAndAcademicYear(Long studentId, String academicYear);
}
