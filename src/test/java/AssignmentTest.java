import domain.Tema;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AssignmentTest {
    private TemaXMLRepository assignmentRepository;
    private Service service;
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

    @Before
    public void setUp() {
        var studentValidator = new StudentValidator();
        var assignmentValidator = new TemaValidator();
        var gradeValidator = new NotaValidator();

        var file = new File("test-assignments.xml");
        FileOutputStream fooStream;
        try {
            fooStream = new FileOutputStream(file, false);

            byte[] myBytes = assignmentContents.getBytes();
            fooStream.write(myBytes);
            fooStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        var studentRepository = new StudentXMLRepository(studentValidator, "test-students.xml");
        assignmentRepository = new TemaXMLRepository(assignmentValidator, "test-assignments.xml");
        var gradeRepository = new NotaXMLRepository(gradeValidator, "test-grades.xml");
        service = new Service(studentRepository, assignmentRepository, gradeRepository);
    }

    @Test
    public void tc_1_Service_Path2() {
        // Arrange
        var id = "10";
        var description = "assignment";
        var deadline = 3;
        var startWeek = 5;

        // Act
        var result = service.saveTema(id, description, deadline, startWeek);

        // Assert
        Assert.assertEquals(0, result);
    }

    @Test
    public void tc_2_Service_Path1() {
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
    public void tc_3_Repository_Path1DoNotPersist() {
        // Arrange
        var id = "10";
        var description = "assignment";
        var deadline = 3;
        var startWeek = 5;
        var assignment = new Tema(id, description, deadline, startWeek);

        // Act
        var result = assignmentRepository.save(assignment);

        // Assert
        Assert.assertEquals(assignment, result);
    }

    @Test
    public void tc_4_Repository_Path2Persist() {
        // Arrange
        var id = "10";
        var description = "assignment";
        var deadline = 10;
        var startWeek = 5;
        var assignment = new Tema(id, description, deadline, startWeek);

        // Act
        var result = assignmentRepository.save(assignment);

        // Assert
        Assert.assertNull(result);
    }

    @After
    public void tearDown() {
        service = null;
    }
}
