package com.example.coffeeshop.config;

import com.example.coffeeshop.models.entities.RoleEntity;
import com.example.coffeeshop.models.repository.RoleRepository;

public class RoleSeeder {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(String... args) {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("MERCHANT");
        createRoleIfNotExists("CUSTOMER");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setName(roleName);
                    return roleRepository.save(role);
                });
    }
}
