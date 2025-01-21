/* (C)2025 */
package com.course.backend.service;

import com.course.backend.dto.CourseStatisticsImpl;
import com.course.backend.dto.GlobalStatisticsImpl;
import com.course.backend.dto.StudentSemesterStatisticsImpl;
import com.course.backend.repository.StatRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * The Stat service.
 * This class is used to interact with the database.
 */
@Service
public class StatService {

    private final StatRepository statRepository;

    /**
     * Instantiates a new Stat service.
     *
     * @param statRepository the stat repository
     */
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    /**
     * Gets global statistics.
     *
     * @param academicYear the academic year
     * @return the global statistics
     */
    public GlobalStatisticsImpl getGlobalStatistics(String academicYear) {
        if (!statRepository.existsByAcademicYear(academicYear)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Academic year not found");
        }
        GlobalStatisticsImpl globalStatistics = statRepository.getGlobalStatistics(academicYear);
        globalStatistics.setGlobalAverage(setFormat(globalStatistics.getGlobalAverage()));
        globalStatistics.setAverageSuccessRate(setFormat(globalStatistics.getAverageSuccessRate()));
        return globalStatistics;
    }

    /**
     * Gets course statistics.
     *
     * @param courseId     the course id
     * @param academicYear the academic year
     * @return the course statistics
     */
    public CourseStatisticsImpl getCourseStatistics(Long courseId, String academicYear) {
        if (!statRepository.existsByCourseIdAndAcademicYear(courseId, academicYear)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Course or Academic year not found");
        }
        CourseStatisticsImpl courseStatistics = statRepository.getCourseStatistics(courseId, academicYear);
        courseStatistics.setAverageGrade(setFormat(courseStatistics.getAverageGrade()));
        courseStatistics.setMaxGrade(setFormat(courseStatistics.getMaxGrade()));
        courseStatistics.setMinGrade(setFormat(courseStatistics.getMinGrade()));
        courseStatistics.setSuccessRate(setFormat(courseStatistics.getSuccessRate()));
        return courseStatistics;
    }

    /**
     * Gets student semester statistics.
     *
     * @param studentId    the student id
     * @param academicYear the academic year
     * @return the student semester statistics
     */
    public List<StudentSemesterStatisticsImpl> getStudentSemesterStatistics(
            Long studentId, String academicYear) {
        if (!statRepository.existsByStudentIdAndAcademicYear(studentId, academicYear)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student or Academic year not found");
        }
        List<StudentSemesterStatisticsImpl> studentSemesterStatistics =
                statRepository.getStudentSemesterStatistics(studentId, academicYear);
        for (StudentSemesterStatisticsImpl studentSemesterStatistic : studentSemesterStatistics) {
            studentSemesterStatistic.setAverageGrade(setFormat(studentSemesterStatistic.getAverageGrade()));
        }
        return studentSemesterStatistics;
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
