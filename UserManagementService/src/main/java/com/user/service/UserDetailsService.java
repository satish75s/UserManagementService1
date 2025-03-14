package com.user.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.UserDetails;
import com.user.entity.UserGroup;
import com.user.entity.UserRole;
import com.user.repository.UserDetailsRepository;
import com.user.repository.UserGroupRepository;
import com.user.repository.UserRoleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    public Optional<UserDetails> getUserById(Long id) {
        return userDetailsRepository.findById(id);
    }

    @Transactional
    public UserDetails saveUser(UserDetails user) {
        Set<UserRole> roles = user.getRoles().stream().map(role ->
            userRoleRepository.findByRoleName(role.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + role.getRoleName()))
        ).collect(Collectors.toSet());
        user.setRoles(roles);

        Set<UserGroup> groups = user.getGroups().stream().map(group ->
            userGroupRepository.findByGroupName(group.getGroupName())
                .orElseThrow(() -> new RuntimeException("Group not found: " + group.getGroupName()))
        ).collect(Collectors.toSet());
        user.setGroups(groups);

        return userDetailsRepository.save(user);
    }

    @Transactional
    public UserDetails updateUser(Long id, UserDetails updatedUser) {
        return userDetailsRepository.findById(id).map(existingUser -> {
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            
            Set<UserRole> roles = updatedUser.getRoles().stream().map(role ->
                userRoleRepository.findByRoleName(role.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + role.getRoleName()))
            ).collect(Collectors.toSet());
            existingUser.setRoles(roles);

            Set<UserGroup> groups = updatedUser.getGroups().stream().map(group ->
                userGroupRepository.findByGroupName(group.getGroupName())
                    .orElseThrow(() -> new RuntimeException("Group not found: " + group.getGroupName()))
            ).collect(Collectors.toSet());
            existingUser.setGroups(groups);

            return userDetailsRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

   
    @Transactional
    public void deleteUser(Long userId) {
        Optional<UserDetails> userOptional = userDetailsRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserDetails user = userOptional.get();
            userDetailsRepository.delete(user); // The deletion will cascade to the join table
        }
    }
}



