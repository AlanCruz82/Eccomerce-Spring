--Para hacer las inserciones, primero verificamos si los roles y administrador ya existen para evitar actualizar
--la informacion

-- Roles
INSERT INTO roles (nombre)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'ADMIN');

INSERT INTO roles (nombre)
SELECT 'CLIENTE'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE nombre = 'CLIENTE');

-- Usuario administrador de ejemplo
INSERT INTO usuarios (nombre, correo_electronico, contrasena, telefono, direccion_envio, activo, id_rol, fecha_creacion, fecha_actualizacion)
SELECT 'Administrador', 'admin@eccomerce.com', '$2a$10$tJ1oJ7oJFE9Bp.DE4B2r3edW0ddgW4UPmieEjskfItgwoMWYUYqde',
       NULL, 'Dirección administrativa', 1, r.id_rol, NOW(), NOW()
FROM roles r
WHERE r.nombre = 'ADMIN'
  AND NOT EXISTS (SELECT 1 FROM usuarios WHERE correo_electronico = 'admin@eccomerce.com');
