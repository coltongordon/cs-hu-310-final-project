/* Put your final project reporting queries here */
USE cs_hu_310_final_project;

-- Example (remove before submitting)
-- Get all students
SELECT
    *
FROM students;




-- 1.Calculate the GPA for student given a student_id (use student_id=1) 
SELECT s.first_name, s.last_name, 
		COUNT(cr.class_section_id) AS "number_of_classes",
		SUM(convert_to_grade_point(g.letter_grade)) AS "total_grade_point_earned",
		(SUM(convert_to_grade_point(g.letter_grade))/COUNT(cr.class_section_id)) AS "GPA"
FROM students s 
LEFT JOIN  class_registrations cr
	ON cr.student_id = s.student_id
LEFT JOIN grades g
	ON g.grade_id = cr.grade_id
--  JOIN <> 
WHERE g.letter_grade IS NOT null AND s.student_id = 1
GROUP BY s.first_name, s.last_name;


SELECT s.first_name, s.last_name, 
		COUNT(cr.class_section_id) AS "number_of_classes",
		SUM(convert_to_grade_point(g.letter_grade)) AS "total_grade_point_earned",
		(SUM(convert_to_grade_point(g.letter_grade))/COUNT(cr.class_section_id)) AS "GPA"
FROM students s 
LEFT JOIN  class_registrations cr
	ON cr.student_id = s.student_id
LEFT JOIN grades g
	ON g.grade_id = cr.grade_id
--  JOIN <> 
WHERE g.letter_grade IS NOT null -- AND s.student_id = 1
GROUP BY s.first_name, s.last_name;
 
 SELECT c.code, c.name,
		COUNT(cr.grade_id) AS "number_of_grades",
        SUM(convert_to_grade_point(g.letter_grade)) AS "total_grade_points",
        (SUM(convert_to_grade_point(g.letter_grade))/COUNT(cr.grade_id)) AS "AVG GPA"
 FROM classes c
 LEFT JOIN class_sections cs
	ON cs.class_id = c.class_id
LEFT JOIN class_registrations cr
	ON cr.class_section_id = cs.class_section_id
LEFT JOIN grades g
	ON g.grade_id = cr.grade_id
GROUP BY c.code, c.name;
 
  SELECT c.code, c.name, t.name,
		COUNT(cr.grade_id) AS "number_of_grades",
        SUM(convert_to_grade_point(g.letter_grade)) AS "total_grade_points",
        (SUM(convert_to_grade_point(g.letter_grade))/COUNT(cr.grade_id)) AS "AVG GPA"
 FROM classes c
 LEFT JOIN class_sections cs
	ON cs.class_id = c.class_id
LEFT JOIN terms t
	ON cs.term_id = t.term_id
LEFT JOIN class_registrations cr
	ON cr.class_section_id = cs.class_section_id
LEFT JOIN grades g
	ON g.grade_id = cr.grade_id
    WHERE g.letter_grade IS NOT NULL
GROUP BY c.code, c.name, t.name;


 SELECT c.code, c.name,
		COUNT(cr.grade_id) AS "number_of_grades",
        SUM(convert_to_grade_point(g.letter_grade)) AS "total_grade_points",
        (SUM(convert_to_grade_point(g.letter_grade))/COUNT(cr.grade_id)) AS "AVG GPA"
 FROM classes c
 LEFT JOIN class_sections cs
	ON cs.class_id = c.class_id
LEFT JOIN class_registrations cr
	ON cr.class_section_id = cs.class_section_id
LEFT JOIN grades g
	ON g.grade_id = cr.grade_id
GROUP BY c.code, c.name;
 
  SELECT i.first_name, i.last_name,
		act.title, c.code, c.name AS "class_name",
        t.name AS "term" 
  FROM instructors i
  INNER JOIN academic_titles act
	ON i.academic_title_id = act.academic_title_id
  LEFT JOIN class_sections cs
	ON cs.instructor_id = i.instructor_id
  LEFT JOIN classes c
	ON c.class_id = cs.class_id
 LEFT JOIN terms t
	ON t.term_id = cs.term_id
WHERE i.instructor_id = 1;
--   GROUP BY i.first_name, i.last_name;

SELECT c.code, c.name, t.name AS "term",
		i.first_name, i.last_name
FROM class_sections cs
LEFT JOIN classes c
	ON c.class_id = cs.class_id
LEFT JOIN terms t
	ON t.term_id = cs.term_id
LEFT JOIN instructors i
	ON i.instructor_id = cs.instructor_id;
 
 
SELECT c.code, c.name, t.name AS "term",
		COUNT(cr.student_id) AS "enrolled_students", 
        c.maximum_students - COUNT(cr.student_id) AS "space_remaining"
FROM class_sections cs
JOIN classes c
	ON c.class_id = cs.class_id
JOIN terms t
	ON t.term_id = cs.term_id
 JOIN class_registrations cr
 	ON cs.class_section_id = cr.class_section_id
GROUP BY c.code, c.name, t.name, c.maximum_students;
