package com.theonova.entities.report;

import com.theonova.ReportStatus;
import com.theonova.ReportType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

public record ReportRun(
        long id,
        ReportType reportType,
        LocalDate periodStart,
        LocalDate periodEnd,
        ReportStatus status,
        Instant generatedAt,
        String fileRef,
        Map<String, Object> meta
) {
}
