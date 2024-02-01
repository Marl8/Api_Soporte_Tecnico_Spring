package com.utn.services;

import com.utn.dto.request.TecnicoCompleteDto;
import com.utn.dto.request.TecnicoDto;
import com.utn.dto.request.TecnicoFindDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseTecnicoDto;
import com.utn.entity.Especialidad;
import com.utn.entity.Incidente;
import com.utn.entity.Tecnico;
import com.utn.repository.EspecialidadRepository;
import com.utn.repository.IncidenteRepository;
import com.utn.repository.TecnicoRepository;
import com.utn.service.TecnicoServiceImpl;
import com.utn.utils.EspecialidadObjectUtils;
import com.utn.utils.IncidenteObjectsUtils;
import com.utn.utils.TecnicoObjectsUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TecnicoServiceTest {

    @Mock
    TecnicoRepository repository;

    @Mock
    EspecialidadRepository especialidadRepository;

    @Mock
    IncidenteRepository incidenteRepository;

    @InjectMocks
    TecnicoServiceImpl service;

    @Test
    @DisplayName("test OK para guardar técnico")
    void guardarTecnicoTestOK() {
        TecnicoDto tecnicoDto = TecnicoObjectsUtils.tecnicoDto();
        Tecnico tecnico = TecnicoObjectsUtils.tecnico1();
        Especialidad especialidad = EspecialidadObjectUtils.especialidad();
        ResponseTecnicoDto expected = new ResponseTecnicoDto("El técnico " + tecnico.getNombre() +
                " " + tecnico.getApellido() + " fue guardado con éxito");

        when(repository.findTecnicoByNombreAndApellido(any(), any())).thenReturn(Optional.empty());
        when(especialidadRepository.findById(any())).thenReturn(Optional.of(especialidad));
        when(repository.save(any())).thenReturn(tecnico);

        ResponseTecnicoDto actual = service.guardar(tecnicoDto);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para asignar especialidades al técnico")
    void asignarEspecialidadTestOK() {
        Long id1 = 1L;
        Long id2 = 1L;
        Tecnico tecnico = TecnicoObjectsUtils.tecnico1();
        Especialidad esp = EspecialidadObjectUtils.especialidad();
        ResponseDto expected = new ResponseDto("Asignación realizada con éxito");

        when(especialidadRepository.findById(any())).thenReturn(Optional.of(esp));
        when(repository.findById(any())).thenReturn(Optional.of(tecnico));
        when(repository.save(any())).thenReturn(tecnico);

        ResponseDto actual = service.asignarEspecialidadATecnico(id1,id2);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para buscar técnico por id")
    void findTecnicoPorIdTestOK() {
        Tecnico tecnico = TecnicoObjectsUtils.tecnico1();
        TecnicoCompleteDto expected = TecnicoObjectsUtils.tecnicoCompleteDto();
        Long id = 1L;

        when(repository.existsById(any())).thenReturn(true);
        when(repository.findById(any())).thenReturn(Optional.of(tecnico));

        TecnicoCompleteDto actual = service.findTecnico(id);

        assertEquals(expected.getNombre(), actual.getNombre());
        assertEquals(expected.getApellido(), actual.getApellido());
    }

    @Test
    @DisplayName("test OK para find all técnicos")
    void findAllTecnicosTestOk() {
        Set<TecnicoFindDto> expected = TecnicoObjectsUtils.listaTecnicoFindDto();
        List<Tecnico> lista = TecnicoObjectsUtils.listaTecnicos();

        when(repository.findAll()).thenReturn(lista);

        Set<TecnicoFindDto> actual = service.findAll();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para modificar técnico")
    void modificarTecnicoTestOK() {
        TecnicoCompleteDto argumentSut = TecnicoObjectsUtils.tecnicoCompleteDto();
        Tecnico tecnico = TecnicoObjectsUtils.tecnico1();
        Tecnico modificado = TecnicoObjectsUtils.tecnico2();
        ResponseTecnicoDto expected = new ResponseTecnicoDto("El técnico " + modificado.getNombre() + " " +
                modificado.getApellido() + " fue modificado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(tecnico));
        when(repository.save(any())).thenReturn(modificado);

        ResponseTecnicoDto actual = service.modificar(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para eliminar un técnico")
    void eliminarTecnicoTestOK() {
        Tecnico tecnico = TecnicoObjectsUtils.tecnico1();
        Long id = 1L;
        ResponseDto expected = new ResponseDto("Técnico eliminado con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(tecnico));

        ResponseDto actual = service.eliminar(id);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test OK para Técnico con más incidentes resueltos en los últimos X dias")
    void tecnicoConMasIncidientesResuletosXDiaTestOK() {
        long dias = 4;
        List<Incidente> listaIncidentes = IncidenteObjectsUtils.listaIncidentes();
        Tecnico tecnico = TecnicoObjectsUtils.tecnico2();
        ResponseTecnicoDto expected = new ResponseTecnicoDto("El Técnico con más incidentes resueltos es: "
                + tecnico.getNombre() + " " + tecnico.getApellido());

        when(incidenteRepository.findIncidenteByEstadoAndFecha(any(), any())).thenReturn(listaIncidentes);

        ResponseTecnicoDto actual = service.tecnicoConMasResueltos(dias);

        assertEquals(expected, actual);
    }
}
