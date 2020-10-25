-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Июн 05 2020 г., 07:21
-- Версия сервера: 10.4.11-MariaDB
-- Версия PHP: 7.2.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `design`
--

-- --------------------------------------------------------

--
-- Структура таблицы `auto_user`
--

CREATE TABLE `auto_user` (
  `autoUserId` bigint(20) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `FIRST_NAME` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `ROLE` varchar(255) DEFAULT NULL,
  `USERNAME` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `auto_user`
--

INSERT INTO `auto_user` (`autoUserId`, `EMAIL`, `FIRST_NAME`, `LAST_NAME`, `PASSWORD`, `ROLE`, `USERNAME`) VALUES
(1, 't3stma11@yandex.ru', 'Katerina', 'Nilolushenko', 'MishkaCoffee', 'ROLE_USER', 'MishkaCoffee'),
(2, 't3stma11@yandex.ru', 'Nikolya', 'Nilolushenko', 'AvocadoIrk', 'ROLE_USER', 'AvocadoIrk'),
(3, 't3stma11@yandex.ru', 'Ivan', 'Popov', 'IvanTea', 'ROLE_USER', 'IvanTea'),
(4, 'ready_steady_go@gmail.com', 'Andrey', 'Kamushkov', 'RiDeForLiFeVrUmVrUm', 'ROLE_USER', 'RideForLife'),
(5, 't3stma11@yandex.ru', 'Yana', 'Ioffe', 'pass', 'ROLE_ADMIN', 'jn'),
(6, 't3stma11@yandex.ru', 'UserName', 'UserSurname', 'pass', 'ROLE_USER', 'user'),
(7, 't3stma11@yandex.ru', 'AdminName', 'AdminSurname', 'pass', 'ROLE_ADMIN', 'admin'),
(8, 'yana.ioffe@yandex.ru', 'Yana', 'Ioffe', 'pass', 'ROLE_USER', 'e-16569'),
(9, 't3stma11@yandex.ru', 'Alisa', 'Pickareva', 'pass', 'ROLE_USER', 'AlisaFox');

-- --------------------------------------------------------

--
-- Структура таблицы `designers`
--

CREATE TABLE `designers` (
  `designer_id` int(11) NOT NULL DEFAULT 0,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `login` varchar(100) DEFAULT NULL,
  `pass` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `designers`
--

INSERT INTO `designers` (`designer_id`, `firstname`, `lastname`, `login`, `pass`, `email`) VALUES
(1, 'Yana', 'Ioffe', 'jn', 'pass', 't3stma11@yandex.ru'),
(2, 'Natalya', 'Tsvetkova', 'design_tsvetkova', 'pass', 't3stma11@yandex.ru');

-- --------------------------------------------------------

--
-- Структура таблицы `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `deadline` date DEFAULT NULL,
  `rank` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `techtask` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `orders`
--

INSERT INTO `orders` (`order_id`, `deadline`, `rank`, `status`, `techtask`, `title`) VALUES
(1, '2020-06-01', 10, 4, 'use turquoise and yellow colors, bg with light geometry pattern', 'SummerMenu'),
(2, '2020-06-01', 10, 4, 'yellow color, sunflowers in bg', 'SummerLogo'),
(3, '2020-06-13', 0, 2, 'business card for Korean cosmetics shop', 'BusinessCard'),
(4, '2020-05-10', 9.5, 4, 'banner with logos of 3 different shops', 'BannerForWindow'),
(5, '2020-06-14', 0, 3, 'create pack for new tea types in our basic style', 'TeaPack'),
(6, '2020-06-14', 0, 0, 'create pack for new product(carob) in our basic style', 'CarobPack'),
(7, '2020-06-15', 1, 4, 'Cool livery for a time attack race car', 'CarLivery'),
(8, NULL, NULL, NULL, 'new order', 'Spring'),
(9, '2021-01-01', NULL, NULL, 'aezsxgdf jkl', 'Spring'),
(10, NULL, NULL, NULL, 'new order12345', 'Spring12345'),
(11, NULL, NULL, NULL, 'Enter your username + techtask', 'Title'),
(12, '2020-10-10', NULL, NULL, '123456', 'aa');

-- --------------------------------------------------------

--
-- Структура таблицы `orders_clients`
--

CREATE TABLE `orders_clients` (
  `order_id` int(11) NOT NULL,
  `client_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `orders_clients`
--

INSERT INTO `orders_clients` (`order_id`, `client_id`) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 2),
(5, 3),
(6, 3),
(7, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `orders_designers`
--

CREATE TABLE `orders_designers` (
  `designer_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `orders_designers`
--

INSERT INTO `orders_designers` (`designer_id`, `order_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 7),
(2, 8),
(2, 9);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `auto_user`
--
ALTER TABLE `auto_user`
  ADD PRIMARY KEY (`autoUserId`);

--
-- Индексы таблицы `designers`
--
ALTER TABLE `designers`
  ADD PRIMARY KEY (`designer_id`);

--
-- Индексы таблицы `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`);

--
-- Индексы таблицы `orders_clients`
--
ALTER TABLE `orders_clients`
  ADD PRIMARY KEY (`order_id`,`client_id`),
  ADD KEY `FKhq1kav4fgpq6g0un4af61tovi` (`client_id`);

--
-- Индексы таблицы `orders_designers`
--
ALTER TABLE `orders_designers`
  ADD KEY `idx_designer_id` (`designer_id`) USING BTREE,
  ADD KEY `idx_order_id` (`order_id`) USING BTREE;

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `auto_user`
--
ALTER TABLE `auto_user`
  MODIFY `autoUserId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
