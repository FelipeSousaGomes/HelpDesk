package br.com.felipe.oderserviceapi.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 45)
    private String requesterId;
    @Column(nullable = false, length = 45)
    private String customerId;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 3000)
    private String description;
    private LocalDateTime createdAt = now();
    private LocalDateTime closedAt;
}
