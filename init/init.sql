CREATE TABLE turno (
    id_turno SERIAL PRIMARY KEY,
    descricao VARCHAR(255),
    ativo BOOLEAN
);

CREATE TABLE dia_semana (
    id_dia_semana SERIAL PRIMARY KEY,
    descricao VARCHAR(255),
    ativo BOOLEAN
);

CREATE TABLE instituicao (
    id_instituicao SERIAL PRIMARY KEY,
    nome VARCHAR(260) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE config_faculdade (
    id_config_faculdade SERIAL PRIMARY KEY,
    campo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL
);

CREATE TABLE instituicao_config (
    id_instituicao_config SERIAL PRIMARY KEY,
    id_config_faculdade INTEGER NOT NULL,
    id_instituicao INTEGER NOT NULL,
    valor VARCHAR(200) NOT NULL,
    CONSTRAINT uq_instituicao_config UNIQUE (id_config_faculdade, id_instituicao),
    FOREIGN KEY (id_instituicao) REFERENCES instituicao(id_instituicao) ON DELETE CASCADE,
    FOREIGN KEY (id_config_faculdade) REFERENCES config_faculdade(id_config_faculdade) ON DELETE CASCADE
);

CREATE TABLE usuario (
    id_usuario BIGSERIAL PRIMARY KEY,
    nome VARCHAR(260) NOT NULL,
    login VARCHAR(200) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE perfil (
    id_perfil BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao TEXT
);

CREATE TABLE usuario_perfil (
    id_usuario_perfil SERIAL PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_perfil BIGINT NOT NULL,
    id_instituicao INTEGER NOT NULL,
    email_usuario VARCHAR(255) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT uq_usuario_perfil UNIQUE (id_usuario, id_perfil, id_instituicao),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_perfil) REFERENCES perfil(id_perfil) ON DELETE CASCADE,
    FOREIGN KEY (id_instituicao) REFERENCES instituicao(id_instituicao) ON DELETE CASCADE
);

CREATE TABLE coordenacao (
    id_coordenacao SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE curso (
    id_curso SERIAL PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE coordenador_curso_instituicao (
    id_coordenador_curso_instituicao SERIAL PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_coordenacao INTEGER NOT NULL,
    id_instituicao INTEGER NOT NULL,
    id_curso INTEGER NOT NULL,
    CONSTRAINT uq_coordenador_curso_instituicao UNIQUE (id_usuario, id_coordenacao, id_instituicao, id_curso),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_coordenacao) REFERENCES coordenacao(id_coordenacao) ON DELETE CASCADE,
    FOREIGN KEY (id_instituicao) REFERENCES instituicao(id_instituicao) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso) ON DELETE CASCADE
);

CREATE TABLE disciplina (
    id_disciplina SERIAL PRIMARY KEY,
    nome VARCHAR(255)
);

CREATE TABLE disciplina_curso (
    id_disciplina_curso SERIAL PRIMARY KEY,
    id_curso INTEGER,
    id_disciplina INTEGER,
    CONSTRAINT uq_disciplina_curso UNIQUE (id_curso, id_disciplina),
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso) ON DELETE CASCADE,
    FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina) ON DELETE CASCADE
);

CREATE TABLE usuario_disciplina (
    id_usuario_disciplina SERIAL PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_disciplina INTEGER NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina) ON DELETE CASCADE
);

CREATE TABLE turma (
    id_turma SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    semestre INTEGER,
    ano INTEGER,
    id_curso INTEGER NOT NULL,
    id_turno INTEGER NOT NULL,
    id_instituicao INTEGER NOT NULL, 
    FOREIGN KEY (id_curso) REFERENCES curso(id_curso) ON DELETE CASCADE,
    FOREIGN KEY (id_instituicao) REFERENCES instituicao(id_instituicao) ON DELETE CASCADE,
    FOREIGN KEY (id_turno) REFERENCES turno(id_turno) ON DELETE CASCADE
);

CREATE TABLE matriz (
    id_matriz SERIAL PRIMARY KEY,
    id_turma INTEGER,
    id_disciplina INTEGER,
    FOREIGN KEY (id_turma) REFERENCES turma(id_turma) ON DELETE CASCADE,
    FOREIGN KEY (id_disciplina) REFERENCES disciplina(id_disciplina) ON DELETE CASCADE
);

CREATE TABLE disponibilidade (
    id_disponibilidade SERIAL PRIMARY KEY,
    semestre INTEGER NOT NULL,
    ano INTEGER NOT NULL,
    id_dia_semana INTEGER NOT NULL,
    id_usuario BIGINT NOT NULL,
    id_turno INTEGER NOT NULL,
    id_instituicao INTEGER NOT NULL,
    CONSTRAINT uq_disponibilidade UNIQUE (id_usuario, id_dia_semana, id_turno, semestre, ano, id_instituicao),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_dia_semana) REFERENCES dia_semana(id_dia_semana) ON DELETE CASCADE,
    FOREIGN KEY (id_instituicao) REFERENCES instituicao(id_instituicao) ON DELETE CASCADE,
    FOREIGN KEY (id_turno) REFERENCES turno(id_turno) ON DELETE CASCADE
);

CREATE TABLE grade (
    id_grade SERIAL PRIMARY KEY,
    id_turma INTEGER,
    seg INTEGER,
    ter INTEGER,
    qua INTEGER,
    qui INTEGER,
    sex INTEGER,
    sab INTEGER,
    versao INTEGER,
    status INTEGER,
    FOREIGN KEY (id_turma) REFERENCES turma(id_turma) ON DELETE CASCADE
);

CREATE TABLE erros (
    id_erros SERIAL PRIMARY KEY,
    versao INTEGER NOT NULL,
    erros VARCHAR(255) NOT NULL
);

CREATE TABLE config_sistema (
    id_config_sistema SERIAL PRIMARY KEY,
    campo VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor VARCHAR(255) NOT NULL
);

CREATE TABLE tipo_link (
    id_tipo_link SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE link (
    id_link SERIAL PRIMARY KEY,
    descricao VARCHAR(255) NOT NULL,
    url TEXT NOT NULL,
    criacao TIMESTAMP,
    id_usuario BIGINT NOT NULL,
    id_tipo_link BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_tipo_link) REFERENCES tipo_link(id_tipo_link) ON DELETE CASCADE
);

-- Tabela: turno
INSERT INTO turno (id_turno, descricao, ativo) VALUES
(1, 'Matutino', true),
(2, 'Vespertino', true),
(3, 'Noturno', true);

-- Tabela: dia_semana
INSERT INTO dia_semana (id_dia_semana, descricao, ativo) VALUES
(1, 'Domingo', true),
(2, 'Segunda-feira', true),
(3, 'Terça-feira', true),
(4, 'Quarta-feira', true),
(5, 'Quinta-feira', true),
(6, 'Sexta-feira', true),
(7, 'Sábado', true);

-- Tabela: instituicao
INSERT INTO instituicao (id_instituicao, nome, ativo) VALUES
(1, 'GRADE MAKER', TRUE),
(2, 'UDF Centro-Universitário', TRUE),
(3, 'IESB', TRUE),
(4, 'UNB', FALSE),
(5, 'UNICEUB', TRUE),
(6, 'UNIEURO', TRUE);

-- Tabela: perfil
INSERT INTO perfil (id_perfil, nome, descricao) VALUES
(1, 'ACESSO_ADMIN', 'Perfil com acesso de administrador, pode gerenciar todo o sistema.'),
(2, 'ACESSO_COORDENADOR', 'Perfil de coordenador, com permissões de gerenciamento de cursos, turmas e professor'),
(3, 'ACESSO_PROFESSOR', 'Perfil de professor, com permissões de preencher disponibilidades e consultar grades aprovadas');

-- Tabela: coordenacao
INSERT INTO coordenacao (id_coordenacao, nome, ativo) VALUES
(1, 'Ciências da Computação', TRUE),
(2, 'Engenharia de Software', TRUE),
(3, 'Sistemas de Informação', TRUE);

-- Tabela: curso
INSERT INTO curso (id_curso, nome) VALUES
(1, 'Ciência da Computação (Bacharelado)'),
(2, 'Engenharia de Software (Bacharelado)'),
(3, 'Sistemas de Informação (Bacharelado)');

-- Tabela: disciplina
INSERT INTO disciplina (id_disciplina, nome) VALUES
(1, 'Análise e Projeto de Sistemas'),
(2, 'Análise e Projeto de Sistemas II'),
(3, 'Ciência de Dados e Aprendizagem de Máquina'),
(4, 'Computação Gráfica'),
(5, 'Engenharia de Software'),
(6, 'Estruturas de Dados I'),
(7, 'Estruturas de Dados II'),
(8, 'Fundamentos de Inteligência Artificial'),
(9, 'Gestão de Projeto em Sistemas de Informação'),
(10, 'Gestão de Sistemas de Informação e Métodos'),
(11, 'Laboratório de Banco de Dados'),
(12, 'Paradigmas de Linguagens de Programação'),
(13, 'Programação Para Dispositivos Móveis'),
(14, 'Programação Web'),
(15, 'Projeto de Linguagens de Programação'),
(16, 'Redes de Computadores'),
(17, 'Segurança e Auditoria de Sistemas'),
(18, 'Sistemas Operacionais'),
(19, 'Técnicas de Desenvolvimento de Algoritmos'),
(20, 'Técnicas de Programação'),
(21, 'Teoria Geral da Administração');

-- Tabela: disciplina_curso
INSERT INTO disciplina_curso (id_disciplina_curso, id_curso, id_disciplina) VALUES
(1, 1, 2),
(2, 1, 5),
(3, 2, 7),
(4, 3, 14),
(5, 3, 16),
(6, 3, 17),
(7, 2, 11);

-- Tabela: config_faculdade (anteriormente chamada de config)
INSERT INTO config_faculdade (campo, descricao) VALUES
('peso_conflito_dia_seguinte', 'Penalidade aplicada ao professor com aulas em dias consecutivos'),
('peso_conflito_aula_mesmo_dia_turnos_distintos', 'Penalidade por alocar aulas em turnos diferentes no mesmo dia para o mesmo professor'),
('peso_conflito_sobrecarga_professor', 'Penalidade por exceder o número máximo de aulas no mesmo dia para o professor'),
('peso_conflito_semana_desbalanceada', 'Penalidade para distribuição irregular de aulas ao longo da semana'),
('estilo_tema', 'Definirá se o tema da aplicação será escuro ou claro'),
('estilo_cor_principal', 'Cor principal da interface');

-- Tabela: config_sistema
INSERT INTO config_sistema (campo, descricao, valor) VALUES
('limite_conexao', 'Limite de conexões simultâneas', '100'),
('idioma', 'Idioma do sistema', 'pt-BR'),
('modo_manutencao', 'Indica se o sistema está em manutenção', 'false');

