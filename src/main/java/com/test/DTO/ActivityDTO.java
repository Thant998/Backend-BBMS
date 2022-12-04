package com.test.DTO;

import lombok.Data;


@Data
public class ActivityDTO 
{
	private int activityId;
	private String activityName;
	private String taskId;
	private String status;
	private String cardId;
	
	public int getActivityId() {
		return activityId;
	}
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
}
