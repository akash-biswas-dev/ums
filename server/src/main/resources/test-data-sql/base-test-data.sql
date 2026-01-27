INSERT INTO users(id, email, password, is_locked, is_enabled, is_profile_completed, joined_on)
VALUES ('550e8400-e29b-41d4-a716-446655440006', 'frank@example.com', 'FrankPass@555', FALSE, TRUE, FALSE, '2023-06-30'),
       ('550e8400-e29b-41d4-a716-446655440007', 'grace@example.com', 'GracePwd!777', FALSE, TRUE, FALSE, '2023-07-12'),
       ('550e8400-e29b-41d4-a716-446655440008', 'henry@example.com', 'HenrySecure#888', TRUE, FALSE, FALSE,
        '2023-08-18'),
       ('550e8400-e29b-41d4-a716-446655440009', 'isabel@example.com', 'IsabelPass@999', FALSE, TRUE, FALSE,
        '2023-09-22'),
       ('550e8400-e29b-41d4-a716-446655440010', 'jack@example.com', 'JackPwd!000', FALSE, TRUE, FALSE, '2023-10-05');

INSERT INTO institutions (code, name, created_on)
VALUES ('INST001', 'Tech University', '2023-01-10'),
       ('INST002', 'Global Business School', '2023-02-15'),
       ('INST003', 'National Medical College', '2023-03-20'),
       ('INST004', 'Creative Arts Institute', '2023-04-05'),
       ('INST005', 'Engineering Research Center', '2023-05-12'),
       ('INST006', 'Law Academy', '2023-06-18'),
       ('INST007', 'International Sports Academy', '2023-07-25');

INSERT INTO roles (id, name, description, created_on)
VALUES ('660e8400-e29b-41d4-a716-446655440001', 'System Admin', 'A role which have all system level permissions',
        '2023-01-10'),
       ('660e8400-e29b-41d4-a716-446655440002', 'Exam Controller',
        'Role that can control all exams across the institutions', '2023-01-10'),
       ('660e8400-e29b-41d4-a716-446655440008', 'Director', 'Institution diretor', '2023-12-20'),
       ('660e8400-e29b-41d4-a716-446655440009', 'Athlete', 'Sports trainee and competitor', '2023-01-10'),
       ('660e8400-e29b-41d4-a716-446655440010', 'Data Analyst', 'Analyzes datasets and builds insights', '2023-01-10');


