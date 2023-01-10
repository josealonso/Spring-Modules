### Spring Security

The Spring *Principal* is not the same as the Java *Principal*.

### Recommendations

- Use Spring abstractions.
- Create your own **Authentication** subclasses, not **UsernamePasswordAuthenticationToken**.

### SecurityContext

          SecurityContextHolder
                 |
          SecurityContext
                 |
          Authentication
     |           |               |
Principal    Credentials    Authorities

### Spring Security Filters

Chain of responsability

```
public void doFilter(
HttpServletRequest request,
HttpServletResponse response,
FilterChain chain) {

// 1.- Before the request proceeds further (e.g. authentication)
// ......

// 2. Invoke the rest of the chain
chain.doFilter(request, response);

// 3.- Once the request has been fully processed (e.g. cleanup)
// ......
``` 

### Spring AuthenticationManager

The Spring Authentication object represents either 
the request login    or  
the result of a successful login request.

**Authentication Manager**
Does the process of transforming a username and password into a username, password authentication token 
It returns an Authentication object or throws an exception.
Every filter has its own Authentication Manager. 

### Spring AuthenticationProviders

In general, you should create your own authentication rules here, not using a whole filter.

### Spring Configurers

A configurer allows you to **configure the filter chain** and do multiple operations on the HttpBuilder 
**with one call**.

