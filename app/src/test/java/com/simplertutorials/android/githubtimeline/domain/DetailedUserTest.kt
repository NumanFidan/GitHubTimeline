package com.simplertutorials.android.githubtimeline.domain

import com.simplertutorials.android.githubtimeline.utils.TestUtils
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class DetailedUserTest{

    /*
        compare two identical DetailedUser
        they are equal
        return true
     */
    @Test
    fun isEqual_twoIdentical_returnTrue(){
        var detailedUser = DetailedUser(TestUtils.detailedUser_one)
        var detailedUser2= DetailedUser(TestUtils.detailedUser_one)
        assertEquals(true, detailedUser.equals(detailedUser2) )
    }


    /*
        compare two DetailedUser with different Username
        they are not equal
        return false
     */
    @Test
    fun isEqual_twoDifferentUserName_returnFalse(){
        var detailedUser = DetailedUser(TestUtils.detailedUser_one)
        detailedUser.userName = "detailedUser1"
        var detailedUser2= DetailedUser(TestUtils.detailedUser_one)
        detailedUser.userName = "detailedUser1"

        assertEquals(false, detailedUser.equals(detailedUser2) )
    }

    /*
        compare two DetailedUser with same Username but different Location
        they are equal (usernames are unique at API)
        return true
     */
    @Test
    fun isEqual_sameUserNameDifferentLocation_returnTrue(){
        var detailedUser = DetailedUser(TestUtils.detailedUser_one)
        detailedUser.location ="London"

        val detailedUser2 =DetailedUser(TestUtils.detailedUser_one)
        detailedUser2.location = "Berlin"

        assertEquals(true, detailedUser.equals(detailedUser2) )
    }
    /*
        compare two DetailedUser one with userName one with no empty username
        they are not equal
        return false
     */
    @Test
    fun isEqual_oneWithUserNameOtherWithoutUserName_returnFalse(){
        var detailedUser = DetailedUser(TestUtils.detailedUser_one)
        detailedUser.userName ="foo"

        val detailedUser2 =DetailedUser(TestUtils.detailedUser_one)
        detailedUser2.userName = ""

        assertEquals(false, detailedUser.equals(detailedUser2) )
    }
}