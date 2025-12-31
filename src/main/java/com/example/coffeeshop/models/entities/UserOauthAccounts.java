package com.example.coffeeshop.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "UserOauthAccounts")
@Data
public class UserOauthAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int user_id;

    @Column(length = 200)
    private String provider;

    private String provider_user_id;

    @Column(length = 200)
    private String provider_email;

    @Column(length = 50)
    private String created_at;
}
