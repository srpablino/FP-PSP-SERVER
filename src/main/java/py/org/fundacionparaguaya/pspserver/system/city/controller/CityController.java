package py.org.fundacionparaguaya.pspserver.system.city.controller;

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
import py.org.fundacionparaguaya.pspserver.system.city.domain.CityEntityDTO;
import py.org.fundacionparaguaya.pspserver.system.city.service.CityService;

@RestController
@RequestMapping(value = "/api/v1/cities")
public class CityController {

	
	private CityService cityService;
	
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	
	@PostMapping()
	public ResponseEntity<CityEntityDTO> addCity(@RequestBody CityEntityDTO city) {
		return cityService.addCity(city);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateCity(@RequestBody CityEntityDTO city) {
		return cityService.updateCity(city);
	}
	

	
	@GetMapping("/{cityId}")
	
	public ResponseEntity<CityEntityDTO> getCity(@PathVariable("cityId") Long cityId) {
		return cityService.getCityById(cityId);
	}
	


	@GetMapping()
	public ResponseEntity<List<CityEntityDTO>> getAllCities() {
		return cityService.getAllCities();
	}
	
	
	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<Void> deleteCity(@PathVariable("cityId") Long cityId) {
		return cityService.deleteCity(cityId);
	}

}