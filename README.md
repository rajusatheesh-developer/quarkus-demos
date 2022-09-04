# quarkus-demos

# Create Quarkus Project
- We can create a microservice using Quarkus in the following ways:
  - With the project generator at https://code.quarkus.io/
  - In a terminal with the Quarkus Maven plugin
     ``````````````````
     mvn io.quarkus:quarkus-maven-plugin:2.1.3.Final:create \
    -DprojectGroupId=quarkus \
    -DprojectArtifactId=account-service \
    -DclassName="quarkus.accounts.AccountResource" \
    -Dpath="/accounts"
    
     `````````````````````
  - By manually creating the project and including the Quarkus dependencies and plugin configuration : Complex and Error Prone
  
# Features
 - Live coding 
   - We can do changes while running appplication
   - Live coding enables hot deployment via background compilation 
 - Provides EndPoints for installed extensions
   - endpoints are related to Arc, the CDI container for Quarkus
     Dev UI contains extension-specific behavior, such as editing configuration, and links to the guides for each installed extension

# Bean Scopes
 - @Singleton, @ApplicationScoped, or @RequestScoped
# Commands 
  ```````````````````````
  1. Run the project 
     mvn quarkus:dev
  
  
  ````````````````````````
  
  
# RESTEasy Implementing JAX-RS Components
  ``````````````````````
  @Path
  @Produces
  @GET
  @QuarkusTest
  @NativeImageTest
  @PostConstruct : indicates the method should be called straight after creation of the CDI Bean
  @PathParam :  maps the accountNumber URL parameter into the accountNumber method parameter.
  WebApplicationException
  @Provider indicates the class is an autodiscovered JAX-RS Provider
  ``````````````````````
# Unit Test Cases
  - JUnit5 , RestEasy ( has Hamcrests )
  - Annotations
    - @QuarkusTest :Prior to running the tests, the extension starts the constructed Quarkus service, just as if it was constructed with any build tool.
    - @TestMethodOrder(OrderAnnotation.class) - ensure method order execution
    - @Order(x) - 
  - Run Tests : <b>mvn test</b>


  - Dependencies
  ```````````````````````
  <dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-junit5</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <scope>test</scope>
</dependency>

 <plugin>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>${surefire-plugin.version}</version>      ❶
  <configuration>
    <systemPropertyVariables>
      <java.util.logging.manager>org.jboss.logmanager.LogManager</java.util.logging.manager>                               ❷
    </systemPropertyVariables>
  </configuration>
</plugin>
  
  ```````````````````````
  
