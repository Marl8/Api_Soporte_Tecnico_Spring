package com.utn.services;

import com.utn.dto.request.UserCompleteDto;
import com.utn.dto.request.UserDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseUserDto;
import com.utn.entity.Rol;
import com.utn.entity.UserEntity;
import com.utn.repository.RolRepository;
import com.utn.repository.UserRepository;
import com.utn.service.UserServiceImpl;
import com.utn.utils.RolObjectUtils;
import com.utn.utils.UserObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    RolRepository rolRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl service;


    @Test
    @DisplayName("test OK para guardar usuario")
    void guardarUserTestOk() {
        UserDto argumentSut = UserObjectUtils.userDto1();
        UserEntity user = UserObjectUtils.user1();
        Rol rol = RolObjectUtils.rol();
        ResponseUserDto expected = new ResponseUserDto(user.getNombre(), user.getApellido(),
                "Usuario guardado con éxito");

        when(repository.findByUsername(any())).thenReturn(Optional.empty());
        when(rolRepository.findRolByName(any())).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode(any())).thenReturn("$2a$12$rv6hPy.kQVhqwcnpvT5KSeI1r1Y.okGygjGRsCd.OiikVwmJn2qFS");
        when(repository.save(any())).thenReturn(user);

        ResponseUserDto actual = service.guardarUser(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para modificar un usuario")
    void modificarUsuarioTestOK(){
        Long id = 1L;
        UserCompleteDto argumentSut = UserObjectUtils.userCompleteDto();
        UserEntity user = UserObjectUtils.user1();
        UserEntity modificado = UserObjectUtils.user2();
        ResponseUserDto expected = new ResponseUserDto(modificado.getNombre(), modificado.getApellido(),
                "Usuario modificado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(any())).thenReturn("$2a$12$rv6hPy.kQVhqwcnpvT5KSeI1r1Y.okGygjGRsCd.OiikVwmJn2qFS");
        when(repository.save(any())).thenReturn(modificado);

        ResponseUserDto actual = service.modificarUser(argumentSut, id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar un usuario")
    void eliminarUserTestOk() {
        Long id = 1L;
        UserEntity user = UserObjectUtils.user1();
        ResponseDto expected = new ResponseDto("Usuario eliminado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(user));

        ResponseDto actual = service.deleteUser(id);

        assertEquals(expected, actual);
    }
}
