package com.utn.service;

import com.utn.dto.request.ServicioDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseServicioDto;
import com.utn.entity.Cliente;
import com.utn.entity.Servicio;
import com.utn.exception.ServicioNotFoundException;
import com.utn.repository.ClienteRepository;
import com.utn.repository.ServicioRepository;
import com.utn.service.Interfaces.IServicioService;
import com.utn.utils.ServicioMapper;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl implements IServicioService {

    ServicioRepository repository;

    public ServicioServiceImpl(ServicioRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseServicioDto guardarServicio(ServicioDto servicioDto) {
        Servicio servicio = ServicioMapper.servicioSaveMapper(servicioDto);

        if(verificarSiExiste(servicio)){
            throw new ServicioNotFoundException("El servicio ya existe.", HttpStatus.BAD_REQUEST);
        }
        Servicio guardado = repository.save(servicio);
        return new ResponseServicioDto(guardado.getTipoServicio(), guardado.getDescripcion(), "Servicio guardado con éxito.");
    }

    @Override
    public ServicioDto findServicio(Long id) {
        ModelMapper mapper = new ModelMapper();

        if(!repository.existsById(id)){
            throw new ServicioNotFoundException("No existen servicios con ese id.", HttpStatus.NOT_FOUND);
        }
        Servicio servicio = repository.findById(id).get();
        return mapper.map(servicio, ServicioDto.class);
    }

    @Override
    public Set<ServicioDto> findAll() {
        List<Servicio> servicios = repository.findAll();
        Set<Servicio> result = new HashSet<>(servicios);
        return result.stream().map(s -> new ServicioDto(s.getId(),
                s.getTipoServicio(), s.getDescripcion())).collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public ResponseServicioDto modificar(ServicioDto servicioDto) {
        ModelMapper mapper = new ModelMapper();
        Servicio servicio = mapper.map(servicioDto, Servicio.class);
        Servicio encontrado = repository.findById(servicio.getId())
                .orElseThrow(() -> new ServicioNotFoundException("No se encontraron servicios con este id", HttpStatus.NOT_FOUND));

        encontrado.setTipoServicio(servicio.getTipoServicio());
        encontrado.setDescripcion(servicio.getDescripcion());
        encontrado.setClientes(servicio.getClientes());

        Servicio modificado = repository.save(encontrado);
        return new ResponseServicioDto(modificado.getTipoServicio(), modificado.getDescripcion(), "Servicio modificado con éxito");
    }

    @Override
    public ResponseDto eliminar(Long id) {
        Optional<Servicio> servicio = repository.findById(id);

        if(servicio.isPresent()){
            repository.deleteById(id);
        }
        return new ResponseDto("Servicio eliminado con éxito");
    }

    private boolean verificarSiExiste(Servicio servicio){

        List<Servicio> lista = repository.findAll();
        if(lista.isEmpty()){
            return false;
        }
        List<Servicio> listaBusqueda = lista.stream()
                .filter(s -> s.getDescripcion().equals(servicio.getDescripcion())
                        && s.getTipoServicio().equals(servicio.getTipoServicio()))
                .toList();
        if(listaBusqueda.isEmpty()){
            return false;
        }
        return true;
    }
}
