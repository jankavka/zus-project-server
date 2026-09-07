# ZUS Project Server

REST API backend for a Czech Basic Art School (Základní umělecká škola) website. The system manages school data, personnel, achievements, photo galleries, documents, calendar events, and user accounts.

## Tech Stack

- **Java 21** + **Spring Boot 3.4.3**
- **Spring Data JPA** with Hibernate ORM
- **Liquibase** for database schema migrations
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
│   ├── db/changelog/      # Liquibase migrations
│   └── service_account_json/  # Google API credentials
├── data/                  # JSON data and PDF files
├── uploads/               # Uploaded files (photos)
├── carousel-photos/       # Uploaded carousel photos
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
| `CarouselPhotoEntity` | Photos shown in the frontend carousel |

## API Endpoints

### Health
- `GET /` / `GET /api` - Plain-text health check ("OK, Backend works")

### Authentication
- `POST /api/user` - Register user
- `POST /api/auth` - Login
- `DELETE /api/auth` - Logout
- `GET /api/auth` - Current user

### Articles
- `GET /api/articles` - List articles (paginated)
- `GET /api/articles/{id}` - Article details
- `GET /api/articles/album-name/{titleImageAlbumName}` - Articles whose title image is in the given album
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
- `GET /api/school-year/{id}` - School year details
- `POST /api/school-year/create` - Create year (ADMIN)
- `DELETE /api/school-year/{id}` - Delete year (ADMIN)

### Teachers
- `GET /api/teachers` - List teachers
- `POST /api/teachers/create` - Add teacher (ADMIN)
- `PUT /api/teachers/{id}/edit` - Update (ADMIN)
- `DELETE /api/teachers/{id}/delete` - Delete (ADMIN)

### Photo Gallery
- `GET /api/photos/get-albums` - List albums
- `GET /api/photos/all-albums-names` - List album names only
- `GET /api/photos/get-album/{albumName}` - Album details
- `GET /api/photos/get-images/{albumName}` - Photos in album
- `GET /api/photos/get-one-image/{albumName}` - Single (cover) image of an album
- `GET /api/photos/search` - Search albums
- `POST /api/photos/new-album` - Create album (ADMIN)
- `POST /api/photos/add-photos` - Upload photos (ADMIN)
- `PUT /api/photos/edit-album/{albumName}` - Update album (ADMIN)
- `DELETE /api/photos/delete-album/{albumName}` - Delete album (ADMIN)
- `DELETE /api/photos/delete-image/{id}` - Delete photo (ADMIN)

### Carousel Photos
- `GET /api/carousel-photos` - List carousel photos
- `POST /api/carousel-photos` - Upload one or more photos; optional `names[]` per file, defaults to original filename (ADMIN)
- `PUT /api/carousel-photos/{id}/visibility` - Toggle photo visibility (`isHidden`) (ADMIN)
- `DELETE /api/carousel-photos/{id}` - Delete photo (ADMIN)

### Files (PDF)
- `GET /api/files` - List files
- `GET /api/files/section/{section}` - List files in a section
- `GET /api/files/{fileName}` - Serve file inline (PDF)
- `POST /api/files` - Upload file (ADMIN)
- `DELETE /api/files/{id}` - Delete file (ADMIN)

### Static Content
- `GET /api/static/basic-data` - School basic information
- `POST /api/static/basic-data/create-or-edit` - Update basic information (ADMIN)
- `GET /api/static/required-info` - Required public information
- `POST /api/static/required-info/create-or-edit` - Update required information (ADMIN)
- `GET /api/static/keys` - List available content keys
- `GET /api/static/{key}` - Page content by key
- `PUT /api/static/update/{key}` - Update content (ADMIN)

### Entrance Exam
- `GET /api/entrance-exam` - Entrance exam info
- `GET /api/entrance-exam/is-hidden` - Whether the entrance exam block is hidden
- `POST /api/entrance-exam` - Update entrance exam info (ADMIN)

### Calendar and YouTube
- `GET /api/calendar/events` - Upcoming Google Calendar events
- `GET /api/calendar/all-events` - All Google Calendar events
- `GET /api/youtube/videos` - YouTube channel videos

### Search
- `GET /search?query=...` - Redirect to Google site search

## Security

- Session-based (servlet container-managed) email/password authentication with BCrypt hashing
- Roles: `ROLE_USER`, `ROLE_ADMIN`
- `ApplicationSecurityConfiguration` `permitAll()`s the whole filter chain (CSRF disabled);
  actual protection is per-endpoint method-level security (`@Secured("ROLE_ADMIN")`)
- All modification operations require `ROLE_ADMIN`

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
java -jar target/zus-project-server-0.0.1-SNAPSHOT.jar
```

### Docker Compose Services

| Service | Port | Description |
|---------|------|-------------|
| frontend | 80, 443 | React app + Nginx reverse proxy (also proxies `/api` and `/uploads` to the backend) |
| backend | 8080 (internal `expose` only) | Spring Boot API |
| db | 3306 | MySQL 8.0 database |
| certbot | – | Let's Encrypt certificate renewal |

## Configuration

`src/main/resources/application.yaml` holds the static config (JPA, multipart limits,
JSON file paths, hardcoded YouTube API key/channel, `app.search.siteDomain`). It does
**not** contain a datasource block — the DB connection is supplied entirely through
environment variables (see `docker-compose.yml`).

Environment variables (set via `.env` / `docker-compose.override.yml`, both gitignored):

| Variable | Used by | Notes |
|----------|---------|-------|
| `DB_PASSWORD` | backend + db | Password for the MySQL `zus` user |
| `MYSQL_ROOT_PASSWORD` | db | MySQL root password |
| `SPRING_DATASOURCE_URL` / `_USERNAME` / `_PASSWORD` | backend | Set in `docker-compose.yml`; username is `zus`, not `root` |
| `FILE_UPLOADS_DIR` | backend | Photo upload dir (default `uploads`) |
| `CAROUSEL_UPLOADS_DIR` | backend | Carousel photo dir (default `carousel-photos`) |
| `VITE_GOOGLE_CSE_ID` | frontend build | Google Custom Search Engine id, baked into the JS bundle |
| `GOOGLE_CALENDAR_CREDENTIALS_JSON` | backend | Raw service-account JSON for the Google Calendar API; required in production (see Integrations) |

> A fresh local MySQL volume also needs `MYSQL_USER=zus` / `MYSQL_PASSWORD` (via
> `docker-compose.override.yml`), otherwise the backend gets "access denied" — the
> base `docker-compose.yml` provisions that user, but a pre-existing volume from an
> older setup may not have it.

## Integrations

### Google Calendar
- Fetches events from the fixed calendar `akce@zusdh.cz`
- Handles both timed and all-day events
- Credentials are resolved in this order (`CalendarServiceImpl.authorize`):
  1. `GOOGLE_CALENDAR_CREDENTIALS_JSON` env var — the raw service-account JSON.
     Set this in production via the `.env` file next to `docker-compose.yml`
     (Compose passes it through to the backend). Preferred, since the file is gitignored.
  2. Fallback: a **service-account** JSON on the classpath at
     `src/main/resources/service_account_json/rscalendar-credentials2.json`
     (gitignored — used for local dev).
- With neither present, `/api/calendar/*` returns 500.

### YouTube
- Fetches videos from the school's YouTube channel
- API key and channel id are hardcoded in `application.yaml` (`youtube.api.key` / `youtube.api.channel-id`), no OAuth

### Google Custom Search
- `GET /search?query=...` (`SiteSearchController`) redirects to a Google `site:zusdh.cz` search
- The frontend also uses a Google CSE widget configured with `VITE_GOOGLE_CSE_ID` at build time

## Data Storage

**Database (MySQL):**
- Users, articles, teachers, management, school years, achievements, albums, photos, files, carousel photos

**JSON Files:**
- `/data/basic-data.json` - school basic data
- `/data/required-information.json` - required public information
- `/data/entrance-exam.json` - entrance exam info
- `/data/title-and-content.json` - page content

**File System:**
- `/uploads/` - uploaded photos (organized by album)
- `/carousel-photos/` - uploaded carousel photos
- `/data/pdf_files/` - PDF documents

## Database Migrations

Schema changes are managed by Liquibase, not Hibernate auto-DDL (`spring.jpa.hibernate.ddl-auto` is set to `validate`, which only checks the schema at startup). Changelogs live under `src/main/resources/db/changelog/`, included from `db.changelog-master.yaml`. To change the schema, add a new changeset file under `db/changelog/changes/` and include it in the master changelog — never edit an already-released changeset.

## Deployment

`.github/workflows/deploy.yml` runs on every push to `main` (and on manual dispatch):

1. **test** job - `mvn -B test` on Temurin JDK 21
2. **deploy** job (needs `test`) - SSHes into the Hetzner host and runs `~/apps/deploy.sh`

Requires the repo secrets `DEPLOY_HOST`, `DEPLOY_USER`, `DEPLOY_SSH_KEY`.

## License

Proprietary software.
