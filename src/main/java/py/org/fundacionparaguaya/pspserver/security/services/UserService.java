package py.org.fundacionparaguaya.pspserver.security.services;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.security.dtos.UserDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserDetailsDTO;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserRoleApplicationDTO;

public interface UserService {

	UserDTO updateUser(Long userId, UserDTO user);

	UserDTO addUser(UserDTO user);

	UserDTO addUserWithRoleAndApplication(UserRoleApplicationDTO userRoleApplicationDTO, UserDetailsDTO userDetails);

	UserDTO getUserById(Long userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Long userId);
	
}