INSERT INTO users (id, email, password, is_locked, is_enabled, joined_on)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'alice@example.com', 'Password@123', FALSE, TRUE, '2023-01-15'),
       ('550e8400-e29b-41d4-a716-446655440002', 'bob@example.com', 'SecurePass!456', FALSE, TRUE, '2023-02-20'),
       ('550e8400-e29b-41d4-a716-446655440003', 'charlie@example.com', 'MyPass789$', TRUE, FALSE, '2023-03-05'),
       ('550e8400-e29b-41d4-a716-446655440004', 'diana@example.com', 'DianaPass#321', FALSE, TRUE, '2023-04-10'),
       ('550e8400-e29b-41d4-a716-446655440005', 'eve@example.com', 'EveStrong!999', TRUE, FALSE, '2023-05-25'),
       ('550e8400-e29b-41d4-a716-446655440006', 'frank@example.com', 'FrankPass@555', FALSE, TRUE, '2023-06-30'),
       ('550e8400-e29b-41d4-a716-446655440007', 'grace@example.com', 'GracePwd!777', FALSE, TRUE, '2023-07-12'),
       ('550e8400-e29b-41d4-a716-446655440008', 'henry@example.com', 'HenrySecure#888', TRUE, FALSE, '2023-08-18'),
       ('550e8400-e29b-41d4-a716-446655440009', 'isabel@example.com', 'IsabelPass@999', FALSE, TRUE, '2023-09-22'),
       ('550e8400-e29b-41d4-a716-446655440010', 'jack@example.com', 'JackPwd!000', FALSE, TRUE, '2023-10-05');

INSERT INTO institution (code, name, created_on)
VALUES ('INST001', 'Tech University', '2023-01-10'),
       ('INST002', 'Global Business School', '2023-02-15'),
       ('INST003', 'National Medical College', '2023-03-20'),
       ('INST004', 'Creative Arts Institute', '2023-04-05'),
       ('INST005', 'Engineering Research Center', '2023-05-12'),
       ('INST006', 'Law Academy', '2023-06-18'),
       ('INST007', 'International Sports Academy', '2023-07-25'),
       ('INST008', 'Data Science Institute', '2023-08-30'),
       ('INST009', 'Hospitality Management School', '2023-09-14'),
       ('INST010', 'Cybersecurity Training Center', '2023-10-22');

INSERT INTO role (id, name, description, institution_code)
VALUES ('660e8400-e29b-41d4-a716-446655440001', 'Admin', 'Full system access and management', 'INST001'),
       ('660e8400-e29b-41d4-a716-446655440002', 'Professor', 'Responsible for teaching and mentoring students',
        'INST001'),
       ('660e8400-e29b-41d4-a716-446655440003', 'Student', 'Enrolled learner with limited access', 'INST002'),
       ('660e8400-e29b-41d4-a716-446655440004', 'Researcher', 'Conducts academic and industrial research', 'INST003'),
       ('660e8400-e29b-41d4-a716-446655440005', 'Doctor', 'Medical practitioner with patient responsibilities',
        'INST003'),
       ('660e8400-e29b-41d4-a716-446655440006', 'Artist', 'Creative professional in arts programs', 'INST004'),
       ('660e8400-e29b-41d4-a716-446655440007', 'Engineer', 'Designs and develops technical solutions', 'INST005'),
       ('660e8400-e29b-41d4-a716-446655440008', 'Lawyer', 'Legal advisor and case handler', 'INST006'),
       ('660e8400-e29b-41d4-a716-446655440009', 'Athlete', 'Sports trainee and competitor', 'INST007'),
       ('660e8400-e29b-41d4-a716-446655440010', 'Data Analyst', 'Analyzes datasets and builds insights', 'INST008');

INSERT INTO user_role (user_id, role_id)
VALUES ('550e8400-e29b-41d4-a716-446655440001', '660e8400-e29b-41d4-a716-446655440001'), -- Alice → Admin
       ('550e8400-e29b-41d4-a716-446655440002', '660e8400-e29b-41d4-a716-446655440002'), -- Bob → Professor
       ('550e8400-e29b-41d4-a716-446655440003', '660e8400-e29b-41d4-a716-446655440003'), -- Charlie → Student
       ('550e8400-e29b-41d4-a716-446655440004', '660e8400-e29b-41d4-a716-446655440004'), -- Diana → Researcher
       ('550e8400-e29b-41d4-a716-446655440005', '660e8400-e29b-41d4-a716-446655440005'), -- Eve → Doctor
       ('550e8400-e29b-41d4-a716-446655440006', '660e8400-e29b-41d4-a716-446655440006'), -- Frank → Artist
       ('550e8400-e29b-41d4-a716-446655440007', '660e8400-e29b-41d4-a716-446655440007'), -- Grace → Engineer
       ('550e8400-e29b-41d4-a716-446655440008', '660e8400-e29b-41d4-a716-446655440008'), -- Henry → Lawyer
       ('550e8400-e29b-41d4-a716-446655440009', '660e8400-e29b-41d4-a716-446655440009'), -- Isabel → Athlete
       ('550e8400-e29b-41d4-a716-446655440010', '660e8400-e29b-41d4-a716-446655440010');
-- Jack → Data Analyst

-- Admin role (Institution-1)
INSERT INTO role_permissions (role_id, permission)
VALUES ('660e8400-e29b-41d4-a716-446655440001', 'VIEW_USERS'),
       ('660e8400-e29b-41d4-a716-446655440001', 'EDIT_USERS'),
       ('660e8400-e29b-41d4-a716-446655440001', 'DELETE_USERS'),
       ('660e8400-e29b-41d4-a716-446655440001', 'MANAGE_ROLES');

-- Professor role (Institution-1)
INSERT INTO role_permissions (role_id, permission)
VALUES ('660e8400-e29b-41d4-a716-446655440002', 'VIEW_USERS'), -- common with Admin
       ('660e8400-e29b-41d4-a716-446655440002', 'EDIT_USERS'), -- common with Admin
       ('660e8400-e29b-41d4-a716-446655440002', 'GRADE_STUDENTS'),
       ('660e8400-e29b-41d4-a716-446655440002', 'CREATE_ASSIGNMENTS');

-- Student role (Institution-2)
INSERT INTO role_permissions (role_id, permission)
VALUES ('660e8400-e29b-41d4-a716-446655440003', 'VIEW_COURSES'),
       ('660e8400-e29b-41d4-a716-446655440003', 'SUBMIT_ASSIGNMENTS'),
       ('660e8400-e29b-41d4-a716-446655440003', 'VIEW_GRADES');
