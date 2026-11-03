package com.hackerrank.burger.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
@Entity @Table(name = "burger_orders")
public class OrderBean {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  @NotBlank private String burgerName;
  @Min(1) private Integer quantity;
  @NotBlank @Column(length = 1000) private String deliveryAddress;
  private Double totalCost;
  private String orderStatus;
  private LocalDateTime orderTime;
  private LocalDateTime createdAt;
  public OrderBean() {}
  public OrderBean(String burgerName, Integer quantity, String deliveryAddress) {
    this.burgerName=burgerName; this.quantity=quantity; this.deliveryAddress=deliveryAddress;
  }
  @PrePersist public void beforeSave() { if (createdAt == null) createdAt=LocalDateTime.now(); }
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getBurgerName(){return burgerName;} public void setBurgerName(String v){burgerName=v;}
  public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
  public String getDeliveryAddress(){return deliveryAddress;} public void setDeliveryAddress(String v){deliveryAddress=v;}
  public Double getTotalCost(){return totalCost;} public void setTotalCost(Double v){totalCost=v;}
  public String getOrderStatus(){return orderStatus;} public void setOrderStatus(String v){orderStatus=v;}
  public LocalDateTime getOrderTime(){return orderTime;} public void setOrderTime(LocalDateTime v){orderTime=v;}
  public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
