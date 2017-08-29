package py.org.fundacionparaguaya.pspserver.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.service.dto.UserDTO;

public interface UserService {

	UserDTO updateUser(Long userId, UserDTO user);

	UserDTO addUser(UserDTO user);
	
	UserDTO getUserById(Long userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Long userId);
	
}