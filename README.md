Congestion Tax Calculator
---------------------------
This project includes Spring Boot REST Api service to calculate the congestion tax for a given city. 
As of today, only Stockholm and Gothenburg are included, but it is possible to add another cities using application.yml file. 
There are business rules which are read by config. And also config values are reading from application.yml file.

Config includes:
* city.name : which city's rules
* durationTime : a vehicle that passes several tolling stations within "durationTime" is only taxed once.
  (The amount that must be paid is the highest one.)
* maxAmount : The maximum amount per day
* rules : includes time slots with tax amount (starttime, endtime, amount)
* time slots with tax amount , exempt vehicle names,
* taxExemptVehicles : includes list of vehicles which are not charged.


Project Tech Stack : Java 11, Spring Boot, Maven


* Run application
-------------------
java -jar target/congestion-tax-calculator-0.0.1-SNAPSHOT.jar

* Run application with external .yml file
------------------------------------------
java -jar target/congestion-tax-calculator-0.0.1-SNAPSHOT.jar --spring.config.location=../application.yml


REST Endpoint
---------------
API URL: http://localhost:8080/tax/calculator

API method: POST

API Content Type: application/json


* Postman Request:

{
    "cityName": "Gothenburg",
    "vehicleType": "Car",
    "dates": [
        "2013-01-08 06:59:00",
        "2013-01-08 11:59:00"
        ]
}

* Postman Response:

{
    "taxAmount": 21,
    "detailMessage": "Congestion Tax Amount Calculated for: Car"
}