package com.caesars.ziotodo.api.user

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import sttp.tapir.Schema

case class BanUserReq(id: String)

object BanUserReq {
  implicit val banUserReqCodec: Codec[BanUserReq] = deriveCodec
  implicit val banUserReqSchema: Schema[BanUserReq] = Schema.derived
}
