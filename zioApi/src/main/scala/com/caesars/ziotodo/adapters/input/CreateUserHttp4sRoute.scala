package com.caesars.ziotodo.adapters.input

import com.caesars.ziotodo.api.user.model.{ApiError, NewUserReq, UserRes}
import com.caesars.ziotodo.domain.TodoService
import com.caesars.ziotodo.domain.ports.input.UserCommands
import com.caesars.ziotodo.domain.ports.output.UserRepository
import sttp.tapir.Endpoint
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.ziohttp._
import zio.blocking.Blocking
import zio.clock.Clock
import zio.{Has, Task, UIO, URIO, ZIO}


class CreateUserHttp4sRoute {
  def meh(endpoint: Endpoint[Unit, NewUserReq, ApiError, UserRes, Any]) =
    endpoint.serverLogic { (newUserReq: NewUserReq) =>
      TodoService
        .addNewUser(newUserReq.firstName, newUserReq.lastName)
        .map(user => UserRes(user.id, user.firstName, user.lastName))
        .map(Right.apply)
        .catchSome { case _: Throwable => ZIO.left(ApiError("blah")) }
    }

  def toRoute(endpoint: Endpoint[Unit, NewUserReq, ApiError, UserRes, Any]) =
    ZioHttpInterpreter[Has[TodoService]]().toHttp(meh(endpoint))
}
