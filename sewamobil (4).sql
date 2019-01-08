-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 08, 2019 at 02:30 PM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sewamobil`
--

-- --------------------------------------------------------

--
-- Table structure for table `denda`
--

DROP TABLE IF EXISTS `denda`;
CREATE TABLE IF NOT EXISTS `denda` (
  `ID_SEWA` varchar(5) NOT NULL,
  `KETERLAMBATAN` varchar(20) DEFAULT NULL,
  `KERUSAKAN` varchar(20) DEFAULT NULL,
  `TOTAL_DENDA` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `kendaraan`
--

DROP TABLE IF EXISTS `kendaraan`;
CREATE TABLE IF NOT EXISTS `kendaraan` (
  `PLAT_NO` varchar(144) CHARACTER SET latin1 NOT NULL,
  `NAMA` varchar(144) DEFAULT NULL,
  `HARGA` varchar(15) CHARACTER SET latin1 NOT NULL,
  `KAPASITAS` varchar(3) CHARACTER SET latin1 NOT NULL,
  `TAHUN` varchar(4) CHARACTER SET latin1 NOT NULL,
  `TIPE` varchar(144) CHARACTER SET latin1 NOT NULL,
  `WARNA` varchar(8) CHARACTER SET latin1 NOT NULL,
  `ID_PERUSAHAAN` varchar(5) CHARACTER SET latin1 DEFAULT NULL,
  `ID_USER` varchar(8) CHARACTER SET latin1 NOT NULL,
  `STATUS` int(12) NOT NULL DEFAULT '0',
  PRIMARY KEY (`PLAT_NO`),
  KEY `ID_PERUSAHAAN` (`ID_PERUSAHAAN`),
  KEY `ID_USER` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `kendaraan`
--

INSERT INTO `kendaraan` (`PLAT_NO`, `NAMA`, `HARGA`, `KAPASITAS`, `TAHUN`, `TIPE`, `WARNA`, `ID_PERUSAHAAN`, `ID_USER`, `STATUS`) VALUES
('A0202CG', 'AVANZA', '100000', '10', '2003', 'Mobil Keluarga', 'HITAM', NULL, '2', 0),
('AA5656GV', 'Kharisma', '10000000', '1', '2019', 'Mobil Pickup', 'Pelangi', NULL, '22', 0),
('AD2929CG', 'Jerapah', '250000', '4', '2018', 'Mobil Keluarga', 'KUNING', NULL, '36', 0),
('AD8909', 'Unta', '1000000', '5', '2018', 'Mobil Pickup', 'Merah', NULL, '36', 0),
('B48I', 'Asus', '123000', '20', '2020', 'Mobil Keluarga', 'PINK', NULL, '25', 0),
('D4457DS', 'Tossa', '400000', '50', '2008', 'Mobil Keluarga', 'Hijau', NULL, '10', 0),
('G0987FG', 'Xenia', '300000', '6', '2012', 'Mobil Keluarga', 'Putih', NULL, '10', 1),
('G12345BA', 'Avanza', '300000', '6', '2010', 'Mobil Keluarga', 'Hitam', NULL, '10', 0),
('G1234BF', 'Juke', '550000', '8', '2018', 'Mobil Keluarga', 'Emas', NULL, '10', 1),
('GIV4AN', 'Fortuner', '500000', '6', '2015', 'Mobil Keluarga', 'Hitam', NULL, '10', 1),
('K8888GB', 'SUPRAX', '1000000', '10', '2018', 'Motor', 'HIJAU', NULL, '17', 1);

--
-- Triggers `kendaraan`
--
DROP TRIGGER IF EXISTS `delete_sewa_bd`;
DELIMITER $$
CREATE TRIGGER `delete_sewa_bd` BEFORE DELETE ON `kendaraan` FOR EACH ROW DELETE FROM sewa WHERE ID_KENDARAAN = Old.PLAT_NO
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `offline_`
--

DROP TABLE IF EXISTS `offline_`;
CREATE TABLE IF NOT EXISTS `offline_` (
  `OFFLINEID_TF` varchar(10) NOT NULL,
  `STATUS` varchar(10) NOT NULL,
  `TOTAL_TRANS` varchar(20) NOT NULL,
  PRIMARY KEY (`OFFLINEID_TF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `online_`
--

DROP TABLE IF EXISTS `online_`;
CREATE TABLE IF NOT EXISTS `online_` (
  `ONLINEID_TF` varchar(10) NOT NULL,
  `PENERIMA` varchar(10) NOT NULL,
  `FOTO_BUKTI` blob NOT NULL,
  `TOTAL_TRANS` varchar(20) NOT NULL,
  PRIMARY KEY (`ONLINEID_TF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pegawai`
--

DROP TABLE IF EXISTS `pegawai`;
CREATE TABLE IF NOT EXISTS `pegawai` (
  `SSN` int(9) NOT NULL AUTO_INCREMENT,
  `NAMA` varchar(144) NOT NULL,
  `TELP` varchar(144) NOT NULL,
  `ALAMAT` varchar(144) NOT NULL,
  `ID_USER` int(14) NOT NULL,
  PRIMARY KEY (`SSN`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pegawai`
--

INSERT INTO `pegawai` (`SSN`, `NAMA`, `TELP`, `ALAMAT`, `ID_USER`) VALUES
(1, 'Admin', '081111', 'JL. Admin', 2),
(2, 'yosi', '0000', 'kkkk', 8),
(3, 'Wilda azmi', '888888', 'Jl mimik', 10),
(4, 'Hilma', '08131111', 'JL Kampus', 11),
(9, 'Nurmajid', '08138572', 'JL Surya', 16),
(10, 'Nurmajid', '08138572', 'JL Surya', 17),
(15, 'bayu', '0888', 'Kaya', 22),
(16, 'wafi', '9999', 'wafi uhuy', 23),
(18, 'JONI', '0393', 'SURYA TENGGELAM', 25),
(25, 'dwikiray', '081', 'jakarta', 32),
(26, 'Dwiki Rayyana', '081385728206', 'jakarta', 33),
(27, 'boy william', '08888', 'boy', 34),
(28, 'Wafi Hamdani', '081111888', 'JL. Mawar', 35),
(29, 'Wafi Hamdani', '081111', 'JL Mawar', 36),
(30, 'Yosi', '081111', 'JL Surya', 37);

--
-- Triggers `pegawai`
--
DROP TRIGGER IF EXISTS `insert_perusahaan_ai`;
DELIMITER $$
CREATE TRIGGER `insert_perusahaan_ai` AFTER INSERT ON `pegawai` FOR EACH ROW INSERT INTO perusahaan_ VALUES (NULL, New.ID_USER, New.NAMA, New.TELP, New.ALAMAT)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `perusahaan_`
--

DROP TABLE IF EXISTS `perusahaan_`;
CREATE TABLE IF NOT EXISTS `perusahaan_` (
  `ID_PERUSAHAAN` int(14) NOT NULL AUTO_INCREMENT,
  `ID_PEGAWAI` int(144) DEFAULT NULL,
  `NAMA` varchar(144) NOT NULL,
  `TELP` varchar(144) NOT NULL,
  `ALAMAT` varchar(30) NOT NULL,
  PRIMARY KEY (`ID_PERUSAHAAN`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `perusahaan_`
--

INSERT INTO `perusahaan_` (`ID_PERUSAHAAN`, `ID_PEGAWAI`, `NAMA`, `TELP`, `ALAMAT`) VALUES
(1, 2, 'Admin', '081111', 'JL. Admin'),
(2, 8, 'yosi', '0000', 'kkkk'),
(3, 10, 'Wilda azmi', '888888', 'Jl mimik'),
(4, 11, 'Hilma', '08131111', 'JL Kampus'),
(5, 16, 'Nurmajid', '08138572', 'JL Surya'),
(6, 17, 'Nurmajid', '08138572', 'JL Surya'),
(7, 22, 'bayu', '0888', 'Kaya'),
(8, 23, 'wafi', '9999', 'wafi uhuy'),
(9, 25, 'JONI', '0393', 'SURYA TENGGELAM'),
(10, 32, 'dwikiray', '081', 'jakarta'),
(11, 33, 'Dwiki Rayyana', '081385728206', 'jakarta'),
(12, 34, 'boy william', '08888', 'boy'),
(13, 35, 'Wafi Hamdani', '081111888', 'JL. Mawar'),
(14, 36, 'Wafi Hamdani', '081111', 'JL Mawar'),
(15, 37, 'Yosi', '081111', 'JL Surya');

-- --------------------------------------------------------

--
-- Table structure for table `rekening`
--

DROP TABLE IF EXISTS `rekening`;
CREATE TABLE IF NOT EXISTS `rekening` (
  `NO_REK` varchar(16) NOT NULL,
  `NAMA` varchar(20) NOT NULL,
  `ID_USER` varchar(8) NOT NULL,
  PRIMARY KEY (`NO_REK`),
  KEY `ID_USER` (`ID_USER`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `sewa`
--

DROP TABLE IF EXISTS `sewa`;
CREATE TABLE IF NOT EXISTS `sewa` (
  `ID_SEWA` int(144) NOT NULL AUTO_INCREMENT,
  `ID_KENDARAAN` varchar(144) DEFAULT NULL,
  `ID_PENYEWA` int(144) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `tgl_pinjam` varchar(144) NOT NULL,
  `tgl_kembali` varchar(144) NOT NULL,
  `total_biaya_sewa` varchar(144) DEFAULT NULL,
  `metode` varchar(144) DEFAULT NULL,
  PRIMARY KEY (`ID_SEWA`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `sewa`
--

INSERT INTO `sewa` (`ID_SEWA`, `ID_KENDARAAN`, `ID_PENYEWA`, `STATUS`, `tgl_pinjam`, `tgl_kembali`, `total_biaya_sewa`, `metode`) VALUES
(19, 'K8888GB', 34, '1', '18/11/2018', '18/11/2018', '1000000', '0'),
(20, 'G1234BF', 34, '1', '18/11/2018', '18/11/2018', '550000', '0'),
(23, 'GIV4AN', 22, '1', '19/11/2018', '19/11/2018', '500000', '0'),
(25, 'G0987FG', 37, '1', '19/11/2018', '24/11/2018', '300000', '0');

--
-- Triggers `sewa`
--
DROP TRIGGER IF EXISTS `delete_transaksi_bd`;
DELIMITER $$
CREATE TRIGGER `delete_transaksi_bd` BEFORE DELETE ON `sewa` FOR EACH ROW DELETE FROM transaksi WHERE idsewa = Old.ID_SEWA
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `input_transaksi_ai`;
DELIMITER $$
CREATE TRIGGER `input_transaksi_ai` AFTER INSERT ON `sewa` FOR EACH ROW INSERT INTO transaksi VALUES(NULL,New.ID_SEWA,New.metode,New.total_biaya_sewa)
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `update_kendaraan_ai`;
DELIMITER $$
CREATE TRIGGER `update_kendaraan_ai` AFTER INSERT ON `sewa` FOR EACH ROW UPDATE kendaraan SET STATUS = '1' WHERE PLAT_NO = New.ID_KENDARAAN
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `update_kendaraan_bd`;
DELIMITER $$
CREATE TRIGGER `update_kendaraan_bd` BEFORE DELETE ON `sewa` FOR EACH ROW UPDATE kendaraan SET STATUS = '0' WHERE PLAT_NO = Old.ID_KENDARAAN
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE IF NOT EXISTS `transaksi` (
  `id` int(144) NOT NULL AUTO_INCREMENT,
  `idsewa` varchar(144) DEFAULT NULL COMMENT '0 Offline, 1 Online',
  `tipe` varchar(144) DEFAULT NULL,
  `jumlah` varchar(144) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id`, `idsewa`, `tipe`, `jumlah`) VALUES
(1, '6', '0', '100000'),
(2, '7', '1', '100000'),
(20, '25', '0', '300000'),
(18, '23', '0', '500000'),
(15, '20', '0', '550000'),
(14, '19', '0', '1000000');

-- --------------------------------------------------------

--
-- Table structure for table `user_`
--

DROP TABLE IF EXISTS `user_`;
CREATE TABLE IF NOT EXISTS `user_` (
  `ID_USER` int(133) NOT NULL AUTO_INCREMENT,
  `NAMA` varchar(144) CHARACTER SET latin1 NOT NULL,
  `EMAIL` varchar(144) CHARACTER SET latin1 NOT NULL,
  `PASSWORD` varchar(144) CHARACTER SET latin1 NOT NULL,
  `ALAMAT` varchar(30) CHARACTER SET latin1 NOT NULL,
  `TELP` varchar(144) CHARACTER SET latin1 NOT NULL,
  `NO_KTP` varchar(20) CHARACTER SET latin1 DEFAULT NULL,
  `FOTO_KTP` blob,
  `FOTO_KK` blob,
  `FOTO_SIM` blob,
  `ONLINEID_TF` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  `OFFLINEID_TF` varchar(15) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`ID_USER`),
  UNIQUE KEY `EMAIL` (`EMAIL`),
  KEY `ONLINEID_TF` (`ONLINEID_TF`),
  KEY `OFFLINEID_TF` (`OFFLINEID_TF`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_`
--

INSERT INTO `user_` (`ID_USER`, `NAMA`, `EMAIL`, `PASSWORD`, `ALAMAT`, `TELP`, `NO_KTP`, `FOTO_KTP`, `FOTO_KK`, `FOTO_SIM`, `ONLINEID_TF`, `OFFLINEID_TF`) VALUES
(2, 'Admin', 'admin@mail.com', '123qwe', 'JL. Admin', '081111', '0517053', NULL, NULL, NULL, NULL, NULL),
(8, 'yosi', 'yosi@mail.com', '123qwe', 'kkkk', '0000', '081111', NULL, NULL, NULL, NULL, NULL),
(10, 'Wilda azmi', 'wildan@mail.com', '123qwe', 'Jl mimik', '888888', '999999', NULL, NULL, NULL, NULL, NULL),
(11, 'Hilma', 'hilma@mail.com', '123qwe', 'JL Kampus', '08131111', '999999', NULL, NULL, NULL, NULL, NULL),
(16, 'Nurmajid', 'bayu@mail.com', '123qwe', 'JL Surya', '08138572', '99999999', NULL, NULL, NULL, NULL, NULL),
(17, 'Nurmajid', 'majid@mail.com', '123qwe', 'JL Surya', '08138572', '99999999', NULL, NULL, NULL, NULL, NULL),
(22, 'bayu', 'babay@mail.com', '123qwe', 'Kaya', '0888', '999', NULL, NULL, NULL, NULL, NULL),
(23, 'wafi', 'wafi@mail.com', '123qwe', 'wafi uhuy', '9999', '9999', NULL, NULL, NULL, NULL, NULL),
(25, 'JONI', 'joni@staff.uns.ac.id', 'misterjoni12', 'SURYA TENGGELAM', '0393', '3342', NULL, NULL, NULL, NULL, NULL),
(32, 'dwikiray', 'dwiki@mail.com', '123qwe', 'jakarta', '081', '999', NULL, NULL, NULL, NULL, NULL),
(33, 'Dwiki Rayyana', 'dwiki@coba.com', '123qwe', 'jakarta', '081385728206', '99999912', NULL, NULL, NULL, NULL, NULL),
(34, 'boy william', 'boy@mail.com', '123qwe', 'boy', '08888', '99999', NULL, NULL, NULL, NULL, NULL),
(36, 'Wafi Hamdani', 'wafi@student.uns.ac.id', '123qwe', 'JL Mawar', '081111', '9999999', NULL, NULL, NULL, NULL, NULL),
(37, 'Yosi', 'yosi@student.uns.ac.id', '123qwe', 'JL Surya', '081111', '999999', NULL, NULL, NULL, NULL, NULL);

--
-- Triggers `user_`
--
DROP TRIGGER IF EXISTS `insert_pegawai_ai`;
DELIMITER $$
CREATE TRIGGER `insert_pegawai_ai` AFTER INSERT ON `user_` FOR EACH ROW INSERT INTO pegawai VALUES(NULL,New.NAMA,New.TELP,New.ALAMAT,New.ID_USER)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `warna`
--

DROP TABLE IF EXISTS `warna`;
CREATE TABLE IF NOT EXISTS `warna` (
  `KWARNA` varchar(8) NOT NULL,
  `PLAT_NO` varchar(7) NOT NULL,
  KEY `PLAT_NO` (`PLAT_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
