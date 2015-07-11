#Cerberus

##About
This project is a demonstration of a stateless, RESTful token-based authentication system using Spring Security.

##Usage
This app has two endpoints:

```
/api/auth
/api/protected
```

Only `/api/auth` allows anonymous access. All protected paths require a JWT token to be in the request header. Sending a POST request with the following credentials will give you a token:

```
Request:
{
  "username": "user",
  "password": "password"
}

Response:
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIn0.ZDhNdKRKU0pheH5-OKrY5nZRHT6fvPEVmmAJCEupQG1Oy_7bAuUyyZBSOhe-J6FhxnY8XWVhwn0kTPDL7hLTnQ"
}
```

You can now insert this token into your request header for GET access to `/api/protected`:

```
X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIn0.ZDhNdKRKU0pheH5-OKrY5nZRHT6fvPEVmmAJCEupQG1Oy_7bAuUyyZBSOhe-J6FhxnY8XWVhwn0kTPDL7hLTnQ
```
