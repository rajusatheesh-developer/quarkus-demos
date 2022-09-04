
# account-service Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory. Be aware that it’s not an _über-jar_ as
the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/account-service-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
"# aws-demos" 

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
  
# Native Exceutables
   - Native executables are files containing programs to be executed directly on an operating system, only relying on operating system libraries to be present.
   - AOT Compiler
   - GraalVM : Before creating a native executable, it’s necessary to install GraalVM for the JDK version and operating system in use
   - In the pom.xml for the project, the profile for the native executable creation was added by the generator 
   - Instead of using a new profile, we can create a native executable by passing <b>-Dquarkus.package.type=native to mvn clean install</b>. However, having a    
     profile is more convenient and enables integration testing with a native executable.  
   - <b>mvn clean install -Pnative</b>  
      -------------------------------------------
      The native build process can take a few minutes to complete—much slower than regular Java compilation—depending on the number of classes in the application         and the number of external libraries included.

     Once complete, a -runner executable will be in the /target directory, which is the result of the native executable build process. The native executable will be      specific to the operating system it was built on, as GraalVM uses native libraries to implement certain functionality.
     To create a native executable that is suitable for use within a Linux container, run mvn package -Pnative -Dquarkus.native.container-build=true
     Within a native executable, we still have garbage collection, though it uses different garbage collectors than the JVM. One impact of this is very long-running      processes will see better memory performance over time with the JVM instead of the native executable, due to the JVM continually optimizing memory utilization.
      
      
      --------------------------------------------

# Running in Kubernetes
 - dependency
  `````````````````````````
  <dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-kubernetes</artifactId>
  </dependency>

  This dependency adds the Kubernetes extension for Quarkus, which offers the ability to generate, and customize, the necessary YAML for deploying to Kubernetes.
  We can modify 
  quarkus.container-image.group=quarkus-mp
  quarkus.container-image.name=account-service
  quarkus.kubernetes.name=account-service
  
  
  <dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-minikube</artifactId>
</dependency>
  
  https://quarkus.io/guides/deploying-to-kubernetes#deploying-to-minikube.
  `````````````````````````
# Packaging Application
  - Three ways we can build for deploying to Kubernetes
    Jib (https://github.com/GoogleContainerTools/jib) - quarkus-container-image-jib
    Docker - quarkus-container-image-docker and Docker daemon
    S2I (Source to Image) binary build -quarkus-container-image-s2i.
    
    ```````````````````````````````````````
    <dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-container-image-jib</artifactId>
    </dependency>
    
    mvn clean package -Dquarkus.container-image.build=true - Builds Container
    
    
    `````````````````````````````````````````
# Database:
 - Panache :
    - Hibernate ORM with Panache (Panache)
    - Two approaches : <b>active record</b> and <b>data repository - similar to Spring Data JPA</b>
    - Sample properties
   
     ``````````````````````
     quarkus.datasource.db-kind=postgresql
     quarkus.datasource.username=database-user
     quarkus.datasource.password=database-pwd
     quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/my_database
     
         <dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-jdbc-postgresql</artifactId>
</dependency>

@QuarkusTestResource(H2DatabaseTestResource.class)
     
     ``````````````````````
   - Agroal extension from Quarkus handles data source configuration
      adding a dependency for the extension is unnecessary when being used for JPA or Panache, because they have a dependency on Agroal
