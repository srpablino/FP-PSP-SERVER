package py.org.fundacionparaguaya.pspserver.security.userrole.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.security.userrole.dto.UserRoleDTO;

public interface UserRoleService extends BaseService {

	ResponseEntity<Void> updateUserRole(UserRoleDTO userRoleEntityDTO);

	ResponseEntity<UserRoleDTO> addUserRole(UserRoleDTO userRoleEntityDTO);
	
	ResponseEntity<UserRoleDTO> getUserRoleById(Long userRoleId);
	
	ResponseEntity<List<UserRoleDTO>> getAllUserRoles();
	
	ResponseEntity<Void> deleteUserRole(Long userRoleId);
	
}
