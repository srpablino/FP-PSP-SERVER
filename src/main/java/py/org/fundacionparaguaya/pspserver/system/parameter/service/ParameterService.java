package py.org.fundacionparaguaya.pspserver.system.parameter.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.system.parameter.dto.ParameterDTO;

public interface ParameterService extends BaseService {

	ResponseEntity<Void> updateParameter(ParameterDTO parameterEntityDTO);

	ResponseEntity<ParameterDTO> addParameter(ParameterDTO parameterEntityDTO);
	
	ResponseEntity<ParameterDTO> getParameterById(Long parameterId);
	
	ResponseEntity<List<ParameterDTO>> getAllParameters();
	
	ResponseEntity<Void> deleteParameter(Long parameterId);
	
}