package com.utn.dto.request;

import com.utn.entity.ERol;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolDto {

    @NotNull(message = "Name no puede ser Null")
    private ERol name;
}
