## Initialize the Spring Boot project
- Open your browser, and go to https://start.spring.io/

![spring-initializr.png](%231%2Fspring-initializr.png)

- Select the following options :
  - **Project**:  _Gradle - Kotlin_
  - **Language**:  _Kotlin_
  - **Spring Boot**: _3.1.3_


- For Project Metadata, fill the form with :
  - **Group**: me.elgregos
  - **Artifact**: escapecamp 
  - **Name**: escape-camp
  - **Description** (optional): Escape camp codelab
  
Other options should be kept as we're targeting the JRE **17** and we want to deploy the application as a **Jar**.

- The necessary dependencies to select are :
  - Spring Boot DevTools
  - Spring Reactive Web
  - Spring Data R2DBC
  - Validation
  - Liquibase Migration
  - PostgreSQL Driver
  - Testcontainers


- Click on the GENERATE button to download the project zip
- Unzip the content in the project you have previously cloned

Next
