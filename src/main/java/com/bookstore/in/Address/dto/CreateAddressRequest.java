package com.bookstore.in.Address.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAddressRequest(
		@NotBlank String title,
		@NotBlank String addressLine1,
		String addressLine2,
		@NotBlank String country,
		@NotBlank String city,
		@NotBlank String postalCode,
		String landmark,
		@NotBlank String phoneNumber
) {}


