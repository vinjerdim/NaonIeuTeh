-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 29, 2017 at 02:51 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ojek_account`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL,
  `full_name` varchar(50) NOT NULL,
  `username` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `is_driver` tinyint(1) NOT NULL,
  `picture_path` text,
  `driver_ready` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_id`, `full_name`, `username`, `email`, `password`, `phone`, `is_driver`, `picture_path`, `driver_ready`) VALUES
(3, 'Marvin Jerremy', 'vinjerdim', 'marjer03dim@gmail.com', 'vinjer', '081322198520', 1, NULL, 1),
(4, 'Martin Lutta', 'martin', 'martinlutta@gmail.com', 'lutta', '081322198520', 0, NULL, 0),
(5, 'Dayu', 'idaayu', 'dayu@example.com', 'dayu', '081322198520', 1, NULL, 0),
(8, 'Marvin Jerremy', 'vinjerdim17', 'budiman@gmail.com', 'VINJER', '081322198520', 1, NULL, 0),
(9, 'HM', 'hna', 'hna@gmail.com', 'VINJER', '081322198520', 0, NULL, 0),
(10, 'kevin', 'kevin', 'keviniswara@gmail.com', 'iswara', '08765456712', 1, NULL, 0),
(12, 'lalala', 'lalala', 'lalala', 'lalala', '09876543234', 0, NULL, 0),
(13, 'lilili', 'lilili', 'lilili', 'lilili', '0987654567', 1, NULL, 0),
(14, 'kevin', 'kevinm', 'kevinm', 'kevinm', '0987672212', 1, NULL, 0);

--
-- Triggers `account`
--
DELIMITER $$
CREATE TRIGGER `after_account_insert` AFTER INSERT ON `account` FOR EACH ROW BEGIN
    INSERT INTO account_token
    SET account_token.account_id = NEW.account_id,
    token = NULL, refresh_token = NULL;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `account_token`
--

CREATE TABLE `account_token` (
  `account_id` int(11) NOT NULL,
  `token` varchar(21) DEFAULT NULL,
  `refresh_token` varchar(21) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `user_agent` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `account_token`
--

INSERT INTO `account_token` (`account_id`, `token`, `refresh_token`, `ip`, `user_agent`) VALUES
(3, 'H4chril3GHna97ThPpVN', 'H4chril3GHna97ThPpVN', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36'),
(4, NULL, NULL, NULL, NULL),
(5, 'WTgyYElpANmrGdusau6i', 'WTgyYElpANmrGdusau6i', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0'),
(8, NULL, NULL, NULL, NULL),
(9, NULL, NULL, NULL, NULL),
(10, NULL, NULL, NULL, NULL),
(12, NULL, NULL, NULL, NULL),
(13, NULL, NULL, NULL, NULL),
(14, NULL, NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `account_token`
--
ALTER TABLE `account_token`
  ADD PRIMARY KEY (`account_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `account_token`
--
ALTER TABLE `account_token`
  ADD CONSTRAINT `id_constraint` FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
