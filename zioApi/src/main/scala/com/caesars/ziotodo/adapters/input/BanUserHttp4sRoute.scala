package com.caesars.ziotodo.adapters.input

import com.caesars.ziotodo.api.user.model.{ApiError, NewUserReq, UserRes}
import com.caesars.ziotodo.domain.ports.input.UserCommands
import org.http4s.HttpRoutes
import sttp.tapir.Endpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import zio.Task
import zio.interop.catz._
import zio.interop.catz.implicits.rts

class BanUserHttp4sRoute(userCommands: UserCommands) {
  def toRoute(endpoint: Endpoint[Unit, String, ApiError, UserRes, Any]): HttpRoutes[Task] =
    Http4sServerInterpreter[Task]().toRoutes(endpoint.serverLogic { userId =>
      userCommands
        .banUser(userId)
        .map(user => UserRes(user.id, user.firstName, user.lastName))
        .map(Right.apply)
    })
}
