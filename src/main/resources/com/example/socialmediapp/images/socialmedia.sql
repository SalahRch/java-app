-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : ven. 15 déc. 2023 à 16:21
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `socialmedia`
--

-- --------------------------------------------------------

--
-- Structure de la table `following`
--

CREATE TABLE `following` (
  `user_id` int(11) DEFAULT NULL,
  `followed_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `following`
--

INSERT INTO `following` (`user_id`, `followed_id`) VALUES
(7, 1);

-- --------------------------------------------------------

--
-- Structure de la table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `sender_id` int(11) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notifications`
--

INSERT INTO `notifications` (`id`, `type`, `sender_id`, `receiver_id`, `content`, `timestamp`) VALUES
(8, 'follow', 1, 7, 'lorem1 has requested to follow you !', '2023-12-09 14:59:21');

-- --------------------------------------------------------

--
-- Structure de la table `post`
--

CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `content` text DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp(),
  `file` blob DEFAULT NULL,
  `image` blob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `User_ID` int(11) NOT NULL,
  `Username` varchar(50) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Full_Name` varchar(100) DEFAULT NULL,
  `Profile_Picture_URL` varchar(255) DEFAULT NULL,
  `Date_of_Birth` date DEFAULT NULL,
  `Join_Date` timestamp NOT NULL DEFAULT current_timestamp(),
  `Last_Login` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `Status` enum('Active','Inactive') DEFAULT NULL,
  `Privacy_Settings` varchar(255) DEFAULT NULL,
  `Number_of_Posts` int(11) DEFAULT 0,
  `Number_of_Followers` int(11) DEFAULT 0,
  `Number_of_Following` int(11) DEFAULT 0,
  `Phone_Number` varchar(20) DEFAULT NULL,
  `Department` varchar(100) DEFAULT NULL,
  `Clubs_Extracurricular_Activities` varchar(255) DEFAULT NULL,
  `Work_Experience_Internship` varchar(255) DEFAULT NULL,
  `Skills` varchar(255) DEFAULT NULL,
  `Bio_Summary` text DEFAULT NULL,
  `Professional_Interests` text DEFAULT NULL,
  `Portfolio_Projects` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`User_ID`, `Username`, `Password`, `Email`, `Full_Name`, `Profile_Picture_URL`, `Date_of_Birth`, `Join_Date`, `Last_Login`, `Status`, `Privacy_Settings`, `Number_of_Posts`, `Number_of_Followers`, `Number_of_Following`, `Phone_Number`, `Department`, `Clubs_Extracurricular_Activities`, `Work_Experience_Internship`, `Skills`, `Bio_Summary`, `Professional_Interests`, `Portfolio_Projects`) VALUES
(1, 'lorem1', '55a882fc2c0bfd916d7ca509e2636df9ca17fc9dd1a2d0d5f538fa0cdf8badfe', 'lorem1@etu.uae.ac.ma', 'Salah rch', 'file:///C:\\Users\\srouc\\Desktop\\bs\\battleship\\socialmediAPP\\src\\main\\resources\\com\\example\\socialmediapp\\imageswinniejr..jpg', '2002-04-20', '2023-11-29 16:54:17', '2023-12-09 14:59:18', NULL, NULL, 0, 1, 0, '0660902690', 'Informatique', NULL, NULL, NULL, 'temporary bio', NULL, NULL),
(7, 'admin', '55a882fc2c0bfd916d7ca509e2636df9ca17fc9dd1a2d0d5f538fa0cdf8badfe', 'admin@etu.uae.ac.ma', 'administrator', NULL, '2002-11-13', '2023-11-29 20:33:58', '2023-12-09 14:59:18', NULL, NULL, 0, 0, 1, NULL, 'Informatique', NULL, NULL, NULL, NULL, NULL, NULL),
(9, 'afafblota', '8d33790a939c247de7927b57702864c8a4c59ef8847c50b6bb7424b4622e0a1a', 'afafblota@etu.uae.ac.ma', 'Afaf rasslblota', NULL, '2018-12-06', '2023-12-09 15:59:43', '2023-12-09 15:59:43', NULL, NULL, 0, 0, 0, NULL, 'Génie civil', NULL, NULL, NULL, NULL, NULL, NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`User_ID`),
  ADD UNIQUE KEY `Username` (`Username`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `User_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
