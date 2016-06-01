# [General Networks Corporation](http://www.gennet.com/) Data Export Utility

# Download Executable Jar (GNC_Data_Export.jar) and Documentation

Located [here](http://www.gennet.com/offerings/big-data/marklogic-nosql-database-platform-services/).

## Running from command prompt

1. Download Executable Jar (GNC_Data_Export.jar)
2. Configure settings.txt 
    - Set data source type ORACLE, MSSQL, MARKLOGIC
    - Configure source connection strings
    - Configure log destination, resultset output & performance settings
3. Configure query.txt
    - Enter query string
4. Execute the following command
    - java -jar C:\Export\GNC_Data_Export.jar C:\Export\settings.txt
5. See default C:\Export\ResultSet\<<configured filename in settings.txt>> for resultset
6. See default C:\Export\Logs\<<configured filename in settings.txt>> for log file entries

## Jar File Dependencies

	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/commons-codec-1.7.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/commons-logging-1.1.1.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/httpclient-4.1.1.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/httpcore-4.1.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jackson-annotations-2.4.1.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jackson-core-2.4.1.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jackson-databind-2.4.1.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/java-client-api-3.0.4.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jersey-apache-client4-1.17.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jersey-client-1.17.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jersey-core-1.17.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/jersey-multipart-1.17.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/logback-classic-1.1.2.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/logback-core-1.1.2.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/mimepull-1.6.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/mimepull-1.9.4.jar
	MarkLogic-Java-API-3.0.4/java-client-api-3.0.4/lib/slf4j-api-1.7.4.jar
	ojdbc6.jar
	sqljdbc4.jar
	jaxp-1_6_0/lib/jaxp-api.jar
	jaxp-1_6_0/lib/jaxp-ri.jar

# License

[Apache License Version 2.0](https://github.com/indeedeng/util/blob/master/LICENSE)
