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
2.Ensure the default PostgreSQL user is correctly set up:
 '''sql
 CREATE USER postgres WITH PASSWORD 'postgres';
GRANT ALL PRIVILEGES ON DATABASE taskmanagement TO postgres;

'''sql
psql -U postgres -d taskmanagement
