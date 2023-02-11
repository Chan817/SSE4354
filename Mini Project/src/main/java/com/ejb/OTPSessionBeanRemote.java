package com.ejb;

import java.time.LocalDateTime;

import javax.ejb.Remote;

@Remote
public interface OTPSessionBeanRemote {

	public String getOTPFromCloud();

	public void sendEmail(int id, int otp);

	void storeOTP(int otp, int id);

	LocalDateTime getOtpSentTime();

}
