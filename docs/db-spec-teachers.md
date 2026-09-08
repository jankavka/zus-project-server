# Databázová specifikace – tabulka `teachers`

> Skutečný název tabulky je **`teachers`** (ne `teachers_entity`).
> Java entita se jmenuje `TeachersEntity`, ale je mapovaná přes `@Entity(name = "teachers")`.
> Zdroje: `entity/TeachersEntity.java`, Liquibase `db/changelog/changes/0001-baseline-schema.yaml` (changeSet `0001-teachers`), `zus_dump.sql` (kanonický mysqldump).

## Přehled

| Vlastnost        | Hodnota |
|------------------|---------|
| Název tabulky    | `teachers` |
| Engine           | InnoDB |
| Charset / collation | `utf8mb4` / `utf8mb4_general_ci` |
| Primární klíč    | `id` |
| Cizí klíče       | žádné |
| Indexy           | pouze PRIMARY (`id`) |
| Migrace          | Liquibase, changeSet `0001-teachers` (autor `jankavka`) |

## Sloupce

| Sloupec       | Typ            | Null | Default            | Klíč | Java pole (`TeachersEntity`) | Popis |
|---------------|----------------|------|--------------------|------|------------------------------|-------|
| `id`          | `BIGINT`       | NE   | `AUTO_INCREMENT`   | PK   | `Long id`                    | Identifikátor, generovaný DB (`GenerationType.IDENTITY`) |
| `degree`      | `VARCHAR(255)` | ANO  | `NULL`             | –    | `String degree`              | Titul (např. `Mgr`) |
| `email`       | `VARCHAR(255)` | ANO  | `NULL`             | –    | `String email`               | E-mail učitele |
| `name`        | `VARCHAR(255)` | ANO  | `NULL`             | –    | `String name`                | Jméno a příjmení |
| `tel_number`  | `VARCHAR(255)` | ANO  | `NULL`             | –    | `String telNumber`           | Telefonní číslo (uloženo jako text, může obsahovat `+420`) |
| `issued_date` | `DATE`         | ANO  | `NULL`             | –    | `LocalDate issuedDate`       | Datum vytvoření záznamu; v entitě se defaultně nastaví na `LocalDate.now()` |

Pozn.: `TeachersDTO` používá pro `issuedDate` typ `java.util.Date` (mapováno MapStructem), entita a DB pracují s `DATE` / `LocalDate`.

## DDL (dle `zus_dump.sql`)

```sql
DROP TABLE IF EXISTS `teachers`;
CREATE TABLE `teachers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `degree` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tel_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `issued_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

## Ukázková data (`zus_dump.sql`)

| id | degree | email | name | tel_number | issued_date |
|----|--------|-------|------|------------|-------------|
| 12 | Mgr | jankavka@seznam.cz | Jan Kavka | 731103217 | 2025-04-23 |
| 13 | Mgr | jan.stepanek85@gmail.com | Jan Štěpánek | +420606602013 | 2025-04-23 |
