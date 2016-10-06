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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagEvents other = (TagEvents) obj;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TagEventsEntity [tag=" + tag + ", events=" + events + "]";
	}
    
}
