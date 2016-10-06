package com.epam.bigdata.q3.task8.model;

import java.io.Serializable;

public class AttendeeData implements Serializable{

    private String id;
    
    private String name;

    private int count = 0;

	public AttendeeData() {
		super();
	}
	
	

	public AttendeeData(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
    
    
    
    
    
}
