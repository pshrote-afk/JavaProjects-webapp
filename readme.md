# Student/Course Management Web Application

A 3-tier web application for managing students and courses using Java Servlets and Apache Tomcat.


## Architecture
- **Presentation Layer**: jQuery with `jQuery UI`, `jQuery Validation`, `jTable`
    - PDF Export: Server-side PDF generation using iText7
- **Business Layer**: Business logic, validation, and in-memory caching
  - **CourseManager**: Singleton manager with dual indexing (code, title)
  - **StudentManager**: Singleton manager with multi-key indexing (rollNo, enrollmentNumber, aadharCardNumber, courseCode)
- **Data Layer**: MySQL database

## Features
- **Courses**: CRUD operations, PDF export
- **Students**: CRUD operations with form validation, PDF export
- **Business Layer**: 
  - Singleton design pattern for managers
  - In-memory caching with multiple indexed data structures for fast lookups

## Technology Stack
- Java Servlets, Apache Tomcat
- jQuery, jQuery UI, jTable
- MySQL Database
- iText7 (PDF export)
- Gradle

## Servlet Endpoints
**Courses:** `/courses/getAll`, `/courses/add`, `/courses/update`, `/courses/delete`, `/courses/pdf`

**Students:** `/students/getAll`, `/students/add`, `/students/update`, `/students/delete`, `/students/pdf`

## Setup
1. Configure MySQL database connection in DBDL layer
2. Build project using Gradle
3. Deploy to Apache Tomcat
4. Access application at `http://localhost:8080/[project-name]/`