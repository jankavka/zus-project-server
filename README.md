# ZUS Project Server

REST API backend for a Czech Basic Art School (Základní umělecká škola) website. The system manages school data, personnel, achievements, photo galleries, documents, calendar events, and user accounts.

## Tech Stack

- **Java 21** + **Spring Boot 3.4.3**
- **Spring Data JPA** with Hibernate ORM
- **MySQL 8.0**
- **Spring Security** with BCrypt encryption
- **Google Calendar API** & **YouTube API**
- **MapStruct** for DTO mapping
- **Lombok**
- **Apache PDFBox** for PDF handling
- **Docker** & **Docker Compose**

## Project Structure

```
zus-project-server/
├── src/main/java/cz/kavka/
│   ├── configuration/     # Configuration (Security, Google APIs, Web)
│   ├── constant/          # Enums and constants
│   ├── controller/        # REST API endpoints
│   ├── dto/               # Data Transfer Objects + mappers
│   ├── entity/            # JPA entities
│   └── service/           # Business logic
├── src/main/resources/
│   ├── application.yaml   # Application configuration
│   └── service_account_json/  # Google API credentials
├── data/                  # JSON data and PDF files
├── uploads/               # Uploaded files (photos)
├── nginx/                 # Nginx reverse proxy configuration
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## Main Entities

| Entity | Description |
|--------|-------------|
| `UserEntity` | User accounts and authentication |
| `ArticleEntity` | News and articles |
| `SchoolYearEntity` | School years |
| `SchoolAchievementsEntity` | School achievements |
| `TeachersEntity` | Teachers |
| `SchoolManagementEntity` | School management members |
| `AlbumEntity` | Photo albums |
| `ImageEntity` | Photos in albums |
| `FileEntity` | PDF documents |

## API Endpoints

### Authentication
- `POST /api/user` - Register user
- `POST /api/auth` - Login
- `DELETE /api/auth` - Logout
- `GET /api/auth` - Current user

### Articles
- `GET /api/articles` - List articles (paginated)
- `GET /api/articles/{id}` - Article details
- `POST /api/articles/create` - Create article (ADMIN)
- `PUT /api/articles/edit/{id}` - Update article (ADMIN)
- `DELETE /api/articles/delete/{id}` - Delete article (ADMIN)

### School Management
- `GET /api/school-management` - List management members
- `POST /api/school-management/create` - Add member (ADMIN)
- `PUT /api/school-management/{id}/edit` - Update (ADMIN)
- `DELETE /api/school-management/{id}/delete` - Delete (ADMIN)

### School Achievements
- `GET /api/school-achievements/{id}` - Achievement details
- `GET /api/school-achievements/year/{yearId}` - Achievements by year
- `POST /api/school-achievements/create` - Create (ADMIN)
- `PUT /api/school-achievements/edit/{id}` - Update (ADMIN)
- `DELETE /api/school-achievements/delete/{id}` - Delete (ADMIN)

### School Years
- `GET /api/school-year` - List school years
- `POST /api/school-year/create` - Create year (ADMIN)
- `DELETE /api/school-year/{id}` - Delete year (ADMIN)

### Teachers
- `GET /api/teachers` - List teachers
- `POST /api/teachers/create` - Add teacher (ADMIN)
- `PUT /api/teachers/{id}/edit` - Update (ADMIN)
- `DELETE /api/teachers/{id}/delete` - Delete (ADMIN)

### Photo Gallery
- `GET /api/photos/get-albums` - List albums
- `GET /api/photos/get-images/{albumName}` - Photos in album
- `POST /api/photos/new-album` - Create album (ADMIN)
- `POST /api/photos/add-photos` - Upload photos (ADMIN)
- `DELETE /api/photos/delete-album/{albumName}` - Delete album (ADMIN)
- `DELETE /api/photos/delete-image/{id}` - Delete photo (ADMIN)

### Files (PDF)
- `GET /api/files` - List files
- `GET /api/files/{fileName}` - Download file
- `POST /api/files` - Upload file (ADMIN)
- `DELETE /api/files/{id}` - Delete file (ADMIN)

### Static Content
- `GET /api/static/basic-data` - School basic information
- `GET /api/static/required-info` - Required public information
- `GET /api/static/{key}` - Page content by key
- `PUT /api/static/update/{key}` - Update content (ADMIN)

### Calendar and YouTube
- `GET /api/calendar/events` - Google Calendar events
- `GET /api/youtube/videos` - YouTube channel videos

### Search
- `GET /search?query=...` - Redirect to Google site search

## Security

- Email/password authentication with BCrypt hashing
- Roles: `ROLE_USER`, `ROLE_ADMIN`
- All modification operations require `ROLE_ADMIN`
- Method-level security using `@Secured` annotations

## Getting Started

### Requirements
- Java 21
- Maven
- Docker & Docker Compose
- MySQL 8.0 (or use Docker)

### Local Development

```bash
# Build the project
mvn clean package

# Run with Docker
docker-compose up

# Or run JAR directly
java -jar target/zusProjectServer-0.0.1-SNAPSHOT.jar
```

### Docker Compose Services

| Service | Port | Description |
|---------|------|-------------|
| frontend | 80, 443 | React app (Nginx) |
| backend | 8080 | Spring Boot API |
| db | 3306 | MySQL database |

## Configuration

Main configuration is in `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://db:3306/zusProjectServer
    username: root
    password: ${DB_PASSWORD}

youtube:
  api-key: ${YOUTUBE_API_KEY}
  channel-id: ${YOUTUBE_CHANNEL_ID}

google:
  calendar:
    calendar-id: ${GOOGLE_CALENDAR_ID}
```

## Integrations

### Google Calendar
- Fetches events from Google Calendar
- OAuth2 credentials stored in `service_account_json/`

### YouTube
- Fetches videos from school's YouTube channel
- Requires API key and channel ID configuration

## Data Storage

**Database (MySQL):**
- Users, articles, teachers, management, school years, achievements, albums, photos, files

**JSON Files:**
- `/data/basic-data.json` - school basic data
- `/data/required-information.json` - required public information
- `/data/entrance-exam.json` - entrance exam info
- `/data/title-and-content.json` - page content

**File System:**
- `/uploads/` - uploaded photos (organized by album)
- `/data/pdf_files/` - PDF documents

## License

Proprietary software.
