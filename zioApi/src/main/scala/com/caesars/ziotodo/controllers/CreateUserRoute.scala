package com.caesars.ziotodo.controllers

import com.caesars.ziotodo.api.user.model.{ApiError, NewUserReq, UserRes}
import com.caesars.ziotodo.domain.ports.input.{TodoCommands, UserCommands}
import sttp.tapir.Endpoint
import sttp.tapir.server.ziohttp._
import zio.{Has, ZIO}

class CreateUserRoute {
  def toRoute(endpoint: Endpoint[Unit, NewUserReq, ApiError, UserRes, Any]) =
    ZioHttpInterpreter[Has[UserCommands]]().toHttp(endpoint.serverLogic { (newUserReq: NewUserReq) =>
      UserCommands
        .addNewUser(newUserReq.firstName, newUserReq.lastName)
        .map(user => UserRes(user.id, user.firstName, user.lastName))
        .map(Right.apply)
        .catchSome { case _: Throwable => ZIO.left(ApiError("blah")) }
    })
}
