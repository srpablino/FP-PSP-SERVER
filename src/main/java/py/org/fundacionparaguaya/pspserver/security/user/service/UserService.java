package py.org.fundacionparaguaya.pspserver.security.user.service;

import java.util.List;

import py.org.fundacionparaguaya.pspserver.security.user.dto.UserDTO;

public interface UserService {

	UserDTO updateUser(Long userId, UserDTO user);

	UserDTO addUser(UserDTO user);
	
	UserDTO getUserById(Long userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Long userId);
	
}