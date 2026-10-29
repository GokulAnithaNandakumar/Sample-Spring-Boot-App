package com.example.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "primarytable")
@NamedQuery(name = "PrimaryEntity.findByStatusNamed",
        query = "SELECT p FROM PrimaryEntity p WHERE p.status = :status")
public class PrimaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String name;
    @Column(length = 2000)
    private String description;
    private Integer age;
    private Double score;
    private Boolean active;
    private String gender;
    private LocalDate birthDate;
    private LocalTime reminderTime;
    private LocalDateTime eventDateTime;

    @ElementCollection
    @CollectionTable(name = "primary_tags", joinColumns = @JoinColumn(name = "primary_uid"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

    private Integer priority;
    private String status;
    private String country;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "primary", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ForeignEntity> foreignRecords = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addForeign(ForeignEntity child) {
        child.setPrimary(this);
        foreignRecords.add(child);
    }

    public void removeForeign(ForeignEntity child) {
        foreignRecords.remove(child);
        child.setPrimary(null);
    }

    public Long getUid() { return uid; }
    public void setUid(Long uid) { this.uid = uid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public LocalTime getReminderTime() { return reminderTime; }
    public void setReminderTime(LocalTime reminderTime) { this.reminderTime = reminderTime; }
    public LocalDateTime getEventDateTime() { return eventDateTime; }
    public void setEventDateTime(LocalDateTime eventDateTime) { this.eventDateTime = eventDateTime; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public Integer getPriority() { return priority; }
    public void setPriority(Integer priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public List<ForeignEntity> getForeignRecords() { return foreignRecords; }
    public void setForeignRecords(List<ForeignEntity> foreignRecords) { this.foreignRecords = foreignRecords; }
}
