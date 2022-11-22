package com.epam.esm.service.impl;

import com.epam.esm.dto.userDto.ReadUser;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.certificateMapper.TransitionCertificateDtoFromCertificate;
import com.epam.esm.mapper.impl.orderMapper.TransitionOrderFromCreateOrder;
import com.epam.esm.mapper.impl.userMapper.TransitionReadUserFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserDtoFromUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromCreateUser;
import com.epam.esm.mapper.impl.userMapper.TransitionUserFromUserDto;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.util.validator.impl.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock UserRepository repository;
    @Mock OrderRepository orderRepository;
    @Mock CertificateRepository certificateRepository;
    @Mock UserValidator userValidator;
    @Mock PasswordEncoder passwordEncoder;
    @Mock TransitionReadUserFromUser readMapper;
    @Mock TransitionOrderFromCreateOrder createOrder;
    @Mock TransitionUserFromUserDto userFromUserDto;
    @Mock TransitionUserDtoFromUser userDtoFromUser;
    @Mock TransitionUserFromCreateUser userFromCreateUser;
    @Mock TransitionCertificateDtoFromCertificate certificateDtoFromCertificate;
    @InjectMocks
    UserServiceImpl service;


    private static final User USER_1 = User.builder()
            .id(3L)
            .nickName("Andrei")
            .email("andrei666@gmail.com")
            .role(Role.ADMIN)
            .password("andrei")
            .build();
    private static final User USER_2 = User.builder()
            .id(4L)
            .nickName("Svetlana")
            .email("svetic_7@gmail.com")
            .role(Role.USER)
            .password("user")
            .build();

    private static final Certificate CERTIFICATE_1 = Certificate.builder()
            .id(1L)
            .certificateName("coffee")
            .description("operates in a chain of cafes 'My Coffee'")
            .price(1.5)
            .duration(30)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusDays(30))
            .build();

    private static final Order ORDER = Order.builder()
            .id(2L)
            .user(USER_1)
            .certificates(List.of(CERTIFICATE_1))
            .cost(CERTIFICATE_1.getPrice())
            .datePurchase(LocalDateTime.now())
            .build();
    private static final List<User> LIST = List.of(USER_1, USER_2);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void getAllEntity() {
        when(repository.findAll((Pageable) any())).thenReturn(Page.empty());
        repository.findAll((Pageable)any());
        verify(repository,times(1)).findAll((Pageable) any());

    }

    @Test
    void saveEntity() {
        User user3 = User.builder()
                .id(5L)
                .nickName("Ben13")
                .email("ben13@gmail.com")
                .role(Role.USER)
                .password("ben")
                .build();
        when(repository.save(user3)).thenReturn(user3);
        User user = repository.save(user3);
        verify(repository,times(1)).save(any());
        assertEquals(user.getNickName(), "Ben13");
        assertEquals(user.getEmail(), "ben13@gmail.com");
    }

    @Test
    void updateEntity() {
        User user = USER_2;
        assertEquals(user.getNickName(), "Svetlana");
        String name = "Svetick";
        user.setNickName(name);
        when(repository.save(user)).thenReturn(user);
        repository.save(user);
        verify(repository,times(1)).save(user);
        assertEquals(user.getNickName(), name);
    }

    @Test
    void findById() {
        when(repository.findById(3L)).thenReturn(Optional.of(USER_1));
        Optional<ReadUser> user = service.findById(3);
        assertNotNull(user);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void deleteEntity() {
        when(repository.findById(3L)).thenReturn(Optional.of(USER_1));
        Optional<User> user1 = repository.findById(3L);
        assertNotNull(user1);
        when(repository.findById(3L)).thenReturn(null);
        repository.deleteById(3L);
        Optional<User> user = repository.findById(3L);
        assertNull(user);
        verify(repository).deleteById(anyLong());
        verify(repository,times(2)).findById(anyLong());
    }


    @Test
    void findByName() {
        String name = "Andrei";
        when(repository.findUserByNickName(name)).thenReturn(USER_1);
        User user = service.findByName(name);
        verify(repository).findUserByNickName(any());
        assertNotNull(user);
        assertEquals(user.getId(), 3L);
    }

    @Test
    void purchaseCertificate() {
        when(orderRepository.save(ORDER)).thenReturn(ORDER);
        Order order = orderRepository.save(ORDER);
        verify(orderRepository,times(1)).save(order);
        assertEquals(order.getCost(), CERTIFICATE_1.getPrice());
    }


}