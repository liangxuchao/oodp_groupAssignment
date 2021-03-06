package model;

import java.util.Date;

public class AllocatedListingModel {
	private int CourseIndex;

	private String CourseCode;
	private String UserID;
	private Date RegisterTime;
	
	public AllocatedListingModel(int CourseIndex, String CourseCode, String UserID, Date RegisterTime) {
		this.CourseIndex = CourseIndex;
		this.CourseCode = CourseCode;
		this.UserID = UserID;
		this.RegisterTime = RegisterTime;
	}
	
	public String getUserID() {
		return UserID;
	}
	

	public int getCourseIndex() {
		return CourseIndex;
	}
	
	public Date getRegisterTime() {
		return RegisterTime;
	}
	
	public String getCourseCode() {
		return CourseCode;
	}
}
