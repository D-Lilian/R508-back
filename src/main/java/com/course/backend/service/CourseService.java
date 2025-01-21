/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.Course;
import com.course.backend.repository.CourseRepository;

import java.sql.SQLException;
import java.util.List;

import com.course.backend.repository.GradeRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * The Course service.
 * This class is used to interact with the database.
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;

    /**
     * Instantiates a new Course service.
     *
     * @param courseRepository the course repository
     */
    public CourseService(CourseRepository courseRepository, GradeRepository gradeRepository) {
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
    }

    /**
     * Gets all courses.
     *
     * @return the all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Add course.
     *
     * @param course the course
     */
    public void addCourse(Course course) {
        courseRepository.save(
                new Course(
                        course.getCode(),
                        course.getName(),
                        course.getCredits(),
                        course.getDescription()));
    }

    /**
     * Update course.
     *
     * @param id           the id
     * @param updatedCourse the updated course
     */
    public void updateCourse(Long id, Course updatedCourse) {
        Course course =
                courseRepository
                        .findById(id)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Course not found"));
        course.setCode(updatedCourse.getCode());
        course.setName(updatedCourse.getName());
        course.setCredits(updatedCourse.getCredits());
        course.setDescription(updatedCourse.getDescription());
        try {
            courseRepository.save(course);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Data integrity violation");
        }
    }

    /**
     * Delete course.
     *
     * @param id the id
     */
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        if (gradeRepository.existsByCourseId(id)) {
            throw new ConstraintViolationException(
                    "Course is used in grades", new SQLException("Course is used in grades"), "Course");
        }
        courseRepository.deleteById(id);
    }

    /**
     * Gets course by id.
     *
     * @param courseId the course id
     * @return the course by id
     */
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
    }
}
