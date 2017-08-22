package py.org.fundacionparaguaya.pspserver.security.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserRoleEntityDTO;

public interface UserRoleService extends BaseService {

	ResponseEntity<Void> updateUserRole(UserRoleEntityDTO userRoleEntityDTO);

	ResponseEntity<UserRoleEntityDTO> addUserRole(UserRoleEntityDTO userRoleEntityDTO);
	
	ResponseEntity<UserRoleEntityDTO> getUserRoleById(Long userRoleId);
	
	ResponseEntity<List<UserRoleEntityDTO>> getAllUserRoles();
	
	ResponseEntity<Void> deleteUserRole(Long userRoleId);
	
}
