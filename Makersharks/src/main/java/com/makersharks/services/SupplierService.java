package com.makersharks.services;

import com.makersharks.dto.SupplierCreateDTO;
import com.makersharks.entities.Supplier;
import com.makersharks.entities.NatureOfBusiness;
import com.makersharks.entities.ManufacturingProcess;
import com.makersharks.exception.exception.ResourceNotFoundException;
import com.makersharks.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(SupplierCreateDTO supplierCreateDTO) {
        Supplier supplier = new Supplier();
        supplier.setCompanyName(supplierCreateDTO.getCompanyName());
        supplier.setWebsite(supplierCreateDTO.getWebsite());
        supplier.setLocation(supplierCreateDTO.getLocation());
        supplier.setNatureOfBusiness(NatureOfBusiness.valueOf(supplierCreateDTO.getNatureOfBusiness().toUpperCase()));
        supplier.setManufacturingProcesses(ManufacturingProcess.valueOf(supplierCreateDTO.getManufacturingProcesses().toUpperCase()));

        return supplierRepository.save(supplier);

    }
    public List<SupplierCreateDTO> getSuppliersByCriteria(String location, String natureOfBusiness, String manufacturingProcess,
                                                 int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        NatureOfBusiness natureOfBusinessEnum = NatureOfBusiness.valueOf(natureOfBusiness.toUpperCase());
        ManufacturingProcess manufacturingProcessEnum = ManufacturingProcess.valueOf(manufacturingProcess.toUpperCase());

        Page<Supplier> suppliersPage = supplierRepository.findByLocationAndNatureOfBusinessAndManufacturingProcesses(
                location, natureOfBusinessEnum, manufacturingProcessEnum, pageable);

        List<Supplier> supplierList = suppliersPage.getContent();
        List<SupplierCreateDTO> collect = supplierList.stream().map(supplier -> toDto(supplier)).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            return collect;
        } else {
            throw new ResourceNotFoundException("Supplier", "given location", location);
        }
    }


    public SupplierCreateDTO updateSupplier(SupplierCreateDTO supplierCreateDTO,Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new ResourceNotFoundException("supplier", "given id","supplierId"));
        supplier.setCompanyName(supplierCreateDTO.getCompanyName());
        supplier.setLocation(supplierCreateDTO.getLocation());
        supplier.setWebsite(supplierCreateDTO.getWebsite());
        supplier.setNatureOfBusiness(NatureOfBusiness.valueOf(supplierCreateDTO.getNatureOfBusiness().toUpperCase()));
        supplier.setManufacturingProcesses(ManufacturingProcess.valueOf(supplierCreateDTO.getManufacturingProcesses().toUpperCase()));
        Supplier save = supplierRepository.save(supplier);
        SupplierCreateDTO dto = toDto(save);
        return dto;

    }

    private SupplierCreateDTO toDto(Supplier supplier) {
        SupplierCreateDTO dto=new SupplierCreateDTO();
        dto.setSupplierId(supplier.getSupplierId());
        dto.setCompanyName(supplier.getCompanyName());
        dto.setWebsite(supplier.getWebsite());
        dto.setLocation(supplier.getLocation());
        dto.setNatureOfBusiness(String.valueOf(supplier.getNatureOfBusiness()));
        dto.setManufacturingProcesses(String.valueOf(supplier.getManufacturingProcesses()));
        return dto;
    }

    public String deleteSupplier(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new ResourceNotFoundException("supplier", "given id", "supplierId"));

        supplierRepository.deleteById(supplierId);
        return "deleted successfully";
    }

    public List<SupplierCreateDTO> getAllSuppliers() {
        List<Supplier> all = supplierRepository.findAll();
        return all.stream().map(supplier -> toDto(supplier)).collect(Collectors.toList());
    }
}
