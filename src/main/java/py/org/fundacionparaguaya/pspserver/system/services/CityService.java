package py.org.fundacionparaguaya.pspserver.system.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;

public interface CityService extends BaseService {

	ResponseEntity<Void> updateCity(CityDTO cityEntityDTO);

	ResponseEntity<CityDTO> addCity(CityDTO cityEntityDTO);
	
	ResponseEntity<CityDTO> getCityById(Long cityId);
	
	ResponseEntity<List<CityDTO>> getAllCities();
	
	ResponseEntity<Void> deleteCity(Long cityId);
	
}
