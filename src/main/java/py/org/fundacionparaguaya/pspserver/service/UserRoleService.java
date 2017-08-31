package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.service.dto.UserRoleDTO;

public interface UserRoleService {
	
	UserRoleDTO updateUserRole(Long userRoleId, UserRoleDTO userRoleDTO);

	UserRoleDTO addUserRole(UserRoleDTO userRoleDTO);
	
	UserRoleDTO getUserRoleById(Long userRoleId);
	
	List<UserRoleDTO> getAllUserRoles();
	
	void deleteUserRole(Long userRoleId);
	
}
