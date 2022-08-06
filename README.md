Xmeme is the backend of a meme application that lets users post memes, get a list of top 100 memes posted by users using the application. 

This RESTful API has been built using spring boot following the MVCS architecure, using MongoDB as the data source. Data is exchanged in JSON format.
Swagger and Docker has also been configured. 

Usage - 

1. To build the repository - 

From the repository root, 

1. run `./gradlew build test`run the build
2. run `./gradlew bootjar` to create executable jar. The jar will be located inside build directories.

To run inside docker container, use below commands

To build docker image, use the command below - `docker build -t your_tag_name  .`

To run the generated container, use this command - `docker run -p 8080:8081 your_tag_name`. This will run the server on 8080 port. You can change the ports as per your needs. 

Once the application is running, swagger UI can be accessed from `http://localhost:<port no>/swagger-ui/` (The / at the end is important)

License - 
While this repository is licensed under APACHE 2.0 license, It is mandatory for users to share the readme.md and License file along with the changes they do in the contents.
