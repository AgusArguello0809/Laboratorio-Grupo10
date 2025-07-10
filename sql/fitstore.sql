-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Servidor: mysql:3306
-- Tiempo de generación: 10-07-2025 a las 14:11:50
-- Versión del servidor: 8.4.5
-- Versión de PHP: 8.2.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `fitstore`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito_entity`
--

CREATE TABLE `carrito_entity` (
  `id` bigint NOT NULL,
  `total` double NOT NULL,
  `owner_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `carrito_entity`
--

INSERT INTO `carrito_entity` (`id`, `total`, `owner_id`) VALUES
(1, 89999.91, 2),
(2, 60.300000000000004, 3),
(3, 39999.96, 1),
(4, 99999.99, 4),
(5, 29999.97, 5),
(6, 0, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito_entity_productos`
--

CREATE TABLE `carrito_entity_productos` (
  `carrito_entity_id` bigint NOT NULL,
  `cantidad` int DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  `sub_total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `carrito_entity_productos`
--

INSERT INTO `carrito_entity_productos` (`carrito_entity_id`, `cantidad`, `precio_unitario`, `producto_id`, `sub_total`) VALUES
(2, 3, 20.1, 1, 60.300000000000004),
(3, 4, 9999.99, 2, 39999.96),
(1, 9, 9999.99, 2, 89999.91),
(4, 1, 99999.99, 3, 99999.99),
(5, 3, 9999.99, 2, 29999.97);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` bigint NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`) VALUES
(1, 'Calzado'),
(2, 'Equipamiento'),
(3, 'Ropa'),
(4, 'Suplementos'),
(5, 'Accesorios');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_entity`
--

CREATE TABLE `orden_entity` (
  `id` bigint NOT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `total` double NOT NULL,
  `comprador_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `orden_entity`
--

INSERT INTO `orden_entity` (`id`, `fecha`, `total`, `comprador_id`) VALUES
(1, '2025-07-10 12:38:22.211558', 20.1, 2),
(2, '2025-07-10 12:59:52.866519', 9999.99, 1),
(3, '2025-07-10 13:00:01.618423', 20.1, 3),
(4, '2025-07-10 13:00:11.407128', 10020.09, 2),
(5, '2025-07-10 13:05:19.950045', 100020.09000000001, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_entity_productos`
--

CREATE TABLE `orden_entity_productos` (
  `orden_entity_id` bigint NOT NULL,
  `cantidad` int DEFAULT NULL,
  `nombre_producto` varchar(255) DEFAULT NULL,
  `precio_unitario` double DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  `sub_total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `orden_entity_productos`
--

INSERT INTO `orden_entity_productos` (`orden_entity_id`, `cantidad`, `nombre_producto`, `precio_unitario`, `producto_id`, `sub_total`) VALUES
(1, 1, 'Mancuernas de 2kg', 20.1, 1, 20.1),
(2, 1, 'Pelota Mundial de Clubes 2025', 9999.99, 2, 9999.99),
(3, 1, 'Mancuernas de 2kg', 20.1, 1, 20.1),
(4, 1, 'Mancuernas de 2kg', 20.1, 1, 20.1),
(4, 1, 'Pelota Mundial de Clubes 2025', 9999.99, 2, 9999.99),
(5, 1, 'Mancuernas de 2kg', 20.1, 1, 20.1),
(5, 1, 'Camiseta Palermo 9 LG Temporada 2009 - 2010 FIRMADA', 99999.99, 3, 99999.99);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` bigint NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `owner_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `description`, `price`, `stock`, `title`, `category_id`, `owner_id`) VALUES
(1, 'Mancuernas de varios colores de 2kg cada una.', 20.1, 17, 'Mancuernas de 2kg', 2, 1),
(2, 'Pelota Mundial de Clubes 2025 importada directo de Estados Unidos. Ultima oportunidad para conseguirla!', 9999.99, 18, 'Pelota Mundial de Clubes 2025', 2, 3),
(3, 'Yo soy palermo así que nada, la firmé yo.', 99999.99, 1, 'Camiseta Palermo 9 LG Temporada 2009 - 2010 FIRMADA', 3, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_images`
--

CREATE TABLE `producto_images` (
  `producto_id` bigint NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `orden` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto_images`
--

INSERT INTO `producto_images` (`producto_id`, `image_url`, `orden`) VALUES
(1, 'https://storage.googleapis.com/fitstore-tpo.firebasestorage.app/13768358-95ab-435a-9fb9-adb069916cc6-par-de-mancuernas-hexagonales-odea-2-kg-11-cee8f042d2e1f4ee5716662886880230-1024-1024.png', 0),
(1, 'https://storage.googleapis.com/fitstore-tpo.firebasestorage.app/3e5a6c0a-07d8-407b-a2d9-265d377664f0-images.jpeg', 1),
(1, 'https://storage.googleapis.com/fitstore-tpo.firebasestorage.app/805fdd42-23e4-4f20-9302-e036670feb95-27938__600__600__MANCUERNAS-DE-VINILO-DE-2-KILOS-AC2043-VERDE.jpeg', 2),
(2, 'https://storage.googleapis.com/fitstore-tpo.firebasestorage.app/b8d3662e-f36a-4fcc-b12a-5b6d03b6f067-balon-mundial-de-clubes.png', 0),
(3, 'https://storage.googleapis.com/fitstore-tpo.firebasestorage.app/20404c04-24d6-42c2-ab4e-c3025f6d2b60-images (1).jpeg', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','CLIENTE','INVITADO') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `email`, `last_name`, `name`, `password`, `role`, `username`) VALUES
(1, 'juanroman@gmail.com', 'Riquelme', 'Roman', '$2a$10$t/U2Hik/KnHy/UAqakptN.h4wl5lX4R/lK8nSOPTBzyYV9/aEKCJ.', 'CLIENTE', 'juanroman10'),
(2, 'palermo9@gmail.com', 'Palermo', 'Martin', '$2a$10$uKAW8kRk22GN789nmAO22O7t0BL2QKztW3zuvBJba9ogLm8z/iamG', 'CLIENTE', 'palermotitan9'),
(3, 'guille7@gmail.com', 'Barros Schelotto', 'Guillermo', '$2a$10$8V.tCZ1uDcJ/u/9wzZo.7.Qr0io28XdTVhgWk0M2gN3al7SkbQpQO', 'CLIENTE', 'guillermo7'),
(4, 'messi@gmail.com', 'Messi', 'Lionel', '$2a$10$VjRURkC97HukhSiGXPQ7wuIknYidqdxVLHOMM9hcBlr0TEjdMwtMW', 'ADMIN', 'lionelmessi10'),
(5, 'suarrez@gmail.com', 'Suarez', 'Luis', '$2a$10$mBOfu96XXU4CA93O.V5yvOB44zkoWKBNykb2tvhWtGyH74QT3r2VS', 'CLIENTE', 'luissuarez9'),
(6, 'siuuu7@gmail.com', 'Ronaldo', 'Cristiano', '$2a$10$1KN6j9W.ZJ4QVkNkxQfgleOVd9B2jnk0OSExWLMU296vjO0Dt3lVi', 'CLIENTE', 'cristianoronaldosu7');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrito_entity`
--
ALTER TABLE `carrito_entity`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKdp8cevlaoo0iycm0wenn2x91r` (`owner_id`);

--
-- Indices de la tabla `carrito_entity_productos`
--
ALTER TABLE `carrito_entity_productos`
  ADD KEY `FKpor649ygfsttk0ajb8q3r4fam` (`carrito_entity_id`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `orden_entity`
--
ALTER TABLE `orden_entity`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKmp4yfq6e1oo7i6yrt35y60cc0` (`comprador_id`);

--
-- Indices de la tabla `orden_entity_productos`
--
ALTER TABLE `orden_entity_productos`
  ADD KEY `FK199v8o68pvaxct1uqoje34tn2` (`orden_entity_id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK626khgoki8ks0q5344cou8ptd` (`category_id`),
  ADD KEY `FK4u17u9cbgqeyrae6mbs4pgml4` (`owner_id`);

--
-- Indices de la tabla `producto_images`
--
ALTER TABLE `producto_images`
  ADD PRIMARY KEY (`producto_id`,`orden`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `carrito_entity`
--
ALTER TABLE `carrito_entity`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `orden_entity`
--
ALTER TABLE `orden_entity`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrito_entity`
--
ALTER TABLE `carrito_entity`
  ADD CONSTRAINT `FKg34vbtqi3q9kql8uybxokkinm` FOREIGN KEY (`owner_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `carrito_entity_productos`
--
ALTER TABLE `carrito_entity_productos`
  ADD CONSTRAINT `FKpor649ygfsttk0ajb8q3r4fam` FOREIGN KEY (`carrito_entity_id`) REFERENCES `carrito_entity` (`id`);

--
-- Filtros para la tabla `orden_entity`
--
ALTER TABLE `orden_entity`
  ADD CONSTRAINT `FKmp4yfq6e1oo7i6yrt35y60cc0` FOREIGN KEY (`comprador_id`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `orden_entity_productos`
--
ALTER TABLE `orden_entity_productos`
  ADD CONSTRAINT `FK199v8o68pvaxct1uqoje34tn2` FOREIGN KEY (`orden_entity_id`) REFERENCES `orden_entity` (`id`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `FK4u17u9cbgqeyrae6mbs4pgml4` FOREIGN KEY (`owner_id`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `FK626khgoki8ks0q5344cou8ptd` FOREIGN KEY (`category_id`) REFERENCES `categorias` (`id`);

--
-- Filtros para la tabla `producto_images`
--
ALTER TABLE `producto_images`
  ADD CONSTRAINT `FKnog4vseokwk69oehuf4wyy04v` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
