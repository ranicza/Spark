package com.epam.bigdata.q3.task8;

import org.apache.commons.lang.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import com.epam.bigdata.q3.task8.model.DateCity;
import com.epam.bigdata.q3.task8.model.DateCityTag;
import com.epam.bigdata.q3.task8.model.EventData;
import com.epam.bigdata.q3.task8.model.TagEvents;
import com.epam.bigdata.q3.task8.model.LogData;
import com.epam.bigdata.q3.task8.model.AttendeeData;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Event;
import com.restfb.types.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import scala.Tuple2;
import org.apache.spark.sql.Row;

public class SparkUniqueWords {
	private static final String SPLIT = "\\s+";
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("mm.dd.yyyy");
	private static final String SEARCH = "search";
	private static final String KEY = "q";
	private static final String TYPE = "type";
	private static final String EVENT = "event";
	private static final String FIELDS = "fields";
	private static final String PARAMS = "id,attending_count,place,name,description,start_time";
	
	
	private static final String UNDEFINED = "undefined";
	private static final String DEFAULT_DATE = "2016-10-05";
	private static final String LIMIT = "limit";
	private static final String ATTENDING ="/attending";
	
	private static final String TOKEN = "EAACEdEose0cBAJBwYGYTnoeNibjFHUkiBLLJssjclr7ce2OVcZBcOr7d3fwuHZAvMvll0gWOOFpbs28pTCFW0BR4D1IqYrziD2vGgdEgd4LsmC2r5ZBZC5EfcNKRmPtaMUqYb63kFQTZBZAoPx1UZA4KLeFUVjkIpIfnxCLvynhFRyP3xRGWdcA";
	private static final FacebookClient facebookClient = new DefaultFacebookClient(TOKEN, Version.VERSION_2_5);
    
    
	public static void main(String[] args) throws Exception {
		
		String logFile = args[0];
	    String tagFile = args[1];
	    String cityFile = args[2];

	    if (args.length < 2) {
	      System.err.println("Usage: file <file_input>  <file_output>");
	      System.exit(1);
	    }    
	    
	    SparkSession spark = SparkSession
	    	      .builder()
	    	      .appName("SparkUniqueWords")
	    	      .getOrCreate();

	    /*
	     * ----------------------------------------------------------------------------------------------------------
	     *    1. Collect all unique keyword per day per location (city)
	     * ----------------------------------------------------------------------------------------------------------
	     * 
	     * -------------------- GET TAG_ID + LIST OF TAGS -------------------------
	     */
        Dataset<String> data = spark.read().textFile(tagFile);
        String header = data.first();
        
	    // Reads file as a collection of lines skipping header from the file.
        JavaRDD<String> tagsRdd = data.filter(x -> !x.equals(header)).javaRDD();
        
        /*
         *  In Java, key-value pairs are represented using the scala.Tuple2 class
         *  from the Scala standard library. 
         *  RDDs of key-value pairs are represented by the JavaPairRDD class. 
         *  mapToPair(PairFunction<T,K2,V2> f) 
         */
        JavaPairRDD<Long, List<String>> tagsPairs = tagsRdd.mapToPair(line -> {
                String[] parts = line.split(SPLIT);             
                return new Tuple2<Long, List<String>>(Long.parseLong(parts[0]), Arrays.asList(parts[1].split(",")));
        });
        
        Map<Long, List<String>> tagsMap = tagsPairs.collectAsMap();
        
        
        /*
         * ----------------------- GET CITIES --------------------------------------
         */
        Dataset<String> cityData = spark.read().textFile(cityFile);
        String cityHeader = cityData.first();       
        JavaRDD<String> citiesRDD = cityData.filter(x -> !x.equals(cityHeader)).javaRDD();

        JavaPairRDD<Integer, String> citiesIdsPairs = citiesRDD.mapToPair(line -> {
                String[] parts = line.split(SPLIT);
                return new Tuple2<Integer, String>(Integer.parseInt(parts[0]), parts[1]);
        });
        
        Map<Integer, String> citiesMap = citiesIdsPairs.collectAsMap();
        
        /*
         * ----------------------- GET LOGS + TAGS + CITIES + DATE ------------------------
         * map(Function<T,R> f) 
         */
        JavaRDD<LogData> logsRdd = spark.read().textFile(logFile).javaRDD().map(line -> {              
                String[] parts = line.split(SPLIT);             
                List<String> tags = tagsMap.get(Long.parseLong(parts[parts.length-2]));
                String city = citiesMap.get(Long.parseLong(parts[parts.length-15]));
                String date = parts[1].substring(0,8);             
                return new LogData(Long.parseLong(parts[parts.length-2]), Long.parseLong(parts[parts.length-15]), date, tags, city);
        });
        logsRdd.cache();
        
        Dataset<Row> df = spark.createDataFrame(logsRdd, LogData.class);
        df.createOrReplaceTempView("logs");
        df.limit(15).show();


        /*
         *  Date/City by pairs
         *  RDDs of key-value pairs are represented by the JavaPairRDD class. 
         *  mapToPair(PairFunction<T,K2,V2> f) 
         */
        JavaPairRDD<DateCity, Set<String>> dateCityTags = logsRdd.mapToPair(log-> {
       	 DateCity dc = new DateCity(log.getTimestampDate(), log.getCity());
            return new Tuple2<DateCity, Set<String>>(dc, new HashSet<String>(log.getTags()));
       });

        /*
         * 	reduceByKey(Function2<V,V,V> func)
		 *	Merge the values for each key using an associative reduce function.
		 *  This will also perform the merging locally on each mapper before sending
		 *  results to a reducer, similarly to a "combiner" in MapReduce.
		 *  Output will be hash-partitioned with the existing partitioner/ parallelism level.
         */
        JavaPairRDD<DateCity, Set<String>> dateCityTagsPairs = dateCityTags.reduceByKey((set1, set2) -> {
                set1.addAll(set2);
                return set1;
        });
        
        for (Tuple2<DateCity, Set<String>> tuple : dateCityTagsPairs.collect()) {
            System.out.println("CITY: " + tuple._1().getCity() + " DATE: " + tuple._1().getDate() + " TAGS: ");
            if (tuple._2 != null) {
                for (String tag : tuple._2()) {
                    System.out.print(tag + " ");
                }
            }
        }

      /*
       * ===========================   FACEBOOK API PART  ==========================================================
       * 
       * -----------------------------------------------------------------------------------------------------------
       * 2.	For each keyword per day per city store information like: 
       * KEYWORD DAY CITY TOTAL_AMOUNT_OF_VISITORS TOKEN_MAP(KEYWORD_1, AMOUNT_1... KEYWORD_N, AMOUNT_N). 
       * Where: 
			TOTAL_AMOUNT_OF_VISITORS - SUM all VISITORS and ATTENDEES found for this KEYWORD DAY CITY combination;
			TOKEN_MAP(KEYWORD_1, AMOUNT_1... KEYWORD_10, AMOUNT_10) – tokenized and aggregated descriptions for 
			all events and places (total world count of all descriptions, like you do in HW1 top 10 keywords)
	   *-------------------------------------------------------------------------------------------------------------
       */
        
       /*
        *  Get a sequence of all unique tags from the file.
        *  flatMap(FlatMapFunction<T,U> f) 
        */       
       JavaRDD<String> uniqueTagsRdd = logsRdd.flatMap(log -> 
       		log.getTags().iterator()
       ).distinct();

               
      /*
       * Get events according tag.  
       * map(Function<T,R> f) 
       */
       JavaRDD<TagEvents> tagsEventsRDD = uniqueTagsRdd.map(tag -> {
				Connection<Event> con = facebookClient.fetchConnection(SEARCH, Event.class, Parameter.with(KEY, tag), 
						Parameter.with(TYPE, EVENT), Parameter.with(FIELDS, PARAMS));
				
				List<EventData> eventsByTag = new ArrayList<EventData>();
				for (List<Event> events : con) {
					 for (Event event : events) {
						 if (event != null) {
							 EventData eventEntity = new EventData(event.getDescription(), event.getId(), event.getName(), event.getAttendingCount(), tag);
							 
							 if (event.getPlace() != null && event.getPlace().getLocation() != null && event.getPlace().getLocation().getCity() != null) {
								 eventEntity.setCity(event.getPlace().getLocation().getCity());
	                         } else {
	                        	 eventEntity.setCity(UNDEFINED);
	                         }
	                            if (event.getStartTime() != null) {
	                            	eventEntity.setStartDate(dateFormatter.format(event.getStartTime()).toString());
	                            } else {
	                            	eventEntity.setStartDate(DEFAULT_DATE);
	                            }
							 
	                            // Get words from event's description
	                            if (StringUtils.isNotBlank(event.getDescription()) && StringUtils.isNotEmpty(event.getDescription())) {
	                                String[] words = event.getDescription().split(SPLIT);	                                
	                                for (String word : words) {
	                                	eventEntity.getWordsFromDescription().add(word);
	                                }
	                            }
	                            eventsByTag.add(eventEntity);							 
						 }
					 }
				}
               TagEvents tagEvents = new TagEvents(tag, eventsByTag);
               System.out.println("TAG: " + tag +  " , amount of events: " + eventsByTag.size());
				return tagEvents;     	
		});
       

       // Get all events
        JavaRDD<EventData> allEventsRdd = tagsEventsRDD.flatMap(tagEvents ->
        	tagEvents.getEvents().iterator()
        );

        JavaPairRDD<DateCityTag, EventData> dateCityTagsByPairs = allEventsRdd.mapToPair(eventEntity -> {
        	DateCityTag dctEntity = new DateCityTag(eventEntity.getStartDate(), eventEntity.getCity(), eventEntity.getTag());
        	 return new Tuple2<DateCityTag, EventData>(dctEntity, eventEntity);
        	}
        );
        
       
        JavaPairRDD<DateCityTag, EventData> dctPairs = dateCityTagsByPairs.reduceByKey((event1, event2) -> {
        	EventData eventEntity = new EventData();    
        	// collect all the attendees  
        	eventEntity.setAttendingCount(event1.getAttendingCount() + event2.getAttendingCount());
        	// word count of all descriptions
        	Map<String, Integer> map = getCountedWords(event1.getWordsFromDescription(), event2.getWordsFromDescription());
        	eventEntity.setCountedWords(map);	
        	return eventEntity;
        });
        
        dctPairs.collect().forEach(tuple -> {
        	System.out.println("KEYWORD: " + tuple._1().getTag() + " DATE: " + tuple._1().getDate() + " CITY: " + tuple._1().getCity() + "  VISITORS : " + tuple._2.getAttendingCount());
        	
        	Map<String, Integer> outputWords = tuple._2.getCountedWords().entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(java.util.Comparator.reverseOrder())).limit(10)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            System.out.println("TOP 10 KEYWORDS: ");
            for (String str : outputWords.keySet()) {
                System.out.print(str + "(" + outputWords.get(str) + ")  ");
            }
        });
        
        
        /*
         * ----------------------------------------------------------------------------------------------------------------------
         * 	3. Collect all the attendees and visitors of this events with amount of occurrences
         * ----------------------------------------------------------------------------------------------------------------------
         */
        JavaRDD<EventData> eventsWithAttendees = allEventsRdd.map(eventEntity -> {
        	Connection<User> conAttendees = facebookClient.fetchConnection(eventEntity.getId() + ATTENDING, User.class, Parameter.with(LIMIT, 500));
        	List<AttendeeData> attendeesResult = new ArrayList<AttendeeData>();
        	for (List<User> users : conAttendees) {
        		for (User user : users) {
        			AttendeeData attendee = new AttendeeData(user.getId(), user.getName());
        			attendeesResult.add(attendee);
        		}
        	}
        	eventEntity.setAttendees(attendeesResult);
        	return eventEntity;
        });
        
        // Get all  attendees
        JavaRDD<AttendeeData> attendeesRDD = eventsWithAttendees.flatMap(eventEntity -> eventEntity.getAttendees().iterator());
        
        JavaPairRDD<AttendeeData, Integer> attendesPairs = attendeesRDD.mapToPair(eventEntity -> new Tuple2<>(eventEntity, 1));
        
        JavaPairRDD<AttendeeData, Integer> attendesCounts = attendesPairs.reduceByKey((n1, n2) -> n1 + n2);
        
		JavaRDD<AttendeeData> attendeesResult = attendesCounts.map(tuple -> {
			tuple._1().setCount(tuple._2());
			return tuple._1();
		});
		
		// Get sorted result according amount of attendees.
		JavaRDD<AttendeeData> sortedAttendees = attendeesResult.sortBy(item -> item.getCount(), false, 1);
		
		Dataset<Row> attendeesDf = spark.createDataFrame(sortedAttendees, AttendeeData.class);
		attendeesDf.createOrReplaceTempView("attendees");
        attendeesDf.show(15);

        spark.stop();
	  }

	/*
	 * Return a map (word, count).
	 */
	private static Map<String, Integer> getCountedWords(List<String> words, List<String> words2) {
		Map<String, Integer> occuranceWords = new HashMap<String, Integer>();
		words.addAll(words2);
		for (String word : words) {
    		int occurrences = Collections.frequency(words, word);
    		occuranceWords.put(word, occurrences);
		}
		return occuranceWords;
	}
}
