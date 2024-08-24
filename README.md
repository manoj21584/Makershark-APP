To run the application please follow these step:

1-clone git repo:https://github.com/manoj21584/Makershark-APP

2-create database in mysql with name "springBoot_db" 
and add these properties in application.properties file as such--

spring.application.name=Makersharks
server.port=7000
spring.datasource.url=jdbc:mysql://localhost:3306/springBoot_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql=true

3-run the application

4-to create supplier fire theis url in postman with proper body -- http://localhost:7000/api/supplier/create

5-to Retrieve a list of X number of manufacturer(s) in a given location, with a specific nature of business, and the capability to perform a specific manufacturing process.
fire this url with proper location ,nature of bussiness,manuficaturing process in body in postman -- http://localhost:7000/api/supplier/query
6-to update supplier  --http://localhost:7000/api/supplier/update/supplierId
7-to get all supplier --http://localhost:7000/api/supplier
8-to delete supplier  --http://localhost:7000/api/supplier/delete/supplierId
