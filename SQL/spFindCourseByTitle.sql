DELIMITER $$

DROP PROCEDURE IF EXISTS spFindCourseByTitle $$

CREATE PROCEDURE spFindCourseByTitle (
	IN courseTitle varchar(25)
)
BEGIN
    SELECT id, title
    FROM Course
    WHERE Course.title = courseTitle;
END $$

DELIMITER ;