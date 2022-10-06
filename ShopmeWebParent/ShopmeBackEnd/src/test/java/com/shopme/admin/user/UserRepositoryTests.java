package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User user = new User("chan@gmail.com", "chan123", "chan", "ngo");
		user.addRole(roleAdmin);
		
		User savedUser = userRepository.save(user);
		assertThat(savedUser.getId()).isGreaterThan(0);
	} 
	
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		User user = new User("ronaldo@gmail.com", "ronaldo123", "ronaldo", "cr7");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		user.addRole(roleEditor); 
		user.addRole(roleAssistant);
		
		User savedUser = userRepository.save(user);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = userRepository.findAll();
		listUsers.forEach(user -> System.out.print(user));
	}
	
	@Test
	public void testGetUserById() {
		User user = userRepository.findById(1).get();
		System.out.print(user);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		User user = userRepository.findById(1).get();
		user.setEnabled(true);
		user.setEmail("channgo@gmail.com");
		
		userRepository.save(user);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User user = userRepository.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalePerson = new Role(4);
		
		user.getRoles().remove(roleEditor);
		user.addRole(roleSalePerson);
		
		userRepository.save(user);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		userRepository.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "channgo@gmail.com";
		User user = userRepository.getUserByEMail(email);
		
		assertThat(user).isNotNull();
	}
}
