package com.androidapp.snu.repository.account;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public class Account implements Serializable {
	private UUID accountId;
	private String email;
	private String firstName;
	private String lastName;
	private Set<UUID> connectedDeviceIds;

	public UUID getAccountId() {
		return accountId;
	}

	public void setAccountId(UUID accountId) {
		this.accountId = accountId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<UUID> getConnectedDeviceIds() {
		return connectedDeviceIds;
	}

	public void setConnectedDeviceIds(Set<UUID> connectedDeviceIds) {
		this.connectedDeviceIds = connectedDeviceIds;
	}
}
