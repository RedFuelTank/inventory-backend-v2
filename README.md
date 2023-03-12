## Inventory backend (v2)

### Preface

Decisions, especially those related to architecture were made by me in order to explore new possibilities in development. These solutions may not be optimal for such a small task.  Basically, my inspiration was the book "Clean Architecture" by Robert Martin

### Introduction

This project has been developed for NetGroup. 

### Architecture

![Use cases](usecases.png)

![Architecture design](architecture.png)

This architecture consists of three layers (entity, use case, adapter (divided into ui and infrastructure)). Entity layer is a POJO class that implements critical application data. 
The use case layer represents the business logic of the application.
The adapter layer mediates between different forms of data and acts as a translator, which translates the data into a form understandable to the use case.

All dependencies are directed upwards, each higher layer knows nothing about the lower ones, and the more upwards the more stable and important the layer.




