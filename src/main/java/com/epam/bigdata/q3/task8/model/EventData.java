package com.epam.bigdata.q3.task8.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventData implements Serializable {

	private String description;

	private String id;

	private String city;

	private String startDate;

	private String name;

	private int attendingCount;

	private List<AttendeeData> attendees;

	private String tag;

	private Map<String, Integer> countedWords = new HashMap<String, Integer>();

	private List<String> wordsFromDescription = new ArrayList<String>();

	public EventData() {
		super();
	}

	public EventData(String description, String id, String name, int attendingCount, String tag) {
		super();
		this.description = description;
		this.id = id;
		this.name = name;
		this.attendingCount = attendingCount;
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getWordsFromDescription() {
		return wordsFromDescription;
	}

	public void setWordsFromDescription(List<String> wordsFromDescription) {
		this.wordsFromDescription = wordsFromDescription;
	}

	public Map<String, Integer> getCountedWords() {
		return countedWords;
	}

	public void setCountedWords(Map<String, Integer> countedWords) {
		this.countedWords = countedWords;
	}

	public List<AttendeeData> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<AttendeeData> attendees) {
		this.attendees = attendees;
	}

	public int getAttendingCount() {
		return attendingCount;
	}

	public void setAttendingCount(int attendingCount) {
		this.attendingCount = attendingCount;
	}
	
	/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventData test = (EventData) o;

        if (!getDescription().equals(test.getDescription())) return false;
        if (!getId().equals(test.getId())) return false;
        if (!getCity().equals(test.getCity())) return false;
        if (!getStartDate().equals(test.getStartDate())) return false;
        if (!getName().equals(test.getName())) return false;
        return getTag().equals(test.getTag());

    }

    @Override
    public int hashCode() {
        int result = getDescription().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getTag().hashCode();
        return result;
    }

*/
	@Override
	public String toString() {
		return "EventData [description=" + description + ", id=" + id + ", city=" + city + ", startDate=" + startDate
				+ ", name=" + name + ", attendees=" + attendees + ", tag=" + tag + "]";
	}
    

}
