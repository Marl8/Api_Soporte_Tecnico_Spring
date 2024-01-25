package com.utn.services;

import com.utn.dto.request.EspecialidadDto;
import com.utn.dto.request.EspecialidadFindDto;
import com.utn.dto.request.EspecialidadUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseEspecialidadDto;
import com.utn.entity.Especialidad;
import com.utn.entity.TipoProblema;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.ProblemaRepository;
import com.utn.service.EspecialidadServiceImpl;
import com.utn.utils.EspecialidadObjectUtils;
import com.utn.utils.ProblemasObjectUtils;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EspecialidadesServiceTest {

    @Mock
    EspecialidadRepository repository;

    @Mock
    ProblemaRepository problemaRepository;

    @InjectMocks
    EspecialidadServiceImpl service;

    @Test
    @DisplayName("test OK para guardar una especialidad")
    void guardarEspecialidadTestOK() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        EspecialidadDto argumentSut = EspecialidadObjectUtils.especialidadDto();
        Especialidad especialidad = EspecialidadObjectUtils.especialidad();
        TipoProblema problema = ProblemasObjectUtils.problema();
        List<Especialidad> lista = new ArrayList<>();
        ResponseEspecialidadDto expected = new ResponseEspecialidadDto(especialidad.getNombre(), especialidad.getDescripcion(),
                "Especialidad guardada con éxito");

        Method mockeado = EspecialidadServiceImpl.class.getDeclaredMethod("verificarSiExiste", Especialidad.class);
        mockeado.setAccessible(true);

        when(mockeado.invoke(service,especialidad)).thenReturn(lista);
        when(problemaRepository.findById(any())).thenReturn(Optional.of(problema));
        when(repository.save(any())).thenReturn(especialidad);

        ResponseEspecialidadDto actual = service.guardar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para asignar un problema a una especialidad")
    void asignarProblemaTestOK(){
        Long id1 = 1L;
        Long id2 = 1L;
        Especialidad especialidad = EspecialidadObjectUtils.especialidad();
        TipoProblema problema = ProblemasObjectUtils.problema();
        ResponseDto expected = new ResponseDto("Asignación realizada con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(especialidad));
        when(problemaRepository.findById(any())).thenReturn(Optional.of(problema));
        when(repository.save(any())).thenReturn(especialidad);

        ResponseDto actual = service.asignarProblema(id1, id2);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para findEspecialidad")
    void findEspecialidadTestOK(){
        EspecialidadFindDto expected = EspecialidadObjectUtils.especialidadFindDto();
        Long id = 1L;
        Especialidad especialidad = EspecialidadObjectUtils.especialidad();

        when(repository.findById(any())).thenReturn(Optional.of(especialidad));

        EspecialidadFindDto actual = service.findEspecialidad(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para  modificar especialidad")
    void modificarEspecialidadTestOk(){
        Especialidad especialidad = EspecialidadObjectUtils.especialidad();
        Especialidad modificado = EspecialidadObjectUtils.especialidad2();
        EspecialidadUpdateDto argumentSut = EspecialidadObjectUtils.especialidadUpdateDto();
        ResponseEspecialidadDto expected = new ResponseEspecialidadDto(modificado.getNombre(),
                modificado.getDescripcion(), "Especialidad modificada con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(especialidad));
        when(repository.save(any())).thenReturn(modificado);

        ResponseEspecialidadDto actual = service.modificar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar una especialidad")
    void eliminarEspecialidadTestOK(){
        Long id = 1L;
        Especialidad especialidad = EspecialidadObjectUtils.especialidad();
        ResponseDto expected = new ResponseDto("Especialidad eliminada con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(especialidad));

        ResponseDto actual = service.eliminar(id);

        assertEquals(expected, actual);
    }
}
