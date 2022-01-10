package com.caesars.ziotodo.api.user

import com.caesars.ziotodo.api.user.model.{ApiError, UserRes}
import sttp.tapir._
import sttp.model._
import sttp.tapir.json.circe._

object BanUser {
  val route: Endpoint[Unit, String, ApiError, UserRes, Any] =
    endpoint
      .description("Ban a user")
      .in("todo" / "user" / path[String]("userId") / "ban")
      .out(statusCode(StatusCode.Ok))
      .out(jsonBody[UserRes])
      .errorOut(jsonBody[ApiError])
}
