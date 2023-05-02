import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IncrementalIntegrationTest {

    private Service service;

    @Mock
    private StudentXMLRepository studentXMLRepository;

    @Mock
    private TemaXMLRepository assignmentXMLRepository;

    private NotaXMLRepository gradeXMLRepository;

    @Before
    public void setUp(){
        clearTestData();
        var gradeValidator = new NotaValidator();
        // Create mock object for student repository
        studentXMLRepository = mock(StudentXMLRepository.class);
        // Create mock object for assignment repository
        assignmentXMLRepository = mock(TemaXMLRepository.class);
        gradeXMLRepository = new NotaXMLRepository(gradeValidator, "test-grades.xml");
        service = new Service(studentXMLRepository, assignmentXMLRepository, gradeXMLRepository);
    }

    @Test
    public void tc_1_AddValidStudent(){
        // Arrange
        var id = "10";
        var name= "name";
        var group = 111;

        Student student = new Student(id, name, group);
        when(studentXMLRepository.save(student)).thenReturn(null);

        // Act
        var result = service.saveStudent(id, name, group);

        // Assert
        Assert.assertEquals(1, result);
    }

    @Test
    public void tc_2_AddValidStudentAndAssignment() {
        // Arrange
        var studId = "10";
        var name= "name";
        var group = 111;

        Student student = new Student(studId, name, group);
        when(studentXMLRepository.save(student)).thenReturn(null);

        var assignmentId = "10";
        var description = "assignment";
        var deadline = 10;
        var startWeek = 5;

        Tema assignment = new Tema(assignmentId, description, deadline, startWeek);
        when(assignmentXMLRepository.save(assignment)).thenReturn(null);

        // Act
        var result1 = service.saveStudent(studId, name, group);
        var result2 = service.saveTema(assignmentId, description, deadline, startWeek);

        // Assert
        Assert.assertEquals(1, result1);
        Assert.assertEquals(1, result2);
    }

    @Test
    public void tc_3_AddValidEntities() {
        // Arrange
        var studId = "10";
        var name= "name";
        var group = 111;

        Student student = new Student(studId, name, group);
        when(studentXMLRepository.save(student)).thenReturn(null);
        when(studentXMLRepository.findOne(studId)).thenReturn(student);

        var assignmentId = "10";
        var description = "assignment";
        var deadline = 10;
        var startWeek = 5;

        Tema assignment = new Tema(assignmentId, description, deadline, startWeek);
        when(assignmentXMLRepository.save(assignment)).thenReturn(null);
        when(assignmentXMLRepository.findOne(assignmentId)).thenReturn(assignment);

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

    @After
    public void tearDown(){
        clearTestData();
    }

    private void clearTestData(){
        // Clear file with test data
        try {
            PrintWriter writer = new PrintWriter("test-grades.xml");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Entitati>\n" +
                    "</Entitati>");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
