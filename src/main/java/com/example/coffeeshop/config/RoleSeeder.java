package com.example.coffeeshop.config;

import com.example.coffeeshop.models.entities.RoleEntity;
import com.example.coffeeshop.models.enums.RoleName;
import com.example.coffeeshop.models.repository.RoleRepository;

public class RoleSeeder {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void run(String... args) {
        createRoleIfNotExists(RoleName.ADMIN);
        createRoleIfNotExists(RoleName.MERCHANT);
        createRoleIfNotExists(RoleName.CUSTOMER);
    }

    private void createRoleIfNotExists(RoleName roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    RoleEntity role = new RoleEntity();
                    role.setName(roleName); // ✅ ENUM
                    return roleRepository.save(role);
                });
    }
}
