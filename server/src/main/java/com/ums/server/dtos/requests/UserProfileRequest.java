package com.ums.server.dtos.requests;

import com.ums.server.models.Gender;
import com.ums.server.models.address.CountryCode;
import com.ums.server.utils.FieldValidator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Add validation checks on the defined fields.
public record UserProfileRequest(
        String firstName,
        String middleName,
        String lastName,
        String password,
        Gender gender,
        CountryCode phoneCountry,
        String phone,
        CountryCode alternatePhoneCountry,
        String alternatePhone,
        AddressRequest currentAddress,
        Boolean isPermanentAddressEqual,
        AddressRequest permanentAddress
){

    private static final Set<String> ignoredFields = new HashSet<>(
            List.of(
                    "currentAddress",
                    "isPermanentAddressEqual",
                    "permanentAddress"
            )
    );

    public static boolean isFieldValid(String fieldName) {
        return ignoredFields.contains(fieldName);
    }
}
