package com.mykhailo_pavliuk.smart_cookie.service.impl;

import com.mykhailo_pavliuk.smart_cookie.dto.UserDto;
import com.mykhailo_pavliuk.smart_cookie.mapper.UserMapper;
import com.mykhailo_pavliuk.smart_cookie.model.Subscription;
import com.mykhailo_pavliuk.smart_cookie.model.User;
import com.mykhailo_pavliuk.smart_cookie.repository.PublicationRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.SubscriptionRepository;
import com.mykhailo_pavliuk.smart_cookie.repository.UserRepository;
import com.mykhailo_pavliuk.smart_cookie.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

	private final UserRepository userRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final PublicationRepository publicationRepository;

	@Override
	public UserDto addSubscriptionToUser(long userId, long publicationId, int periodInMonths) {
		log.info("Add subscription to user");

		Subscription subscription = Subscription.builder()
				.userId(userId)
				.publicationId(publicationId)
				.periodInMonths(periodInMonths)
				.startDate(LocalDate.now())
				.build();

		User user = userRepository.getUser(subscription.getUserId());
		BigDecimal fullPrice = publicationRepository.getPublication(subscription.getPublicationId())
				.getPricePerMonth()
				.multiply(BigDecimal.valueOf(subscription.getPeriodInMonths()));

		if (user.getUserDetail().getBalance().compareTo(fullPrice) < 0) {
			throw new IllegalArgumentException("Not enough money to make a transaction");
		}

		user.getUserDetail().setBalance(user.getUserDetail().getBalance().subtract(fullPrice));
		userRepository.updateUser(userId, user);
		Subscription newSubscription = subscriptionRepository.createSubscription(subscription);
		user.getSubscriptions().add(newSubscription);
		return UserMapper.INSTANCE.mapUserToUserDto(user);
	}

	@Override
	public List<Subscription> getAllSubscriptionsByUserId(long userId) {
		log.info("Get all subscriptions by user id {}", userId);
		return subscriptionRepository.getAllSubscriptionsByUserId(userId);
	}
}
