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