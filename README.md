## What is Spring


- Spring is a framework for dependency-injection which is a pattern that allows to build very decoupled systems.

- The Spring Framework is divided into modules.
   
- Applications can choose which modules they need.

- At the heart are the modules of the core container, including a configuration model and a dependency injection mechanism.

### Spring Releases

    Spring 1     : 2004
    Spring 2     : 2006
    Spring 3     : 2009
    Spring 4     : 2013
    Spring 4.3   : 2016
    Spring 5     : 2017
    Spring 6     : 2022

### Version Changes

    Spring 2.0 provided XML namespaces and AspectJ support.
    Spring 2.5 embraced annotation-driven configuration.
    Spring 3.0 introduced a strong Java 5+ foundation across the framework codebase, and features such as the Java-based @Configuration model.
    Version 4.0 was first to fully support Java 8 features.
    You can still use Spring with older versions of Java, however, the minimum requirement has now been raised to Java SE 6.


## Spring Core Annotations

### Configuration and Bean annotations

Spring @Configuration annotation is part of the spring core framework. 

@Configuration is a class-level annotation indicating that an object is a source of bean definitions. 
@Configuration classes declare beans via public @Bean annotated methods. Calls to @Bean methods on @Configuration classes can also be used to define inter-bean dependencies.

#### Importing Additional Configuration Classes 

You need not put all your @Configuration into a single class.
The @Import annotation can be used to import additional configuration classes. 
Alternatively, you can use @ComponentScan to automatically pick up all Spring components, 
including @Configuration classes.

```
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.companyname.projectname.customer.CustomerService;
import com.companyname.projectname.order.OrderService;

@Configuration
public class Application {

     @Bean
     public CustomerService customerService() {
         return new CustomerService();
     }

     @Bean
     public OrderService orderService() {
         return new OrderService();
     }
}
```

The class above would be equivalent to the following Spring XML:

```
<beans>
        <bean id="customerService" class="com.companyname.projectname.CustomerService"/>
        <bean id="orderService" class="com.companyname.projectname.OrderService"/>
</beans>
```

#### Injecting inter-bean dependencies

```
@Configuration
public class AppConfig {

    @Bean
    public Foo foo() {
        return new Foo(bar());
    }

    @Bean
    public Bar bar() {
        return new Bar();
    }
}
```

#### Constraints when authoring @Configuration classes

- Configuration classes must be provided as classes (i.e. not as instances returned from factory methods), allowing for runtime enhancements through a generated subclass.

- Configuration classes must be non-final.

- Configuration classes must be non-local (i.e. may not be declared within a method).

- Any nested configuration classes must be declared as static.

- @Bean methods may not, in turn, create further configuration classes (any such instances will be treated as regular beans, with their configuration annotations remaining undetected).

####  How Java-based configuration works internally

```
@Configuration
public class AppConfig {

    @Bean
    public ClientService clientService1() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientService clientService2() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.setClientDao(clientDao());
        return clientService;
    }

    @Bean
    public ClientDao clientDao() {
        return new ClientDaoImpl();
    }
}
```

`clientDao()` has been called once in `clientService1()` and once in `clientService2()`. 
Since this method creates a new instance of `ClientDaoImpl`, you would normally 
expect to have two instances.
But instantiated Spring beans have a singleton scope by default. The child method 
checks the Spring container first for any cached (scoped) beans before it 
calls the parent method and creates a new instance.

#### Import config files

Spring provides the @Import annotation which allows for loading @Bean definitions from another configuration class.

```
@Configuration
public class ConfigA {

    @Bean
    public A a() {
        return new A();
    }
}

@Configuration
public class ConfigB {

    @Bean
    public B b() {
        return new B();
    }
}

@Configuration
@Import(value = {ConfigA.class, ConfigB.class) 
public class ConfigD {

    @Bean("classD")
    public D d() {
        return new D();
    }
}
```

```
public static void main(String[] args) {
    ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);

    D d = ctx.getBean(classD);
}
```

#### Differences between @Bean and @Component

If we mark a class with @Component or one of the other Stereotype annotations, 
these classes will be auto-detected using classpath scanning.
- Control of wiring is quite limited with this approach since it's purely declarative. 
- The stereotype annotations are class level annotations.

@Bean is used to explicitly declare a single bean, rather than letting Spring do it automatically like we did with @Controller. 
It decouples the declaration of the bean from the class definition and lets you create and configure beans exactly how you choose. 
- Typically, @Bean methods are declared within @Configuration classes.
- It is a method level annotation.

```
package com.beanvscomponent;

public class User {

private String first;
private String last;

public User(String first, String last) {
    this.first = first;
    this.last = last;
   }
}
```

```
package com.beanvscomponent;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

@Bean   // The name of the method is actually going to be the name of the bean.
public User superUser() {
    return new User("Partho","Bappy");
   }

}
```

### Stereotype Annotations

They are: @Component, @Controller, @Repository, @Service.

- @Repository’s job is to catch platform-specific exceptions and re-throw them as one of Spring’s unified unchecked exceptions.
- @Service is only a business service facade.
It does not provide any additional behavior over the @Component annotation.
- @Controller is a stereotype for the presentation layer (spring-mvc).
Dispatcher will scan these annotated classes for mapped methods, detecting @RequestMapping annotations.

Spring’s component-scan only scans **@Component** and does not look for @Controller, 
@Service and @Repository directly. They are scanned because they are annotated with @Component.





















