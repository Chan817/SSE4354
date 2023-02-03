package com.ejb;

import javax.ejb.Remote;

@Remote
public interface OTPSessionBeanRemote {

	public String getOTPFromCloud();

	public void sendEmail(int id, int otp);

	void storeOTP(int otp, int id);

}
