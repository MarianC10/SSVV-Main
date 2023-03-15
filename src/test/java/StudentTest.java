import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

public class StudentTest {
    private Service service;

    @Before
    public void setUp() {
        var studentValidator = new StudentValidator();
        var assignmentValidator = new TemaValidator();
        var gradeValidator = new NotaValidator();

        var studentRepository = new StudentXMLRepository(studentValidator, "test-students.xml");
        var assignmentRepository = new TemaXMLRepository(assignmentValidator, "test-assignments.xml");
        var gradeRepository = new NotaXMLRepository(gradeValidator, "test-grades.xml");
        service = new Service(studentRepository, assignmentRepository, gradeRepository);
    }

    @Test
    public void tc_1_AddStudentValid() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(0, result);
    }

    @Test
    public void tc_2_AddStudentInvalidGroup() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = -10;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @After
    public void tearDown() {
        service = null;
    }
}
