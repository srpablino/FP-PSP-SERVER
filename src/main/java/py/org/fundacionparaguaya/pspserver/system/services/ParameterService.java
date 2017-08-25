package py.org.fundacionparaguaya.pspserver.system.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.system.dtos.ParameterEntityDTO;

public interface ParameterService extends BaseService  {

	ResponseEntity<Void> updateParameter(ParameterEntityDTO parameterEntityDTO);

	ResponseEntity<ParameterEntityDTO> addParameter(ParameterEntityDTO parameterEntityDTO);
	
	ResponseEntity<ParameterEntityDTO> getParameterById(Long parameterId);
	
	ResponseEntity<List<ParameterEntityDTO>> getAllParameters();
	
	ResponseEntity<Void> deleteParameter(Long parameterId);
	
}