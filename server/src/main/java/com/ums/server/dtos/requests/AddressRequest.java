package com.ums.server.dtos.requests;

import com.ums.server.models.address.Country;
import com.ums.server.models.address.District;
import com.ums.server.models.address.State;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record AddressRequest(
        String buildingNo,
        String street,
        String city,
        District district,
        State state,
        Country country
)  {

    private static final Set<String> IGNORED_FIELDS = new HashSet<>(List.of(
//     For now all the fields can acceptable.
    ));

    public static boolean isFieldValid(String fieldName) {
        return false;
    }
}
