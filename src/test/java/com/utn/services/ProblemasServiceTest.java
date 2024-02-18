package com.utn.services;

import com.utn.dto.request.TipoProblemaCompleteDto;
import com.utn.dto.request.TipoProblemaDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseProblemaDto;
import com.utn.entity.TipoProblema;
import com.utn.repository.ProblemaRepository;
import com.utn.service.ProblemaServiceImpl;
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
public class ProblemasServiceTest {

    @Mock
    ProblemaRepository repository;

    @InjectMocks
    ProblemaServiceImpl service;

    @Test
    @DisplayName("test Ok para guardar un tipo de problema")
    void guardarProblemaTestOK() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TipoProblemaDto argumentSut = ProblemasObjectUtils.problemaDto();
        TipoProblema problema = ProblemasObjectUtils.problema2();
        List<TipoProblema> lista = new ArrayList<>();
        ResponseProblemaDto expected = new ResponseProblemaDto("Problema guardado con éxito.");
        Method mockeado = ProblemaServiceImpl.class.getDeclaredMethod("verificarSiExiste", TipoProblema.class);
        mockeado.setAccessible(true);

        when(mockeado.invoke(service, problema)).thenReturn(lista);
        when(repository.save(any())).thenReturn(problema);

        ResponseProblemaDto actual = service.guardar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para find un problema por id")
    void findProblemaPorIdTestOK(){
        Long id = 1L;
        TipoProblemaDto expected = ProblemasObjectUtils.problemaDto();
        TipoProblema problema = ProblemasObjectUtils.problema2();

        when(repository.findById(any())).thenReturn(Optional.of(problema));

        TipoProblemaDto actual = service.findProblema(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para modificar problemas")
    void modificarProblemaTestOK() {
        Long id = 1L;
        TipoProblemaCompleteDto argumentSut = ProblemasObjectUtils.problemaCompleteDto();
        TipoProblema problema = ProblemasObjectUtils.problema2();
        ResponseProblemaDto expected = new ResponseProblemaDto("Tipo de problema modificado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(problema));
        when(repository.save(any())).thenReturn(problema);

        ResponseProblemaDto actual = service.modificar(argumentSut, id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar problema")
    void eliminarProblemaTestOK() {
        Long id = 1L;
        TipoProblema problema = ProblemasObjectUtils.problema2();
        ResponseDto expected = new ResponseDto("Problema eliminado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(problema));

        ResponseDto actual = service.eliminar(id);

        assertEquals(expected, actual);
    }
}
