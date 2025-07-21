# Recruitment task for Atipera
A simple Spring Boot 3.5 REST API built with Java 21 that returns a list of public not forked GitHub repositories and their branches for a given username.

---

## Tech Stack
- Java 21
- Spring Boot 3.5
- Gradle
- JUnit

---

## Getting Started

### Prerequisites
- Java 21
- Git

### Clone the Repository
```bash
git clone https://github.com/loudsheep/github-api-task
cd github-api-task
```

### Build the project
```bash
./gradlew build
```

### Run the application
```bash
./gradlew bootRun
```
The application will start on: `http://localhost:8080`

---

## API Usage
### Get Public Not Forked Repositories for a User

**Endpoint:**
`GET /api/github/{username}/repos`

**Example:**
```bash
curl http://localhost:8080/api/github/octocat/repos
```

**Response:**
```json
[
  {
    "name": "hello-worId",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "7e068727fdb347b685b658d2981f8c85f7bf0585"
      }
    ]
  }
]
```

> Note: Replace `{username}` with any GitHub username.

---

## Testing
Run all tests using Gradle:
```bash
./gradlew test
```

---

## License
This project is licensed under the MIT License.