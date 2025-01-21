/* (C)2025 */
package com.course.backend.model;

public enum SEMESTER {
    S1,
    S2,
    S3,
    S4,
    S5,
    S6;

    public static SEMESTER fromString(String semester) {
        return switch (semester) {
            case "S1" -> S1;
            case "S2" -> S2;
            case "S3" -> S3;
            case "S4" -> S4;
            case "S5" -> S5;
            case "S6" -> S6;
            default -> null;
        };
    }
}
