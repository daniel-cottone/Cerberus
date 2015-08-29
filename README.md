#Cerberus

##About
This project is a demonstration of a stateless, RESTful token-based authentication system using Spring Security.

##Usage
This app has two endpoints and two user accounts:

```
/api/auth
/api/protected

User account - user:password
Admin account - admin:admin
```

Only `/api/auth` allows anonymous access, while `/api/protected` requires that you have admin access. All secure paths require a JWT token to be in the request header. Sending a POST request with the following credentials will give you a token:

```
Request:
{
  "username": "admin",
  "password": "admin"
}

Response:
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiJ9.bKweskM-3QqOY8ScxhC9AcREOCG2UDY0Ylezdv1h81ALFg_v0QYBgxwfUjtf_Ns7RqAQIh_kFg1ZkeFV-szRUg"
}
```

You can now insert this token into your request header for GET access to `/api/protected`:

```
X-Auth-Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIn0.ZDhNdKRKU0pheH5-OKrY5nZRHT6fvPEVmmAJCEupQG1Oy_7bAuUyyZBSOhe-J6FhxnY8XWVhwn0kTPDL7hLTnQ
```
