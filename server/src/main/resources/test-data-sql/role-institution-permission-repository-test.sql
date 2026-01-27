-- =========================
-- Institutions
-- =========================
INSERT INTO institutions (code, name, created_on)
VALUES ('INST_001', 'Institute of engineering and management', CURRENT_DATE),
       ('INST_002', 'Global Tech Institute', CURRENT_DATE);
-- =========================
-- Roles
-- =========================
INSERT INTO roles (id, name, description, created_on, institution_code)
VALUES
-- Institution-specific roles
('ROLE_ADMIN_INST_001', 'Institution Admin', 'Dummy role', CURRENT_DATE, 'INST_001'),
('ROLE_USER_INST_001', 'Institution User', 'Dummy role', CURRENT_DATE, 'INST_001'),

('ROLE_ADMIN_INST_002', 'Institution Admin', 'Dummy role', CURRENT_DATE, 'INST_002'),
('ROLE_USER_INST_002', 'Institution User', 'Dummy Role', CURRENT_DATE, 'INST_002');

-- =========================
-- Role → Institution Permissions
-- (placeholders as requested)
-- =========================
INSERT INTO role_institution_permissions (role_id, institution_permission)
VALUES
-- Acme University permissions
('ROLE_ADMIN_INST_001', 'TEST_INSTITUTE_PERMISSION_1'),
('ROLE_USER_INST_001', 'TEST_INSTITUTE_PERMISSION_1'),
('ROLE_USER_INST_001', 'TEST_INSTITUTE_PERMISSION_2'),
('ROLE_USER_INST_001', 'TEST_INSTITUTE_PERMISSION_4'),

-- Global Tech Institute permissions
('ROLE_ADMIN_INST_002', 'TEST_INSTITUTE_PERMISSION_1'),
('ROLE_USER_INST_002', 'TEST_INSTITUTE_PERMISSION_3'),
('ROLE_USER_INST_002', 'TEST_INSTITUTE_PERMISSION_5');
