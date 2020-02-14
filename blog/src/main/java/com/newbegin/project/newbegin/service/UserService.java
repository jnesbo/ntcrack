package com.newbegin.project.newbegin.service;

import com.newbegin.project.newbegin.model.Role;
import com.newbegin.project.newbegin.model.User;
import com.newbegin.project.newbegin.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;
    @Value("${server.port}")
    private int port;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);


    }


    public boolean addUser(User user) throws MessagingException {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActive(false);
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail())) {

            String message = String.format(
                    "Приветсвую, %s! \n" +
                            "Вы зарегистрировались на сервисе InNutshell.\n" +
                            "Для активации аккаунта перейдите по ссылке.\n"
                            + "<a href='http://localhost:%s/activate/%s'> Подтвердить регистрацию.</a>",
                    user.getUsername(),
                    port,
                    user.getActivationCode()
            );
            mailService.send(user.getEmail(), "Код активации", message);
        }
        return true;

    }


    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void saveUserByAdmin(User user, String username, Map<String, String> form) {

        user.setUsername(username);
        Set<String> role = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (role.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);

    }

    public void updateProfileSecurity(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail))
                || (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
    }

    public void updateProfile(User user, String description) {
        user.setDescription(description);
        userRepository.save(user);

    }

    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);
        return true;
    }


    public boolean forgotPassword(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        } else if (!user.isActive()) {
            return false;
        }
        user.setResetCode(randString());
        userRepository.save(user);
        String message = String.format(
                "Здравствуйте, %s! \n" +
                        "Для сброса пароля перейдите по ссылке. \n"
                        + "<a href='http://localhost:%s/resetPassword'> Сбросить пароль. </a> \n" +
                        " и введите код %s",
                user.getUsername(),
                port,
                user.getResetCode()
        );
        mailService.send(user.getEmail(), "Сброс пароля", message);

        return true;
    }

    public String randString() {
        int length = 11;
        return RandomStringUtils.random(length, true, true);
    }

    public boolean resetPass(String username, String password, String code) {
        User user = userRepository.findByUsername(username);
        if(!StringUtils.isEmpty(user.getResetCode())) {
            if (user.getResetCode().equals(code)) {
                user.setResetCode(null);
                user.setPassword(passwordEncoder.encode(password));
            } else {
                return false;
            }
        }else
        {return false;}
        userRepository.save(user);

        return true;
    }

    public void follow(User currentUser, User user) {
        user.getFollowers().add(currentUser);
        userRepository.save(user);
    }

    public void unfollow(User currentUser, User user) {
        user.getFollowers().remove(currentUser);
        userRepository.save(user);
    }

    public String isEmailFree(String email) {
        return userRepository.getEmail(email);
    }

    public List<User> findUser(String username) {
        return userRepository.findByUsernameContains(username);
    }

    public void setAvatar(User user){
        userRepository.save(user);
    }
}
