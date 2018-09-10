package com.androidapp.snu.repository.appCredentials;

import java.io.Serializable;
import java.util.UUID;

public class AppCredentials implements Serializable {
	private UUID deviceRegistrationId;

	public UUID getDeviceRegistrationId() {
		return deviceRegistrationId;
	}

	public void setDeviceRegistrationId(UUID deviceRegistrationId) {
		this.deviceRegistrationId = deviceRegistrationId;
	}
}
