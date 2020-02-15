package com.documentflow.services;

import com.documentflow.entities.Role;
import com.documentflow.repositories.RoleRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {
    @Setter(onMethod_ = {@Autowired})
    private RoleRepository roleRepository;

    @Transactional
    Role getRoleByBusinessKey(String businessKey) {
        return roleRepository.findRoleByBusinessKey(businessKey);
    }
}
