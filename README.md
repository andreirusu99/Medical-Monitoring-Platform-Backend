## Medical Monitoring Platform
##### Spring Server Application

By Andrei Rusu

Build an image using the Dockerfile:
`docker build -t spring-app . `

Run the built image, exposing the server port:
`docker run -p 8080:8080 --name SpringApp-docker spring-app `
