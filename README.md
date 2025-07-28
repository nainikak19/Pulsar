<h1>Pulsar - Social Media Backend</h1><br>

<h3>Project Overview</h3><br>
Pulsar is a Spring Boot-based backend for a Twitter-like social media platform. It includes user authentication, posting tweets (posts), a follow/unfollow system, timeline feeds, likes/unlikes, and search functionality.The project is fully secured using Spring Security & JWT Authentication and documented with Swagger.<br><br>

<h3>Features</h3>

- User Registration & Login with unique username/email
- JWT Authentication for secure APIs
- Create, Update, Delete Posts (Tweets)
- Follow/Unfollow Users
- Timeline / Feed based on followed users
- Like/Unlike Posts
- Search Posts by content
- Recent Tweets view
-Swagger API Documentation

<he>How to Run the Project Locally</h3><br>

*1. Clone the repository*<br>
git clone https://github.com/nainikak19/Pulsar.git<br>
*2. Prerequisites*<br>
- Java 17
- Maven 3+
-MySQL

*3. Setup Database*<br>
Create a database in MySQL:<br>
CREATE DATABASE pulsar_db;<br>
*4. Build & Run the project*<br>
The app will start on:<br>
http://localhost:9090<br><br>

<h3>Authentication Guide</h3><br>

*1. Register User*<br>
POST /api/auth/register<br>
Example Body(JSON):<br>
{<br>
    "username": "nainika",<br>
    "email": "nainika@gmail.com",<br>
    "password": "nainika123"<br>
}<br>

*2. Login User*
POST /api/auth/login<br>
Authorization -> Auth Type(Bearer Token)<br>
Username: nainika@gmail.com<br>
Password: nainika123<br>
Response Header: Authorization: <JWT_TOKEN><br>

*3. Use JWT Token in API Requests*<br>
Authorization -> Auth Type(Bearer Token)<br>
Token: <JWT_TOKEN><br><br>

<h3>Swagger Documentation</h3><br>
Swagger is available at:<br>
For UI:<br>
[<small>http://localhost:9090/swagger-ui/index.html</small>](http://localhost:9090/swagger-ui/index.html)<br>

For Documentation:<br>
[<small>http://localhost:9090/v3/api-docs</small>](http://localhost:9090/v3/api-docs)<br>
This shows all available endpoints with request/response models.<br><br>

<h3>Architecture Diagram</h3><br>
[<small>Click here</small>](https://excalidraw.com/#json=hDPVOGzhd5-wiUjESOfdi,31cqfIjUwI7PJ7pZBgFCSg)<br><br>

<h3>Video Walkthrough/Presentation</h3><br>
[<small>Get Video here</small>](https://drive.google.com/file/d/1cunIowJhAaw0SkjvICJ5aoMTasYmlK7X/view?usp=sharing)


