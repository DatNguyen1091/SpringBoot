package com.example.demo.service;

import com.example.demo.automapping.AddressesMapper;
import com.example.demo.dto.request.AddressesRequest;
import com.example.demo.dto.request.RequestParameter;
import com.example.demo.dto.response.AddressesResponse;
import com.example.demo.entity.Addresses;
import com.example.demo.entity.CustomerAddresses;
import com.example.demo.repository.AddressesRepository;
import com.example.demo.repository.CustomerAddressesRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.specification.SpecificationsAddresses;
import com.example.demo.shared.ErrorMessage.ValidatorMessage;
import com.example.demo.shared.wrappers.Messages;
import com.example.demo.shared.wrappers.PaginatedResult;
import com.example.demo.shared.wrappers.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressesService {
    private static final Logger logger = LoggerFactory.getLogger(AddressesService.class);
    private final AddressesRepository _addressesRepository;
    private final CustomerAddressesRepository _customerAddressesRepository;
    private final AddressesMapper _addressesMapper;
    private final CurrentUserService _currentUserService;

    public AddressesService(AddressesRepository addressesRepository, CustomerAddressesRepository customerAddressesRepository,
                            AddressesMapper addressesMapper, CurrentUserService currentUserService){
        _addressesRepository = addressesRepository;
        _customerAddressesRepository = customerAddressesRepository;
        _addressesMapper = addressesMapper;
        _currentUserService = currentUserService;
    }

    public PaginatedResult<AddressesResponse> getAllAddresses(RequestParameter request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), request.getOrder());
        Specification<Addresses> spec = Specification.where(SpecificationsAddresses.isNotDeleted());

        Page<Addresses> page = _addressesRepository.findAll(spec, pageable);
        List<Addresses> Addresses = page.getContent();

        var result = _addressesMapper.toListAddressesResponse(Addresses);

        int totalRecord = (int)_addressesRepository.count(Specification.where(SpecificationsAddresses.isNotDeleted()));

        return PaginatedResult.Success(result, totalRecord, request.getPageNumber(), request.getPageSize());
    }

    public Result<AddressesResponse> getAddressesById(Long id) {
        var addresses = _addressesRepository.findById(id);
        if(addresses.isEmpty()){
            return Result.fail(new Messages(ValidatorMessage.SYS003I));
        }

        var result = _addressesMapper.toAddressesResponse(addresses.get());

        return Result.success(result);
    }

    @Transactional
    public Result<AddressesResponse> createCustomerAddresses(AddressesRequest request) {
        try {
            var userId = _currentUserService.getUserId();
            Addresses addresses = _addressesMapper.toAddresses(request);
            _addressesRepository.save(addresses);

            var customerAddresses = new CustomerAddresses();
            customerAddresses.setAddressId(addresses.getId());
            customerAddresses.setCustomerId(userId);
            _customerAddressesRepository.save(customerAddresses);

            AddressesResponse result = _addressesMapper.toAddressesResponse(addresses);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<AddressesResponse> createAddresses(AddressesRequest request) {
        try {
            Addresses addresses = _addressesMapper.toAddresses(request);
            _addressesRepository.save(addresses);

            AddressesResponse result = _addressesMapper.toAddressesResponse(addresses);

            return Result.success(result);
        } catch (Exception e) {
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<AddressesResponse> updateAddresses(Long id, AddressesRequest request) {
        try{
            var addresses = _addressesRepository.findById(id);
            if(addresses.isEmpty()){
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }

            _addressesMapper.updateAddresses(request, addresses.get());

            Addresses addressesUpdate = _addressesRepository.save(addresses.get());
            var result = _addressesMapper.toAddressesResponse(addressesUpdate);

            return Result.success(result);
        } catch (Exception e){
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }

    public Result<Boolean> deleteAddresses(Long id) {
        try {
            var addressesOpt = _addressesRepository.findById(id);

            if (addressesOpt.isEmpty()) {
                return Result.fail(new Messages(ValidatorMessage.SYS003I));
            }
            _addressesRepository.delete(addressesOpt.get());

            return Result.success(true);
        } catch (Exception e){
            logger.error("Error: ", e);
            return Result.fail(new Messages(ValidatorMessage.SYS008I));
        }
    }
}
