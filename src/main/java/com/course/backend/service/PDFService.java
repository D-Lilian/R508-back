/* (C)2025 */
package com.course.backend.service;

import com.course.backend.model.SemesterSummary;
import com.course.backend.model.StudentGrade;
import com.course.backend.model.StudentInfo;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Service to generate PDF transcripts
 */
@Service
public class PDFService {

    /**
     * Organize grades by semester and calculate semester averages
     *
     * @param grades List of student grades
     * @return List of SemesterSummary objects
     */
    private List<SemesterSummary> organizeBySemester(List<StudentGrade> grades) {
        Map<String, List<StudentGrade>> semesters =
                grades.stream().collect(Collectors.groupingBy(StudentGrade::getSemester));

        return semesters.entrySet().stream()
                .map(
                        entry -> {
                            String semester = entry.getKey();
                            List<StudentGrade> semesterGrades = entry.getValue();
                            int totalCredits =
                                    semesterGrades.stream()
                                            .mapToInt(StudentGrade::getCredits)
                                            .sum();
                            double weightedSum =
                                    semesterGrades.stream()
                                            .mapToDouble(g -> g.getGrade() * g.getCredits())
                                            .sum();
                            int validatedCredits =
                                    semesterGrades.stream()
                                            .mapToInt(g -> g.getGrade() >= 10 ? g.getCredits() : 0)
                                            .sum();

                            SemesterSummary summary = new SemesterSummary();
                            summary.setSemester(semester);
                            summary.setGrades(semesterGrades);
                            summary.setAverage(weightedSum / totalCredits);
                            summary.setTotalCredits(totalCredits);
                            summary.setValidatedCredits(validatedCredits);
                            return summary;
                        })
                .sorted((a, b) -> a.getSemester().compareTo(b.getSemester()))
                .collect(Collectors.toList());
    }

    /**
     * Generate a PDF transcript for a student
     *
     * @param studentInfo   Student information
     * @param grades        List of student grades
     * @param academicYear  Academic year
     * @return Byte array containing the PDF transcript
     * @throws DocumentException If an error occurs while generating the PDF
     * @throws IOException       If an error occurs while generating the PDF
     */
    public byte[] generateTranscript(
            StudentInfo studentInfo, List<StudentGrade> grades, String academicYear)
            throws DocumentException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        Font subTitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        Paragraph title = new Paragraph("IUT de Laval", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph subTitle = new Paragraph("Relevé de notes", subTitleFont);
        subTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitle);

        document.add(new Paragraph(" "));

        document.add(
                new Paragraph(
                        "Étudiant: " + studentInfo.getFirstName() + " " + studentInfo.getLastName(),
                        textFont));
        document.add(new Paragraph("Numéro étudiant: " + studentInfo.getStudentId(), textFont));
        document.add(new Paragraph("Année universitaire: " + academicYear, textFont));
        document.add(new Paragraph(" "));

        List<SemesterSummary> semesterSummaries = organizeBySemester(grades);

        for (SemesterSummary semester : semesterSummaries) {
            document.add(new Paragraph("Semestre " + semester.getSemester(), subTitleFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new int[] {1, 3, 1, 1});

            addTableHeader(table, new String[] {"Code", "Cours", "Crédits", "Note"});

            for (StudentGrade grade : semester.getGrades()) {
                table.addCell(new PdfPCell(new Phrase(grade.getCourseCode(), textFont)));
                table.addCell(new PdfPCell(new Phrase(grade.getCourseName(), textFont)));
                table.addCell(
                        new PdfPCell(new Phrase(String.valueOf(grade.getCredits()), textFont)));
                table.addCell(
                        new PdfPCell(
                                new Phrase(String.format("%.2f", grade.getGrade()), textFont)));
            }

            document.add(table);

            document.add(
                    new Paragraph(
                            "Moyenne du semestre: "
                                    + String.format("%.2f", semester.getAverage())
                                    + "/20",
                            textFont));
            document.add(
                    new Paragraph(
                            "Crédits validés: "
                                    + semester.getValidatedCredits()
                                    + "/"
                                    + semester.getTotalCredits()
                                    + " ECTS",
                            textFont));
            document.add(new Paragraph(" "));
        }

        document.add(
                new Paragraph(
                        "Fait à Laval, le " + java.time.LocalDate.now().toString(), textFont));
        document.add(new Paragraph("Le Directeur des Études", textFont));

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Add a header row to a table
     *
     * @param table   Table to add the header to
     * @param headers Array of header names
     */
    private void addTableHeader(PdfPTable table, String[] headers) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }
}
