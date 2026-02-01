package com.theonova.service.report;

import com.theonova.enums.ReportStatus;
import com.theonova.enums.ReportType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Map;

public record ReportRunRepositoryService(
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
