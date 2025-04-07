# 🚀 Webshop Backend

> A Spring Boot–powered backend for a webshop application, using MySQL and JWT‑based Spring Security.  
> Built to showcase implementation of these technologies in solving real world problems.

---

## 📋 Table of Contents

1. [About The Project](#about)  
2. [Built With](#built-with)  
3. [Architecture & Design](#architecture--design)  
4. [Details](#details)  
  

---
<a name="about"></a>
### 📖 About The Project

Short description:
- **Purpose:** To demonstrate a simple and secure, production-ready REST API for an e‑commerce backend…
- **Key features:** JWT authentication, role-based access, DB connection, shopping cart product/catalog management, order processing.

<a name="built-with"></a>
### 🛠️ Built With

List of major frameworks, libraries, and tools:
- [Spring Boot](https://spring.io/projects/spring-boot)  
- [Spring Security](https://spring.io/projects/spring-security)  
- [JSON Web Tokens (JWT)](https://jwt.io/)  
- [MySQL](https://www.mysql.com/)  

<a name="architecture--design"></a>
### 🏗️ Architecture & Design

Brief outline of project’s structure and key design decisions:
- **Layers:** Controller → Service → Repository  
- **Security:** JWT filter, `UserDetailsService`, Password encryption   
- **Persistence:** Entities, JPA repositories  
- **Error handling:** Custom exceprions like: `ResourceNotFoundException`, `ProductNotFoundException`...    

<a name="details"></a>
### 🔍 Details
- **Entities:** User, Role, Product, Category, Image, Order, OrderItem, Cart, CartItem
- **EER Diagram**: ![Alt text](/images/EER.png)
- **Persistence**: Persistence in Spring Boot is achieved through the use of Spring Data JPA. All of this is implemented using Spring Annotations that defines relations between entities.
- **Security:** Applications supports authentication and authorization implemented using Spring security. Implementation of JWT ensures authentication and roles: ROLE_USER, ROLE_ADMIN ensures authorization. User passwords are hashed using PasswordEncoder that sets up BCrypt-based password encoder that hashes passwords using a salted hash.
- **Architecture:** Controllers define endpoints with paths. They call service classes that contains logic for solving problems. Service classes call repository classes that communicates with database.
- **DTO:** In order to avoid infinite loops and sending irelevant data, controllers return DTO classes which are maped to model classes using Model Mapper.
- **Flow:** Spring security filter chain is configured so that every upcoming request is intercepted and if it is trying to reach secured path, JWT is taken and parsed from HTTP request header. If it is valid, request passes, othervise it is rejected and appropriate error code is returned. CORS policy ensures that only requests from specified (trusted) origins are accepted.
