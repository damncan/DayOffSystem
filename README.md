


## DayOff System
It is a little project which dedicated to reduce labor cost for small and medium businesses. It provides a web-based tool to assist every employee manage their leaves.



## Development Environment:
- JDK 1.8.0_151
- Eclipse Oxygen IDE for Java EE Developers
- Maven 3.3.9
- Tomcat 9.0.2
- SQL Server 2017



## System Architecture
**3-tier architecture**

This project is designed with 3-tier architecture: web server, AP server and SQL server.
<img alt="3-tier architecture" src="/img/3-layers.png" title="3-tier architecture" style="width: 200px;"/>

**Web Server**

In the web server, I use Spring 4 to achieve a MVC structure. To keep the controller layer simply handle request/response, I extract all business logic into the service layer.
<img alt="Web server" src="/img/web server.png" title="Web server" style="width: 200px;"/>

**AP Server**

In the AP server, all database query logic (hibernate session) are put in the DAO layer. XML configuration contains three parts: datasource, sessionFactory (ORM settings) and DAOs (Dependency Injection: refer datasource and sessionFactory to every DAO by XML setting).
<img alt="AP server" src="/img/ap server.png" title="AP server" style="width: 200px;"/>

**SQL Server**

Each department cotains several users; each user has several leave events.
<img alt="SQL server" src="/img/ERD.png" title="SQL server" style="width: 200px;"/>



## Demo
**Login**