package com.sumerge.course_recommender.user;

import com.sumerge.course_recommender.exception_handling.custom_exceptions.UserAlreadyExistsException;
import com.sumerge.course_recommender.mapper.MapStructMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MapStructMapper mapStructMapper;

    public AppUser register(UserDTO user) {
        if (userRepository.existsByUserName(user.getUserName())){
            throw new UserAlreadyExistsException("User with the username: " + user.getUserName() + " already exists");
        }
        AppUser appUser = mapStructMapper.userDTOToAppUser(user);
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }
}
