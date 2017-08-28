package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.CityDTO;

public interface CityService extends BaseService {

	ResponseEntity<Void> updateCity(CityDTO cityEntityDTO);

	ResponseEntity<CityDTO> addCity(CityDTO cityEntityDTO);
	
	ResponseEntity<CityDTO> getCityById(Long cityId);
	
	ResponseEntity<List<CityDTO>> getAllCities();
	
	ResponseEntity<Void> deleteCity(Long cityId);
	
}
