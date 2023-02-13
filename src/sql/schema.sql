
  CREATE DATABASE IF NOT EXISTS cs_hu_310_final_project; 
USE cs_hu_310_final_project; 
DROP TABLE IF EXISTS class_registrations; 
DROP TABLE IF EXISTS grades; 
DROP TABLE IF EXISTS class_sections; 
DROP TABLE IF EXISTS instructors; 
DROP TABLE IF EXISTS academic_titles; 
DROP TABLE IF EXISTS students; 
DROP TABLE IF EXISTS classes; 
DROP FUNCTION IF EXISTS convert_to_grade_point; 
 
CREATE TABLE IF NOT EXISTS classes( 
    class_id INT NOT NULL AUTO_INCREMENT, 
    name VARCHAR(50) NOT NULL, 
    description VARCHAR(1000), 
    code VARCHAR(10) UNIQUE, 
    maximum_students INT DEFAULT 10, 
    PRIMARY KEY(class_id) 
); 
 
CREATE TABLE IF NOT EXISTS students( 
    student_id INT NOT NULL AUTO_INCREMENT, 
    first_name VARCHAR(30) NOT NULL, 
    last_name VARCHAR(50) NOT NULL, 
    birthdate DATE, 
    PRIMARY KEY (student_id) 
); 

CREATE TABLE IF NOT EXISTS `academic_titles` (
  `academic_title_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`academic_title_id`)
);

CREATE TABLE IF NOT EXISTS `cs_hu_310_final_project`.`instructors` (
  `instructor_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(80) NOT NULL,
  `last_name` VARCHAR(80) NOT NULL,
  `academic_title_id` INT NULL,
  PRIMARY KEY (`instructor_id`),
  INDEX `FK_instructors_academic_idx` (`academic_title_id` ASC) VISIBLE,
  CONSTRAINT `FK_instructors_academic`
    FOREIGN KEY (`academic_title_id`)
    REFERENCES `cs_hu_310_final_project`.`academic_titles` (`academic_title_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

   
CREATE TABLE IF NOT EXISTS `cs_hu_310_final_project`.`terms` (
  `term_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(80) NOT NULL,
  PRIMARY KEY (`term_id`));

CREATE TABLE IF NOT EXISTS `cs_hu_310_final_project`.`class_sections` (
  `class_section_id` INT NOT NULL AUTO_INCREMENT,
  `class_id` INT NOT NULL,
  `instructor_id` INT NOT NULL,
  `term_id` INT NOT NULL,
  PRIMARY KEY (`class_section_id`),
  INDEX `FK_class_id_idx` (`class_id` ASC) VISIBLE,
  INDEX `FK_instructor_id_idx` (`instructor_id` ASC) VISIBLE,
  INDEX `FK_term_id_idx` (`term_id` ASC) VISIBLE,
  CONSTRAINT `FK_class_id`
    FOREIGN KEY (`class_id`)
    REFERENCES `cs_hu_310_final_project`.`classes` (`class_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_instructor_id`
    FOREIGN KEY (`instructor_id`)
    REFERENCES `cs_hu_310_final_project`.`instructors` (`instructor_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_term_id`
    FOREIGN KEY (`term_id`)
    REFERENCES `cs_hu_310_final_project`.`terms` (`term_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE IF NOT EXISTS `cs_hu_310_final_project`.`grades` (
  `grade_id` INT NOT NULL AUTO_INCREMENT,
  `letter_grade` CHAR(2) NOT NULL,
  PRIMARY KEY (`grade_id`));
     
CREATE TABLE IF NOT EXISTS `cs_hu_310_final_project`.`class_registrations` (
  `class_registration_id` INT NOT NULL AUTO_INCREMENT,
  `class_section_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `grade_id` INT NULL,
  `signup_timestamp` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`class_registration_id`),
  INDEX `FK_class_section_idx` (`class_section_id` ASC) VISIBLE,
  INDEX `FK_student_idx` (`student_id` ASC) VISIBLE,
  INDEX `FK_grade_idx` (`grade_id` ASC) VISIBLE,
  UNIQUE INDEX `UNIQUE_dupReg` (`student_id` ASC, `class_section_id` ASC) INVISIBLE,
  CONSTRAINT `FK_class_section`
    FOREIGN KEY (`class_section_id`)
    REFERENCES `cs_hu_310_final_project`.`class_sections` (`class_section_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_student`
    FOREIGN KEY (`student_id`)
    REFERENCES `cs_hu_310_final_project`.`students` (`student_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_grade`
    FOREIGN KEY (`grade_id`)
    REFERENCES `cs_hu_310_final_project`.`grades` (`grade_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

    
