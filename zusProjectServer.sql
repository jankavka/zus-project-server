-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Počítač: localhost
-- Vytvořeno: Úte 27. kvě 2025, 08:52
-- Verze serveru: 10.4.28-MariaDB
-- Verze PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `zusProjectServer`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `article`
--

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `content` text DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `issued_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `article`
--

INSERT INTO `article` (`id`, `content`, `title`, `issued_date`) VALUES
(13, '<p>V sobotu 18. ledna jsme si zahr&aacute;li na biskupstv&iacute; v Ostravě v r&aacute;mci diec&eacute;zn&iacute;ho Dne pro vyučuj&iacute;c&iacute; n&aacute;boženstv&iacute; a katechety.</p>\n<p>Děkujeme za mil&eacute; pozv&aacute;n&iacute;.</p>', 'ZAHRÁLI JSME SI NA BISKUPSTVÍ', '2025-03-18'),
(14, '13. 3. proběhla na ZUŠ Frýdek-Místek Okresní přehlídka žáků základních uměleckých škol ve hře na klavír. Náš žák Matúš Moravčík ze třídy paní učitelky Mgr. Karly Zouharové postoupil do krajského kola. Srdečně gratulujeme a přejeme hodně štěstí v krajském kole!', 'ÚSPĚCH V OKRESNÍ KLAVÍRNÍ PŘEHLÍDCE', '2025-03-28'),
(15, '11. a 12. března se konalo okresní kolo soutěže ZUŠ ve hře na dechové nástroje. Všichni naši žáci získali první místo a většina z nich také postupuje do krajského kola. Moc gratulujeme! Děkujeme také vyučujícím a v neposlední řadě korepetitorkám Silvii Hrčkové, dipl.um., Ester Kubátkové, dipl.um., Martě Pintzkerové a Mgr. Kateřině Vojvodíkové. HRA NA HOBOJ Eliška Vojvodíková ze třídy Mgr. Josefa Vojvodíka, X. kategorie, 1. míst HRA NA LESNÍ ROH Vít Gurník ze třídy Víta Jančálka, DiS., VI. kategorie, 1. místo HRA NA TRUBKU Jan Sobek ze třídy Mgr. Jana Štěpánka, XI. kategorie, 1. místo', 'ÚSPĚCHY NAŠICH ŽÁKŮ V OKRESNÍM KOLE SOUTĚŽE ZUŠ VE HŘE NA DECHOVÉ NÁSTROJE', '2025-03-28'),
(16, '<p>Někteř&iacute; lid&eacute; přich&aacute;zej&iacute; na svět, aby rozd&aacute;vali radost a inspiraci. Jiř&iacute; Hajdu&scaron;ek byl jedn&iacute;m z nich. Byl nejen vynikaj&iacute;c&iacute;m houslistou a učitelem, ale předev&scaron;&iacute;m laskav&yacute;m, mil&yacute;m a upř&iacute;mn&yacute;m člověkem, kter&yacute; sv&yacute;m nad&scaron;en&iacute;m a l&aacute;skou k hudbě inspiroval nespočet ž&aacute;ků i kolegů. Bude n&aacute;m moc chybět. Upř&iacute;mnou soustrast jeho rodině a bl&iacute;zk&yacute;m.</p>', 'ODEŠEL VYNIKAJÍCÍ HUDEBNÍK A PEDAGOG JIŘÍ HAJDUŠEK', '2025-03-28'),
(17, 'Chtěli bychom i touto cestou moc poděkovat farnosti Místek za velkorysý finanční příspěvek na pořízení a instalaci nového, efektivnějšího a úspornějšího osvětlení v naší škole. Děkujeme!!!', 'PODĚKOVÁNÍ', '2025-03-28'),
(18, '<p>Srdečně zveme na adventn&iacute; koncerty do kostela sv. Jakuba v M&iacute;stku, kter&eacute; proběhnou ve středy 4. a 18. prosince od 18:00.</p>\n<p>Tě&scaron;&iacute;me se na V&aacute;s!</p>', 'ADVENTNÍ KONCERTY', '2025-03-28');

-- --------------------------------------------------------

--
-- Struktura tabulky `school_achievements`
--

CREATE TABLE `school_achievements` (
  `id` bigint(20) NOT NULL,
  `content` text DEFAULT NULL,
  `issued_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `school_year` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `school_achievements`
--

INSERT INTO `school_achievements` (`id`, `content`, `issued_date`, `title`, `school_year`) VALUES
(1, 'Toto je obsah', '2025-03-27', 'Titul úspěchu', 4),
(2, 'Toto je obsah', '2025-03-27', 'Titul úspěchu', 4),
(3, 'Toto je obsah', '2025-03-27', 'Titul úspěchu', 4),
(4, 'Toto je obsah', '2025-03-27', 'Titul úspěchu', 4),
(5, 'Toto je obsah', '2025-03-27', 'Titul úspěchu', 4);

-- --------------------------------------------------------

--
-- Struktura tabulky `school_management`
--

CREATE TABLE `school_management` (
  `id` bigint(20) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tel_number` varchar(255) DEFAULT NULL,
  `issued_date` date DEFAULT NULL,
  `management_type` enum('deputyDirector','director') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `school_management`
--

INSERT INTO `school_management` (`id`, `degree`, `email`, `name`, `tel_number`, `issued_date`, `management_type`) VALUES
(19, 'Mgr.', 'jan.stepanek@zusdh.cz', 'Jan Štěpánek', '+420 606 602 013', '2025-05-08', 'director'),
(20, 'Mgr.', 'martina.babincova@zusdh.cz', 'Martina Babincová', '+420 605 587 876', '2025-05-08', 'deputyDirector');

-- --------------------------------------------------------

--
-- Struktura tabulky `school_year`
--

CREATE TABLE `school_year` (
  `id` bigint(20) NOT NULL,
  `school_year` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `school_year`
--

INSERT INTO `school_year` (`id`, `school_year`) VALUES
(4, '2024-2025');

-- --------------------------------------------------------

--
-- Struktura tabulky `teachers`
--

CREATE TABLE `teachers` (
  `id` bigint(20) NOT NULL,
  `degree` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tel_number` varchar(255) DEFAULT NULL,
  `issued_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `teachers`
--

INSERT INTO `teachers` (`id`, `degree`, `email`, `name`, `tel_number`, `issued_date`) VALUES
(12, 'Mgr', 'jankavka@seznam.cz', 'Jan Prdel', '731103217', '2025-04-23'),
(13, 'Mgr', 'jan.stepanek85@gmail.com', 'Jan Štěpánek', '+420606602013', '2025-04-23');

-- --------------------------------------------------------

--
-- Struktura tabulky `user_entity`
--

CREATE TABLE `user_entity` (
  `id` bigint(20) NOT NULL,
  `admin` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexy pro exportované tabulky
--

--
-- Indexy pro tabulku `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pro tabulku `school_achievements`
--
ALTER TABLE `school_achievements`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjogbaoqpkgg4xpukw1fuga3m4` (`school_year`);

--
-- Indexy pro tabulku `school_management`
--
ALTER TABLE `school_management`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pro tabulku `school_year`
--
ALTER TABLE `school_year`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pro tabulku `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pro tabulku `user_entity`
--
ALTER TABLE `user_entity`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `article`
--
ALTER TABLE `article`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT pro tabulku `school_achievements`
--
ALTER TABLE `school_achievements`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pro tabulku `school_management`
--
ALTER TABLE `school_management`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT pro tabulku `school_year`
--
ALTER TABLE `school_year`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pro tabulku `teachers`
--
ALTER TABLE `teachers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT pro tabulku `user_entity`
--
ALTER TABLE `user_entity`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- Omezení pro exportované tabulky
--

--
-- Omezení pro tabulku `school_achievements`
--
ALTER TABLE `school_achievements`
  ADD CONSTRAINT `FKjogbaoqpkgg4xpukw1fuga3m4` FOREIGN KEY (`school_year`) REFERENCES `school_year` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
