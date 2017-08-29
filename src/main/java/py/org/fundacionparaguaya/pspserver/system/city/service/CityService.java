package py.org.fundacionparaguaya.pspserver.system.city.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.system.city.dto.CityDTO;

public interface CityService extends BaseService {

	ResponseEntity<Void> updateCity(CityDTO cityEntityDTO);

	ResponseEntity<CityDTO> addCity(CityDTO cityEntityDTO);
	
	ResponseEntity<CityDTO> getCityById(Long cityId);
	
	ResponseEntity<List<CityDTO>> getAllCities();
	
	ResponseEntity<Void> deleteCity(Long cityId);
	
}
