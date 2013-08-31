package com.example.ex_checkbox;
public class Child {
	private String userid;

    private String fullname;

    private String username;

    private boolean isChecked;
 
    private String phonenum;
    public Child(String userid, String fullname, String username,String phonenum) {

        this.userid = userid;

        this.fullname = fullname;

        this.username = username;

        this.isChecked = false;
        this.phonenum = phonenum;
    }
 

    public void setChecked(boolean isChecked) {

        this.isChecked = isChecked;

    }
 

    public void toggle() {

        this.isChecked = !this.isChecked;

    }
 

    public boolean getChecked() {

        return this.isChecked;

    }
 

    public String getUserid() {

        return userid;

    }
 

    public String getFullname() {

        return fullname;

    }
 

    public String getUsername() {

        return username;

    }


	public String getPhonenum() {
		return phonenum;
	}


	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
    
    
}
