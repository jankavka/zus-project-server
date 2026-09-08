# Databázová specifikace – tabulka `school_achievements` (úspěchy školy)

> Skutečný název tabulky je **`school_achievements`** (ne `school_achievements_entity`).
> Java entita se jmenuje `SchoolAchievementsEntity`, mapovaná přes `@Entity(name = "school_achievements")`.
> Zdroje: `entity/SchoolAchievementsEntity.java`, `entity/SchoolYearEntity.java`,
> `entity/repository/SchoolAchievementsRepository.java`, `dto/SchoolAchievementsDTO.java`,
> `dto/mapper/SchoolAchievementsMapper.java`, `controller/SchoolAchievementsController.java`,
> Liquibase `db/changelog/changes/0001-baseline-schema.yaml` (changeSet `0001-school_achievements`),
> `zus_dump.sql` (kanonický mysqldump).

## Přehled

| Vlastnost        | Hodnota |
|------------------|---------|
| Název tabulky    | `school_achievements` |
| Engine           | InnoDB |
| Charset / collation | `utf8mb4` / `utf8mb4_general_ci` |
| Primární klíč    | `id` |
| Cizí klíče       | `school_year` → `school_year(id)`, název omezení `FKjogbaoqpkgg4xpukw1fuga3m4` |
| Indexy           | `PRIMARY` (`id`), `FKjogbaoqpkgg4xpukw1fuga3m4` (`school_year`) |
| Migrace          | Liquibase, changeSet `0001-school_achievements` (autor `jankavka`) |

## Sloupce

Pořadí sloupců níže odpovídá skutečné DB (dump i baseline changelog): `id`, `content`, `issued_date`, `title`, `school_year`.
Java entita deklaruje pole v pořadí `id`, `title`, `content`, `issuedDate`, `schoolYear` – na pořadí sloupců v DB to nemá vliv.

| Sloupec       | Typ            | Null | Default          | Klíč | Java pole (`SchoolAchievementsEntity`) | Popis |
|---------------|----------------|------|------------------|------|---------------------------------------|-------|
| `id`          | `BIGINT`       | NE   | `AUTO_INCREMENT` | PK   | `Long id`                             | Identifikátor, generovaný DB (`GenerationType.IDENTITY`) |
| `content`     | `TEXT`         | ANO  | `NULL`           | –    | `String content` (`@Column(columnDefinition = "TEXT")`) | Text úspěchu, ukládá se jako HTML (WYSIWYG editor – obsahuje `<ul>`/`<li>` a HTML entity typu `&aacute;`) |
| `issued_date` | `DATE`         | ANO  | `NULL`           | –    | `LocalDate issuedDate`                | Datum vytvoření / zveřejnění záznamu; v entitě se defaultně nastaví na `LocalDate.now()` |
| `title`       | `VARCHAR(255)` | ANO  | `NULL`           | –    | `String title`                        | Nadpis úspěchu (v datech obvykle velkými písmeny, např. název soutěže) |
| `school_year` | `BIGINT`       | ANO  | `NULL`           | FK   | `SchoolYearEntity schoolYear` (`@ManyToOne`, `@JoinColumn(name = "school_year")`) | Odkaz na školní rok (`school_year.id`), do kterého úspěch spadá |

Pozn.: `SchoolAchievementsDTO` používá pro `issuedDate` typ `java.util.Date` (mapováno MapStructem přes
`SchoolAchievementsMapper`, `uses = SchoolYearMapper`), entita a DB pracují s `DATE` / `LocalDate`.
DTO nese vnořený `SchoolYearDTO schoolYear`, ne jen ID.

## Vazba na `school_year`

`school_achievements.school_year` je cizí klíč na tabulku `school_year` (číselník školních roků).
Vztah je v JPA obousměrný: `SchoolYearEntity` má `@OneToMany(mappedBy = "schoolYear") List<SchoolAchievementsEntity> schoolAchievements`.

Struktura nadřazené tabulky (dle `zus_dump.sql`):

```sql
CREATE TABLE `school_year` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `school_year` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

Ukázková data `school_year`: `(1,' 2019-2020'), (2,' 2021-2022'), (3,'2023-2024'), (4,'2024-2025')`
(hodnoty v datech nejsou normalizované – některé mají úvodní mezeru).

`SchoolAchievementsRepository` nabízí nativní dotaz
`SELECT * FROM school_achievements WHERE school_year = :yearId` (`getAllAchievementsByYear`).

## DDL (dle `zus_dump.sql`)

```sql
DROP TABLE IF EXISTS `school_achievements`;
CREATE TABLE `school_achievements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_general_ci,
  `issued_date` date DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `school_year` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjogbaoqpkgg4xpukw1fuga3m4` (`school_year`),
  CONSTRAINT `FKjogbaoqpkgg4xpukw1fuga3m4` FOREIGN KEY (`school_year`) REFERENCES `school_year` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```

## Ukázková data (`zus_dump.sql`)

| id | title | issued_date | school_year | content (zkráceno) |
|----|-------|-------------|-------------|--------------------|
| 1 | MODERNÍ ZPĚV (SOUTĚŽNÍ PŘEHLÍDKA ZUŠ MORAVSKOSLEZSKÉHO KRAJE, FRÝDLANT N.O.) | 2025-03-27 | 4 | `<ul><li>Karolína Moniaková – kategorie IX. a) … zlaté pásmo</li><li>Klára Drabinová … bronzové pásmo</li></ul>` |
| 7 | PŘIJETÍ KE STUDIU NA KONZERVATOŘI | 2025-05-29 | 4 | `<ul><li>Anna Hrabcová ze třídy Mgr. Josefa Vojvodíka: Janáčkova konzervatoř v Ostravě, hra na hoboj</li></ul>` |

> V dumpu je `content` uložen s HTML entitami (`&aacute;`, `&nbsp;` …) a escapovanými `\n`; výše je pro čitelnost dekódováno a zkráceno.

## REST API (`SchoolAchievementsController`, base path `/api/school-achievements`)

| Metoda | Cesta            | Ochrana            | Popis |
|--------|------------------|--------------------|-------|
| POST   | `/create`        | `@Secured("ROLE_ADMIN")` | Vytvoření úspěchu |
| PUT    | `/edit/{id}`     | `@Secured("ROLE_ADMIN")` | Úprava úspěchu |
| DELETE | `/delete/{id}`   | `@Secured("ROLE_ADMIN")` | Smazání úspěchu |
| GET    | `/{id}`          | veřejné            | Detail jednoho úspěchu |
| GET    | `/year/{yearId}` | veřejné            | Seznam úspěchů pro daný školní rok (`school_year = yearId`) |
