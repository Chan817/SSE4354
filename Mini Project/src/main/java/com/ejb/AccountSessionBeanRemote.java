package com.ejb;

import javax.ejb.Remote;

@Remote
public interface AccountSessionBeanRemote {
	public Account getAccount(int id, int pin);

}
