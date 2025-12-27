DROP TABLE IF EXISTS student_upcoming_exams;
DROP TABLE IF EXISTS exam_invigilators;
DROP TABLE IF EXISTS student_result;
DROP TABLE IF EXISTS faculty_in_institution;
DROP TABLE IF EXISTS students_in_institution;
DROP TABLE IF EXISTS exams;
DROP TABLE IF EXISTS subjects_on_curriculum;
DROP TABLE IF EXISTS term;
DROP TABLE IF EXISTS curriculums_on_institute;
DROP TABLE IF EXISTS curriculum;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS program;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS building;
DROP TABLE IF EXISTS user_education;
DROP TABLE IF EXISTS education;
DROP TABLE IF EXISTS user_permissions;
DROP TABLE IF EXISTS role_permissions;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS stuff_salary;
-- This two only works if teh tables already created.

# ALTER TABLE institution
#     DROP FOREIGN KEY fk_director_stuff_profile;
# ALTER TABLE institution
#     DROP FOREIGN KEY fk_principal_stuff_profile;


DROP TABLE IF EXISTS stuff_details;
DROP TABLE IF EXISTS stuff_profile;
DROP TABLE IF EXISTS salary;
DROP TABLE IF EXISTS institution;
DROP TABLE IF EXISTS student_profile;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS address;

CREATE TABLE address
(
    id          VARCHAR(36) PRIMARY KEY,
    building_no VARCHAR(20) NOT NULL,
    street      VARCHAR(100),
    city        VARCHAR(50),
    district    VARCHAR(50),
    state       VARCHAR(50),
    country     VARCHAR(50)
);
CREATE TABLE users
(
    id                      VARCHAR(36) PRIMARY KEY,
    email                   VARCHAR(100) UNIQUE NOT NULL,
    password                VARCHAR(100)        NOT NULL,
    first_name              VARCHAR(50),
    middle_name             VARCHAR(50),
    last_name               VARCHAR(50),
    date_of_birth           DATE,
    phone_country           VARCHAR(5),
    phone                   VARCHAR(15),
    alternate_phone_country VARCHAR(5),
    alternate_phone         VARCHAR(15),
    current_address         VARCHAR(36),
    permanent_address       VARCHAR(36),
    gender                  VARCHAR(10)         NOT NULL,
    is_locked               BOOLEAN             NOT NULL,
    is_enabled              BOOLEAN             NOT NULL,
    joined_on               DATE                NOT NULL
);

CREATE TABLE role
(
    name        VARCHAR(100) PRIMARY KEY,
    description VARCHAR(500)
);

CREATE TABLE user_role
(
    user_id       VARCHAR(36),
    role_name     VARCHAR(100),
    PRIMARY KEY (user_id, role_name),
    starting_from DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_name) REFERENCES role (name) ON DELETE CASCADE
);

CREATE TABLE role_permissions
(
    role_name  VARCHAR(100),
    permission VARCHAR(200),
    FOREIGN KEY (role_name) REFERENCES role (name) ON DELETE CASCADE,
    PRIMARY KEY (role_name, permission)
);


CREATE TABLE institution
(
    code       VARCHAR(50) PRIMARY KEY,
    name       VARCHAR(200) NOT NULL UNIQUE,
    created_on DATE         NOT NULL
);

CREATE TABLE stuff_profile
(
    user_id VARCHAR(36) PRIMARY KEY,
    -- TODO: define the stuff profile fields.
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

);

CREATE TABLE user_permissions
(
    stuff_id   VARCHAR(36),
    permission VARCHAR(200),
    FOREIGN KEY (stuff_id) REFERENCES stuff_profile (user_id) ON DELETE CASCADE,
    PRIMARY KEY (stuff_id, permission)
);


CREATE TABLE stuff_details
(
    id               VARCHAR(36) PRIMARY KEY,
    stuff_id         VARCHAR(36) NOT NULL,
    institution_code VARCHAR(50) NOT NULL,
    stuff_type       ENUM ('ACADEMIC','NON_ACADEMIC','LAB_OPERATOR','ENGINEER'),
    UNIQUE (stuff_id, institution_code, stuff_type),
    starting_from    DATE        NOT NULL,
    ending_at        DATE,
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE,
    FOREIGN KEY (stuff_id) REFERENCES stuff_profile (user_id) ON DELETE CASCADE
);

ALTER TABLE institution
    ADD COLUMN principal VARCHAR(36);
ALTER TABLE institution
    ADD CONSTRAINT fk_principal_stuff_profile
        FOREIGN KEY (principal) REFERENCES stuff_details (id) ON DELETE SET NULL;


ALTER TABLE institution
    ADD COLUMN director VARCHAR(36);
ALTER TABLE institution
    ADD CONSTRAINT fk_director_stuff_profile
        FOREIGN KEY (director) REFERENCES stuff_details (id) ON DELETE SET NULL;


CREATE TABLE salary
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50)
);

CREATE TABLE stuff_salary
(
    salary_id     BIGINT,
    stuff_id      VARCHAR(36),

    academic_year VARCHAR(9),
    FOREIGN KEY (salary_id) REFERENCES salary (id) ON DELETE CASCADE,
    FOREIGN KEY (stuff_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE education
(
    id         VARCHAR(50) PRIMARY KEY,
    major      VARCHAR(50) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    user_id    VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE user_education
(
    user_id      VARCHAR(36),
    education_id VARCHAR(36),
    added_on     DATE NOT NULL,
    PRIMARY KEY (user_id, education_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (education_id) REFERENCES education (id) ON DELETE CASCADE
);



CREATE TABLE building
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    address_id       VARCHAR(36)  NOT NULL,
    institution_code VARCHAR(50)  NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (id),
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE
);


CREATE TABLE subject
(
    code             VARCHAR(50) PRIMARY KEY,
    name             VARCHAR(200) NOT NULL,
    credit           INT          NOT NULL,
    institution_code VARCHAR(50),
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE
);
CREATE TABLE program
(
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);
CREATE TABLE department
(
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE
);


CREATE TABLE curriculum
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_approved     BOOLEAN NOT NULL,
    is_autonomous   BOOLEAN NOT NULL,
    created_on      DATE    NOT NULL,
    discontinued_on DATE,
    name            VARCHAR(200)
);

CREATE TABLE curriculums_on_institute
(
    institution_code VARCHAR(50),
    program_code     VARCHAR(50),
    department_code  VARCHAR(50),
    curriculum_id    BIGINT,
    start_date       DATE NOT NULL,
    end_date         DATE,
    PRIMARY KEY (institution_code, program_code, department_code, curriculum_id),
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE,
    FOREIGN KEY (program_code) REFERENCES program (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES department (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE
);

CREATE TABLE term
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    duration_in_months INT NOT NULL,
    term_serial        INT
);

CREATE TABLE subjects_on_curriculum
(
    subject_code  VARCHAR(50),
    curriculum_id BIGINT,
    PRIMARY KEY (subject_code, curriculum_id),
    term_id       BIGINT NOT NULL,
    credit_points INT    NOT NULL,
    FOREIGN KEY (subject_code) REFERENCES subject (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE,
    FOREIGN KEY (term_id) REFERENCES term (id) ON DELETE CASCADE
);


CREATE TABLE exams
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    curriculum_id     BIGINT        NOT NULL,
    term_id           BIGINT        NOT NULL,
    subject_code      VARCHAR(50)   NOT NULL,
    total_marks       DECIMAL(5, 2) NOT NULL,
    duration_in_hours INT           NOT NULL,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE,
    FOREIGN KEY (term_id) REFERENCES term (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_code) REFERENCES subject (code) ON DELETE CASCADE
);

-- This table store records about the academic faculties who teaches what on which institution from to till a date.
CREATE TABLE faculty_in_institution
(
    id              VARCHAR(36) PRIMARY KEY,
    faculty_id      VARCHAR(36) NOT NULL,
    program_code    VARCHAR(50) NOT NULL,
    department_code VARCHAR(50) NOT NULL,
    subject_code    VARCHAR(50) NOT NULL,
    start_date      DATE        NOT NULL,
    end_date        DATE,
    type            ENUM ('Permanent','Guest'),
    FOREIGN KEY (faculty_id) REFERENCES stuff_details (id) ON DELETE CASCADE,
    FOREIGN KEY (program_code) REFERENCES program (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES department (code) ON DELETE CASCADE,
    FOREIGN KEY (subject_code) REFERENCES subject (code) ON DELETE CASCADE
);


CREATE TABLE students_in_institution
(
    registration      VARCHAR(20) PRIMARY KEY,
    student_id        VARCHAR(36) NOT NULL,
    program_code      VARCHAR(50) NOT NULL,
    institution_code  VARCHAR(50) NOT NULL,
    department_code   VARCHAR(50) NOT NULL,
    curriculum_id     BIGINT      NOT NULL,
    registration_year DATE        NOT NULL,
    term_id           BIGINT,
    passing_year      DATE,
    FOREIGN KEY (term_id) REFERENCES term (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (program_code) REFERENCES program (code) ON DELETE CASCADE,
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES department (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE
);

CREATE TABLE exam_invigilators
(
    exam_id        BIGINT,
    venue_id       BIGINT,
    held_on        DATE,
    invigilator_id VARCHAR(36),
    PRIMARY KEY (exam_id, venue_id, held_on, invigilator_id),
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES building (id) ON DELETE CASCADE,
    FOREIGN KEY (invigilator_id) REFERENCES stuff_profile (user_id) ON DELETE CASCADE
);


CREATE TABLE student_upcoming_exams
(
    exam_id       BIGINT,
    student_id    VARCHAR(36),
    held_on       DATE,
    venue_id      BIGINT NOT NULL,
    starting_time TIME,
    PRIMARY KEY (exam_id, student_id, held_on),
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES building (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE student_result
(
    student_id   VARCHAR(36),
    exam_id      BIGINT,
    attempt_id   INT DEFAULT 1,
    obtain_marks DECIMAL(5, 2),
    held_on      DATE,
    status       ENUM ('NOT_ATTEMPTED','ATTEMPTED','CANCELLED'),
    PRIMARY KEY (student_id, exam_id, held_on, attempt_id),
    FOREIGN KEY (student_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE
);