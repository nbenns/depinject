package com.caesars.ziotodo.api.user.model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import sttp.tapir.Schema

case class NewUserReq(firstName: String, lastName: String)

object NewUserReq {
  implicit val newUserCodec: Codec[NewUserReq] = deriveCodec
  implicit val newUserSchema: Schema[NewUserReq] = Schema.derived
}
