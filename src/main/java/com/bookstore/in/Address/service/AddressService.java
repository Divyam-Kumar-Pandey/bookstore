package com.bookstore.in.Address.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.bookstore.in.Address.dto.CreateAddressRequest;
import com.bookstore.in.Address.model.Address;
import com.bookstore.in.Address.repository.AddressRepository;
import com.bookstore.in.User.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final AddressRepository addressRepository;

	public Address createForUser(User user, CreateAddressRequest request) {
		Address address = Address.builder()
				.user(user)
				.title(request.title())
				.addressLine1(request.addressLine1())
				.addressLine2(request.addressLine2())
				.country(request.country())
				.city(request.city())
				.postalCode(request.postalCode())
				.landmark(request.landmark())
				.phoneNumber(request.phoneNumber())
				.build();
		return Objects.requireNonNull(addressRepository.save(address));
	}
}


