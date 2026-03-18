# Repository Guidelines

## Project Structure & Module Organization
This repository is a Maven multi-module backend built around Spring Boot 3.5 and Spring Cloud. Core services live in `bacon-register`, `bacon-gateway`, `bacon-auth`, `bacon-upms`, and `bacon-visual`. Shared libraries are under `bacon-common/*`. Use `bacon-boot` only for the monolithic profile. Configuration files live in each module’s `src/main/resources`, and database bootstrap scripts are in `db/` (`bacon.sql`, `bacon_config.sql`). Docker assets are kept alongside modules plus the root `docker-compose.yml`.

## Build, Test, and Development Commands
Run commands from the repository root unless a module-specific build is needed.

- `mvn clean verify`: compile all modules, run checks, and execute tests.
- `mvn spring-javaformat:apply`: apply the required Spring Java Format rules before committing.
- `mvn -pl bacon-gateway -am spring-boot:run`: start one service and build required dependencies.
- `mvn -Pboot -pl bacon-boot -am spring-boot:run`: run the monolithic launcher with the `boot` profile.
- `docker compose up`: start the local container stack defined in `docker-compose.yml`.

## Coding Style & Naming Conventions
Use Java 17, UTF-8, and the formatter enforced by `spring-javaformat-maven-plugin` during `validate`. Follow existing Java package conventions such as `com.github.thundax.bacon.*` and Maven coordinates under `com.github.thundax`. Class names use `UpperCamelCase`; methods, fields, and bean names use `lowerCamelCase`; constants use `UPPER_SNAKE_CASE`. Keep YAML and properties files aligned with existing keys and profile naming like `application-dev.yml`.

## Testing Guidelines
`spring-boot-starter-test` is available from the parent POM, so add JUnit-based tests under `src/test/java` in the module you change. Name unit tests `*Test` and broader Spring context tests `*IT` when they load infrastructure or multiple beans. Run `mvn test` for quick verification and `mvn clean verify` before opening a PR. If a change affects SQL or configuration wiring, include at least one regression test or document the manual verification steps in the PR.

## Commit & Pull Request Guidelines
Recent history mixes conventional commits (`docs:`, `refactor(db):`) with short upgrade summaries. Prefer `type(scope): imperative summary`, for example `fix(gateway): handle missing tenant header`. Open PRs against the `dev` branch. Include a concise description, linked issue when applicable, affected modules, setup or migration notes, and screenshots only for UI-facing changes or generated docs.
