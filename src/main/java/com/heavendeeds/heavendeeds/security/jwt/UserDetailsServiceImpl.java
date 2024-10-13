package com.heavendeeds.heavendeeds.security.jwt;

import com.heavendeeds.heavendeeds.entities.HAVEENuserRoleMap;
import com.heavendeeds.heavendeeds.entities.HAVEENuserRoles;
import com.heavendeeds.heavendeeds.entities.HAVEENuserDetails;
import com.heavendeeds.heavendeeds.exception.HeavenApiExceptionCode;
import com.heavendeeds.heavendeeds.repository.HAVEENuserDetailsRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENUserRoleMapRepo;
import com.heavendeeds.heavendeeds.repository.HAVEENuserRolesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    HAVEENuserDetailsRepo forMuserRegisterDetailsRepo;
    @Autowired
    HAVEENuserRolesRepo HAVEENuserRolesRepo;
    @Autowired
    HAVEENUserRoleMapRepo forMuserRoleMapRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HAVEENuserDetails user = Optional.ofNullable(forMuserRegisterDetailsRepo.findOneByEmail(username))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        HAVEENuserRoleMap HAVEENuserRoleMap = forMuserRoleMapRepo.findByUserId(user.getUserId());
        if (Objects.isNull(HAVEENuserRoleMap)) {
            throw new UsernameNotFoundException(HeavenApiExceptionCode.USER_ROLE_NOT_FOUND.toString());
        }
        Optional<HAVEENuserRoles> formUserRoles = HAVEENuserRolesRepo.findById(HAVEENuserRoleMap.getRoleId());
        if (formUserRoles.isEmpty()) {
            throw new UsernameNotFoundException(HeavenApiExceptionCode.USER_ROLE_NOT_FOUND.toString());
        }
        return UserDetailsImpl.build(user, formUserRoles.get().getRole());
    }
}