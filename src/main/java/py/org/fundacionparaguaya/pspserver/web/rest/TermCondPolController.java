package py.org.fundacionparaguaya.pspserver.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;
import py.org.fundacionparaguaya.pspserver.security.dtos.TermCondPolDTO;
import py.org.fundacionparaguaya.pspserver.security.services.TermCondPolService;

@RestController
@RequestMapping(value = "/api/v1/termcondpol")
public class TermCondPolController {
    
    private TermCondPolService service;
    
    public TermCondPolController(TermCondPolService service) {
        this.service = service;
    }
    
    @GetMapping("/last/{type}")
    public ResponseEntity<TermCondPolDTO> getLastTermCondPolByType(@PathVariable("type") TermCondPolType type) {
        TermCondPolDTO dto = service.getLastTermCondPol(type);
        return ResponseEntity.ok(dto);
    }
}
