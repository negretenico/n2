# N2: In-Memory JSON Document Store with Query & Console UI (MVP)

## Overview

N2 is a lightweight, embeddable in-memory JSON document store designed for ease of use in Java projects. It supports
CRUD operations and advanced querying using Jayway JsonPath.

This MVP includes:

* A core store interface (N2Store) with flexible querying
* An in-memory implementation (InMemory) backed by a concurrent map
* A simple web console based on Undertow for interacting with the store via HTTP endpoints
* Spring Boot integration for easy configuration and automatic console startup

## Features

* Put/Update documents by key

* Delete documents by key

* Query documents using JsonPath expressions across the entire store

* Typed retrieval of documents

* Configurable seed and schema files loaded on startup

* Console UI exposed over HTTP to run queries and commands

* Spring Boot integration with externalized properties

* Java 21 pattern matching for clean HTTP routing in console

## Getting Started

### Dependency

Add `n2-sdk` and `n2-core` to your project dependencies.

### Configuration

Configure properties via application.yml or application.properties:

```yaml
n2:
  seed: data/seed.json
  schema: data/schema.json
  type: IN_MEMORY
  console-enabled: true
  port: 8080
```

* seed - JSON file to load initial data

* schema - JSON schema file for validation (optional)

* type - store type (currently only IN_MEMORY supported)

* console-enabled - start the embedded web console

* port - port for the console server

## Usage

```java

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

The store and console will auto-configure based on properties.

## Console HTTP Endpoints

`POST /post` Insert or update a document. Payload:

```json
{
  "key": "user123",
  "value": {
    "name": "Nico",
    "age": 32
  }
}
```

* `POST /delete` Delete a document by key. Payload is the key as a plain string.

* `POST /query` Query the store using JsonPath expressions. Payload is the JsonPath string, e.g.:

```json
$.[
  'user123'
].name
````

* `GET /` Returns a simple confirmation message.

## Example Query

Retrieve all documents with age greater than 30:

`$.values[?(@.age > 30)]`

## Development

* `N2Store` interface defines the store contract.
* `InMemory` is the default store implementation.
* `N2Console` starts an Undertow HTTP server with the endpoints.
* `N2Config` loads seed and schema files from classpath resources.
* `N2ConsoleConfig` conditionally starts the console based on config.

### Testing

* Unit tests cover configuration and store logic.
* Manual testing for console endpoints using curl/Postman.

#### Example curl query:

```shell
curl -X POST -d '$.user123.name' http://localhost:8080/query
```