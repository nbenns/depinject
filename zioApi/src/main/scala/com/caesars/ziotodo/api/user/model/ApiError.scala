package com.caesars.ziotodo.api.user.model

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import sttp.tapir.Schema

case class ApiError(message: String)

object ApiError {
  implicit val apiErrorCodec: Codec[ApiError] = deriveCodec
  implicit val apiErrorSchema: Schema[ApiError] = Schema.derived
}
