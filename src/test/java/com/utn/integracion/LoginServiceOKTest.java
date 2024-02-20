package com.utn.integracion;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.utn.dto.request.LoginDto;
import com.utn.entity.ERol;
import com.utn.entity.Rol;
import com.utn.entity.UserEntity;
import com.utn.service.securityServices.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.util.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
public class LoginServiceOKTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Test
    void loginServicesOktest() throws Exception {

        Rol rol = new Rol(1L, ERol.ADMIN);
        Set<Rol> roles = new HashSet<>();
        roles.add(rol);

        UserEntity usuario = new UserEntity();
        usuario.setNombre("Victor");
        usuario.setApellido("Sosa");
        usuario.setUsername("userPrueba");
        usuario.setPassword(passwordEncoder.encode("12345"));
        usuario.setRoles(roles);

        LoginDto dto = new LoginDto();
        dto.setUsername("userPrueba");
        dto.setPassword("12345");

        ObjectMapper obMapper = new ObjectMapper();
        obMapper.registerModule(new JavaTimeModule());
        ObjectWriter mapper = obMapper
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .writer();

        String payload = mapper.writeValueAsString(dto);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", usuario.getNombre() + " " + usuario.getApellido());
        extraClaims.put("roles", usuario.getRoles().stream()
                .map(r -> r.getName().name()).toList());
        String token = jwtService.generate(usuario, extraClaims);

        MvcResult response = mockMvc.perform(post("/v1/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .header("Authorization", "Bearer " + token))
                .andDo(print()) // imprime por consola el request y él response
                .andExpect(status().isOk())
                .andReturn();
    }
}
