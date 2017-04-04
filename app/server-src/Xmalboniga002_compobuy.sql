-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2+deb7u7
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 30-03-2017 a las 20:43:08
-- Versión del servidor: 5.5.54
-- Versión de PHP: 5.4.45-0+deb7u7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `Xmalboniga002_compobuy`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE IF NOT EXISTS `categoria` (
  `idcat` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` text,
  PRIMARY KEY (`idcat`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`idcat`, `nombre`) VALUES
(1, 'Placas base'),
(2, 'Memorias RAM'),
(3, 'Discos duros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `componente`
--

CREATE TABLE IF NOT EXISTS `componente` (
  `idcomp` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` text,
  `idcat` int(11) NOT NULL,
  `descr` text,
  `precio` float NOT NULL,
  `spec` text,
  `imagepath` text,
  PRIMARY KEY (`idcomp`),
  KEY `idcat` (`idcat`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `componente`
--

INSERT INTO `componente` (`idcomp`, `nombre`, `idcat`, `descr`, `precio`, `spec`, `imagepath`) VALUES
(1, 'Gigabyte GT3200', 1, 'Una placa base genial para poner un procesador i5. Tiene un alto rendimiento y dura mucho.', 40.26, '2 PCI Express, 4 slots para memorias RAM, 2USB3...', 'http://galan.ehu.eus/malboniga002/WEB/imagescompobuy/placabase1.jpg'),
(2, 'MS 7234', 1, 'Una placa base genial para poner un procesador i3. Tiene un alto rendimiento y dura mucho.', 34.2, '2 PCI Express, 4 slots para memorias RAM, 4USB...', 'http://galan.ehu.eus/malboniga002/WEB/imagescompobuy/placabase2.jpg'),
(3, 'Whichman WorkX AG', 2, 'Una memoria RAM de 2GB.', 20.2, 'Meria RAM DDR3 para portatiles de 2GB...', 'http://galan.ehu.eus/malboniga002/WEB/imagescompobuy/memoram1.JPG'),
(4, 'Ballistix Tracer', 2, 'Memorias RAM de 1GB cada una, DDR2.', 35.67, 'Memorias RAM DDR2 de 1GB cada una.', 'http://galan.ehu.eus/malboniga002/WEB/imagescompobuy/memoram2.jpg'),
(5, 'HDD Samsung 4.3GB', 3, 'Disco duro de Samsung para meter una o dos peliculas.', 5.68, 'Disco duro Samsung HDD de 4.3GB, 2000RPM...', 'http://galan.ehu.eus/malboniga002/WEB/imagescompobuy/discoduro2.jpg'),
(6, 'Disco duro Western Digital de 250GB', 3, 'Disco duro Disco duro Western Digital de 250GB para meter cualquier sistema operativo', 50.68, 'Disco duro Western Digital de 250GB, SATA...', 'http://galan.ehu.eus/malboniga002/WEB/imagescompobuy/discoduro1.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `nombre` varchar(50) NOT NULL,
  `pass` text NOT NULL,
  `ciudad` text,
  `fechanacimiento` text NOT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usu_comp`
--

CREATE TABLE IF NOT EXISTS `usu_comp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `idcomp` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `nombre` (`nombre`),
  KEY `idcomp` (`idcomp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `componente`
--
ALTER TABLE `componente`
  ADD CONSTRAINT `componente_ibfk_1` FOREIGN KEY (`idcat`) REFERENCES `categoria` (`idcat`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usu_comp`
--
ALTER TABLE `usu_comp`
  ADD CONSTRAINT `usu_comp_ibfk_1` FOREIGN KEY (`nombre`) REFERENCES `usuario` (`nombre`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `usu_comp_ibfk_2` FOREIGN KEY (`idcomp`) REFERENCES `componente` (`idcomp`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
