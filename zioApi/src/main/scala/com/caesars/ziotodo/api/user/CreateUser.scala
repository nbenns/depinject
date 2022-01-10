package com.caesars.ziotodo.api.user

import com.caesars.ziotodo.api.user.model.{ApiError, NewUserReq, UserRes}
import sttp.model.StatusCode
import sttp.tapir._
import sttp.tapir.json.circe.jsonBody

object CreateUser {
  val route: Endpoint[Unit, NewUserReq, ApiError, UserRes, Any] =
    endpoint
      .description("Create a new user")
      .post
      .in("todo" / "user")
      .in(jsonBody[NewUserReq])
      .out(statusCode(StatusCode.Created))
      .out(jsonBody[UserRes])
      .errorOut(jsonBody[ApiError])
}
