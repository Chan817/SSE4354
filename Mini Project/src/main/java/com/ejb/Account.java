package com.ejb;

import javax.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {
	
	@Id
	@Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private String balance;
    
    public Account() {
    }

    public Account(int id, String name, String balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
    

}
