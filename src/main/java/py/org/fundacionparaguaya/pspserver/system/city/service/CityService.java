package py.org.fundacionparaguaya.pspserver.system.city.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.system.city.domain.CityEntityDTO;

public interface CityService extends BaseService  {

	ResponseEntity<Void> updateCity(CityEntityDTO cityEntityDTO);

	ResponseEntity<CityEntityDTO> addCity(CityEntityDTO cityEntityDTO);
	
	ResponseEntity<CityEntityDTO> getCityById(Long cityId);
	
	ResponseEntity<List<CityEntityDTO>> getAllCities();
	
	ResponseEntity<Void> deleteCity(Long cityId);
	
}
