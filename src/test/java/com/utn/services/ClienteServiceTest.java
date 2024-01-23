package com.utn.services;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.response.ResponseClienteDto;
import com.utn.dto.response.ResponseDto;
import com.utn.entity.Cliente;
import com.utn.entity.Servicio;
import com.utn.exception.ClienteNotFoundException;
import com.utn.repository.ClienteRepository;
import com.utn.repository.ServicioRepository;
import com.utn.service.ClienteServiceImpl;
import com.utn.utils.ClienteObjectUtils;
import com.utn.utils.ServicioObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    ClienteRepository repository;

    @Mock
    ServicioRepository servicioRepository;

    @InjectMocks
    ClienteServiceImpl clienteService;

    @Test
    @DisplayName("Test OK para guardar cliente")
    void guardarClienteTestOK(){
        ClienteDto argumentSut = ClienteObjectUtils.clienteDto();
        Cliente cliente = ClienteObjectUtils.cliente();
        Servicio servicio = ServicioObjectUtils.servicio();
        ResponseClienteDto expected = new ResponseClienteDto(argumentSut.getRazonSocial(),
                argumentSut.getCuit(), "Cliente guardado con éxito.");

        when(repository.findClienteByCuit(any())).thenReturn(Optional.empty());
        when(servicioRepository.findById(any())).thenReturn(Optional.of(servicio));
        when(repository.save(any())).thenReturn(cliente);

        ResponseClienteDto actual = clienteService.guardarCliente(argumentSut);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test FAIL para guardar si el cliente ya existe")
    void guardarClienteTestFail(){
        ClienteDto argumentSut = ClienteObjectUtils.clienteDto();
        ClienteNotFoundException expected = new ClienteNotFoundException("El cliente ya existe.", HttpStatus.BAD_REQUEST);
        Cliente cliente = ClienteObjectUtils.cliente();

        when(repository.findClienteByCuit(any())).thenReturn(Optional.of(cliente));

        ClienteNotFoundException actual = assertThrows(ClienteNotFoundException.class, () -> clienteService.guardarCliente(argumentSut));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test Ok para asignar servicio al cliente")
    void asignarServicioAClienteTestOK(){
        Long id1 = 1L;
        Long id2 = 2L;
        Cliente cliente = ClienteObjectUtils.cliente();
        Servicio servicio = ServicioObjectUtils.servicio();
        ResponseDto expected = new ResponseDto("Asignación realizada con éxito");

        when(repository.findById(any())).thenReturn(Optional.of(cliente));
        when(servicioRepository.findById(any())).thenReturn(Optional.of(servicio));

        ResponseDto actual = clienteService.asignarServicioACliente(id1, id2);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("test para obtener un cliente por id")
    void buscarClientePorId(){
        Long id1 = 1L;
        Cliente cliente = ClienteObjectUtils.cliente();
        ClienteDto expected = ClienteObjectUtils.clienteDto();

        when(repository.findById(any())).thenReturn(Optional.of(cliente));

        ClienteDto actual = clienteService.findCliente(id1);

        assertEquals(expected.getRazonSocial(), actual.getRazonSocial());
        assertEquals(expected.getCuit(), actual.getCuit());
    }
}
