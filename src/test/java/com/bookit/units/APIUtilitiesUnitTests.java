package com.bookit.units;

import com.bookit.utilities.APIUtilities;
import com.bookit.utilities.Environment;
import org.junit.Assert;
import org.junit.Test;

public class APIUtilitiesUnitTests {

    @Test
    public void getTokenTest(){
        String token = APIUtilities.getToken();
        String tokenForStudent = APIUtilities.getToken("student");
        String tokenForTeacher = APIUtilities.getToken("teacher");

        Assert.assertNotNull(token);
        Assert.assertNotNull(tokenForStudent);
        Assert.assertNotNull(tokenForTeacher);
    }

    @Test
    public void testIfUserExists(){
        int actual = APIUtilities.getUserId("thereisnoemaillikethis@email.com", "123123");
        Assert.assertEquals(-1, actual);//negative test

        int actual2 = APIUtilities.getUserId(Environment.MEMBER_USERNAME, Environment.MEMBER_PASSWORD);
        //positive test
        Assert.assertTrue(actual2 > 0);//if ID is positive - user exists indeed, otherwise it return -1
    }
}
