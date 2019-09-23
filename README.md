# zooProject

This is the repo will contain all starter service for zooProject.
Please refer to this instruction for installation and testing of the project to ensure consistency between environment.

## Develop environment requirement:

- Most project will be on JDK 11 at version 11.0.2.
- Database version will be Postgresql 11
- Install Postman to test the APIs (or built-in Swagger)

## Installation Instruction

1. git clone https://github.com/nhatnguyen26/zooProject.git
2. install postgresql (using Terminal):
    - For mac: use home brew: 
        - `brew update` 
        - `brew install postgresql`
    - Follow step to initdb:
        - `createdb zoodb`
        - `psql zoodb`
        - `create user zoodb;`
        - `alter user zoodb with encrypted password 'ZooDBPassword';`
        - `grant all privileges on database zoodb to zoodb;`
        - `\q` to exit psql console
        - `cd dbScripts`
        - `ls | xargs -n 1 psql -U zoodb -d zoodb -f` this will init all the starting tables
3. Initial test to make sure set up work: 
    - Run `com.zoo.bookingService.application.BookingServiceApplication` if using Eclipse or IntelliJ
    - In terminal, can use `mvnw clean install springboot:run` server should start
4. Testing using Swagger: `http://localhost:8080/swagger/index.html` and test using UI

## Working convention:

- Github pro free but can't add protection branch.
- For some initial functional code, it is ok to push to master
- When start normally, **DO NOT COMMIT TO MASTER!!!**
- Let's use branch work flow, branch out, PR review then merge.
- Try to add unit test for all code added.
- As start, it is ok to stub and leave code empty and still PR, but put TODO or TASK or FIXME to mark work need to be done.

## Design Documents:

- https://dbdiagram.io/d/5d13054e37c1673299db1a95
