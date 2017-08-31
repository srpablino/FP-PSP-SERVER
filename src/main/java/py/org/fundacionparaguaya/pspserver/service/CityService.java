package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.CityDTO;

public interface CityService {

	CityDTO updateCity(Long cityId, CityDTO cityDTO);

	CityDTO addCity(CityDTO cityDTO);
	
	CityDTO getCityById(Long cityId);
	
	List<CityDTO> getAllCities();
	
	void deleteCity(Long cityId);
	
	
	
}
