
package edu.ai_trip_planner.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.ai_trip_planner.dto.request.RegisterRequest;
import edu.ai_trip_planner.entities.User;
import edu.ai_trip_planner.repository.UserRepository;
import edu.ai_trip_planner.wrapperClasse.CustomUserDetails;


// this userSrvice class is built inorder to implemeant Spring Security
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;

    @Lazy
    public UserService(PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        var optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found: " + email);
        }

        return new CustomUserDetails(optionalUser.get());
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByEmail( request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public User updateProfile(int userId, String name, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (isPresent(name)) {
            user.setName(name.trim());
        }

        if (isPresent(email)) {
            String trimmedEmail = email.trim();
            if (trimmedEmail.equalsIgnoreCase(user.getEmail())) {
                return userRepository.save(user);
            }
            if (userRepository.existsByEmail(trimmedEmail)) {
                throw new RuntimeException("Email already in use");
            }
            user.setEmail(trimmedEmail);
        }

        return userRepository.save(user);
    }

    public User updateProfileByEmail(String currentEmail, String name, String newEmail) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + currentEmail));

        return updateProfile(user.getId(), name, newEmail);
    }

    public User changePassword(int userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        if (!isPresent(newPassword)) {
            throw new RuntimeException("New password is required");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    private boolean isPresent(String value) {
        return value != null && !value.trim().isEmpty();
    }

    
}
