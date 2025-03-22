# 📝 Task Manager API

This is a **Task Management API** built with **Spring Boot 3** and **PostgreSQL**.  
It provides RESTful endpoints for creating, updating, retrieving, and deleting tasks.  

---

## 🚀 **How to Run the Application**

### **1️⃣ Prerequisites**
Ensure the following software is installed on your system:  
- **Java 21** (Check with `java -version`)  
- **Maven** (Check with `mvn -v`)  
- **PostgreSQL** (Check with `psql -V`)  
- **Postman** or **cURL** (Optional, for API testing)  

---

### **2️⃣ PostgreSQL Setup**
1. Start PostgreSQL and create the database:  
   ```sql
   CREATE DATABASE taskmanagement;
   ```
2. Ensure the default PostgreSQL user is correctly set up:  
   ```sql
   CREATE USER postgres WITH PASSWORD 'postgres';
   GRANT ALL PRIVILEGES ON DATABASE taskmanagement TO postgres;
   ```
3. Verify the connection:
   ```bash
   psql -U postgres -d taskmanagement
   ```

---

### **3️⃣ Application Configuration**
The application uses the following database configuration in `application.properties`:  
```properties
spring.application.name=TaskManager

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanagement
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always

# Show SQL queries in logs
spring.jpa.show-sql=true

# Server Configuration
server.port=8080
spring.mvc.pathmatch.matching-strategy=ant_path_matcher

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.springframework=INFO
```

---

### **4️⃣ Running the Application**
1. Clone the repository:  
   ```bash
   git clone https://github.com/your-repo/task-manager-api.git
   cd task-manager-api
   ```
2. Install dependencies:  
   ```bash
   mvn clean install
   ```
3. Start the application:  
   ```bash
   mvn spring-boot:run
   ```
4. The API will be accessible at:  
   ```
   http://localhost:8080
   ```

---

## 🛠 **API Endpoints**
| Method | Endpoint            | Description            |
|--------|---------------------|------------------------|
| GET    | `/api/tasks`        | Get all tasks         |
| GET    | `/api/tasks/{id}`   | Get task by ID        |
| POST   | `/api/tasks`        | Create a new task     |
| PUT    | `/api/tasks/{id}`   | Update a task         |
| DELETE | `/api/tasks/{id}`   | Delete a task         |

### **Example API Request (Create Task)**
```json
{
  "title": "Implement Authentication",
  "description": "Secure API with JWT",
  "status": "Pending",
  "assignedTo": {
    "id": 1
  }
}
```

---

## 📌 **Assumptions & Considerations**
- The database runs on **localhost:5432** with default credentials (`postgres/postgres`).
- The API follows **REST principles** and uses **JSON format** for requests and responses.
- Hibernate is configured to automatically update the schema (`ddl-auto=update`).
- API logging is enabled for debugging purposes.

---

