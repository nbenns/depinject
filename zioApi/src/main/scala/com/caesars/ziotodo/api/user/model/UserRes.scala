package com.caesars.ziotodo.api.user.model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import sttp.tapir.Schema

case class UserRes(id: String, firstName: String, lastName: String)

object UserRes {
  implicit val userResCodec: Codec[UserRes] = deriveCodec
  implicit val userResSchema: Schema[UserRes] = Schema.derived
}
