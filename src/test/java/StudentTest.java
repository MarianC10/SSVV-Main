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

    @Test
    public void ec_1_AddStudentUniqueId() {
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
    public void ec_2_AddStudentNullId() {
        // Arrange
        String id = null;
        var name= "name";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void ec_3_AddStudentNullName() {
        // Arrange
        var id = "10";
        String name = null;
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void ec_4_AddStudentOutOfBoundsGroup() {
        // Arrange
        var id = "10";
        var name = "name";
        int group = 100;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void ec_5_AddStudentOutOfBoundsGroup() {
        // Arrange
        var id = "10";
        var name = "name";
        int group = 940;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void ec_6_AddStudentInBoundsGroup() {
        // Arrange
        var id = "10";
        var name = "name";
        int group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(0, result);
    }

    @Test
    public void ec_7_AddStudentInBoundsGroup() {
        // Arrange
        var id = "10";
        var name = "name";
        int group = 920;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(0, result);
    }

    @Test
    public void ec_8_AddStudentEmptyName() {
        // Arrange
        var id = "10";
        var name = "";
        int group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void ec_9_AddStudentEmptyId() {
        // Arrange
        var id = "";
        var name = "name";
        int group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_1_AddStudentInvalidGroup() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = 110;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_2_AddStudentValidGroup() {
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
    public void bva_3_AddStudentValidGroup() {
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
    public void bva_4_AddStudentValidGroup() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = 936;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(0, result);
    }

    @Test
    public void bva_5_AddStudentValidGroup() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = 937;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(0, result);
    }

    @Test
    public void bva_6_AddStudentInvalidGroup() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = 938;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_7_AddStudentInvalidId() {
        // Arrange
        String id = null;
        var name= "name";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_8_AddStudentInvalidId() {
        // Arrange
        var id = "";
        var name= "name";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_9_AddStudentValidId() {
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
    public void bva_10_AddStudentInvalidName() {
        // Arrange
        var id = "10";
        String name = null;
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_11_AddStudentInvalidName() {
        // Arrange
        var id = "10";
        var name = "";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void bva_12_AddStudentValidName() {
        // Arrange
        var id = "10";
        var name = "test";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(0, result);
    }

    @After
    public void tearDown() {
        service = null;
    }
}
