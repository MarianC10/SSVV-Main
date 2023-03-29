import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

public class AssignmentTest {
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
    public void tc_1_Path1True() {
        // Arrange
        var id = "10";
        var description = "assignment";
        var deadline = 3;
        var startWeek = 5;

        // Act
        var result = service.saveTema(id, description, deadline, startWeek);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void tc_2_Path2False() {
        // Arrange
        var id = "10";
        var description = "assignment";
        var deadline = 10;
        var startWeek = 5;

        // Act
        var result = service.saveTema(id, description, deadline, startWeek);

        // Assert
        Assert.assertEquals(0, result);
    }

    @After
    public void tearDown() {
        service = null;
    }
}
