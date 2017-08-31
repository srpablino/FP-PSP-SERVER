package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.ParameterDTO;

public interface ParameterService {
	
	ParameterDTO updateParameter(Long parameterId, ParameterDTO parameterDTO);

	ParameterDTO addParameter(ParameterDTO parameterDTO);
	
	ParameterDTO getParameterById(Long parameterId);
	
	List<ParameterDTO> getAllParameters();
	
	void deleteParameter(Long parameterId);
	
}