# Spring Boot API Backend

Dieses Repository enthält eine Spring Boot-Anwendung, die eine API mit verschiedenen Schnittstellen bereitstellt. Die API wurde entwickelt, um eine robuste und skalierbare Grundlage als Backend-Services zu bieten.

## Inhaltsverzeichnis

1. [Features](#features)
2. [Voraussetzungen](#voraussetzungen)
3. [Installation](#installation)
4. [Konfiguration](#konfiguration)
5. [Verwendung](#verwendung)
6. [API-Dokumentation](#api-dokumentation)
7. [Tests](#tests)
8. [Technologien](#technologien)
9. [Mitwirken](#mitwirken)
10. [Lizenz](#lizenz)

## Features

- RESTful API für späteres Webapp-Frontend
- Datenbankintegration 
- Fehlerbehandlung und Logging
- Unterstützung von OAuth2/JWT für Authentifizierung und Autorisierung
- Umfangreiche Dokumentation mit OpenAPI/Swagger
- Testabdeckung mit JUnit

## Voraussetzungen

- Java 21
- Gradle
- Eine relationale Datenbank
- Git

## Installation

1. Repository klonen:
   ```bash
   git clone https://github.com/dein-benutzername/dein-repository.git
   cd dein-repository
   ```
2. Abhängigkeiten installieren:
   ```bash
   mvn clean install
   ```
3. Anwendung starten:
   ```bash
   mvn spring-boot:run
   ```

## Konfiguration

Die Konfigurationswerte befinden sich in der Datei `src/main/resources/application.properties`. Beispielsweise:

```properties
# Server-Konfiguration
server.port=8080

# Datenbank-Konfiguration
spring.datasource.url=jdbc:mysql://localhost:3306/deine_datenbank
spring.datasource.username=dein_benutzername
spring.datasource.password=dein_passwort

# JWT-Konfiguration
jwt.secret=dein-geheimes-passwort
jwt.expiration=3600000
```

Erstelle eine Datei `.env`, um sensible Informationen lokal zu speichern.

## Verwendung

Nach dem Start der Anwendung ist die API unter `http://localhost:8080` erreichbar. Verwende Tools wie Postman oder cURL, um die Endpunkte zu testen.

### Beispielanfrage:

```bash
curl -X GET http://localhost:8080/api/beispiel-endpunkt \
  -H "Authorization: Bearer <JWT-Token>"
```

## API-Dokumentation

Die API-Dokumentation ist unter `http://localhost:8080/swagger-ui.html` verfügbar, wenn die Anwendung läuft. Sie beschreibt die verfügbaren Endpunkte, deren Parameter und Rückgabewerte.

## Tests

Tests können mit Maven ausgeführt werden:

```bash
mvn test
```

## Technologien

- **Framework**: Spring Boot (Web, Security, Data JPA, etc.)
- **Build-Tool**: Gradle
- **Datenbank**: PostgreSQL
- **Dokumentation**: OpenAPI/Swagger
- **Test-Frameworks**: JUnit

## Mitwirken

Beiträge sind willkommen! Bitte folge diesen Schritten, um mitzuwirken:

1. Forke das Repository
2. Erstelle einen neuen Branch:
   ```bash
   git checkout -b feature/mein-feature
   ```
3. Committe deine Änderungen:
   ```bash
   git commit -m "Feature: Mein neues Feature"
   ```
4. Push deinen Branch:
   ```bash
   git push origin feature/mein-feature
   ```
5. Erstelle einen Pull-Request.

## Lizenz

Dieses Projekt ist unter der [MIT-Lizenz](LICENSE) lizenziert. Weitere Informationen findest du in der Lizenzdatei.

---

Vielen Dank, dass du dieses Projekt nutzt und unterstützt! Solltest du Fragen haben, zögere nicht, ein Issue zu erstellen.
