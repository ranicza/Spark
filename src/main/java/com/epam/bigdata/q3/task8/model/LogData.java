package com.epam.bigdata.q3.task8.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LogData implements Serializable{

	private long  IDUserTags;
	
	private long IDCity;
	
	private String timestampDate;
	
	private List<String> tags;
	
    private String city;

	public LogData() {
	}

	public LogData(long iDUserTags, long iDCity, String timestampDate, List<String> tags, String city) {
		super();
		IDUserTags = iDUserTags;
		IDCity = iDCity;
		this.timestampDate = timestampDate;
		this.tags = tags;
		this.city = city;
	}
	
	public long getIDUserTags() {
		return IDUserTags;
	}

	public void setIDUserTags(long iDUserTags) {
		IDUserTags = iDUserTags;
	}

	public long getIDCity() {
		return IDCity;
	}

	public void setIDCity(long iDCity) {
		IDCity = iDCity;
	}

	public String getTimestampDate() {
		return timestampDate;
	}

	public void setTimestampDate(String timestampDate) {
		this.timestampDate = timestampDate;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogData test = (LogData) o;

        if (getIDUserTags() != test.getIDUserTags()) return false;
        if (getIDCity() != test.getIDCity()) return false;
        if (!getTimestampDate().equals(test.getTimestampDate())) return false;
        return getCity().equals(test.getCity());

    }

    @Override
    public int hashCode() {
        int result = (int) (getIDUserTags() ^ (getIDUserTags() >>> 32));
        result = 31 * result + (int) (getIDCity() ^ (getIDCity() >>> 32));
        result = 31 * result + getTimestampDate().hashCode();
        result = 31 * result + getCity().hashCode();
        return result;
    }
    */
    
	@Override
	public String toString() {
		return "ULogEntity [IDUserTags=" + IDUserTags + ", IDCity=" + IDCity + ", timestampDate=" + timestampDate + "]";
	}

}
