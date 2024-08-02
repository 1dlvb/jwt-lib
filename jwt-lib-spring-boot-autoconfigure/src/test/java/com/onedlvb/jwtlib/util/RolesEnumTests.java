package com.onedlvb.jwtlib.util;

import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RolesEnumTests {

    @Test
    public void testRolesEnumValues() {
        Set<RolesEnum> expectedRoles = EnumSet.of(
                RolesEnum.USER,
                RolesEnum.CREDIT_USER,
                RolesEnum.OVERDRAFT_USER,
                RolesEnum.DEAL_SUPERUSER,
                RolesEnum.CONTRACTOR_RUS,
                RolesEnum.CONTRACTOR_SUPERUSER,
                RolesEnum.SUPERUSER,
                RolesEnum.ADMIN
        );

        Set<RolesEnum> actualRoles = EnumSet.allOf(RolesEnum.class);

        assertEquals(expectedRoles, actualRoles);
    }
}