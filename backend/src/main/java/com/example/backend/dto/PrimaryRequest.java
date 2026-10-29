package com.example.backend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record PrimaryRequest(
        String name,
        String description,
        Integer age,
        Double score,
        Boolean active,
        String gender,
        LocalDate birthDate,
        LocalTime reminderTime,
        LocalDateTime eventDateTime,
        List<String> tags,
        Integer priority,
        String status,
        String country,
        List<ForeignRequest> foreignRecords
) {}
