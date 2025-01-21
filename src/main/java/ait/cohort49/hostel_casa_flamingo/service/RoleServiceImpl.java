package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.Role;
import ait.cohort49.hostel_casa_flamingo.repository.RoleRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoleService;

public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role getRoleUser() {

        /**
         * получить роль"USER" из БД по title
         */
        return roleRepository.findRoleByTitle("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Database doesn't contain ROLE_USER"));
    }
}
