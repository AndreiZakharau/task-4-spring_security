package com.epam.esm.service.impl;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.userMapper.TransitionReadUserFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserDtoFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromCreateUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.messange.LanguageMassage;
import com.epam.esm.util.validator.impl.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final TransitionReadUserFromUser readMapper;
    private final TransitionOrderFromCreateOrder createOrder;
    private final TransitionUserFromUserDto userFromUserDto;
    private final TransitionUserDtoFromUser userDtoFromUser;
    private final TransitionUserFromCreateUser userFromCreateUser;
    private final TransitionCertificateDtoFromCertificate certificateDtoFromCertificate;
    private final LanguageMassage languageMassage;


    @Transactional
    @Override
    public Page<ReadUser> getAllEntity(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = repository.findAll(pageable);
        return users.map(readMapper::mapFrom);
    }

    @Transactional
    @Override
    public void saveEntity(CreateUser createUser) {
        Role defaultRole = Role.USER;
        if (userValidator.isValid(userFromCreateUser.mapFrom(createUser))) {
            User user = userFromCreateUser.mapFrom(createUser);
            if (repository.findUserByNickName(user.getNickName()) == null) {
                user.setRole(defaultRole);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                repository.save(user);
            } else {
                throw new IncorrectDataException(languageMassage.getMessage("message.such.user"));
            }

        } else {
            throw new IncorrectDataException(languageMassage.getMessage("message.incorrect.data"));
        }

    }

    @Transactional
    @Override
    public UserDto updateEntity(long id, UserDto userDto) {
        User user = userFromUserDto.mapFrom(userDto);
        User newUser;
        Optional<User> user1 = repository.findById(id);
        if (user1.isPresent()) {
            user.setId(id);
            if (user.getNickName() == null) {
                user.setNickName(user1.get().getNickName());
            }
            if (user.getEmail() == null) {
                user.setEmail(user1.get().getEmail());
            }
            if (user.getPassword() == null) {
                user.setPassword(user1.get().getPassword());
            }

            user.setRole(user1.get().getRole());
            newUser = repository.save(user);
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id") + id +
                    languageMassage.getMessage("message.does.not"));
        }
        return userDtoFromUser.mapFrom(newUser);
    }

    @Transactional
    @Override
    public Optional<ReadUser> findById(long id) {
        Optional<User> user = Optional.of(repository.findById(id)).orElseThrow();
        if (user.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id") + id +
                    languageMassage.getMessage("message.does.not"));
        }
        return user.map(readMapper::mapFrom);
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id") + id +
                    languageMassage.getMessage("message.does.not"));
        } else {
            repository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public long countAll() {
        return repository.count();
    }

    @Transactional
    @Override
    public ReadUser getUserByName(String name) {
        User user = repository.findUserByNickName(name);
        return readMapper.mapFrom(user);
    }

    @Transactional
    @Override
    public User findByName(String name) {
        return repository.findUserByNickName(name);

    }


    @Transactional
    @Override
    public CreateOrder purchaseCertificate(long userId, long certificateId) {

        Optional<Certificate> certificate = certificateRepository.findById(certificateId);
        Optional<User> user = repository.findById(userId);
        CreateOrder order = new CreateOrder();
        if (certificate.isPresent() && user.isPresent()) {
            order.setUser(userDtoFromUser.mapFrom(user.orElseThrow()));
            order.setCertificate(certificateDtoFromCertificate.mapFrom(certificate.orElseThrow()));
            order.setCost(certificate.get().getPrice());
            order.setDatePurchase(LocalDateTime.now());
            orderRepository.save(createOrder.mapFrom(order));
        }

        return order;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findUserByNickName(username);
        if (user == null) {
            throw new UsernameNotFoundException(languageMassage.getMessage("message.user.with.name") + username +
                    languageMassage.getMessage("message.does.not"));
        }
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return new org.springframework.security.core.userdetails.User(
                user.getNickName(), user.getPassword(), Arrays.asList(authority));
    }

}
