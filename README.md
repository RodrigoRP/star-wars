# star-wars

[![Build Status](https://travis-ci.org/RodrigoRP/star-wars.svg?branch=master)](https://travis-ci.org/RodrigoRP/star-wars)
[![codecov](https://codecov.io/gh/RodrigoRP/star-wars/branch/master/graph/badge.svg)](https://codecov.io/gh/RodrigoRP/star-wars)




## About the API

It is an API that contains information about planets that appeared in the movie Star Wars. The API main URL `/api/v1/planet`.

## Features

This API provides HTTP endpoint's and tools for the following:

* Create a Planet: `POST/`
* Delete a Planet (by id): `DELETE/1`
* Get report of all Planet created: `GET/`
* Get report of number of film appearances: `GET/film/{filmName}`
* Find a unique Planet by id: `GET/1`
* Find a unique Planet by Name: `GET/name/{namePlanet}`

**Body:**

```json
{
    "id": 2,
    "name": "Alderaan",
    "climate": "temperate",
    "terrain": "mountains"
}
```

### Technologies used

This project was developed with:

* **Java 8**
* **Spring Boot**
* **Maven**
* **JUnit**
* **Swagger**
* **Model Mapper**
* **Travis**
* **Codecov**
* **H2**

### Execution

In the test profile, the application uses **H2** database (data in memory).

### Run

By default, the API will be available at [http://localhost:8080/api/v1/](http://localhost:8080/api/v1/)

### Documentation

* Swagger (development environment): http://localhost:8080/swagger-ui.html
