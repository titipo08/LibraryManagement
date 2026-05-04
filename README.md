# 📚 Library Management System
A web-based Java EE application that allows users to register, sign in, issue books, return books, track reading sessions, and view their library account — all through a clean, blurred-glass UI.

✨ Features
🔐 User Registration & Sign-In with unique auto-generated codes
📖 Book Issue System with 2 book slots per user (14-day return window)
🔁 Book Return tracking per slot
🕐 Reading Session logging with 1-hour session allotment
📋 Display Info panel showing issued books, return dates, and penalties
🚪 Secure session-based Logout
🎨 Glassmorphism UI with a warm library-themed aesthetic

📚 Use Cases
🔹 Manage book borrowing and returns in a small/college library

🔹 Track reading activity and penalties for overdue books

🔹 Learn Java Servlet + JSP + JDBC full-stack web development

🔹 Use as a starter project for a larger library management platform

🛠 Technologies
| Layer | Technology |
|---|---|
| Backend | Java Servlets (Jakarta EE 6.0) |
| Frontend | JSP, HTML, CSS (Glassmorphism) |
| Database | MySQL (`librarymanagement` schema) |
| JDBC Driver | `mysql-connector-j-9.6.0` |
| Build Tool | Apache Ant (NetBeans `build.xml`) |
| Server | Apache Tomcat (or any Jakarta EE container) |
| IDE | NetBeans (project config included) |

📌 Requirements
To build and run this project, you'll need:

- **Java JDK 11+** (Jakarta EE 6.0 compatible)
- **Apache Tomcat 10+** (supports `jakarta.*` namespace)
- **MySQL 8.0+**
- **Apache Ant** (or NetBeans IDE with built-in Ant support)
- (Optional) **NetBeans IDE** — `.nbproject/` config is included for one-click build & deploy

## 📁 Folder Structure

```
library/
│
├── src/
│   ├── conf/
│   │   └── MANIFEST.MF
│   └── java/com/library/
│       ├── loginservelet.java          ← Register new user, generate unique code
│       ├── signinservelet.java         ← Sign in with name + code
│       ├── Logoutservlet.java          ← Invalidate session, redirect to index
│       ├── readingservlet.java         ← Log reading session (1-hour allotment)
│       ├── BookIssueServlet.java       ← Issue a book to slot 1 or 2
│       ├── BookReturnServlet.java      ← Return a book from slot 1 or 2
│       └── DisplayInfoServlet.java     ← Show user info, issued books, penalty
│
├── web/
│   ├── index.jsp                       ← Landing page: Register / Sign-In toggle
│   ├── section1.jsp                    ← Dashboard with 4 action buttons
│   ├── bookissue.jsp                   ← Book issue UI (2 slots)
│   ├── bookreturn.jsp                  ← Book return UI (2 slots)
│   ├── displayinfo.jsp                 ← User info & penalty display
│   ├── reading.jsp                     ← Reading session confirmation
│   ├── pic/
│   │   └── librarypic.jpg              ← Background image
│   ├── META-INF/
│   │   └── context.xml
│   └── WEB-INF/
│       ├── web.xml                     ← Servlet & URL mappings
│       └── lib/
│           └── mysql-connector-j-9.6.0.jar
│
├── build/                              ← Compiled output (auto-generated)
├── dist/
│   └── library.war                    ← Deployable WAR file
├── nbproject/                         ← NetBeans project configuration
├── build.xml                          ← Ant build script
└── librarypic.jpg
```

## 🗄 Database Dictionary

Create the MySQL schema and tables before running the application.

```sql
CREATE DATABASE librarymanagement;
USE librarymanagement;

-- Users table
CREATE TABLE info (
    name            VARCHAR(100),
    code            VARCHAR(10) PRIMARY KEY,
    book1_code      VARCHAR(20),
    book1_issuedate VARCHAR(20),
    book1_returndate VARCHAR(20),
    book2_code      VARCHAR(20),
    book2_issuedate VARCHAR(20),
    book2_returndate VARCHAR(20),
    reading_time    VARCHAR(50),
    penalty         VARCHAR(50)
);

-- Books table
CREATE TABLE book (
    bk_code   VARCHAR(20) PRIMARY KEY,
    bk_name   VARCHAR(100),
    bk_author VARCHAR(100)
);
```

> ⚠️ **Note:** The default DB credentials in the servlets are `root` / `kinjal`. Update these in each servlet file (or extract to a config file) before deploying to any shared or production environment.

## 🚀 How to Run

### 🖥️ Option 1: Deploy the Prebuilt WAR

A ready-to-deploy WAR file is included in `dist/library.war`.

1. Copy `library.war` into your Tomcat `webapps/` directory.
2. Start Tomcat — it will auto-deploy.
3. Open your browser and go to:
   ```
   http://localhost:8080/library/
   ```

### 🧩 Option 2: Build & Run with NetBeans

1. Open NetBeans IDE.
2. Go to **File → Open Project** and select the `library/` folder.
3. Right-click the project → **Clean and Build**.
4. Right-click → **Run** — NetBeans will deploy to the configured Tomcat instance automatically.

### 🔧 Option 3: Build with Apache Ant (Command Line)

```bash
# From the root library/ directory
ant clean
ant build
ant deploy   # or copy dist/library.war to Tomcat manually

## 🔗 Servlet URL Mappings

| URL Pattern | Servlet Class | Purpose |
|---|---|---|
| `/loginservelet` | `loginservelet` | Register new user |
| `/signinservelet` | `signinservelet` | Sign in existing user |
| `/Logoutservlet` | `Logoutservlet` | Invalidate session |
| `/readingservlet` | `readingservlet` | Log reading session |
| `/BookIssueServlet` | `BookIssueServlet` | Issue / view issued books |
| `/BookReturnServlet` | `BookReturnServlet` | Return a book |
| `/DisplayInfoServlet` | `DisplayInfoServlet` | View account info & penalty |

## ❓ Frequently Asked Questions (FAQ)

**📌 Q1: Do I need NetBeans to run this project?**
A: No. You can deploy the prebuilt `dist/library.war` directly to any Tomcat 10+ server without an IDE.

**📌 Q2: Why does the project use `jakarta.*` instead of `javax.*`?**
A: The project targets Jakarta EE 6.0, which migrated from the `javax.servlet` namespace to `jakarta.servlet`. Make sure you're using Tomcat 10 or later, as earlier versions use the old `javax.*` namespace and will throw `ClassNotFoundException` errors.

**📌 Q3: How is the unique user code generated?**
A: On registration, `loginservelet` generates a code by combining a random uppercase letter (A–Z) with a random 4-digit number (e.g., `K4821`). This code is stored in the database and shown to the user once — it's required for every future sign-in.

**📌 Q4: How does the book return deadline work?**
A: When a book is issued, `BookIssueServlet` automatically sets the issue date to today and the return date to 14 days from today using `java.time.LocalDate`.

**📌 Q5: What happens if I lose my unique code?**
A: Currently there is no recovery flow. A future improvement could add an admin panel or code-recovery via name lookup.

**📌 Q6: Can multiple users borrow the same book simultaneously?**
A: Yes — the current version does not enforce exclusive book availability. Adding a book availability check (i.e., checking that a `bk_code` is not already assigned to another user) would be a good enhancement.

## 🧑‍💻 Author
kinjal sethiya — titipo08

## 📄 License
This project is licensed under the [MIT License](LICENSE)
