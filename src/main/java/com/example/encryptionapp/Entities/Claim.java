package com.example.encryptionapp.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String object;
    @Column(columnDefinition="TEXT",length = 5000)
    private String message;
    @ManyToOne
    private User user;
}
