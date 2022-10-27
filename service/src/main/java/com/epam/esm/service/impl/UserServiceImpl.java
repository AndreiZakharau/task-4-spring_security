package com.epam.esm.service.impl;

import com.epam.esm.dto.orderDto.CreateOrder;
import com.epam.esm.dto.userDto.CreateUser;
import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.dto.userDto.UserDto;
import com.epam.esm.entity.Certificate;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;

    private final TransitionReadUserFromUser readMapper;
    private final TransitionOrderFromCreateOrder createOrder;
    private final TransitionUserFromUserDto userFromUserDto;
    private final TransitionUserDtoFromUser userDtoFromUser;
    private final TransitionUserFromCreateUser userFromCreateUser;
    private final TransitionCertificateDtoFromCertificate certificateDtoFromCertificate;
    private final LanguageMassage languageMassage;

    @Transactional
    @Override
    public List<ReadUser> getAllEntity(int limit, int offset) {
//        return readMapper.buildUserModelReadMapper(repository.findAll(limit, offset)); //todo
        return null;
    }

    @Transactional
    @Override
    public void saveEntity(CreateUser createUser) {
        User user = userFromCreateUser.mapFrom(createUser);
        if (repository.findUserByNickName(user.getNickName())==null) {
            repository.save(user);
        } else {
            throw new IncorrectDataException(languageMassage.getMessage("message.such.user"));
        }

    }

    @Transactional
    @Override
    public void updateEntity(long id, UserDto userDto) {
        User user = userFromUserDto.mapFrom(userDto);
        Optional<User> user1 = repository.findById(id);
        if (user1.isPresent()) {
            user.setId(id);
            if(user.getNickName()==null)
                user.setNickName(user1.get().getNickName());
            if(user.getEmail()==null)
                user.setEmail(user1.get().getEmail());
            repository.save(user);
        } else {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
        }
    }

    @Transactional
    @Override
    public Optional<ReadUser> findById(long id) {
        Optional<User> user = Optional.of(repository.findById(id)).orElseThrow();
        if (user.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
        }
        return user.map(readMapper::mapFrom);
    }

    @Transactional
    @Override
    public void deleteEntity(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NoSuchEntityException(languageMassage.getMessage("message.user.with.id"));
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
    public CreateOrder purchaseCertificate(long userId, long certificateId) {

        Optional<Certificate> certificate = certificateRepository.findById(certificateId);
        Optional<User> user = repository.findById(userId);
        CreateOrder order = new CreateOrder();
        if(certificate.isPresent()&&user.isPresent()){
            order.setUser(userDtoFromUser.mapFrom(user.orElseThrow()));
            order.setCertificate(certificateDtoFromCertificate.mapFrom(certificate.orElseThrow()));
            order.setCost(certificate.get().getPrice());
            order.setDatePurchase(LocalDateTime.now());
            orderRepository.save(createOrder.mapFrom(order));
        }

        return order;
    }
}
