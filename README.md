# Jdk & springboot version

### `jdk 17`

### `3.2.5`

# Visual Code Extensions

Before start the project:

1. `Install mysql`
2. `Install mongodb or use url of mongocloud`

# Code Conventions

1. Naming Conventions:

- Classes and Interfaces: Use CamelCase. E.g., CustomerService, OrderRepository.
- Methods and Variables: Use camelCase. E.g., calculateTotal, orderAmount.
- Constants: Use UPPER_CASE with underscores. E.g., MAX_ATTEMPTS.
- Controller name : \*Controller.java. Eg., CustomerController.java
- Service name : \*Service.java. Eg., CustomerService.java
- Repository name : \*Repository.java. Eg., CustomerRepository.java

2. Annotations: Place annotations on separate lines above the class/method.

3. Service Layer:

- Use the @Service annotation for service implement classes.
- Use descriptive method names.
- Keep methods focused on a single responsibility.

4. Repository Layer:

- Use the @Repository annotation for repository classes.
- Extend JpaRepository for data access.

5. Configuration:

- Use @Configuration for configuration classes.
- Define properties in ApplicApplicationProperties class to get application.yaml
- Use application.yaml to define environments for deployment, and use application-local.yaml to override environments in order to start the local server. (Add spring.profiles.active:local in application.yaml to use application-local.yaml.)
  **Do not push application-local.yaml and spring.profiles.active: local in application.yaml to GitHub.**

6. Entity

- Use anotation @Entity @Table(name = "") to define entity class.
- Use anotation @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor in order to use constructor method

7. Logging

- Use a logging framework like SLF4J with Logback. @Slf4j

8. Liquibase

- Use liquibase to migrate db.
- Changelog file name: yyyymmddhhmmss_database_change.xml
- changeSet : id="yyyymmddhhmmss-index" author="{git_username}". E.g., <changeSet id="20240703090000-1" author="trinhnlb"></changeSet>

9. Dto & Mapper

- Use DTOs class to define response/request types. ( anotation @Data for geter/setter)
- Define a mapper class to transform entities to DTOs.

# Struct of Project

```
src/
  main/
    java/
      com/
        example/
          project/
            Application.java
            controller/
            service/
             *Service.class
             impl/
              *ServiceImpl.class
             dto/
             mapper/
             schedule/
            repository/
            entity/
            constant/
            config/
                ApplicationProperties.java
                DatabaseConfiguration.java
                WebConfig.java
    resources/
      db/
       changelog/
       db.changelog-master.xml
      application.yaml
      application-local.yaml
      static/
      templates/
  test/
    java/
      com/
        example/
          project/
```
