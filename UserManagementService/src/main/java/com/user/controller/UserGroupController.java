package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.UserGroup;
import com.user.service.UserGroupService;

@RestController
@RequestMapping("/groups")
@CrossOrigin
public class UserGroupController {
    @Autowired
    private UserGroupService userGroupService;

    @GetMapping
    public List<UserGroup> getAllGroups() {
        return userGroupService.getAllGroups();
    }

    @PostMapping
    public UserGroup createGroup(@RequestBody UserGroup group) {
        return userGroupService.saveGroup(group);
    }

    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        userGroupService.deleteGroup(id);
    }
}
