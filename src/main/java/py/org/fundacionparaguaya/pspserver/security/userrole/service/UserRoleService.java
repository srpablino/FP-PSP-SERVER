package py.org.fundacionparaguaya.pspserver.security.userrole.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.base.BaseService;
import py.org.fundacionparaguaya.pspserver.security.userrole.domain.UserRoleEntityDTO;

public interface UserRoleService extends BaseService {

	ResponseEntity<Void> updateUserRole(UserRoleEntityDTO userRoleEntityDTO);

	ResponseEntity<UserRoleEntityDTO> addUserRole(UserRoleEntityDTO userRoleEntityDTO);
	
	ResponseEntity<UserRoleEntityDTO> getUserRoleById(Long userRoleId);
	
	ResponseEntity<List<UserRoleEntityDTO>> getAllUserRoles();
	
	ResponseEntity<Void> deleteUserRole(Long userRoleId);
	
}
