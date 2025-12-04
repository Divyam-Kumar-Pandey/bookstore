package com.bookstore.in.Address.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.in.Address.dto.CreateAddressRequest;
import com.bookstore.in.Address.model.Address;
import com.bookstore.in.Address.service.AddressService;
import com.bookstore.in.User.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AddressController {

	private final AddressService addressService;

	@PostMapping("/my-addresses")
	@PreAuthorize("hasRole('USER')")
	public Address addAddress(
			@AuthenticationPrincipal User currentUser,
			@Valid @RequestBody CreateAddressRequest request
	) {
		return addressService.createForUser(currentUser, request);
	}
}


