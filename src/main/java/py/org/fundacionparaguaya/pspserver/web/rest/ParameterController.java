package py.org.fundacionparaguaya.pspserver.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.system.dtos.ParameterDTO;
import py.org.fundacionparaguaya.pspserver.system.services.ParameterService;

@RestController
@RequestMapping(value = "/api/v1/parameters")
public class ParameterController {

	
	private ParameterService parameterService;
	
	
	@Autowired
	public ParameterController(ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	
	@PostMapping()
	public ResponseEntity<ParameterDTO> addParameter(@RequestBody ParameterDTO parameterEntityDTO) {
		return parameterService.addParameter(parameterEntityDTO);
	}
	
	
	@PutMapping()
	public ResponseEntity<Void> updateParameter(@RequestBody ParameterDTO parameterEntityDTO) {
		return parameterService.updateParameter(parameterEntityDTO);
	}
	
	
	@GetMapping("/{parameterId}")
	public ResponseEntity<ParameterDTO> getParameterById(@PathVariable("parameterId") Long parameterId) {
		return parameterService.getParameterById(parameterId);
	}
	

	@GetMapping()
	public ResponseEntity<List<ParameterDTO>> getAllParameters() {
		return parameterService.getAllParameters();
	}
	
	
	@DeleteMapping("/{parameterId}")
	public ResponseEntity<Void> deleteParameter(@PathVariable("parameterId") Long parameterId) {
		return parameterService.deleteParameter(parameterId);
	}

}