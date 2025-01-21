/* (C)2025 */
package com.course.backend.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.course.backend.BaseTest;
import com.course.backend.model.SEMESTER;
import com.course.backend.model.StudentGrade;
import com.course.backend.model.StudentInfo;
import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class PDFServiceTest extends BaseTest {

    @InjectMocks private PDFService pdfService;

    @Mock private StudentInfo studentInfo;

    @Test
    public void testGenerateTranscript() throws DocumentException, IOException {
        // Arrange
        String academicYear = "2024-2025";

        // Génération d'une liste de notes
        List<StudentGrade> grades;
        grades =
                List.of(
                        new StudentGrade("", "Math", 3, 15.0, SEMESTER.S1),
                        new StudentGrade("", "Science", 3, 18.0, SEMESTER.S1),
                        new StudentGrade("", "History", 3, 12.0, SEMESTER.S1));

        // Act
        byte[] pdfBytes = pdfService.generateTranscript(studentInfo, grades, academicYear);

        // Assert
        assertNotNull(pdfBytes);
        verify(studentInfo, times(1)).getFirstName();
        verify(studentInfo, times(1)).getLastName();
    }
}
