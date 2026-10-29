package com.example.backend.repository;

import com.example.backend.entity.PrimaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrimaryRepository extends JpaRepository<PrimaryEntity, Long> {

    // A. Derived queries
    List<PrimaryEntity> findByNameContainingIgnoreCase(String name);
    List<PrimaryEntity> findByActive(Boolean active);
    List<PrimaryEntity> findByStatus(String status);
    List<PrimaryEntity> findByAgeGreaterThanEqual(Integer age);

    // B. JPQL
    @Query("""
        SELECT p FROM PrimaryEntity p
        WHERE p.age >= :age AND p.active = :active
        """)
    List<PrimaryEntity> findByAgeAndActive(@Param("age") Integer age, @Param("active") Boolean active);

    // C. Named query
    @Query(name = "PrimaryEntity.findByStatusNamed")
    List<PrimaryEntity> findByStatusNamed(@Param("status") String status);

    // D. Native SQL
    @Query(value = "SELECT * FROM primarytable WHERE country = :country", nativeQuery = true)
    List<PrimaryEntity> findByCountryNative(@Param("country") String country);
}
