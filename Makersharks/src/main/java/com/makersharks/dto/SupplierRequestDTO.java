package com.makersharks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SupplierRequestDTO {
    @NotBlank(message = "location name cannot be blank ")
    @Pattern(regexp = "^[\\S]+$", message = "location  must not contain any whitespace")
    private String location;
    @NotBlank(message = "natureOfBusiness  cannot be blank ")
    @Pattern(regexp = "^[\\S]+$", message = "natureOfBusiness  must not contain any whitespace")
    private String natureOfBusiness;
    @NotBlank(message = "manufacturingProcess  cannot be blank ")
    @Pattern(regexp = "^[\\S]+$", message = "manufacturingProcess  must not contain any whitespace")
    private String manufacturingProcess;
}
