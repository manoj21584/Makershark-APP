package com.makersharks.services;

import com.makersharks.dto.SupplierCreateDTO;
import com.makersharks.entities.*;
import com.makersharks.exception.exception.ResourceNotFoundException;
import com.makersharks.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SupplierServiceTest {


    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testCreateSupplier() {
        SupplierCreateDTO dto = new SupplierCreateDTO();
        dto.setCompanyName("ABC Corp");
        dto.setWebsite("http://abc.com");
        dto.setLocation("India");
        dto.setNatureOfBusiness("small_scale");
        dto.setManufacturingProcesses("three_d_printing");

        Supplier supplier = new Supplier();
        supplier.setCompanyName(dto.getCompanyName());
        supplier.setWebsite(dto.getWebsite());
        supplier.setLocation(dto.getLocation());
        supplier.setNatureOfBusiness(NatureOfBusiness.SMALL_SCALE);
        supplier.setManufacturingProcesses(ManufacturingProcess.THREE_D_PRINTING);


        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        Supplier createdSupplier = supplierService.createSupplier(dto);

        assertNotNull(createdSupplier);
        assertEquals("ABC Corp", createdSupplier.getCompanyName());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testGetSuppliersByCriteria() {
        String location = "India";
        String natureOfBusiness = "small_scale";
        String manufacturingProcess = "three_d_printing";
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "companyName";
        String sortDir = "asc";

        Supplier supplier1 = new Supplier();
        supplier1.setCompanyName("ABC Corp");
        supplier1.setLocation(location);
        supplier1.setNatureOfBusiness(NatureOfBusiness.SMALL_SCALE);
        supplier1.setManufacturingProcesses(ManufacturingProcess.THREE_D_PRINTING);

        Supplier supplier2 = new Supplier();
        supplier2.setCompanyName("XYZ Ltd");
        supplier2.setLocation(location);
        supplier2.setNatureOfBusiness(NatureOfBusiness.SMALL_SCALE);
        supplier2.setManufacturingProcesses(ManufacturingProcess.THREE_D_PRINTING);

        List<Supplier> supplierList = Arrays.asList(supplier1, supplier2);

        Page<Supplier> supplierPage = new PageImpl<>(supplierList);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));

        when(supplierRepository.findByLocationAndNatureOfBusinessAndManufacturingProcesses(
                eq(location), eq(NatureOfBusiness.SMALL_SCALE), eq(ManufacturingProcess.THREE_D_PRINTING), eq(pageable)
        )).thenReturn(supplierPage);

        List<SupplierCreateDTO> foundSuppliers = supplierService.getSuppliersByCriteria(
                location, natureOfBusiness, manufacturingProcess, pageNo, pageSize, sortBy, sortDir
        );

        assertEquals(2, foundSuppliers.size());
        assertEquals("ABC Corp", foundSuppliers.get(0).getCompanyName());
        verify(supplierRepository, times(1)).findByLocationAndNatureOfBusinessAndManufacturingProcesses(
                eq(location), eq(NatureOfBusiness.SMALL_SCALE), eq(ManufacturingProcess.THREE_D_PRINTING), eq(pageable)
        );
    }

    @Test
    void testGetSuppliersByCriteria_ResourceNotFound() {
        String location = "Unknown";
        String natureOfBusiness = "small_scale";
        String manufacturingProcess = "three_d_printing";
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "companyName";
        String sortDir = "asc";

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<Supplier> emptyPage = Page.empty();

        when(supplierRepository.findByLocationAndNatureOfBusinessAndManufacturingProcesses(
                eq(location), eq(NatureOfBusiness.SMALL_SCALE), eq(ManufacturingProcess.THREE_D_PRINTING), eq(pageable)
        )).thenReturn(emptyPage);

        assertThrows(ResourceNotFoundException.class, () -> supplierService.getSuppliersByCriteria(
                location, natureOfBusiness, manufacturingProcess, pageNo, pageSize, sortBy, sortDir
        ));
    }
    @Test
    void testUpdateSupplier() {
        Supplier supplier = getSupplier();
        SupplierCreateDTO supplierCreateDTO =getSupplierDto();
        Long supplierId = 1L;
        when(supplierRepository.findById(supplierId)).thenReturn(Optional.of(supplier));

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setSupplierId(1L);
        updatedSupplier.setCompanyName(supplierCreateDTO.getCompanyName());
        updatedSupplier.setWebsite(supplierCreateDTO.getWebsite());
        updatedSupplier.setLocation(supplierCreateDTO.getLocation());
        updatedSupplier.setNatureOfBusiness(NatureOfBusiness.SMALL_SCALE);
        updatedSupplier.setManufacturingProcesses(ManufacturingProcess.THREE_D_PRINTING);

        when(supplierRepository.save(any(Supplier.class))).thenReturn(updatedSupplier);

        SupplierCreateDTO updatedSupplierDTO = supplierService.updateSupplier(supplierCreateDTO, supplierId);

        assertEquals(supplierCreateDTO.getCompanyName(), updatedSupplierDTO.getCompanyName());
        assertEquals(supplierCreateDTO.getWebsite(), updatedSupplierDTO.getWebsite());
        assertEquals(supplierCreateDTO.getLocation(), updatedSupplierDTO.getLocation());
        assertEquals(supplierCreateDTO.getNatureOfBusiness().toUpperCase(), updatedSupplierDTO.getNatureOfBusiness());
        assertEquals(supplierCreateDTO.getManufacturingProcesses().toUpperCase(), updatedSupplierDTO.getManufacturingProcesses());

        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testDeleteSupplierSuccess(){
        Long supplierId=1L;
        Supplier supplier = getSupplier();

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        doNothing().when(supplierRepository).deleteById(anyLong());

        String result = supplierService.deleteSupplier(supplierId);
        assertEquals("deleted successfully", result);

        verify(supplierRepository, times(1)).findById(supplierId);
        verify(supplierRepository, times(1)).deleteById(supplierId);
    }
    @Test
    void testGetAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(getSupplier());

        when(supplierRepository.findAll()).thenReturn(suppliers);
        List<SupplierCreateDTO> allSuppliers = supplierService.getAllSuppliers();

        assertNotNull(allSuppliers);
        assertEquals(1, allSuppliers.size());
        assertEquals(suppliers.get(0).getSupplierId(), allSuppliers.get(0).getSupplierId());
        assertEquals(suppliers.get(0).getCompanyName(), allSuppliers.get(0).getCompanyName());

        verify(supplierRepository, times(1)).findAll();
    }

    private Supplier getSupplier(){
        Supplier supplier = new Supplier();
        supplier.setSupplierId(1L);
        supplier.setCompanyName("Old Company");
        supplier.setWebsite("http://oldwebsite.com");
        supplier.setLocation("Old Location");
        supplier.setNatureOfBusiness(NatureOfBusiness.LARGE_SCALE);
        supplier.setManufacturingProcesses(ManufacturingProcess.MOULDING);
        return supplier;
    }
    private SupplierCreateDTO getSupplierDto(){
        SupplierCreateDTO supplierCreateDTO = new SupplierCreateDTO();
        supplierCreateDTO.setCompanyName("Updated Company");
        supplierCreateDTO.setWebsite("http://updatedwebsite.com");
        supplierCreateDTO.setLocation("Updated Location");
        supplierCreateDTO.setNatureOfBusiness("SMALL_SCALE");
        supplierCreateDTO.setManufacturingProcesses("THREE_D_PRINTING");
        return supplierCreateDTO;
    }

}
