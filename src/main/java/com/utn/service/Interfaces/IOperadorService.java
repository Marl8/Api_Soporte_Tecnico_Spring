package com.utn.service.Interfaces;

import com.utn.dto.request.OperadorDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseOperadorDto;


public interface IOperadorService {

    ResponseOperadorDto guardar(OperadorDto operadorDto);

    OperadorDto findOperador(Long id);

    ResponseOperadorDto modificar(OperadorDto operadorDto);

    ResponseDto eliminar(Long id);

    ResponseDto asignarTecnico(Long idIncidente);

    ResponseDto asignarListaTecnico(Long idOperador);

}
