package com.example.backend.service;

import com.example.backend.dto.ForeignRequest;
import com.example.backend.dto.PrimaryRequest;
import com.example.backend.entity.ForeignEntity;
import com.example.backend.entity.PrimaryEntity;
import com.example.backend.repository.PrimaryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PrimaryService {

    private final PrimaryRepository repository;

    public PrimaryService(PrimaryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PrimaryEntity> getAll(String name, Boolean active, String status, Integer age, String country) {
        if (country != null && !country.isBlank()) {
            return repository.findByCountryNative(country);
        }
        if (name != null && !name.isBlank()) {
            return repository.findByNameContainingIgnoreCase(name);
        }
        if (status != null && !status.isBlank() && active != null && age != null) {
            return repository.findByAgeAndActive(age, active);
        }
        if (status != null && !status.isBlank()) {
            return repository.findByStatusNamed(status);
        }
        if (active != null && age != null) {
            return repository.findByAgeAndActive(age, active);
        }
        if (active != null) {
            return repository.findByActive(active);
        }
        if (age != null) {
            return repository.findByAgeGreaterThanEqual(age);
        }
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public PrimaryEntity getById(Long uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public PrimaryEntity create(PrimaryRequest req) {
        PrimaryEntity p = new PrimaryEntity();
        applyFields(p, req);
        if (req.foreignRecords() != null) {
            for (ForeignRequest fr : req.foreignRecords()) {
                ForeignEntity child = new ForeignEntity();
                child.setValue(fr.value());
                child.setQuantity(fr.quantity());
                child.setActive(fr.active());
                p.addForeign(child);
            }
        }
        if (req.tags() != null) p.setTags(new ArrayList<>(req.tags()));
        return repository.save(p);
    }

    @Transactional
    public PrimaryEntity update(Long uid, PrimaryRequest req) {
        PrimaryEntity p = getById(uid);
        // editable scalar fields (full replacement)
        p.setName(req.name());
        p.setDescription(req.description());
        p.setAge(req.age());
        p.setScore(req.score());
        p.setActive(req.active());
        p.setGender(req.gender());
        p.setBirthDate(req.birthDate());
        p.setReminderTime(req.reminderTime());
        p.setEventDateTime(req.eventDateTime());
        p.setPriority(req.priority());
        p.setStatus(req.status());
        p.setCountry(req.country());
        if (req.tags() != null) p.setTags(new ArrayList<>(req.tags()));

        // reconcile children if supplied (required for full replacement)
        if (req.foreignRecords() != null) {
            reconcileChildren(p, req.foreignRecords());
        }
        return repository.save(p);
    }

    @Transactional
    public PrimaryEntity patch(Long uid, JsonNode node) {
        PrimaryEntity p = getById(uid);
        if (node.has("name")) p.setName(textOrNull(node, "name"));
        if (node.has("description")) p.setDescription(textOrNull(node, "description"));
        if (node.has("age")) p.setAge(intOrNull(node, "age"));
        if (node.has("score")) p.setScore(doubleOrNull(node, "score"));
        if (node.has("active")) p.setActive(boolOrNull(node, "active"));
        if (node.has("gender")) p.setGender(textOrNull(node, "gender"));
        if (node.has("birthDate")) p.setBirthDate(dateOrNull(node, "birthDate"));
        if (node.has("reminderTime")) p.setReminderTime(timeOrNull(node, "reminderTime"));
        if (node.has("eventDateTime")) p.setEventDateTime(dateTimeOrNull(node, "eventDateTime"));
        if (node.has("priority")) p.setPriority(intOrNull(node, "priority"));
        if (node.has("status")) p.setStatus(textOrNull(node, "status"));
        if (node.has("country")) p.setCountry(textOrNull(node, "country"));
        if (node.has("tags")) {
            List<String> tags = new ArrayList<>();
            for (JsonNode t : node.get("tags")) tags.add(t.asText());
            p.setTags(tags);
        }
        if (node.has("foreignRecords") && node.get("foreignRecords").isArray()) {
            List<ForeignRequest> children = new ArrayList<>();
            for (JsonNode c : node.get("foreignRecords")) {
                Long cuid = c.has("uid") && !c.get("uid").isNull() ? c.get("uid").asLong() : null;
                String value = c.has("value") && !c.get("value").isNull() ? c.get("value").asText() : null;
                Integer qty = c.has("quantity") && !c.get("quantity").isNull() ? c.get("quantity").asInt() : null;
                Boolean act = c.has("active") && !c.get("active").isNull() ? c.get("active").asBoolean() : null;
                children.add(new ForeignRequest(cuid, value, qty, act));
            }
            reconcileChildren(p, children);
        }
        return repository.save(p);
    }

    @Transactional
    public void delete(Long uid) {
        PrimaryEntity p = getById(uid);
        repository.delete(p);
    }

    private void applyFields(PrimaryEntity p, PrimaryRequest req) {
        p.setName(req.name());
        p.setDescription(req.description());
        p.setAge(req.age());
        p.setScore(req.score());
        p.setActive(req.active());
        p.setGender(req.gender());
        p.setBirthDate(req.birthDate());
        p.setReminderTime(req.reminderTime());
        p.setEventDateTime(req.eventDateTime());
        p.setPriority(req.priority());
        p.setStatus(req.status());
        p.setCountry(req.country());
    }

    private void reconcileChildren(PrimaryEntity p, List<ForeignRequest> incoming) {
        // index existing
        List<ForeignEntity> existing = p.getForeignRecords();
        // remove omitted children
        List<Long> incomingIds = new ArrayList<>();
        for (ForeignRequest fr : incoming) {
            if (fr.uid() != null) incomingIds.add(fr.uid());
        }
        existing.removeIf(c -> c.getUid() != null && !incomingIds.contains(c.getUid()));

        for (ForeignRequest fr : incoming) {
            ForeignEntity target = null;
            if (fr.uid() != null) {
                for (ForeignEntity e : existing) {
                    if (fr.uid().equals(e.getUid())) { target = e; break; }
                }
            }
            if (target == null) {
                ForeignEntity ne = new ForeignEntity();
                ne.setValue(fr.value());
                ne.setQuantity(fr.quantity());
                ne.setActive(fr.active());
                p.addForeign(ne);
            } else {
                target.setValue(fr.value());
                target.setQuantity(fr.quantity());
                target.setActive(fr.active());
            }
        }
    }

    // helper null parsing
    private String textOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull()) ? null : n.asText();
    }
    private Integer intOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull()) ? null : n.asInt();
    }
    private Double doubleOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull()) ? null : n.asDouble();
    }
    private Boolean boolOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull()) ? null : n.asBoolean();
    }
    private LocalDate dateOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull() || n.asText().isBlank()) ? null : LocalDate.parse(n.asText());
    }
    private LocalTime timeOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull() || n.asText().isBlank()) ? null : LocalTime.parse(n.asText());
    }
    private LocalDateTime dateTimeOrNull(JsonNode node, String field) {
        JsonNode n = node.get(field);
        return (n == null || n.isNull() || n.asText().isBlank()) ? null : LocalDateTime.parse(n.asText());
    }
}
