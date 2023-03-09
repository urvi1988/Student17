package com.studentapp.cucumber.steps;

import com.studentapp.studentinfo.StudentSteps;
import com.studentapp.utils.TestUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

public class MyStepdefs {
    static ValidatableResponse response;
    static String email = null;
    static String firstName = null;

    static String lastName=null;
    static String courses=null;
    static int studentId;

    @Steps
    StudentSteps studentSteps;

    @When("^User sends a GET request to list endpoint$")
    public void userSendsAGETRequestToListEndpoint() {
        response = studentSteps.getAllStudentInfo();
    }

    @Then("^User must get back a valid status code (\\d+)$")
    public void userMustGetBackAValidStatusCode(int statuscode) {
        response.statusCode(statuscode);
    }

    @When("^I create a new student by providing the information firstName \"([^\"]*)\" lastName \"([^\"]*)\" email \"([^\"]*)\" programme \"([^\"]*)\" courses \"([^\"]*)\"$")
    public void iCreateANewStudentByProvidingTheInformationFirstNameLastNameEmailProgrammeCourses(String _firstname, String lastname, String _email, String programme, String courses) {
        List<String> coursesList = new ArrayList<>();
        coursesList.add(courses);
        email = TestUtils.getRandomValue() + _email;
        firstName = TestUtils.getRandomValue() + _firstname;
        response = studentSteps.createStudent(firstName, lastname, email, programme, coursesList);
    }
    @Then("^I verify that the student with \"([^\"]*)\" is created$")
    public void iVerifyThatTheStudentWithIsCreated(String UpdatedEmail) {
        response.statusCode(201);

        if (UpdatedEmail.contains("@gmail.com")) {
            HashMap<String, Object> map = studentSteps.getStudentInfoByEmail(email);
            studentId = (int) map.get("id");
            Assert.assertThat(map, hasValue(email));
        } else {
            HashMap<String, Object> map1 = studentSteps.getStudentInfoByFirstName(firstName);
            studentId = (int) map1.get("id");
            Assert.assertThat(map1, hasValue(firstName));
        }
    }
    @When("^new student updated with \"([^\"]*)\"$")
    public void newStudentUpdatedWith(String _firstName){
        firstName=TestUtils.getRandomValue()+_firstName;
        response=studentSteps.updateStudent(studentId,firstName,lastName,email,firstName, Collections.singletonList(courses));
    }

    @When("^I have deleted student id$")
    public void iHaveDeletedStudentId() {
        response=studentSteps.deleteStudentInfoByID(studentId);
    }

    @Then("^I verify that student id is deleted$")
    public void iVerifyThatStudentIdIsDeleted() {
        response=studentSteps.getStudentInfoByStudentId(studentId);
    }
}



