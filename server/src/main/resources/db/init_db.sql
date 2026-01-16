
CREATE TABLE address
(
    id          VARCHAR(36) PRIMARY KEY,
    building_no VARCHAR(20) NOT NULL,
    street      VARCHAR(100),
    city        VARCHAR(50),
    district    VARCHAR(50),
    state       VARCHAR(50),
    country     VARCHAR(20)
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
    gender                  VARCHAR(10),
    is_locked               BOOLEAN             NOT NULL,
    is_enabled              BOOLEAN             NOT NULL,
    joined_on               DATE                NOT NULL
);

CREATE TABLE system_role
(
    id         VARCHAR(36) PRIMARY KEY,
    role_name  VARCHAR(50) NOT NULL,
    created_on DATE        NOT NULL
);

CREATE TABLE user_system_permission
(
    user_id        VARCHAR(36),
    system_role_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (system_role_id) REFERENCES system_role (id) ON DELETE CASCADE
);

CREATE TABLE system_role_permission
(
    system_role_id    VARCHAR(36),
    system_permission VARCHAR(50),
    FOREIGN KEY (system_role_id) REFERENCES system_role (id) ON DELETE CASCADE
);

CREATE TABLE user_permissions
(
    user_id           VARCHAR(36),
    system_permission VARCHAR(30),
    PRIMARY KEY (user_id, system_permission)
);

CREATE TABLE institution
(
    code       VARCHAR(50) PRIMARY KEY,
    name       VARCHAR(200) NOT NULL UNIQUE,
    created_on DATE         NOT NULL
);

CREATE TABLE institution_role
(
    id               VARCHAR(36) PRIMARY KEY,
    name             VARCHAR(100),
    description      VARCHAR(500),
    institution_code VARCHAR(50),
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE
);

CREATE TABLE user_institution_role
(
    user_id VARCHAR(36),
    role_id VARCHAR(36),
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES institution_role (id) ON DELETE CASCADE
);

CREATE TABLE role_permissions
(
    role_id    VARCHAR(36),
    permission VARCHAR(30),
    PRIMARY KEY (role_id, permission),
    FOREIGN KEY (role_id) REFERENCES institution_role (id) ON DELETE CASCADE
);

CREATE TABLE salary
(
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE stuff_profile
(
    user_id VARCHAR(36) PRIMARY KEY,
    -- TODO: define the stuff profile fields.
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE

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



CREATE TABLE education
(
    id         VARCHAR(50) PRIMARY KEY,
    major      VARCHAR(50) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    user_id    VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
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

CREATE TABLE curriculum
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(200) NOT NULL,
    is_approved     BOOLEAN      NOT NULL,
    is_autonomous   BOOLEAN      NOT NULL,
    created_on      DATE         NOT NULL,
    discontinued_on DATE
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
    duration_in_months INT    NOT NULL,
    term_serial        INT,
    curriculum_id      BIGINT NOT NULL,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE
);

CREATE TABLE subjects_on_curriculum
(
    subject_code  VARCHAR(50),
    curriculum_id BIGINT,
    -- This protects to create a row where one subject is added two times in on curriculum.
    UNIQUE (subject_code, curriculum_id),
    credit_points INT NOT NULL,
    FOREIGN KEY (subject_code) REFERENCES subject (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE
);

CREATE TABLE subjects_terms
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    term_id      BIGINT      NOT NULL,
    subject_code VARCHAR(50) NOT NULL,
    UNIQUE (term_id, subject_code),
    FOREIGN KEY (term_id) REFERENCES term (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_code) REFERENCES subject (code) ON DELETE CASCADE
);

-- This table store records about the academic faculties who teach what on which institution from to till a date.
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
    institution_code  VARCHAR(50) NOT NULL,
    program_code      VARCHAR(50) NOT NULL,
    department_code   VARCHAR(50) NOT NULL,
    curriculum_id     BIGINT      NOT NULL,
    registration_year DATE        NOT NULL,
    current_term_id   BIGINT,
    passing_year      DATE,
    FOREIGN KEY (current_term_id) REFERENCES term (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (program_code) REFERENCES program (code) ON DELETE CASCADE,
    FOREIGN KEY (institution_code) REFERENCES institution (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES department (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculum (id) ON DELETE CASCADE
);


CREATE TABLE exams
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    subject_term_id   BIGINT        NOT NULL,
    -- Can be final or mid-semester exam.
    exam_type         VARCHAR(20)   NOT NULL,
    total_marks       DECIMAL(5, 2) NOT NULL,
    duration_in_hours INT           NOT NULL,
    FOREIGN KEY (subject_term_id) REFERENCES subjects_terms (id) ON DELETE CASCADE
);

CREATE TABLE exams_up_coming
(
    id       VARCHAR(36) PRIMARY KEY,
    exam_id  BIGINT,
    held_on  DATETIME,
    venue_id BIGINT,
    UNIQUE (exam_id, held_on, venue_id),
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES building (id) ON DELETE CASCADE
);

CREATE TABLE exam_invigilators
(
    exam_upcoming VARCHAR(36),
    stuff_id      VARCHAR(36),
    PRIMARY KEY (exam_upcoming, stuff_id),
    FOREIGN KEY (exam_upcoming) REFERENCES exams_up_coming (id) ON DELETE CASCADE,
    FOREIGN KEY (stuff_id) REFERENCES stuff_profile (user_id) ON DELETE CASCADE
);


CREATE TABLE student_exams
(
    exams_upcoming VARCHAR(36),
    student_registration VARCHAR(20),
    PRIMARY KEY (exams_upcoming, student_registration),
    FOREIGN KEY (exams_upcoming) REFERENCES exams_up_coming (id) ON DELETE CASCADE,
    FOREIGN KEY (student_registration) REFERENCES students_in_institution(registration) ON DELETE CASCADE
);


CREATE TABLE student_result
(
    student_registration VARCHAR(20),
    exam_id      BIGINT,
    obtain_marks DECIMAL(5, 2),
    held_on      DATE,
    status       ENUM ('NOT_ATTEMPTED','ATTEMPTED','CANCELLED'),
    PRIMARY KEY (student_registration, exam_id, held_on),
    FOREIGN KEY (student_registration) REFERENCES students_in_institution(registration) ON DELETE CASCADE,
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE
);