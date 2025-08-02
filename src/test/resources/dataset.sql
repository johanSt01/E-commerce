INSERT INTO cuenta (id, email, password) VALUES
(1, 'johan15stiven@gmail.com', 'password1'),
(2, 'lauragomez@mail.com', 'password1'),
(3, 'mariolopez@mail.com', 'password1'),
(4, 'anitadiaz@mail.com', 'password1'),
(5, 'carlosherrera@mail.com', 'password1');

INSERT INTO usuario (id, nombre, apellido, cedula, direccion, estado_usuario, id_ciudad, reset_code, reset_code_expiry, telefono, tipo_usuario) VALUES
(1, 'Juan', 'Perez', '1010101010', 'Calle 1 #23', 'Activo', 'Bogota', NULL, NULL, '3101234567', 'Comprador'),
(2, 'Laura', 'Gomez', '2020202020', 'Carrera 10 #45', 'Activo', 'Medellin', NULL, NULL, '3112345678', 'Vendedor'),
(3, 'Mario', 'Lopez', '3030303030', 'Av 5 #67', 'Activo', 'Cali', NULL, NULL, '3123456789', 'Comprador'),
(4, 'Anita', 'Diaz', '4040404040', 'Calle 8 #90', 'Inactivo', 'Pereira', NULL, NULL, '3134567890', 'Vendedor'),
(5, 'Carlos', 'Herrera', '5050505050', 'Diagonal 9 #12', 'Activo', 'Armenia', NULL, NULL, '3145678901', 'Comprador');

INSERT INTO administrador (id) VALUES
(1), (2), (3), (4), (5);

INSERT INTO producto (id, nombre, descripcion, precio, stock, estado_producto, id_categoria) VALUES
(1, 'Taladro Bosch', 'Taladro eléctrico de alta potencia', 350000, 10, 'Activo', 'Construccion'),
(2, 'Balón Adidas', 'Balón oficial de fútbol', 120000, 20, 'Activo', 'Deportes'),
(3, 'TV Samsung', 'TV Smart 50 pulgadas', 1800000, 5, 'Activo', 'Electrodomesticos'),
(4, 'PlayStation 5', 'Consola de videojuegos última generación', 2500000, 8, 'Activo', 'Juegos'),
(5, 'Laptop ASUS ZenBook', 'Laptop ultradelgada', 3200000, 6, 'Activo', 'Tecnologia');

INSERT INTO producto_imagenes (producto_id, url_imagen) VALUES
(1, 'taladro.jpg'),
(2, 'balon.jpg'),
(3, 'tv.jpg'),
(4, 'play5.jpg'),
(5, 'laptop.jpg');

INSERT INTO detalle_carrito (id, cantidad, precio_total, id_producto_id) VALUES
(1, 2, 700000, 1),
(2, 1, 120000, 2),
(3, 1, 1800000, 3),
(4, 1, 2500000, 4),
(5, 2, 6400000, 5);

INSERT INTO carrito (id, id_detalle_carrito_id, id_usuario_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5);

INSERT INTO metodo_pago (id, detalle, tipo, id_usuario_id) VALUES
(1, 'Pago con tarjeta visa', 'TARJETA_CREDITO', 1),
(2, 'Pago vía PayPal', 'PAYPAL', 2),
(3, 'Transferencia Bancaria', 'TRANSFERENCIA_BANCARIA', 3),
(4, 'Pago en efectivo', 'EFECTIVO', 4),
(5, 'Tarjeta débito', 'TARJETA_CREDITO', 5);

INSERT INTO compra (id, fecha_compra, monto_total, id_usuario_id, metodo_pago_id) VALUES
(1, NOW(), 700000, 1, 1),
(2, NOW(), 120000, 2, 2),
(3, NOW(), 1800000, 3, 3),
(4, NOW(), 2500000, 4, 4),
(5, NOW(), 6400000, 5, 5);

INSERT INTO comentario (id, calificacion, mensaje, id_producto_id, id_usuario_id) VALUES
(1, 5, 'Muy buen producto, lo recomiendo.', 1, 1),
(2, 4, 'Buena calidad.', 2, 2),
(3, 3, 'Normal, esperaba más.', 3, 3),
(4, 5, 'Excelente consola!', 4, 4),
(5, 2, 'No me funcionó bien.', 5, 5);

INSERT INTO pedido (id, fecha_pedido, total_pagado) VALUES
(1, CURDATE(), 700000),
(2, CURDATE(), 120000),
(3, CURDATE(), 1800000),
(4, CURDATE(), 2500000),
(5, CURDATE(), 6400000);

INSERT INTO pqrs (id, fecha_creacion, id_estado, id_tipo, motivo, id_usuario_id) VALUES
(1, NOW(), 'pendiente', 'Peticion', 'Quiero conocer el estado de mi pedido.', 1),
(2, NOW(), 'enProceso', 'Queja', 'El producto llegó defectuoso.', 2),
(3, NOW(), 'resuelta', 'Reclamo', 'No se respetó el precio de oferta.', 3),
(4, NOW(), 'pendiente', 'Sugerencia', 'Agregar más métodos de pago.', 4),
(5, NOW(), 'enProceso', 'Peticion', '¿Cuándo reponen stock?', 5);

INSERT INTO suscripcion (id, descripcion, fecha_fin, fecha_inicio, id_estado, nombre, porcentaje_descuento, tipo, id_usuario_id) VALUES
(1, 'Plan básico mensual', NOW() + INTERVAL 30 DAY, NOW(), 'Activo', 'Básico Plus', 5.0, 'BASICO', 1),
(2, 'Plan medio trimestral', NOW() + INTERVAL 90 DAY, NOW(), 'Activo', 'Medio Gold', 10.0, 'MEDIO', 2),
(3, 'Plan pro anual', NOW() + INTERVAL 365 DAY, NOW(), 'Activo', 'Pro Premium', 15.0, 'PRO', 3),
(4, 'Plan cancelado', NOW() + INTERVAL 60 DAY, NOW(), 'Cancelado', 'Pro Fallido', 20.0, 'PRO', 4),
(5, 'Plan pausado', NOW() + INTERVAL 60 DAY, NOW(), 'Pausado', 'Medio Pausa', 8.0, 'MEDIO', 5);


