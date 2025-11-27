package com.project007.db;


import jakarta.persistence.*;
import lombok.*;

@ToString
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
}
