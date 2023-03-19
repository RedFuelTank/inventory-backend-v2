# Inventory backend (v2)

## Preface

Decisions, especially those related to architecture were made by me in order to explore new possibilities in development. These solutions may not be optimal for such a small task.  Basically, my inspiration was the book "Clean Architecture" by Robert Martin
This is already second version of this project. You can find first version in my GitHub.

## Introduction

This project was developed for NetGroup Estonia in 2023 year. 
This is a multi-module application that implements the ability to create an inventory as a business user as well as to create business users as a representative. 
This application also includes a payment functionality, statistics for the user's items and the ability to upload pictures for the user's items. All changes can only be made by the business user himself

### Technologies
* Spring
* Hibernate JPA
* Maven
* PostgreSQL
* Liquibase Migration

### Prebuild profiles
| type         | name     | password |
|--------------|----------|----------|
| REPRESENTIVE | user     | user     |
| BUSINESS     | business | business |
| ADMIN        | admin    | admin    |

## Architecture

![Use cases](usecases.png)

![Architecture design](architecture.png)

This architecture consists of three layers (entity, use case, adapter (divided into ui and infrastructure)). Entity layer is a POJO class that implements critical application data. 
The use case layer represents the business logic of the application.
The adapter layer mediates between different forms of data and acts as a translator, which translates the data into a form understandable to the use case.

All dependencies are directed upwards, each higher layer knows nothing about the lower ones, and the more upwards the more stable and important the layer.

## End points
Endpoints via Swagger - UI: ```http://{server}:{port}/api/swagger-ui/index.html#/```








