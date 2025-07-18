package com.example.demo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressesResponse {
    String addressLine1;

    String addressLine2;

    String city;

    String state;

    String Country;

    int addressType;

    String zipCode;
}
