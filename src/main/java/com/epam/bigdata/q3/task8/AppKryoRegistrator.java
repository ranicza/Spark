package com.epam.bigdata.q3.task8;

import org.apache.spark.serializer.KryoRegistrator;

import com.epam.bigdata.q3.task8.model.AttendeeData;
import com.epam.bigdata.q3.task8.model.DateCity;
import com.epam.bigdata.q3.task8.model.DateCityTag;
import com.epam.bigdata.q3.task8.model.EventData;
import com.epam.bigdata.q3.task8.model.LogData;
import com.epam.bigdata.q3.task8.model.TagEvents;
import com.esotericsoftware.kryo.Kryo;

public class AppKryoRegistrator implements KryoRegistrator {

	@Override
	public void registerClasses(Kryo kryo) {
		kryo.register(AttendeeData.class);
		kryo.register(DateCity.class);
		kryo.register(DateCityTag.class);
		kryo.register(EventData.class);
		kryo.register(LogData.class);
		kryo.register(TagEvents.class);
	}

}
