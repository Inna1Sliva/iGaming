package com.template.domain.repository

import javax.swing.text.PasswordView

interface AuthUserRepository {
  suspend fun getDatabaseUser()
  fun checkUser()


}