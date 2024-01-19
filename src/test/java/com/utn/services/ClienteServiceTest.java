package com.utn.services;

import com.utn.dto.request.ClienteDto;
import com.utn.dto.response.ResponseClienteDto;
import com.utn.entity.Cliente;
import com.utn.entity.Servicio;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
