# 🚀 Webshop Backend

> A Spring Boot–powered backend for a webshop application, using MySQL and JWT‑based Spring Security.  
> Built to showcase implementation of these technologies in solving real world problems.

---

## 📋 Table of Contents

1. [About The Project](#about)  
2. [Built With](#built-with)  
3. [Architecture & Design](#architecture--design)  
4. [Details](#details)  
   - [Prerequisites](#prerequisites)  
   - [Installation](#installation)  
   - [Running the Server](#running-the-server)  
5. [Configuration](#configuration)  
6. [API Reference](#api-reference)  
   - [Authentication Endpoints](#authentication-endpoints)  
   - [Product Endpoints](#product-endpoints)  
   - [Order Endpoints](#order-endpoints)  
7. [Security](#security)  
8. [Database Schema](#database-schema)  
9. [Testing](#testing)  
10. [Future Improvements](#future-improvements)  
11. [Contributing](#contributing)  
12. [License](#license)  
13. [Contact](#contact)

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
- **EER Diagram**:

### Prerequisites

- Java 17+  
- Maven 3.6+  
- MySQL instance (or Docker)  
- (Optional) Docker & Docker Compose  

### Installation

1. **Clone the repo**  
   ```bash
   git clone https://github.com/your-username/your-webshop-backend.git
   cd your-webshop-backend
