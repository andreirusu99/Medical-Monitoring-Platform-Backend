# Medical Monitoring Platform
## Spring Server Application

### Phase 1 - Online Medication Platform built using RESTful services and a JS client application


 - PostgreSQL DB
 - Spring Java backend
 - React frontend
 - all deployed on Heroku 
 
 ![Step 1 Architecture](DS1_architecture.png)

#### Database Model - extracted from IntelliJ
![Database](DS1_db.png)

### Phase 2 - Sensor Monitoring and Notifications using MOM and WebSockets

- Added RabbitMQ message queue for asynchronous notifications
- sensor data produced by mock application and pushed on queue
- notifications sent to frontend via a websocket

![Step 2 Architecture](DS2_arch.png)
![Step 2 Deployment](DS2_deploy.png)

### Phase 3 - gRPC-based medication notification service

- interval-based medication service that alerts patient of when they need to take medication
- the medication plan of a patient is requested from the server and sent to the client by gRPC

![Step 3 Architecture](DS3_arch.png)
![Step 3 Deployment](DS3_deploy.png)
