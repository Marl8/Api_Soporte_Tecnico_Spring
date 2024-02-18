package com.utn.services;

import com.utn.dto.request.RolDto;
import com.utn.dto.response.ResponseRolDto;
import com.utn.entity.Rol;
import com.utn.entity.UserEntity;
import com.utn.exception.RolGenericException;
import com.utn.repository.RolRepository;
import com.utn.repository.UserRepository;
import com.utn.service.RolServiceImpl;
import com.utn.utils.RolObjectUtils;
import com.utn.utils.UserObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RolServiceTest {

    @Mock
    RolRepository repository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RolServiceImpl service;

    @Test
    @DisplayName("test Ok para save rol")
    void guardarRolTesOK(){
        RolDto argumentSut = RolObjectUtils.rolDto();
        List<Rol> listaRoles = RolObjectUtils.listaRoles();
        Rol rol = RolObjectUtils.rol();
        ResponseRolDto expected = new ResponseRolDto("Rol guardado con éxito");

        when(repository.findAll()).thenReturn(listaRoles);
        when(repository.save(any())).thenReturn(rol);

        ResponseRolDto actual = service.save(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test FAIL ya existe el rol que se pretende guardar")
    void guardarRolTestFAIL() {
        RolDto argumentSut = RolObjectUtils.rolDto();
        List<Rol> listaRoles = RolObjectUtils.listaRoles2();
        RolGenericException expected = new RolGenericException("Role " + argumentSut.getName() + " ya existe", HttpStatus.BAD_REQUEST);

        when(repository.findAll()).thenReturn(listaRoles);

        RolGenericException actual = assertThrows(RolGenericException.class, () -> service.save(argumentSut));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para asignar roles")
    void asignarRolesTestOk() {
        Long idRol = 1L;
        Long idUser = 2L;
        Rol rol = RolObjectUtils.rol2();
        UserEntity user = UserObjectUtils.user3();
        ResponseRolDto expected = new ResponseRolDto("Rol asignado con éxito");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(repository.findById(any())).thenReturn(Optional.of(rol));
        when(userRepository.save(any())).thenReturn(user);

        ResponseRolDto actual = service.asignarRol(idRol, idUser);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para findAll roles")
    void buscarTodosLosRolesTestOK(){
        List<Rol> lista = RolObjectUtils.listaRoles3();
        List<RolDto> expected = RolObjectUtils.listaRolesDto();

        when(repository.findAll()).thenReturn(lista);

        List<RolDto> actual = service.findAll();

        assertEquals(expected, actual);
    }
}
