package com.utn.services;

import com.utn.dto.request.IncidenteCompleteDto;
import com.utn.dto.request.IncidenteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseIncidenteDto;
import com.utn.entity.Cliente;
import com.utn.entity.Incidente;
import com.utn.entity.Servicio;
import com.utn.entity.TipoProblema;
import com.utn.repository.ClienteRepository;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.ProblemaRepository;
import com.utn.repository.ServicioRepository;
import com.utn.service.IncidenteServiceImpl;
import com.utn.utils.ClienteObjectUtils;
import com.utn.utils.IncidenteObjectsUtils;
import com.utn.utils.ProblemasObjectUtils;
import com.utn.utils.ServicioObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class IncidenteServiceTest {

    @Mock
    IncidenteRepository repository;

    @Mock
    ClienteRepository clienteRepository;

    @Mock
    ServicioRepository servicioRepository;

    @Mock
    ProblemaRepository problemaRepository;

    @InjectMocks
    IncidenteServiceImpl service;

    @Test
    @DisplayName("test OK para guardar incidente")
    void guardarIncidenteTestOK() {
        IncidenteDto argumentSut = IncidenteObjectsUtils.incidenteDto();
        Cliente cliente = ClienteObjectUtils.cliente();
        Servicio servicio = ServicioObjectUtils.servicio();
        TipoProblema problema = ProblemasObjectUtils.problema();
        Incidente incidente = IncidenteObjectsUtils.incidente();
        ResponseIncidenteDto expected = new ResponseIncidenteDto(incidente.getDescripcion(), "Incidente guardado con éxito.");

        when(clienteRepository.findById(any())).thenReturn(Optional.of(cliente));
        when(servicioRepository.findById(any())).thenReturn(Optional.of(servicio));
        when(problemaRepository.findById(any())).thenReturn(Optional.of(problema));
        when(repository.save(any())).thenReturn(incidente);

        ResponseIncidenteDto actual = service.guardar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test Ok para buscar incidente por id")
    void buscarIncidentePorIdTestOK() {
        Long id = 1L;
        Incidente incidente = IncidenteObjectsUtils.incidente();
        IncidenteCompleteDto expected = IncidenteObjectsUtils.incidenteCompleteDto();

        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(incidente));

        IncidenteCompleteDto actual = service.findIncidente(id);

        assertEquals(expected.getDescripcion(), actual.getDescripcion());
        assertEquals(expected.getCliente(), actual.getCliente());
        assertEquals(expected.getTiempoResolucion(), actual.getTiempoResolucion());
    }

    @Test
    @DisplayName("test OK para modificar incidente")
    void modificarIncidenteTestOK() {
        Incidente incidente = IncidenteObjectsUtils.incidente();
        Incidente modificado = IncidenteObjectsUtils.incidente2();
        IncidenteCompleteDto argumentSut = IncidenteObjectsUtils.incidenteCompleteDto2();
        ResponseIncidenteDto expected = new ResponseIncidenteDto(modificado.getDescripcion(), "Incidente modificado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(incidente));
        when(repository.save(any())).thenReturn(modificado);

        ResponseIncidenteDto actual = service.modificar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar un incidente")
    void eliminarIncidenteTestOk() {
        Long id = 1L;
        Incidente incidente = IncidenteObjectsUtils.incidente();
        ResponseDto expected = new ResponseDto("Servicio eliminado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(incidente));

        ResponseDto actual = service.eliminar(id);

        assertEquals(expected, actual);
    }
}
