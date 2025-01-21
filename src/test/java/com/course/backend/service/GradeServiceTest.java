/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.course.backend.BaseTest;
import com.course.backend.model.Course;
import com.course.backend.model.Grade;
import com.course.backend.model.Student;
import com.course.backend.repository.GradeRepository;
import com.course.backend.response.GradeGetResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class GradeServiceTest extends BaseTest {

    @Mock private GradeRepository gradeRepository;

    @Mock private StudentService studentService;

    @Mock private CourseService courseService;

    @InjectMocks private GradeService gradeService;

    @Test
    public void testGetGradesByStudent() {
        Long studentId = 1L;
        Grade grade1 = new Grade(new Student(), new Course(), 15.0, "S1", "2024-2025");
        Grade grade2 = new Grade(new Student(), new Course(), 18.0, "S2", "2024-2025");
        List<Grade> grades = Arrays.asList(grade1, grade2);

        when(gradeRepository.findByStudentId(studentId)).thenReturn(grades);

        List<GradeGetResponse> result = gradeService.getGradesByStudent(studentId);

        assertEquals(2, result.size());
        assertEquals(15.0, result.get(0).getGrade());
        assertEquals(18.0, result.get(1).getGrade());
    }

    @Test
    void testAddGrade() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setGrade(15.0);

        Student student = new Student();
        Course course = new Course();

        when(gradeRepository.existsByStudentIdAndCourseId(1L, 1L)).thenReturn(false);
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);
        when(studentService.getStudentById(1L)).thenReturn(student);
        when(courseService.getCourseById(1L)).thenReturn(course);

        gradeService.addGrade(1L, 1L, 15.0, "S1", "2024-2025");

        verify(gradeRepository, times(1)).existsByStudentIdAndCourseId(1L, 1L);
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void testUpdateGrade() {
        Grade grade = new Grade();
        grade.setId(1L);
        grade.setGrade(15.0);

        when(gradeRepository.findById(1L)).thenReturn(Optional.of(grade));
        when(gradeRepository.save(any(Grade.class))).thenReturn(grade);

        gradeService.updateGrade(1L, 18.0);

        verify(gradeRepository, times(1)).findById(1L);
        verify(gradeRepository, times(1)).save(any(Grade.class));
    }

    @Test
    void testDeleteGrade() {
        Grade grade = new Grade();
        grade.setId(1L);

        when(gradeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(gradeRepository).deleteById(1L);

        gradeService.deleteGrade(1L);

        verify(gradeRepository, times(1)).existsById(1L);
        verify(gradeRepository, times(1)).deleteById(1L);
    }
}
