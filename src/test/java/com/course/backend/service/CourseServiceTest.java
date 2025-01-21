/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.course.backend.BaseTest;
import com.course.backend.model.Course;
import com.course.backend.repository.CourseRepository;
import java.util.Optional;

import com.course.backend.repository.GradeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

public class CourseServiceTest extends BaseTest {

    @Mock private CourseRepository courseRepository;
    @Mock private GradeRepository gradeRepository;

    @InjectMocks private CourseService courseService;

    @Test
    public void testGetCourseById_Success() {
        Long courseId = 1L;
        Course course = new Course("CS101", "Computer Science", 3, "Description");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        Course result = courseService.getCourseById(courseId);

        assertEquals(course, result);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    public void testAddCourse_Success() {
        Course course = new Course("CS101", "Computer Science", 3, "Description");
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        courseService.addCourse(course);

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testUpdateCourse_Success() {
        Long courseId = 1L;
        Course existingCourse = new Course("CS101", "Computer Science", 3, "Description");
        Course updatedCourse =
                new Course("CS101", "Computer Science Updated", 3, "Updated Description");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        courseService.updateCourse(courseId, updatedCourse);

        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testUpdateCourse_NotFound() {
        Long courseId = 1L;
        Course updatedCourse =
                new Course("CS101", "Computer Science Updated", 3, "Updated Description");

        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        assertThrows(
                ResponseStatusException.class,
                () -> courseService.updateCourse(courseId, updatedCourse));
        verify(courseRepository, times(1)).findById(courseId);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    public void testDeleteCourse_Success() {
        Long courseId = 1L;
        when(courseRepository.existsById(courseId)).thenReturn(true);
        doNothing().when(courseRepository).deleteById(courseId);
        when(gradeRepository.existsByCourseId(courseId)).thenReturn(false);

        courseService.deleteCourse(courseId);

        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    public void testDeleteCourse_NotFound() {
        Long courseId = 1L;
        when(courseRepository.existsById(courseId)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> courseService.deleteCourse(courseId));
        verify(courseRepository, times(1)).existsById(courseId);
        verify(courseRepository, never()).deleteById(courseId);
    }
}
