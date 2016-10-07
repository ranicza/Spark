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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttendeeData test = (AttendeeData) o;

        if (getCount() != test.getCount()) return false;
        if (!getId().equals(test.getId())) return false;
        return getName().equals(test.getName());
    }

    @Override
    public int hashCode() {
        int result = ((id == null) ? 0 : id.hashCode());
        result = 31 * result + ((name == null) ? 0 : name.hashCode());
        result = 31 * result + count;
        return result;
    }

	@Override
	public String toString() {
		return "AttendeeData [id=" + id + ", name=" + name + ", count=" + count + "]";
	}

}
