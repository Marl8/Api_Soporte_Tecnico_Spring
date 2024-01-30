package com.utn.services;

import com.utn.dto.request.ServicioDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseServicioDto;
import com.utn.entity.Servicio;
import com.utn.repository.ServicioRepository;
import com.utn.service.ServicioServiceImpl;
import com.utn.utils.ServicioObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ServicioServiceTest {

    @Mock
    ServicioRepository repository;

    @InjectMocks
    ServicioServiceImpl service;

    @Test
    @DisplayName("test OK para guardar servicio")
    void guardarServicioTestOK() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Servicio servicio = ServicioObjectUtils.servicio();
        ServicioDto argumentSut = ServicioObjectUtils.servicioDto();
        List<Servicio> lista = new ArrayList<>();
        ResponseServicioDto expected = new ResponseServicioDto(servicio.getTipoServicio(), servicio.getDescripcion(),
                "Servicio guardado con éxito.");
        Method mockeado = ServicioServiceImpl.class.getDeclaredMethod("verificarSiExiste", Servicio.class);
        mockeado.setAccessible(true);

        when(mockeado.invoke(service, servicio)).thenReturn(lista);
        when(repository.save(any())).thenReturn(servicio);

        ResponseServicioDto actual = service.guardarServicio(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para find servicio by id")
    void servicioFindByIdTestOK() {
        Long id = 1L;
        Servicio servicio = ServicioObjectUtils.servicio();
        ServicioDto expected = ServicioObjectUtils.servicioDto();

        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(servicio));

        ServicioDto actual = service.findServicio(id);

        assertEquals(expected.getTipoServicio(), actual.getTipoServicio());
        assertEquals(expected.getDescripcion(), actual.getDescripcion());
    }


    @Test
    @DisplayName("test OK para find all servicios")
    void buscarTodosServicioTestOK() {
        List<Servicio> listaServicios = ServicioObjectUtils.listaServicios();
        Set<ServicioDto> expected = ServicioObjectUtils.listaSetServicios();

        when(repository.findAll()).thenReturn(listaServicios);

        Set<ServicioDto> actual = service.findAll();

        assertEquals(expected, actual);
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @DisplayName("test OK para modificar servicio")
    void modificarServicioTestOK() {
        Servicio servicio = ServicioObjectUtils.servicio();
        Servicio modificado = ServicioObjectUtils.servicio2();
        ServicioDto argumentSut = ServicioObjectUtils.servicioDto2();
        ResponseServicioDto expected = new ResponseServicioDto(modificado.getTipoServicio(), modificado.getDescripcion(),
                "Servicio modificado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(servicio));
        when(repository.save(any())).thenReturn(modificado);

        ResponseServicioDto actual = service.modificar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar un servicio")
    void eliminarServicioTestOK() {
        Long id = 1L;
        Servicio servicio = ServicioObjectUtils.servicio();
        ResponseDto expected = new ResponseDto("Servicio eliminado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(servicio));

        ResponseDto actual = service.eliminar(id);

        assertEquals(expected, actual);
    }
}
