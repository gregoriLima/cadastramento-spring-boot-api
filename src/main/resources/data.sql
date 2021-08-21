--Usuário para ser cadastrado no banco de dados com a senha 123456 gerada com o Bcrypt (new BCryptPasswordEncoder().encode("123456"))
INSERT INTO usuario(nome, email, senha) VALUES('Grégori', 'gregori@gmail.com', '$2a$10$X/JlWvlnYyGX5OBBS2WeqOvWafI1s/wEkM78IuxpV5qW3QHqzWCQK')
 ON DUPLICATE KEY UPDATE nome=nome;