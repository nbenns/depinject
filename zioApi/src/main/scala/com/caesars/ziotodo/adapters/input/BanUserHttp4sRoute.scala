package com.caesars.ziotodo.adapters.input

import com.caesars.ziotodo.api.user.model.{ApiError, UserRes}
import com.caesars.ziotodo.domain.TodoService
import sttp.tapir.Endpoint
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio._

class BanUserHttp4sRoute {
  def toRoute(endpoint: Endpoint[Unit, String, ApiError, UserRes, Any]) = {
    ZioHttpInterpreter[Has[TodoService]]().toHttp(endpoint.serverLogic { (userId: String) =>
      TodoService
        .banUser(userId)
        .map(user => UserRes(user.id, user.firstName, user.lastName))
        .map(Right.apply)
        .catchSome { case _: Throwable => ZIO.left(ApiError("blah")) }
    })
  }
}
