-- Desativa as verificações de chaves estrangeiras APENAS para esta sessão (para evitar conflitos de ID)
SET SESSION FOREIGN_KEY_CHECKS = 0;

-- Inserções de dados
INSERT INTO `person` (`id`, `address`, `first_name`, `gender`, `last_name`) VALUES
                                                                                (1, 'Paraiba - Brasil', 'João', 'Masculino', 'Galvão'),
                                                                                (2, 'Paraiba - Brasil', 'Lorena', 'Feminino', 'Galvão'),
                                                                                (3, 'Paraiba - Brasil', 'Giselma', 'Feminino', 'Galvão');

-- Reativa as verificações de chaves estrangeiras
SET SESSION FOREIGN_KEY_CHECKS = 1;