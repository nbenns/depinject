package com.caesars.ziotodo.domain.model

import com.caesars.ziotodo.domain.ports.output.UserRepository
import zio.*

case class User(
  id: String,
  firstName: String,
  lastName: String,
  banned: Boolean
)
