package com.sumerge.course_recommender.user;

import com.sumerge.course_recommender.mapper.MapStructMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private MapStructMapper mapStructMapper;

    public AppUser register(UserDTO user) {
        System.out.println("Omar printed here " + user.getPassword() + "  " + user.getUserName());
        AppUser appUser = mapStructMapper.userDTOToAppUser(user);
        System.out.println("Omar printed here again " + appUser.getPassword() + "  " + appUser.getUserName());
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }
}
