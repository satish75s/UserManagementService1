package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.UserGroup;
import com.user.repository.UserGroupRepository;

@Service
public class UserGroupService {
    @Autowired
    private UserGroupRepository userGroupRepository;
    
    public List<UserGroup> getAllGroups() {
        return userGroupRepository.findAll();
    }
    
    public Optional<UserGroup> getGroupById(Long id) {
        return userGroupRepository.findById(id);
    }
    
    public UserGroup saveGroup(UserGroup group) {
        return userGroupRepository.save(group);
    }
    
    public void deleteGroup(Long groupId) {
        Long userCount = userGroupRepository.countUsersByGroupId(groupId);
        if (userCount > 0) {
            throw new RuntimeException("Cannot delete group as it is assigned to users.");
        }
        userGroupRepository.deleteById(groupId);
    }
}
