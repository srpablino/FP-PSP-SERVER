package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.common.BaseMapper;
import py.org.fundacionparaguaya.pspserver.service.common.BaseService;
import py.org.fundacionparaguaya.pspserver.service.dto.UserRoleDTO;

public interface UserRoleService extends BaseService {

	ResponseEntity<Void> updateUserRole(UserRoleDTO userRoleEntityDTO);

	ResponseEntity<UserRoleDTO> addUserRole(UserRoleDTO userRoleEntityDTO);
	
	ResponseEntity<UserRoleDTO> getUserRoleById(Long userRoleId);
	
	ResponseEntity<List<UserRoleDTO>> getAllUserRoles();
	
	ResponseEntity<Void> deleteUserRole(Long userRoleId);
	
}
