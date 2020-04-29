Manual steps:

To create a connection to db the following steps should be performed: 
    - run the following command in terminal:
        1. pull the image from docker hub
            docker pull mysql:8.0.15
        2. run the image with necessary parameters
            docker run -p 3306:3306 --name frontendframeworks-backend-mysql -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=test -e MYSQL_USER=sa -e MYSQL_PASSWORD=secret -d mysql:8.0.15
            docker run
                -p 3306:3306
                --name frontendframeworks-backend-mysql
                -e MYSQL_ROOT_PASSWORD=secret
                -e MYSQL_DATABASE=test
                -e MYSQL_USER=sa
                -e MYSQL_PASSWORD=secret
                -d mysql:5.7   
        3. TODO move to particular file (*.yml) and trigger it via spring app
        
        
Build frontendframeworks backend:
    - build
        docker build -f Dockerfile -t frontendframeworks-backend .
    - run
        docker run -p 8080:8080 --name frontendframeworks-backend --link frontendframeworks-backend-mysql:mysql -d frontendframeworks-backend
        

Build frontendframeworks webapp:
    - build
        docker image build -t frontendframeworks_angular_webapp .
    - run
        docker container run -p 8081:8080 --name frontendframeworks_angular_webapp -d frontendframeworks_angular_webapp
    