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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class IntegrationTest {
    private Service service;
    private final String studentContents ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
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

    private final String assignmentContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<Entitati>\n" +
            "    <tema ID=\"1\">\n" +
            "        <Descriere>File</Descriere>\n" +
            "        <Deadline>7</Deadline>\n" +
            "        <Startline>6</Startline>\n" +
            "    </tema>\n" +
            "    <tema ID=\"2\">\n" +
            "        <Descriere>XML</Descriere>\n" +
            "        <Deadline>8</Deadline>\n" +
            "        <Startline>7</Startline>\n" +
            "    </tema>\n" +
            "    <tema ID=\"3\">\n" +
            "        <Descriere>gui</Descriere>\n" +
            "        <Deadline>9</Deadline>\n" +
            "        <Startline>7</Startline>\n" +
            "    </tema>\n" +
            "    <tema ID=\"4\">\n" +
            "        <Descriere>desc</Descriere>\n" +
            "        <Deadline>5</Deadline>\n" +
            "        <Startline>3</Startline>\n" +
            "    </tema>\n" +
            "    <tema ID=\"5\">\n" +
            "        <Descriere>tema</Descriere>\n" +
            "        <Deadline>10</Deadline>\n" +
            "        <Startline>5</Startline>\n" +
            "    </tema>\n" +
            "    <tema ID=\"6\">\n" +
            "        <Descriere>tema</Descriere>\n" +
            "        <Deadline>5</Deadline>\n" +
            "        <Startline>5</Startline>\n" +
            "    </tema>\n" +
            "</Entitati>\n";

    private final String gradeContents = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
            "<Entitati>\n" +
            "    <nota IDStudent=\"1\" IDTema=\"1\">\n" +
            "        <Nota>10.0</Nota>\n" +
            "        <SaptamanaPredare>7</SaptamanaPredare>\n" +
            "        <Feedback>done</Feedback>\n" +
            "    </nota>\n" +
            "    <nota IDStudent=\"1\" IDTema=\"2\">\n" +
            "        <Nota>7.5</Nota>\n" +
            "        <SaptamanaPredare>9</SaptamanaPredare>\n" +
            "        <Feedback>done</Feedback>\n" +
            "    </nota>\n" +
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

        file = new File("test-assignments.xml");
        try {
            fooStream = new FileOutputStream(file, false);

            byte[] myBytes = assignmentContents.getBytes();
            fooStream.write(myBytes);
            fooStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File("test-grades.xml");
        try {
            fooStream = new FileOutputStream(file, false);

            byte[] myBytes = gradeContents.getBytes();
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
    public void tc_1_AddValidStudent() {
        // Arrange
        var id = "10";
        var name= "name";
        var group = 111;

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void tc_2_AddValidAssignment() {
        // Arrange
        var id = "10";
        var description = "assignment";
        var deadline = 10;
        var startWeek = 5;

        // Act
        var result = service.saveTema(id, description, deadline, startWeek);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void tc_3_AddValidGrade() {
        // Arrange
        var studId = "1";
        var assignmentId = "3";
        var grade = 5;
        var turnInWeek = 8;
        var feedback = "feedback";

        // Act
        var result = service.saveNota(studId, assignmentId, grade, turnInWeek, feedback);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void tc_4_AddValidEntities() {
        // Arrange
        var studId = "10";
        var name= "name";
        var group = 111;

        var assignmentId = "10";
        var description = "assignment";
        var deadline = 10;
        var startWeek = 5;

        var grade = 5;
        var turnInWeek = 8;
        var feedback = "feedback";

        // Act
        var result1 = service.saveStudent(studId, name, group);
        var result2 = service.saveTema(assignmentId, description, deadline, startWeek);
        var result3 = service.saveNota(studId, assignmentId, grade, turnInWeek, feedback);

        // Assert
        Assert.assertEquals(1, result1);
        Assert.assertEquals(1, result2);
        Assert.assertEquals(1, result3);
    }
}
