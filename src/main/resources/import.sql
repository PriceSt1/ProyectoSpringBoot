INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(1, 'Pablo', 'Perez', 'pablop@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(2, 'Luis', 'Garcia', 'luisg@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(3, 'Raquel', 'Muñoz', 'raquelm@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(4, 'Monica', 'Diaz', 'monicad@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(5, 'Javier', 'Diaz', 'javierd@gmail.com', '2022-08-28','');

INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(6, 'Pablo', 'Jose', 'pablop@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(7, 'Luis', 'Pablo', 'luisg@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(8, 'Raquel', 'Muñoz', 'raquelm@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(9, 'Monica', 'Muñoz', 'monicad@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(10, 'Javier', 'Diaz', 'javierd@gmail.com', '2022-08-28','');

INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(11, 'Javier', 'Perez', 'pablop@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(12, 'Luis', 'Perez', 'luisg@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(13, 'Monica', 'Muñoz', 'raquelm@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(14, 'Javier', 'Diaz', 'monicad@gmail.com', '2022-08-28','');
INSERT INTO clientes (id, nombre, apellido, email, create_at,foto) VALUES(15, 'Pablo', 'Diaz', 'javierd@gmail.com', '2022-08-28','');

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);


