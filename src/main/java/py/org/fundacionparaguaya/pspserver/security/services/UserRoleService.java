package py.org.fundacionparaguaya.pspserver.security.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserRoleDTO;

public interface UserRoleService extends BaseService {

	ResponseEntity<Void> updateUserRole(UserRoleDTO userRoleEntityDTO);

	ResponseEntity<UserRoleDTO> addUserRole(UserRoleDTO userRoleEntityDTO);
	
	ResponseEntity<UserRoleDTO> getUserRoleById(Long userRoleId);
	
	ResponseEntity<List<UserRoleDTO>> getAllUserRoles();
	
	ResponseEntity<Void> deleteUserRole(Long userRoleId);
	
}
