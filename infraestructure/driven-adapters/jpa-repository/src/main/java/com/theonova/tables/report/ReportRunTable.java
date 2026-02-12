package com.theonova.tables.report;

import com.theonova.enums.ReportStatus;
import com.theonova.enums.ReportType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "report_runs")
@Getter
@Setter
@NoArgsConstructor
public class ReportRunTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false, length = 20)
    @Comment("Report type")
    private ReportType reportType;

    @Column(name = "period_start", nullable = false)
    @Comment("Reporting period start")
    private LocalDate periodStart;

    @Column(name = "period_end", nullable = false)
    @Comment("Reporting period end")
    private LocalDate periodEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Comment("Report status")
    private ReportStatus status;

    @Column(name = "generated_at", nullable = false)
    @Comment("Generated timestamp")
    private Instant generatedAt;

    @Column(name = "file_ref", length = 255)
    @Comment("Report file reference")
    private String fileRef;

    @Column(name = "meta", columnDefinition = "json")
    @Comment("Report metadata")
    private String meta;
}
