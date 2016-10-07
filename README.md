## Build
``` 
mvn clean install

```
## How to run
```
/usr/hdp/current/spark2-client/bin/spark-submit --class com.epam.bigdata.q3.task8.SparkUniqueWords --master local ~/Spark/target/spark-task-0.0.1-SNAPSHOT-jar-with-dependencies.jar    <path_to_logs_file>  <path_to_tags_file>  <path_to_city_file>
```

### Example
```
/usr/hdp/current/spark2-client/bin/spark-submit --class com.epam.bigdata.q3.task8.SparkUniqueWords --master local /root/Spark/target/spark-task-0.0.1-SNAPSHOT-jar-with-dependencies.jar    hdfs://sandbox.hortonworks.com:8020/tmp/admin/logsFile.txt hdfs://sandbox.hortonworks.com:8020/tmp/admin/tagsFile.txt hdfs://sandbox.hortonworks.com:8020/tmp/admin/city.us.txt
```