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
    gender                  VARCHAR(10),
    date_of_birth           DATE,
    phone_country           VARCHAR(5),
    phone                   VARCHAR(15),
    alternate_phone_country VARCHAR(5),
    alternate_phone         VARCHAR(15),
    current_address         VARCHAR(36),
    permanent_address       VARCHAR(36),
    is_locked               BOOLEAN             NOT NULL,
    is_enabled              BOOLEAN             NOT NULL,
    is_profile_completed    BOOLEAN             NOT NULL,
    joined_on               DATE                NOT NULL
);

CREATE TABLE institutions
(
    code       VARCHAR(50) PRIMARY KEY,
    name       VARCHAR(200) NOT NULL UNIQUE,
    created_on DATE         NOT NULL
);

CREATE TABLE roles
(
    id               VARCHAR(36) PRIMARY KEY,
    name             VARCHAR(50) NOT NULL,
    description      VARCHAR(200),
    created_on       DATE        NOT NULL,
    institution_code VARCHAR(50)
);

CREATE TABLE user_roles
(
    user_id VARCHAR(36),
    role_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE role_system_permissions
(
    role_id           VARCHAR(36),
    system_permission VARCHAR(30),
    PRIMARY KEY (role_id, system_permission),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE role_institution_permissions
(
    role_id                VARCHAR(36),
    institution_permission VARCHAR(30),
    PRIMARY KEY (role_id, institution_permission),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

CREATE TABLE salaries
(
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE stuff_profiles
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
    FOREIGN KEY (institution_code) REFERENCES institutions (code) ON DELETE CASCADE,
    FOREIGN KEY (stuff_id) REFERENCES stuff_profiles (user_id) ON DELETE CASCADE
);



CREATE TABLE educations
(
    id         VARCHAR(50) PRIMARY KEY,
    major      VARCHAR(50) NOT NULL,
    start_date DATE        NOT NULL,
    end_date   DATE        NOT NULL,
    user_id    VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE programs
(
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE departments
(
    code VARCHAR(50) PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE
);


CREATE TABLE buildings
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    address_id       VARCHAR(36)  NOT NULL,
    institution_code VARCHAR(50)  NOT NULL,
    FOREIGN KEY (address_id) REFERENCES address (id),
    FOREIGN KEY (institution_code) REFERENCES institutions (code) ON DELETE CASCADE
);


CREATE TABLE subjects
(
    code             VARCHAR(50) PRIMARY KEY,
    name             VARCHAR(200) NOT NULL,
    credit           INT          NOT NULL,
    institution_code VARCHAR(50),
    FOREIGN KEY (institution_code) REFERENCES institutions (code) ON DELETE CASCADE
);

CREATE TABLE curriculums
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(200) NOT NULL,
    is_approved     BOOLEAN      NOT NULL,
    is_autonomous   BOOLEAN      NOT NULL,
    created_on      DATE         NOT NULL,
    discontinued_on DATE
);

CREATE TABLE curriculums_on_institutions
(
    institution_code VARCHAR(50),
    program_code     VARCHAR(50),
    department_code  VARCHAR(50),
    curriculum_id    BIGINT,
    start_date       DATE NOT NULL,
    end_date         DATE,
    PRIMARY KEY (institution_code, program_code, department_code, curriculum_id),
    FOREIGN KEY (institution_code) REFERENCES institutions (code) ON DELETE CASCADE,
    FOREIGN KEY (program_code) REFERENCES programs (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES departments (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculums (id) ON DELETE CASCADE
);

CREATE TABLE terms
(
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    duration_in_months INT    NOT NULL,
    term_serial        INT,
    curriculum_id      BIGINT NOT NULL,
    FOREIGN KEY (curriculum_id) REFERENCES curriculums (id) ON DELETE CASCADE
);

CREATE TABLE subjects_on_curriculums
(
    subject_code  VARCHAR(50),
    curriculum_id BIGINT,
    -- This protects to create a row where one subject is added two times in on curriculum.
    UNIQUE (subject_code, curriculum_id),
    credit_points INT NOT NULL,
    FOREIGN KEY (subject_code) REFERENCES subjects (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculums (id) ON DELETE CASCADE
);

CREATE TABLE subjects_terms
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    term_id      BIGINT      NOT NULL,
    subject_code VARCHAR(50) NOT NULL,
    UNIQUE (term_id, subject_code),
    FOREIGN KEY (term_id) REFERENCES terms (id) ON DELETE CASCADE,
    FOREIGN KEY (subject_code) REFERENCES subjects (code) ON DELETE CASCADE
);

-- This table store records about the academic faculties who teach what on which institution from to till a date.
CREATE TABLE faculty_in_institutions
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
    FOREIGN KEY (program_code) REFERENCES programs (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES departments (code) ON DELETE CASCADE,
    FOREIGN KEY (subject_code) REFERENCES subjects (code) ON DELETE CASCADE
);


CREATE TABLE students_in_institutions
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
    FOREIGN KEY (current_term_id) REFERENCES terms (id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (program_code) REFERENCES programs (code) ON DELETE CASCADE,
    FOREIGN KEY (institution_code) REFERENCES institutions (code) ON DELETE CASCADE,
    FOREIGN KEY (department_code) REFERENCES departments (code) ON DELETE CASCADE,
    FOREIGN KEY (curriculum_id) REFERENCES curriculums (id) ON DELETE CASCADE
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

CREATE TABLE upcoming_exams
(
    id       VARCHAR(36) PRIMARY KEY,
    exam_id  BIGINT,
    held_on  DATETIME,
    venue_id BIGINT,
    UNIQUE (exam_id, held_on, venue_id),
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE,
    FOREIGN KEY (venue_id) REFERENCES buildings (id) ON DELETE CASCADE
);

CREATE TABLE exam_invigilators
(
    exam_upcoming VARCHAR(36),
    stuff_id      VARCHAR(36),
    PRIMARY KEY (exam_upcoming, stuff_id),
    FOREIGN KEY (exam_upcoming) REFERENCES upcoming_exams (id) ON DELETE CASCADE,
    FOREIGN KEY (stuff_id) REFERENCES stuff_profiles (user_id) ON DELETE CASCADE
);


CREATE TABLE student_exams
(
    exams_upcoming       VARCHAR(36),
    student_registration VARCHAR(20),
    PRIMARY KEY (exams_upcoming, student_registration),
    FOREIGN KEY (exams_upcoming) REFERENCES upcoming_exams (id) ON DELETE CASCADE,
    FOREIGN KEY (student_registration) REFERENCES students_in_institutions (registration) ON DELETE CASCADE
);


CREATE TABLE student_results
(
    student_registration VARCHAR(20),
    exam_id              BIGINT,
    obtain_marks         DECIMAL(5, 2),
    held_on              DATE,
    status               ENUM ('NOT_ATTEMPTED','ATTEMPTED','CANCELLED'),
    PRIMARY KEY (student_registration, exam_id, held_on),
    FOREIGN KEY (student_registration) REFERENCES students_in_institutions (registration) ON DELETE CASCADE,
    FOREIGN KEY (exam_id) REFERENCES exams (id) ON DELETE CASCADE
);