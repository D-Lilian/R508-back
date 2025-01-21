/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.Grade;
import com.course.backend.model.StudentGrade;
import com.course.backend.repository.GradeRepository;
import com.course.backend.response.GradeGetResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service class for handling grade-related operations
 */
@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    /**
     * Constructor for the GradeService class
     *
     * @param gradeRepository The repository for grades
     * @param studentService  The service for student-related operations
     * @param courseService   The service for course-related operations
     */
    public GradeService(
            GradeRepository gradeRepository,
            StudentService studentService,
            CourseService courseService) {
        this.gradeRepository = gradeRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * Get all grades or grades for a specific student
     *
     * @return A list of GradeGetResponse objects
     */
    public List<GradeGetResponse> getAllGrades() {
        return getGradesByStudent(null);
    }

    /**
     * Get grades for a specific student
     *
     * @param id The ID of the student
     * @return A list of GradeGetResponse objects
     */
    public List<GradeGetResponse> getGradesByStudent(Long id) {
        return getGrades(id);
    }

    /**
     * Get grade for a specific course
     *
     * @param id The ID of the grade
     * @return GradeGetResponse object
     */
    public GradeGetResponse getGrade(Long id) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        if (optionalGrade.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grade not found");
        }

        Grade grade = optionalGrade.get();

        return new GradeGetResponse(
                    grade.getId(),
                    grade.getStudent().getId(),
                    grade.getCourse().getId(),
                    grade.getGrade(),
                    grade.getSemester().toString(),
                    grade.getAcademicYear(),
                    grade.getStudent().getFirstName(),
                    grade.getStudent().getLastName(),
                    grade.getCourse().getCode(),
                    grade.getCourse().getName());
    }

    /**
     * Get grades for a specific course
     *
     * @param id The ID of the course
     * @return A list of GradeGetResponse objects
     */
    private List<GradeGetResponse> getGrades(Long id) {
        List<Grade> grades =
                id == null ? gradeRepository.findAll() : gradeRepository.findByStudentId(id);

        return grades.stream()
                .map(
                        grade ->
                                new GradeGetResponse(
                                        grade.getId(),
                                        grade.getStudent().getId(),
                                        grade.getCourse().getId(),
                                        grade.getGrade(),
                                        grade.getSemester().toString(),
                                        grade.getAcademicYear(),
                                        grade.getStudent().getFirstName(),
                                        grade.getStudent().getLastName(),
                                        grade.getCourse().getCode(),
                                        grade.getCourse().getName()))
                .toList();
    }

    /**
     * Add a grade for a student
     *
     * @param StudentId    The ID of the student
     * @param CourseId     The ID of the course
     * @param grade        The grade
     * @param semester     The semester
     * @param academicYear The academic year
     */
    public void addGrade(
            Long StudentId, Long CourseId, double grade, String semester, String academicYear) {
        if (gradeRepository.existsByStudentIdAndCourseId(StudentId, CourseId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Grade already exists for this student and course");
        }
        gradeRepository.save(
                new Grade(
                        studentService.getStudentById(StudentId),
                        courseService.getCourseById(CourseId),
                        setFormat(grade),
                        semester,
                        academicYear));
    }

    /**
     * Update a grade
     *
     * @param id           The ID of the grade
     * @param updatedGrade The updated grade
     */
    public void updateGrade(Long id, double updatedGrade) {
        Grade grade =
                gradeRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Course not found"));
        grade.setGrade(setFormat(updatedGrade));
        gradeRepository.save(grade);
    }

    /**
     * Delete a grade
     *
     * @param id The ID of the grade
     */
    public void deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        gradeRepository.deleteById(id);
    }

    /**
     * Get grades for a specific student and academic year
     *
     * @param studentId    The ID of the student
     * @param academicYear The academic year
     * @return A list of StudentGrade objects
     */
    public List<StudentGrade> getGradesByStudentAndAcademicYear(
            Long studentId, String academicYear) {
        return gradeRepository.findByStudentIdAndAcademicYear(studentId, academicYear);
    }

    /**
     * Format the average grade to two decimal places
     *
     * @param value a value to be formatted
     * @return The formatted value
     */
    private double setFormat(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
