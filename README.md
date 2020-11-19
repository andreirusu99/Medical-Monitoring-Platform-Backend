## DS 2020
##### Laboratory Project Spring Server Application

By Andrei Rusu, 30441

Build an image using the Dockerfile:
`docker build -t spring-app . `

Run the built image, exposing the server port:
`docker run -p 8080:8080 --name SpringApp-docker spring-app `
