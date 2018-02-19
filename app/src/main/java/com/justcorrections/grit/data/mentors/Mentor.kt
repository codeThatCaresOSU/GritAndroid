package com.justcorrections.grit.data.mentors

/**
 * Created by ianwillis on 2/19/18.
 */

data class Mentor(var id: String = "",
                  val address: String = "",
                  val age: String = "",
                  val bio: String = "",
                  val city: String = "",
                  val email: String = "",
                  val firstName: String = "",
                  val gender: String = "",
                  val interests:Object = Object(),
                  val lastName: String = "",
                  val password: String = "",
                  val zip: String = "")
