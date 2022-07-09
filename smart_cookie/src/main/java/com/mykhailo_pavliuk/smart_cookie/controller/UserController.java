package com.mykhailo_pavliuk.smart_cookie.controller;

import com.mykhailo_pavliuk.smart_cookie.api.UserApi;
import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import com.mykhailo_pavliuk.smart_cookie.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

  private final UserService userService;
  private final SubscriptionService subscriptionService;

  @Override
  public UserDto getById(Long id) {
    log.info("Get user by id {}", id);
    return userService.getById(id);
  }

  @Override
  public List<UserDto> getAll() {
    log.info("Get all users");
    return userService.getAll();
  }

  @Override
  public UserDto create(UserDto userDto) {
    log.info("Create user {}", userDto);
    return userService.create(userDto);
  }

  @Override
  public UserDto updateById(Long id, UserDto userDto) {
    log.info("Update user by id {} with request body {}", id, userDto);
    return userService.updateById(id, userDto);
  }

  @Override
  public ResponseEntity<Void> deleteById(Long id) {
    log.info("Delete user by id {}", id);
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public UserDto getByEmail(String email) {
    log.info("Get user by email {}", email);
    return userService.getByEmail(email);
  }

  @Override
  public ResponseEntity<Void> addSubscriptionToUser(
      long userId, long publicationId, int periodInMonths) {
    log.info(
        "Subscribe user with id '{}' to publication with id '{}' for {} month(s)",
        userId,
        publicationId,
        periodInMonths);
    subscriptionService.addSubscriptionToUser(userId, publicationId, periodInMonths);
    return ResponseEntity.noContent().build();
  }

  @Override
  public UserDto addFundsToUser(long id, BigDecimal amount) {
    log.info("Add funds to user with id {} in amount of {}", id, amount);
    return userService.addFunds(id, amount);
  }
}
