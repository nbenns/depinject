package com.caesars.ziotodo.adapters.input

import com.caesars.ziotodo.api.user.model.{ApiError, NewUserReq, UserRes}
import com.caesars.ziotodo.domain.TodoService
import com.caesars.ziotodo.domain.ports.input.UserCommands
import org.http4s.HttpRoutes
import sttp.tapir.Endpoint
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.server.http4s.Http4sServerInterpreter
import zio.{Task, UIO, URIO}
import zio.interop.catz._
import zio.interop.catz.implicits.rts

class CreateUserHttp4sRoute(todoService: TodoService) {
  def toRoute(endpoint: Endpoint[Unit, NewUserReq, ApiError, UserRes, Any]): HttpRoutes[Task] =
    Http4sServerInterpreter[Task]().toRoutes(endpoint.serverLogic { newUserReq =>
      userCommands
        .createUser(newUserReq.firstName, newUserReq.lastName)
        .map(user => UserRes(user.id, user.firstName, user.lastName))
        .map(Right.apply)
    })
}
