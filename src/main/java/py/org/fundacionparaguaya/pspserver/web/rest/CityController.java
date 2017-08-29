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

import py.org.fundacionparaguaya.pspserver.system.dtos.CityDTO;
import py.org.fundacionparaguaya.pspserver.system.services.CityService;

@RestController
@RequestMapping(value = "/api/v1/cities")
public class CityController {

	
	private CityService cityService;
	
	
	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	
	@PostMapping()
	public ResponseEntity<CityDTO> addCity(@RequestBody CityDTO city) {
		return cityService.addCity(city);
	}
	
	
	
	@PutMapping()
	public ResponseEntity<Void> updateCity(@RequestBody CityDTO city) {
		return cityService.updateCity(city);
	}
	

	
	@GetMapping("/{cityId}")
	
	public ResponseEntity<CityDTO> getCity(@PathVariable("cityId") Long cityId) {
		return cityService.getCityById(cityId);
	}
	


	@GetMapping()
	public ResponseEntity<List<CityDTO>> getAllCities() {
		return cityService.getAllCities();
	}
	
	
	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<Void> deleteCity(@PathVariable("cityId") Long cityId) {
		return cityService.deleteCity(cityId);
	}

}