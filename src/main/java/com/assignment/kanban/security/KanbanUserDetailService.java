package com.assignment.kanban.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.assignment.kanban.entity.User;
import com.assignment.kanban.repository.UserRepository;

@Component
public class KanbanUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userOptional = userRepository.findByUserName(username);

		if (userOptional.isEmpty()) {

			throw new ResponseStatusException(HttpStatusCode.valueOf(400),
					"No User Found with userName......" + username);
		}

		else {
			return new KanbanUserDetail(userOptional.get());
		}

	}

}
