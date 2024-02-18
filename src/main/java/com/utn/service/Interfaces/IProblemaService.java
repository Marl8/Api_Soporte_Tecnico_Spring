package com.utn.service.Interfaces;

import com.utn.dto.request.TipoProblemaCompleteDto;
import com.utn.dto.request.TipoProblemaDto;
import com.utn.dto.response.ResponseDto;
import com.utn.dto.response.ResponseProblemaDto;

public interface IProblemaService {

    ResponseProblemaDto guardar(TipoProblemaDto problemaDto);

    TipoProblemaDto findProblema(Long id);

    ResponseProblemaDto modificar(TipoProblemaCompleteDto problemaDto, Long id);

    ResponseDto eliminar(Long id);
}

