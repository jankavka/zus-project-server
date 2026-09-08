# Databázová specifikace – tabulka `school_management` (vedení školy)

> Skutečný název tabulky je **`school_management`** (ne `school_management_entity`).
> Java entita se jmenuje `SchoolManagementEntity`, mapovaná přes `@Entity(name = "school_management")`.
> Zdroje: `entity/SchoolManagementEntity.java`, `constant/ManagementType.java`,
> Liquibase `db/changelog/changes/0001-baseline-schema.yaml` (changeSet `0001-school_management`),
> `zus_dump.sql` (kanonický mysqldump).

## Přehled

| Vlastnost        | Hodnota |
|------------------|---------|
| Název tabulky    | `school_management` |
| Engine           | InnoDB |
| Charset / collation | `utf8mb4` / `utf8mb4_general_ci` |
| Primární klíč    | `id` |
| Cizí klíče       | žádné |
| Indexy           | pouze PRIMARY (`id`) |
| Migrace          | Liquibase, changeSet `0001-school_management` (autor `jankavka`) |

## Sloupce

| Sloupec           | Typ                                     | Null | Default          | Klíč | Java pole (`SchoolManagementEntity`)      | Popis |
|-------------------|-----------------------------------------|------|------------------|------|------------------------------------------|-------|
| `id`              | `BIGINT`                                | NE   | `AUTO_INCREMENT` | PK   | `Long id`                                | Identifikátor, generovaný DB (`GenerationType.IDENTITY`) |
| `degree`          | `VARCHAR(255)`                          | ANO  | `NULL`           | –    | `String degree`                          | Titul (DTO: `@NotBlank`) |
| `email`           | `VARCHAR(255)`                          | ANO  | `NULL`           | –    | `String email`                           | E-mail (DTO: `@NotBlank`) |
| `name`            | `VARCHAR(255)`                          | ANO  | `NULL`           | –    | `String name`                            | Jméno a příjmení (DTO: `@NotBlank`) |
| `tel_number`      | `VARCHAR(255)`                          | ANO  | `NULL`           | –    | `String telNumber`                       | Telefonní číslo jako text (DTO: `@NotBlank`) |
| `issued_date`     | `DATE`                                  | ANO  | `NULL`           | –    | `LocalDate issuedDate`                   | Datum vytvoření záznamu; entita defaultně `LocalDate.now()` |
| `management_type` | `ENUM('deputyDirector','director')`     | ANO  | `NULL`           | –    | `ManagementType managementType` (`EnumType.STRING`) | Role ve vedení školy |

### Hodnoty `management_type`

| Hodnota          | Význam |
|------------------|--------|
| `director`       | ředitel/ředitelka |
| `deputyDirector` | zástupce/zástupkyně ředitele |

> Pozn.: Pořadí hodnot v definici `ENUM` je `('deputyDirector','director')` (dle baseline changelogu i dumpu),
> zatímco Java enum `ManagementType` je deklarován jako `director, deputyDirector`. Díky `EnumType.STRING`
> se ukládá název hodnoty, takže na pořadí nezáleží.

`SchoolManagementDTO` používá pro `issuedDate` typ `java.util.Date` (mapováno MapStructem);
entita a DB pracují s `DATE` / `LocalDate`.

## DDL (dle `zus_dump.sql`)

```sql
DROP TABLE IF EXISTS `school_management`;
CREATE TABLE `school_management` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `degree` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tel_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `issued_date` date DEFAULT NULL,
  `management_type` enum('deputyDirector','director') COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

## Ukázková data (`zus_dump.sql`)

| id | degree | email | name | tel_number | issued_date | management_type |
|----|--------|-------|------|------------|-------------|-----------------|
| 19 | Mgr. | jan.stepanek@zusdh.cz | Jan Štěpánek | +420 606 602 013 | 2025-05-08 | director |
| 20 | Mgr. | martina.babincova@zusdh.cz | Martina Babincová | +420 605 587 876 | 2025-05-08 | deputyDirector |
