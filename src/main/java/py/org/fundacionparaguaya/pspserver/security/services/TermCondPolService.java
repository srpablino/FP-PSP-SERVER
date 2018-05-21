package py.org.fundacionparaguaya.pspserver.security.services;

import org.springframework.web.multipart.MultipartFile;
import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;
import py.org.fundacionparaguaya.pspserver.security.dtos.TermCondPolDTO;
import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolLanguage;

import java.util.List;

/**
 * @author mgonzalez
 */
public interface TermCondPolService {
    TermCondPolDTO getLastTermCondPol(TermCondPolType type, TermCondPolLanguage language);

    TermCondPolDTO updateTerms(MultipartFile htmlFile, Long termCondPolId);

    TermCondPolDTO saveTerms(MultipartFile htmlFile, TermCondPolDTO termCondPolDTO);

    List<TermCondPolDTO> getAll();
}
