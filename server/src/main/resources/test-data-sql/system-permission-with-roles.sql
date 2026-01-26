INSERT INTO roles (id, name, description, created_on)
VALUES ('660e8400-e29b-41d4-a716-446655440001', 'Admin', 'Full system access and management', '2023-01-10'),
       ('660e8400-e29b-41d4-a716-446655440002', 'Professor', 'Responsible for teaching and mentoring students',
        '2023-01-10');

INSERT INTO role_system_permissions(role_id, system_permission)
VALUES ('660e8400-e29b-41d4-a716-446655440001', 'PROGRAM_READ'),
       ('660e8400-e29b-41d4-a716-446655440002', 'PROGRAM_WRITE'),
       ('660e8400-e29b-41d4-a716-446655440002', 'PROGRAM_READ'),
       ('660e8400-e29b-41d4-a716-446655440001', 'PROGRAM_WRITE'),
       ('660e8400-e29b-41d4-a716-446655440001', 'PROGRAM_DELETE');






