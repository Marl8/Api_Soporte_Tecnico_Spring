package com.utn.services;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.request.OperadorUpdateDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseOperadorDto;
import com.utn.entity.Incidente;
import com.utn.entity.Operador;
import com.utn.entity.Tecnico;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.OperadorRepository;
import com.utn.repository.TecnicoRepository;
import com.utn.service.OperadorServiceImpl;
import com.utn.utils.IncidenteObjectsUtils;
import com.utn.utils.OperadorObjectUtils;
import com.utn.utils.TecnicoObjectsUtils;
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
public class OperadorServiceTest {

    @Mock
    OperadorRepository repository;

    @Mock
    IncidenteRepository incidenteRepository;

    @Mock
    TecnicoRepository tecnicoRepository;

    @InjectMocks
    OperadorServiceImpl servicio;



    @Test
    @DisplayName("test OK para guardar operador")
    void guardarOperadorTestOK() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        OperadorDto argumentSut = OperadorObjectUtils.operadorDto();
        Operador operador = OperadorObjectUtils.operador();
        List<Operador> list = new ArrayList<>();
        ResponseOperadorDto expected = new ResponseOperadorDto(operador.getNombre(), operador.getApellido(), "Operador guardado con éxito.");

        Method mockeado = OperadorServiceImpl.class.getDeclaredMethod("verificarSiExiste", Operador.class);
        mockeado.setAccessible(true);

        when(mockeado.invoke(servicio, operador)).thenReturn(list);
        when(repository.save(any())).thenReturn(operador);

        ResponseOperadorDto actual = servicio.guardar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para buscar Operador por id")
    void findOperadorTestOK(){
        Operador operador = OperadorObjectUtils.operador();
        OperadorDto expected = OperadorObjectUtils.operadorDto();
        Long id = 1L;

        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(operador));

        OperadorDto actual = servicio.findOperador(id);

        assertEquals(expected.getNombre(), actual.getNombre());
        assertEquals(expected.getApellido(), actual.getApellido());
    }

    @Test
    @DisplayName("test OK para modificar operador")
    void modificarOperadorTestOK() {
        Long id = 1L;
        Operador operador = OperadorObjectUtils.operador();
        OperadorUpdateDto argumentSut = OperadorObjectUtils.operadorUpdateDto();
        Operador modificado = OperadorObjectUtils.operador2();
        ResponseOperadorDto expected = new ResponseOperadorDto(modificado.getNombre(), modificado.getApellido(), "Operador modificado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(operador));
        when(repository.save(any())).thenReturn(modificado);

        ResponseOperadorDto actual = servicio.modificar(argumentSut, id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar un operador")
    void eliminarOperadorTestOk() {
        Operador operador = OperadorObjectUtils.operador();
        ResponseDto expected = new ResponseDto("Operador eliminado con éxito");
        Long id = 1L;

        when(repository.findById(any())).thenReturn(Optional.of(operador));

        ResponseDto actual = servicio.eliminar(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test Ok para asignar técnico")
    void asignarTecnicoTestOK() {
        Long id = 1L;
        Incidente incidente = IncidenteObjectsUtils.incidente();
        List<Operador> listaOperadores = OperadorObjectUtils.listaOperadores();
        List<Tecnico> listaTecnicos = TecnicoObjectsUtils.listaTecnicos();
        ResponseDto expected = new ResponseDto("Se ha asignado el técnico con éxito");

        when(incidenteRepository.findById(any())).thenReturn(Optional.of(incidente));
        when(repository.findAll()).thenReturn(listaOperadores);
        when(tecnicoRepository.findAll()).thenReturn(listaTecnicos);
        when(incidenteRepository.save(any())).thenReturn(incidente);

        ResponseDto actual = servicio.asignarTecnico(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para asignar horas colchon")
    public void asignarHorasColchonTestOk() {
        int horas = 12;
        Long id = 1L;
        Incidente incidente = IncidenteObjectsUtils.incidente();
        ResponseDto expected = new ResponseDto("Tiempo de resolución ampliado con éxito");

        when(incidenteRepository.findById(any())).thenReturn(Optional.of(incidente));
        when(incidenteRepository.save(any())).thenReturn(incidente);

        ResponseDto actual = servicio.asignarHorasColchon(id, horas);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para asignar la lista de técnicos al operador")
    void asignarListaTecnicosAlOperadorTestOK(){
        Long id = 1L;
        Operador operador = OperadorObjectUtils.operador();
        List<Tecnico> listaTecnicos = TecnicoObjectsUtils.listaTecnicos();
        ResponseDto expected = new ResponseDto("Se ha asignado con éxito la lista de técnicos");

        when(repository.findById(any())).thenReturn(Optional.of(operador));
        when(tecnicoRepository.findAll()).thenReturn(listaTecnicos);
        when(repository.save(any())).thenReturn(operador);

        ResponseDto actual = servicio.asignarListaTecnico(id);

        assertEquals(expected, actual);
    }
}
