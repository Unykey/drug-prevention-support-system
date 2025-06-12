-- User tables --
-- Create ENUM type for gender
CREATE TYPE "gender" AS ENUM ('Male', 'Female', 'Non-binary', 'Other', 'Prefer not to say');
-- Create ENUM type for role
CREATE TYPE "role" AS ENUM('Member', 'Parent', 'Consultant', 'Staff', 'Manager', 'Admin');

-- Create User table
CREATE TABLE "User" (
	user_id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	password TEXT NOT NULL,
	role "role" NOT NULL,
	gender "gender" DEFAULT NULL,
	date_of_birth DATE,
	email VARCHAR(100) UNIQUE,
	phone VARCHAR(15) UNIQUE
);

-- Create Parent table
CREATE TABLE "Parent"(
	parent_id SERIAL PRIMARY KEY,	
    parent_name VARCHAR(100) NOT NULL,
    parent_phone VARCHAR(15),
    parent_email VARCHAR(100)
);

CREATE TABLE "user_parent" (
    user_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
    parent_id INTEGER REFERENCES "Parent"(parent_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, parent_id)
);

-- Create Consultant table
CREATE TABLE "Consultant"(
	consultant_id SERIAL PRIMARY KEY,
    qualification TEXT NOT NULL,
    specialization TEXT,
    available_time TEXT,
	bio TEXT
);

CREATE TABLE "user_consultant" (
    user_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
    consultant_id INTEGER REFERENCES "Consultant"(consultant_id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, consultant_id)
);


-- Survey tables --
-- Create Survey table
CREATE TABLE "Survey"(
	survey_id VARCHAR(30) PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	description TEXT
);

-- Create SurveyQuestion table
CREATE TABLE "SurveyQuestion"(
	question_id INT PRIMARY KEY, 
	survey_id VARCHAR(30) REFERENCES "Survey"(survey_id) ON DELETE CASCADE,
	question_text TEXT NOT NULL,
	question_type VARCHAR(20) NOT NULL
);

-- Create SurveyAnswer table
CREATE TABLE "SurveyResponse" (
	response_id INT PRIMARY KEY,
	survey_id VARCHAR(30) REFERENCES "Survey"(survey_id) ON DELETE CASCADE,
	user_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
	submitted_at TIMESTAMP NOT NULL,
	result_score INT
);

-- Create SurveyAnswer table
CREATE TABLE "SurveyAnswer"(
	answer_id INT PRIMARY KEY,
	response_id INT REFERENCES "SurveyResponse"(response_id) ON DELETE CASCADE,
	question_id INT REFERENCES "SurveyQuestion"(question_id) ON DELETE CASCADE,
	answer_value TEXT NOT NULL
);

-- Course tables --
-- Create Course table
CREATE TABLE "Course"(
	course_id VARCHAR(50) PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	description TEXT,
	target_group VARCHAR(15),
	content_url TEXT
);

-- Create CourseRegistration table

-- Create ENUM type for gender
CREATE TYPE status AS ENUM ('Complete', 'In Progress');

CREATE TABLE "CourseRegistration"(
	user_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
	course_id VARCHAR(50) REFERENCES "Course"(course_id) ON DELETE CASCADE,
	registered_at TIMESTAMP NOT NULL,
	completed_at TIMESTAMP,
	status status,
	PRIMARY KEY (user_id, course_id)
);

-- Program tables --
-- Create Program table --
CREATE TABLE "Program"(
	program_id VARCHAR(50) PRIMARY KEY,
	title VARCHAR(50) NOT NULL,
	description TEXT,
	start_date TIMESTAMP,
	end_date TIMESTAMP,
	target_group VARCHAR(15),
	content_url TEXT
);
-- Create ProgramParticipation table --
CREATE TABLE "ProgramParticipation"(
	user_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
	program_id VARCHAR(50) REFERENCES "Program"(program_id) ON DELETE CASCADE,
	joined_at TIMESTAMP NOT NULL,
	PRIMARY KEY (user_id, program_id)
);

-- ProgramSurvey --
CREATE TABLE "ProgramSurvey"(
	program_survey_id INT PRIMARY KEY,
	user_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
	program_id VARCHAR(50) REFERENCES "Program"(program_id) ON DELETE CASCADE,
	survey_type VARCHAR(20) NOT NULL,
	response_text TEXT,
	submitted_at TIMESTAMP NOT NULL
);

-- Appointment --
CREATE TABLE "Appointment"(
	appointment_id INT PRIMARY KEY,
	member_id INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
	consultant_id INTEGER REFERENCES "Consultant"(consultant_id) ON DELETE CASCADE,
	scheduled_time TIMESTAMP NOT NULL,
	notes TEXT,
	url TEXT NOT NULL
);

-- SystemReport --
CREATE TABLE "SystemReport"(
	report_id INT PRIMARY KEY,
	report_type VARCHAR(50) NOT NULL,
	generated_at TIMESTAMP NOT NULL,
	data_blob TEXT
);

-- Blog --
CREATE TABLE "Blog"(
	blog_id INT PRIMARY KEY,
	created_by INTEGER REFERENCES "User"(user_id) ON DELETE CASCADE,
	title VARCHAR(50) NOT NULL,
	content TEXT NOT NULL,
	created_at TIMESTAMP NOT NULL,
	status BOOLEAN NOT NULL
);