package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.ParameterDTO;

public interface ParameterService extends BaseService {

	ResponseEntity<Void> updateParameter(ParameterDTO parameterEntityDTO);

	ResponseEntity<ParameterDTO> addParameter(ParameterDTO parameterEntityDTO);
	
	ResponseEntity<ParameterDTO> getParameterById(Long parameterId);
	
	ResponseEntity<List<ParameterDTO>> getAllParameters();
	
	ResponseEntity<Void> deleteParameter(Long parameterId);
	
}