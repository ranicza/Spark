package com.epam.bigdata.q3.task8.model;

import java.io.Serializable;

public class DateCityTag implements Serializable{

    private String date;
    
    private String city;
    
    private String tag;

	public DateCityTag() {
		super();
	}

	public DateCityTag(String date, String city, String tag) {
		super();
		this.date = date;
		this.city = city;
		this.tag = tag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateCityTag test = (DateCityTag) o;

        if (!getDate().equals(test.getDate())) return false;
        if (!getCity().equals(test.getCity())) return false;
        return getTag().equals(test.getTag());

    }

    @Override
    public int hashCode() {
        int result = getDate().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getTag().hashCode();
        return result;
    }

	@Override
	public String toString() {
		return "DateCiteTagEntity [date=" + date + ", city=" + city + ", tag=" + tag + "]";
	}
    
    
    
}
