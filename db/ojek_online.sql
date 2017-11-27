-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 25, 2017 at 06:08 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ojek_online`
--

-- --------------------------------------------------------

--
-- Table structure for table `driver_ready`
--

CREATE TABLE `driver_ready` (
  `account_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pref_location`
--

CREATE TABLE `pref_location` (
  `account_id` int(11) NOT NULL,
  `location` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pref_location`
--

INSERT INTO `pref_location` (`account_id`, `location`) VALUES
(3, 'Bandung'),
(3, 'Jakarta');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL,
  `passenger_id` int(11) NOT NULL,
  `driver_id` int(11) NOT NULL,
  `pick_location` varchar(50) NOT NULL,
  `dest_location` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `stars` tinyint(4) NOT NULL,
  `review` text NOT NULL,
  `hide_from_passenger` tinyint(1) NOT NULL,
  `hide_from_driver` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `passenger_id`, `driver_id`, `pick_location`, `dest_location`, `date`, `stars`, `review`, `hide_from_passenger`, `hide_from_driver`) VALUES
(1, 4, 3, 'Tamansari', 'Jakarta', '2017-11-06', 3, 'Bagus sekali servicenya', 0, 0),
(2, 9, 3, 'ITB', 'Jakarta', '2017-11-05', 4, 'Cepet nyampenya, nice', 0, 0),
(3, 4, 5, 'Tamansari', 'Bogor', '2017-11-07', 5, 'Luar biasa', 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `driver_ready`
--
ALTER TABLE `driver_ready`
  ADD PRIMARY KEY (`account_id`);

--
-- Indexes for table `pref_location`
--
ALTER TABLE `pref_location`
  ADD KEY `account_constraint` (`account_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `driver_id_constraint` (`driver_id`),
  ADD KEY `passenger_id_constraint` (`passenger_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `pref_location`
--
ALTER TABLE `pref_location`
  ADD CONSTRAINT `account_constraint` FOREIGN KEY (`account_id`) REFERENCES `ojek_account`.`account` (`account_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `driver_id_constraint` FOREIGN KEY (`driver_id`) REFERENCES `ojek_account`.`account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `passenger_id_constraint` FOREIGN KEY (`passenger_id`) REFERENCES `ojek_account`.`account` (`account_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
