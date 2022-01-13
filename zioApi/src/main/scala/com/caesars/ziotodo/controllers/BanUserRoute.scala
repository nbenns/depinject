package com.caesars.ziotodo.controllers

import com.caesars.ziotodo.api.user.model.{ApiError, UserRes}
import com.caesars.ziotodo.domain.ports.input.UserCommands
import sttp.tapir.Endpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio._

class BanUserRoute {
  def toRoute(endpoint: Endpoint[Unit, String, ApiError, UserRes, Any]) = {
    ZioHttpInterpreter[Has[UserCommands]]().toHttp(endpoint.serverLogic { (userId: String) =>
      UserCommands
        .banUser(userId)
        .map(user => UserRes(user.id, user.firstName, user.lastName))
        .map(Right.apply)
        .catchSome { case _: Throwable => ZIO.left(ApiError("blah")) }
    })
  }
}
