package net.javacode.spring_311.service;

import jakarta.persistence.EntityNotFoundException;
import net.javacode.spring_311.model.Role;
import net.javacode.spring_311.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}




