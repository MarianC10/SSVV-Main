import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.*;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StudentTest {
    private Service service;
    private String studentContents ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<Entitati>\n" +
            "    <student ID=\"1\">\n" +
            "        <Nume>numenou</Nume>\n" +
            "        <Grupa>222</Grupa>\n" +
            "    </student>\n" +
            "    <student ID=\"2\">\n" +
            "        <Nume>Maria</Nume>\n" +
            "        <Grupa>222</Grupa>\n" +
            "    </student>\n" +
            "    <student ID=\"3\">\n" +
            "        <Nume>John</Nume>\n" +
            "        <Grupa>231</Grupa>\n" +
            "    </student>\n" +
            "    <student ID=\"4\">\n" +
            "        <Nume>Ion</Nume>\n" +
            "        <Grupa>227</Grupa>\n" +
            "    </student>\n" +
            "    <student ID=\"5\">\n" +
            "        <Nume>student</Nume>\n" +
            "        <Grupa>932</Grupa>\n" +
            "    </student>\n" +
            "</Entitati>\n";

    @Before
    public void setUp() {
        var studentValidator = new StudentValidator();
        var assignmentValidator = new TemaValidator();
        var gradeValidator = new NotaValidator();

        var file = new File("test-students.xml");
        FileOutputStream fooStream;
        try {
            fooStream = new FileOutputStream(file, false);

            byte[] myBytes = studentContents.getBytes();
            fooStream.write(myBytes);
            fooStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        var id = "11";
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
        var id = "12";
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
        var id = "13";
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
        var id = "14";
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
        var id = "15";
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
        var id = "100";
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
        var id = "100";
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
        var id = "16";
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
