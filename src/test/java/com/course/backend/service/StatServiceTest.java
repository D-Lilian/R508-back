/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.course.backend.BaseTest;
import com.course.backend.dto.CourseStatisticsImpl;
import com.course.backend.dto.GlobalStatisticsImpl;
import com.course.backend.dto.StudentSemesterStatisticsImpl;
import com.course.backend.model.SEMESTER;
import com.course.backend.repository.StatRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.server.ResponseStatusException;

public class StatServiceTest extends BaseTest {

    @Mock private StatRepository statRepository;

    @InjectMocks private StatService statService;

    @Test
    public void testGetGlobalStatistics_OK() {
        String academicYear = "2024-2025";
        GlobalStatisticsImpl mockGlobalStatistics = new GlobalStatisticsImpl(3.5, 100L, 20L, 80.0);

        when(statRepository.existsByAcademicYear(academicYear)).thenReturn(true);
        when(statRepository.getGlobalStatistics(academicYear)).thenReturn(mockGlobalStatistics);

        GlobalStatisticsImpl result = statService.getGlobalStatistics(academicYear);

        assertEquals(mockGlobalStatistics, result);
        verify(statRepository, times(1)).existsByAcademicYear(academicYear);
        verify(statRepository, times(1)).getGlobalStatistics(academicYear);
    }

    @Test
    public void testGetCourseStatistics_OK() {
        Long courseId = 1L;
        String academicYear = "2024-2025";
        CourseStatisticsImpl mockCourseStatistics =
                new CourseStatisticsImpl("CS101", "Computer Science", 3.5, 2.0, 4.0, 50L, 90.0);

        when(statRepository.existsByCourseIdAndAcademicYear(courseId, academicYear))
                .thenReturn(true);
        when(statRepository.getCourseStatistics(courseId, academicYear))
                .thenReturn(mockCourseStatistics);

        CourseStatisticsImpl result = statService.getCourseStatistics(courseId, academicYear);

        assertEquals(mockCourseStatistics, result);
        verify(statRepository, times(1)).existsByCourseIdAndAcademicYear(courseId, academicYear);
        verify(statRepository, times(1)).getCourseStatistics(courseId, academicYear);
    }

    @Test
    public void testGetStudentSemesterStatistics_OK() {
        Long studentId = 1L;
        String academicYear = "2024-2025";
        List<StudentSemesterStatisticsImpl> mockStatistics =
                List.of(new StudentSemesterStatisticsImpl(SEMESTER.S1, 3.5, 30L, 25L, 5L));

        when(statRepository.existsByStudentIdAndAcademicYear(studentId, academicYear))
                .thenReturn(true);
        when(statRepository.getStudentSemesterStatistics(studentId, academicYear))
                .thenReturn(mockStatistics);

        List<StudentSemesterStatisticsImpl> result =
                statService.getStudentSemesterStatistics(studentId, academicYear);

        assertEquals(mockStatistics, result);
        verify(statRepository, times(1)).existsByStudentIdAndAcademicYear(studentId, academicYear);
        verify(statRepository, times(1)).getStudentSemesterStatistics(studentId, academicYear);
    }

    @Test
    public void testGetGlobalStatistics_AcademicYearNotFound() {
        String academicYear = "9999-9999";
        when(statRepository.getGlobalStatistics(academicYear)).thenReturn(null);

        assertThrows(
                ResponseStatusException.class,
                () -> {
                    statService.getGlobalStatistics(academicYear);
                });
    }

    @Test
    public void testGetCourseStatistics_CourseIdNotFound() {
        Long courseId = 999L;
        String academicYear = "2024-2025";
        when(statRepository.getCourseStatistics(courseId, academicYear)).thenReturn(null);

        assertThrows(
                ResponseStatusException.class,
                () -> {
                    statService.getCourseStatistics(courseId, academicYear);
                });
    }

    @Test
    public void testGetStudentSemesterStatistics_StudentIdNotFound() {
        Long studentId = 999L;
        String academicYear = "2024-2025";
        when(statRepository.getStudentSemesterStatistics(studentId, academicYear))
                .thenReturn(Collections.emptyList());

        assertThrows(
                ResponseStatusException.class,
                () -> {
                    statService.getStudentSemesterStatistics(studentId, academicYear);
                });
    }
}
