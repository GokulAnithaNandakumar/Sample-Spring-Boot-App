package com.example.backend.service;

import com.example.backend.dto.PrimaryRequest;
import com.example.backend.entity.ForeignEntity;
import com.example.backend.entity.PrimaryEntity;
import com.example.backend.repository.PrimaryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrimaryServiceTest {

    @Mock
    private PrimaryRepository repository;

    @InjectMocks
    private PrimaryService service;

    private PrimaryEntity existing;

    @BeforeEach
    void setup() {
        existing = new PrimaryEntity();
        existing.setUid(1L);
        existing.setName("Old");
        existing.setStatus("ACTIVE");
        existing.setActive(true);
        existing.setAge(20);
        List<ForeignEntity> children = new ArrayList<>();
        ForeignEntity c = new ForeignEntity();
        c.setUid(10L);
        c.setValue("Child one");
        c.setQuantity(10);
        c.setActive(true);
        children.add(c);
        existing.setForeignRecords(children);
        c.setPrimary(existing);
    }

    @Test
    void createPrimary() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        PrimaryRequest req = new PrimaryRequest("Name", "desc", 22, 85.5, true, "Other",
                null, null, null, List.of("Java"), 7, "ACTIVE", "India", null);
        PrimaryEntity created = service.create(req);
        assertEquals("Name", created.getName());
        verify(repository).save(any());
    }

    @Test
    void createWithChildren() {
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        PrimaryRequest req = new PrimaryRequest("P", null, null, null, null, null,
                null, null, null, null, null, null, null,
                List.of(new com.example.backend.dto.ForeignRequest(null, "Child", 5, true)));
        PrimaryEntity created = service.create(req);
        assertEquals(1, created.getForeignRecords().size());
        assertEquals("Child", created.getForeignRecords().get(0).getValue());
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(List.of(existing));
        List<PrimaryEntity> all = service.getAll(null, null, null, null, null);
        assertEquals(1, all.size());
    }

    @Test
    void getByIdFound() {
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        PrimaryEntity found = service.getById(1L);
        assertEquals(1L, found.getUid());
    }

    @Test
    void getByIdMissingThrows() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> service.getById(99L));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void putUpdate() {
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        PrimaryRequest req = new PrimaryRequest("New", "d", 30, 90.0, false, "Male",
                null, null, null, List.of("React"), 3, "INACTIVE", "Germany",
                List.of(new com.example.backend.dto.ForeignRequest(10L, "Updated child", 20, false)));
        PrimaryEntity updated = service.update(1L, req);
        assertEquals("New", updated.getName());
        assertEquals("Updated child", updated.getForeignRecords().get(0).getValue());
    }

    @Test
    void patchPreservesOmitted() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree("{\"status\":\"INACTIVE\"}");
        PrimaryEntity patched = service.patch(1L, node);
        assertEquals("INACTIVE", patched.getStatus());
        assertEquals("Old", patched.getName()); // preserved
        assertEquals(20, patched.getAge()); // preserved
    }

    @Test
    void removeChildFromCollection() {
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        PrimaryRequest req = new PrimaryRequest("Old", null, 20, null, true, null,
                null, null, null, null, null, "ACTIVE", null, List.of()); // empty children -> remove existing
        PrimaryEntity updated = service.update(1L, req);
        assertEquals(0, updated.getForeignRecords().size());
    }

    @Test
    void deletePrimary() {
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(repository).delete(existing);
        service.delete(1L);
        verify(repository).delete(existing);
    }

    @Test
    void customQueryInteraction() {
        when(repository.findByActive(true)).thenReturn(List.of(existing));
        List<PrimaryEntity> result = service.getAll(null, true, null, null, null);
        assertEquals(1, result.size());
        verify(repository).findByActive(true);
    }
}
