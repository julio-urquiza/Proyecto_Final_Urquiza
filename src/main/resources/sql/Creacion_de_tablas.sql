CREATE SCHEMA clienteventas;
use clienteventas;

CREATE TABLE `cliente` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `correo` varchar(255) DEFAULT NULL,
   `nombre` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `producto` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `descripcion` varchar(255) DEFAULT NULL,
   `precio` float DEFAULT NULL,
   `stock` int DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `venta` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `fecha` date DEFAULT NULL,
   `precio_total` float DEFAULT NULL,
   `id_cliente` bigint NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FKsor2qmi3thao7a8or49vlohp9` (`id_cliente`),
   CONSTRAINT `FKsor2qmi3thao7a8or49vlohp9` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `venta_detalle` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `cantidad` int DEFAULT NULL,
   `precio_cantidad` float DEFAULT NULL,
   `id_producto` bigint NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FKclaa78twf7o52j8kvie9pt218` (`id_producto`),
   CONSTRAINT `FKclaa78twf7o52j8kvie9pt218` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 CREATE TABLE `venta_venta_detalle` (
   `venta_id` bigint NOT NULL,
   `venta_detalle_id` bigint NOT NULL,
   UNIQUE KEY `UK_f1hle5w4wkpnys5swfdwog2i3` (`venta_detalle_id`),
   KEY `FKm9o71dv21mjriv5asa8j6rsc2` (`venta_id`),
   CONSTRAINT `FK66spmdpoij6riw3004em3ssjv` FOREIGN KEY (`venta_detalle_id`) REFERENCES `venta_detalle` (`id`),
   CONSTRAINT `FKm9o71dv21mjriv5asa8j6rsc2` FOREIGN KEY (`venta_id`) REFERENCES `venta` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 