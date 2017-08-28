package py.org.fundacionparaguaya.pspserver.security.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import py.org.fundacionparaguaya.pspserver.common.services.BaseService;
import py.org.fundacionparaguaya.pspserver.security.dtos.UserEntityDTO;

public interface UserService extends BaseService  {

	ResponseEntity<Void> updateUser(UserEntityDTO user);

	ResponseEntity<UserEntityDTO> addUser(UserEntityDTO user);
	
	ResponseEntity<UserEntityDTO> getUserById(Long userId);
	
	ResponseEntity<List<UserEntityDTO>> getAllUsers();
	
	ResponseEntity<Void> deleteUser(Long userId);
	
}