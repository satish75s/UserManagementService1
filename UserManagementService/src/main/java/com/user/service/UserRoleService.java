package com.user.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.UserRole;
import com.user.repository.UserRoleRepository;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;
    
    public List<UserRole> getAllRoles() {
        return userRoleRepository.findAll();
    }
    
    public Optional<UserRole> getRoleById(Long id) {
        return userRoleRepository.findById(id);
    }
    
    public UserRole saveRole(UserRole role) {
        return userRoleRepository.save(role);
    }
    
    public void deleteRole(Long roleId) {
        Long userCount = userRoleRepository.countUsersByRoleId(roleId);
        if (userCount > 0) {
            throw new RuntimeException("Cannot delete role as it is assigned to users.");
        }
        userRoleRepository.deleteById(roleId);
    }
}
