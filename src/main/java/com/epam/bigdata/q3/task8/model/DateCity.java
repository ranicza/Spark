package com.epam.bigdata.q3.task8.model;

import java.io.Serializable;

public class DateCity implements Serializable{

	private String date;
    
    private String city;
    
	public DateCity() {
		super();
	}

	public DateCity(String date, String city) {
		super();
		this.date = date;
		this.city = city;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateCity dc = (DateCity) o;

        if (!getDate().equals(dc.getDate())) return false;
        return getCity().equals(dc.getCity());
    }

    @Override
    public int hashCode() {
        int result = ((date == null) ? 0 : date.hashCode());
        result = 31 * result + ((city == null) ? 0 : city.hashCode());
        return result;
    }

	@Override
	public String toString() {
		return "DateCity [date=" + date + ", city=" + city + "]";
	}


   
}
