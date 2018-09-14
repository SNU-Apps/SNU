package com.androidapp.snu.repository.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class Account implements Serializable {
	@JsonProperty("accountId")
	private UUID accountId;
	@JsonProperty("email")
	private String email;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("connectedDeviceIds")
	private Set<UUID> connectedDeviceIds = new HashSet<>();
	@JsonProperty("isConfirmed")
	private Boolean isConfirmed = false;

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

	public Boolean getConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		isConfirmed = confirmed;
	}

	public void addDeviceId(final UUID deviceId) {
		connectedDeviceIds.add(deviceId);
	}
}
