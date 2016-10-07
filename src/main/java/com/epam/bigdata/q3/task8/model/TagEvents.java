package com.epam.bigdata.q3.task8.model;

import java.io.Serializable;
import java.util.List;

public class TagEvents implements Serializable{
	
	private String tag;
	
    private List<EventData> events;

	public TagEvents() {
		super();
	}

	public TagEvents(String tag, List<EventData> events) {
		super();
		this.tag = tag;
		this.events = events;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<EventData> getEvents() {
		return events;
	}

	public void setEvents(List<EventData> events) {
		this.events = events;
	}

	/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagEvents test = (TagEvents) o;

        if (!getTag().equals(test.getTag())) return false;
        return getEvents().equals(test.getEvents());

    }

    @Override
    public int hashCode() {
        int result = getTag().hashCode();
        result = 31 * result + getEvents().hashCode();
        return result;
    }
    */

	@Override
	public String toString() {
		return "TagEventsEntity [tag=" + tag + ", events=" + events + "]";
	}
    
}
