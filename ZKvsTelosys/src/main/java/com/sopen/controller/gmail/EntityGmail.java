package com.sopen.controller.gmail;

public class EntityGmail {
	private String name;
	private int age;
	private String phone;
	private String email;
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "EntityGmail [name=" + name + ", age=" + age + ", phone=" + phone + ", email=" + email + ", memo=" + memo
				+ "]";
	}

}
