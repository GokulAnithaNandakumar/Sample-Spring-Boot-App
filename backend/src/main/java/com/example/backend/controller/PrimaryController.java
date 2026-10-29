package com.example.backend.controller;

import com.example.backend.dto.PrimaryRequest;
import com.example.backend.entity.PrimaryEntity;
import com.example.backend.service.PrimaryService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/primary")
@CrossOrigin(origins = "http://localhost:3000")
public class PrimaryController {

    private final PrimaryService service;

    public PrimaryController(PrimaryService service) {
        this.service = service;
    }

    @GetMapping
    public List<PrimaryEntity> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String country) {
        return service.getAll(name, active, status, age, country);
    }

    @GetMapping("/{uid}")
    public ResponseEntity<PrimaryEntity> getById(@PathVariable Long uid) {
        return ResponseEntity.ok(service.getById(uid));
    }

    @PostMapping
    public ResponseEntity<PrimaryEntity> create(@RequestBody PrimaryRequest req) {
        PrimaryEntity created = service.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{uid}")
    public PrimaryEntity patch(@PathVariable Long uid, @RequestBody JsonNode node) {
        return service.patch(uid, node);
    }

    @PutMapping("/{uid}")
    public PrimaryEntity update(@PathVariable Long uid, @RequestBody PrimaryRequest req) {
        return service.update(uid, req);
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Void> delete(@PathVariable Long uid) {
        service.delete(uid);
        return ResponseEntity.noContent().build();
    }
}
