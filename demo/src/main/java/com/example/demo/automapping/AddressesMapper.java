package com.example.demo.automapping;

import com.example.demo.dto.request.AddressesRequest;
import com.example.demo.dto.response.AddressesResponse;
import com.example.demo.entity.Addresses;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressesMapper {
    Addresses toAddresses(AddressesRequest request);

    AddressesResponse toAddressesResponse(Addresses addresses);

    List<AddressesResponse> toListAddressesResponse(List<Addresses> addresses);

    void updateAddresses(AddressesRequest request, @MappingTarget Addresses address);
}
