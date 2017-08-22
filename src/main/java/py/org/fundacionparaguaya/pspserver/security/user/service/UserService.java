package py.org.fundacionparaguaya.pspserver.security.user.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.security.user.domain.UserEntityDTO;

public interface UserService  {

	ResponseEntity<Void> updateUser(UserEntityDTO user);

	ResponseEntity<UserEntityDTO> addUser(UserEntityDTO user);
	
	ResponseEntity<UserEntityDTO> getUserById(Long userId);
	
	ResponseEntity<List<UserEntityDTO>> getAllUsers();
	
	ResponseEntity<Void> deleteUser(Long userId);
	
}