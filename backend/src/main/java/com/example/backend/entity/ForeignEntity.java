package com.example.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "foreigntable")
public class ForeignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String value;
    private Integer quantity;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_uid", nullable = false)
    @JsonIgnore
    private PrimaryEntity primary;

    public Long getUid() { return uid; }
    public void setUid(Long uid) { this.uid = uid; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public PrimaryEntity getPrimary() { return primary; }
    public void setPrimary(PrimaryEntity primary) { this.primary = primary; }
}
