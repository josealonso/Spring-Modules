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

